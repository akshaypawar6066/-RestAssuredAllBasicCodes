package pojo;

import io.restassured.RestAssured;
import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.List;

public class SerializationDemo {

	public static void main(String[] args) {
		AddPlaceSerialization p=new AddPlaceSerialization();
		p.setAccuracy(50);
		p.setAddress("29,ABC Chowk Near MG Road");
		p.setLanguage("English");
		p.setPhone_number("6564646465");
		p.setName("AKSHAY PAWAR");
		p.setWebsite("abcbooks.com");
		List<String> myList=new ArrayList<String>();
		myList.add("Book Shop");
		myList.add("Market");
		p.setTypes(myList);
		Location location=new Location();
		location.setLat(-30.40520);
		location.setLng(12.35);
		p.setLocation(location);
		RestAssured.baseURI = "https://rahulshettyacademy.com";
		
		String response = given().log().all().queryParam("key", "qaclick123").body(p)
				.header("Content-Type", "application/json").when().post("/maps/api/place/add/json")
				.then().log().all().assertThat().statusCode(200).extract().response().asString();
		
		
	}
}
