package restAssuredDemo;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import files.Payload;
import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;

import java.io.IOException;
import java.util.HashMap;

public class RestAssuredExcelDrivenTest {

	
	@Test
	public void addBook() throws IOException
	{
		RestAssured.baseURI = "http://216.10.245.166";

		String add_Book_Response = given().log().all().header("Content-Type", "application/json")
				.body(ExcelDataDrivenDemoData.addBookHashMapPayload()).when().post("/Library/Addbook.php").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();
		JsonPath js = ReusableMethods.rawToJson(add_Book_Response);
		String bookId = js.getString("ID");
		System.out.println("Book Id is:" + bookId);
	}
		
	
		
	

}
