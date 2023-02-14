package restAssuredDemo;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.containsString;
import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.Matcher.*;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.Assert;
import org.testng.asserts.Assertion;

import files.Payload;
import files.ReusableMethods;

public class GoogleMapRestAssuredDemo {

	public static void main(String[] args) throws IOException {
		// Validate Add place API is working or not.
		// Given-All the Input required to the API
		// When-You submit the API on particular resource. Resource, http method
		// Then-Validate the Response of the API.
		// Add place-->Update place with new address and get it to verify the place.
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		String response = given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
				.body(new String(Files.readAllBytes(Paths.get("C:\\Users\\aksha\\Downloads\\AddPlace.json")))).when().post("maps/api/place/add/json").then().assertThat().log().all()
				.statusCode(200).body("scope", equalTo("APP")).header("Server", "Apache/2.4.41 (Ubuntu)")
				.body("status", equalTo("OK"),"scope", equalTo("APP")).log().all().extract().response().asString();
		 System.out.println("Response is:" + response);
		 JsonPath js=ReusableMethods.rawToJson(response) ;  //Parse the json
		 String place_id=js.getString("place_id");
		 System.out.println("Place_id in json response is:"+place_id);
		 String scope=js.getString("scope");
		 System.out.println("Scope is:"+scope);
		 
		 String newAdd="XYZ Mars Colony Near Saturn Chowk";
		 
		given().log().all().queryParam("key", "qaclick123").header("Content-Type", "application/json")
		.body("{\r\n"
				+ "    \"place_id\":\""+place_id+"\",\r\n"
				+ "    \"address\":\""+newAdd+"\",\r\n"
				+ "    \"key\":\"qaclick123\"\r\n"
				+ "}").when().put("maps/api/place/update/json").then().log().all().assertThat().statusCode(200)
		.body("msg", equalTo("Address successfully updated"));
	
	String get_Place_Response=	given().log().all().queryParam("key", "qaclick123").queryParam("place_id", place_id)
		.when().get("maps/api/place/get/json").then().log().all().assertThat().statusCode(200)
		.body("address", equalTo("XYZ Mars Colony Near Saturn Chowk")).extract().response().asString();
    JsonPath js1= ReusableMethods.rawToJson(get_Place_Response);
    String addressFromResponse=js1.getString("address");
    Assert.assertEquals(newAdd, addressFromResponse);
    Assert.assertTrue(newAdd.equals(addressFromResponse));
   
  
	}


}
