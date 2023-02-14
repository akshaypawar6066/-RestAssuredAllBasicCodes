package restAssuredDemo;

import io.restassured.RestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;
import pojo.AddPlaceSerialization;
import pojo.Location;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class RequestSpecBuilderDemo {

	public static void main(String[] args) {
		AddPlaceSerialization p = new AddPlaceSerialization();
		p.setAccuracy(50);
		p.setAddress("29,ABC Chowk Near MG Road");
		p.setLanguage("English");
		p.setPhone_number("6564646465");
		p.setName("AKSHAY PAWAR");
		p.setWebsite("abcbooks.com");
		List<String> myList = new ArrayList<String>();
		myList.add("Book Shop");
		myList.add("Market");
		p.setTypes(myList);
		Location location = new Location();
		location.setLat(-30.40520);
		location.setLng(12.35);
		p.setLocation(location);

		RequestSpecification req = new RequestSpecBuilder().setBaseUri("https://rahulshettyacademy.com")
				.addQueryParam("key", "qaclick123").setContentType(ContentType.JSON).build();

		ResponseSpecification res = new ResponseSpecBuilder().expectStatusCode(200).expectContentType(ContentType.JSON)
				.build();

		RequestSpecification request = given().log().all().spec(req).body(p);
		Response response = request.when().post("/maps/api/place/add/json").then().log().all().spec(res).extract()
				.response();

		String responseString = response.asString();
		System.out.println("Response String is:"+responseString);
	}
}
