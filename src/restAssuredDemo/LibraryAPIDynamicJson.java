package restAssuredDemo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

public class LibraryAPIDynamicJson {
	@Test(dataProvider="BooksData")
	public void addBook(String isbn, String aisle) {
		RestAssured.baseURI = "http://216.10.245.166";

		// Adding the book
		String addBookResponse = given().log().all().header("Content-Type", "application/json")
				.body(Payload.addBookPayload(isbn, aisle)).when().post("/Library/Addbook.php").then().log().all()
				.assertThat().statusCode(200).extract().response().asString();
		System.out.println("Before parse to json response of add book:" + addBookResponse);
		JsonPath js = ReusableMethods.rawToJson(addBookResponse);
		String bookId = js.get("ID");
		System.out.println("Id for the book is:" + bookId);

		// Deleting the book

		String delete_book_Response = given().log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"ID\":\"" + bookId + "\"\r\n" + "}").when().post("/Library/DeleteBook.php").then()
				.log().all().assertThat().statusCode(200).extract().response().asString();

		System.out.println("Before parsing delete response is:" + delete_book_Response);
		JsonPath js1 = ReusableMethods.rawToJson(delete_book_Response);
		String delete_message = js1.get("msg");
		System.out.println("After parsing deleted message is:" + delete_message);
	}
	@DataProvider(name="BooksData")
	public Object[][] getData()
	{
		//Array-Collection of elements
		//Multidimensional Array-Collection of arrays
		return new Object[][] {{"shghs","256"},{"shgvhs","246"},{"hscsh","646"}};
	}

}
