package restAssuredDemo;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.equalTo;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import files.ReusableMethods;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.specification.RequestSpecification;
import pojo.EcommerceLoginAPI;
import pojo.EcommerseLoginResponsePayload;
import pojo.OrderDetails;
import pojo.Orders;
import pojo.DeleteProductResponse;

public class EcommerceAPITest {
	public static void main(String[] args) {
		// Login API

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).build();
		EcommerceLoginAPI login = new EcommerceLoginAPI();
		login.setUserEmail("akshaypawar6066@gmail.com");
		login.setUserPassword("Akshay@6066");
		RequestSpecification request = given().log().all().spec(req).body(login);
		EcommerseLoginResponsePayload response = request.when().post("/api/ecom/auth/login").then().log().all()
				.extract().response().as(EcommerseLoginResponsePayload.class);
		String token = response.getToken();
		String userId = response.getUserId();
		String message = response.getMessage();
		System.out.println("Token is:" + token);
		System.out.println("UserId is:" + userId);
		System.out.println("Response message is:" + message);

		// ***Create Product API***
		// To send body in the form of form-data we need to use param() method.
		// To add attachment in the RestAssured-multipart() method is used.

		RequestSpecification addProductBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addHeader("Authorization", token).build();
		RequestSpecification reqAddProduct = given().log().all().spec(addProductBaseReq)
				.param("productName", "AppleLaptop").param("productAddedBy", userId)
				.param("productCategory", "Electronics").param("productSubCategory", "Laptops")
				.param("productPrice", "200000").param("productDescription", "Apple BestLaptop 8GB RAM")
				.param("productFor", "All").multiPart("productImage",
						new File("C://Users//aksha//OneDrive//Documents//APIDocuments//AppleLaptop.png"));

		String addProductresponse = reqAddProduct.when().post("/api/ecom/product/add-product").then().log().all()
				.extract().response().asString();
		JsonPath js = ReusableMethods.rawToJson(addProductresponse);
		String productId = js.getString("productId");
		System.out.println("ProductId is:" + productId);

		// ****Create Order****
		// relaxedHTTPSValidation()-If our API need any SSL certification then this
		// method will bypass that certification.
		RequestSpecification createOrderBaseReq = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.setContentType(ContentType.JSON).addHeader("Authorization", token).build();
		Orders orders = new Orders();
		OrderDetails orderDetails = new OrderDetails();
		orderDetails.setCountry("India");
		orderDetails.setProductOrderedId(productId);
		List<OrderDetails> orderDeatilsList = new ArrayList<OrderDetails>();
		orderDeatilsList.add(orderDetails);
		orders.setOrderDetails(orderDeatilsList);

		RequestSpecification createOrderReq = given().relaxedHTTPSValidation().log().all().spec(createOrderBaseReq)
				.body(orders);
		String createOrderResponse = createOrderReq.when().post("/api/ecom/order/create-order").then().log().all()
				.extract().response().asString();
		System.out.println("After placing order response is:" + createOrderResponse);

		// Delete the product

		RequestSpecification deleteProductBaseReq = new RequestSpecBuilder()
				.setBaseUri("https://rahulshettyacademy.com").addHeader("Authorization", token)
				.addPathParam("productId", productId).build();
		RequestSpecification deleteRequest = given().log().all().spec(deleteProductBaseReq);
		DeleteProductResponse deleteProductResponse = deleteRequest.when()
				.delete("/api/ecom/product/delete-product/{productId}").then().log().all().extract().response()
				.as(DeleteProductResponse.class);
		String deleteMessage = deleteProductResponse.getMessage();
		System.out.println("After deleting the product message is:" + deleteMessage);

	}
}
