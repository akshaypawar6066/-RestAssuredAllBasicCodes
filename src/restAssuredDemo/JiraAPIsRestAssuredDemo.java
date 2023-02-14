package restAssuredDemo;

import static io.restassured.RestAssured.*;

import java.io.File;

import org.testng.Assert;

import files.ReusableMethods;
import io.restassured.RestAssured;
import io.restassured.filter.session.SessionFilter;
import io.restassured.path.json.JsonPath;

public class JiraAPIsRestAssuredDemo {
	public static void main(String[] args) {
		RestAssured.baseURI = "http://localhost:8085";
		// Hit Login API and get the session id to pass it in all the further requests.

		SessionFilter session = new SessionFilter();
		String sessionRes = given().relaxedHTTPSValidation().log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"username\": \"akshaypawar6066\",\r\n" + "    \"password\": \"akshay6066\"\r\n"
						+ "}")
				.filter(session).when().post("/rest/auth/1/session").then().log().all().assertThat().statusCode(200)
				.extract().response().asString();

		// This can used when we don't use /*Session filter/*
//		JsonPath js = ReusableMethods.rawToJson(sessionRes);
//		String sName = js.get("session.name");
//		String sValue = js.get("session.value");
//		String SessionID = sName + "=" + sValue;
//		System.out.println("SessionId is:" + SessionID);
		// Create New Issue

		String Post_AddIssueResponse = given().log().all().header("Content-Type", "application/json")
				.body("{\r\n" + "    \"fields\": {\r\n" + "        \"project\": {\r\n"
						+ "            \"key\": \"REST\"\r\n" + "        },\r\n"
						+ "        \"summary\": \"Credit card issue:::\",\r\n"
						+ "        \"description\": \"This is first bug created by me\",\r\n"
						+ "        \"issuetype\": {\r\n" + "            \"name\": \"Bug\"\r\n" + "        }\r\n"
						+ "    }\r\n" + "}")
				.filter(session).when().post("/rest/api/2/issue").then().log().all().assertThat().statusCode(201)
				.extract().response().asString();
		JsonPath js = ReusableMethods.rawToJson(Post_AddIssueResponse);
		String issueId = js.get("id");
		System.out.println("Created issue is is:" + issueId);

		// Add Comment for the created issue.
		String expectedMessage = "Hi, How are you";

		String addCommentResponse = given().log().all().pathParam("id", "10211")
				.header("Content-Type", "application/json")
				.body("{\r\n" + "    \"body\": \"" + expectedMessage + "\",\r\n" + "    \"visibility\": {\r\n"
						+ "        \"type\": \"role\",\r\n" + "        \"value\": \"Administrators\"\r\n" + "    }\r\n"
						+ "}")
				.filter(session).when().post("/rest/api/2/issue/{id}/comment").then().log().all().statusCode(201)
				.extract().response().asString();
		
		js = ReusableMethods.rawToJson(addCommentResponse);
		String commentId = js.get("id");
		System.out.println("Comment id is:" + commentId);

		// Add Attachment for the created issue

		given().log().all().pathParam("id", issueId).header("X-Atlassian-Token", "no-check").filter(session)
				.header("Content-Type", "multipart/form-data").multiPart("file", new File("jira.txt")).when()
				.post("/rest/api/2/issue/{id}/attachments").then().log().all().assertThat().statusCode(403);
		System.out.println("File is Not attached as you do not have permission to attach the file:");

		// Get the created issue

		String issueDetails = given().log().all().pathParam("id", "10211").filter(session)
				.queryParam("fields", "comment").when().get("/rest/api/2/issue/{id}").then().log().all().assertThat()
				.statusCode(200).extract().response().asString();
		System.out.println("Issue details are:" + issueDetails);
		js = ReusableMethods.rawToJson(issueDetails);
		int commentCount = js.getInt("fields.comment.comments.size()");
		for (int i = 0; i < commentCount; i++) {
			String commentIdIssue = js.get("fields.comment.comments[" + i + "].id");
			System.out.println(commentIdIssue);
			if (commentId.equalsIgnoreCase(commentIdIssue)) {
				String message = js.get("fields.comment.comments[" + i + "].body");
				System.out.println("Body message in the response is:" + message);
				Assert.assertEquals(message, expectedMessage);
				// https://accounts.google.com/o/oauth2/auth?redirect_uri=storagerelay%3A%2F%2Fhttps%2Fin.bookmyshow.com%3Fid%3Dauth844995&response_type=permission%20id_token&scope=email%20profile%20openid&openid.realm=&include_granted_scopes=true&client_id=990572338172-iibth2em4l86htv30eg1v44jia37fuo5.apps.googleusercontent.com&ss_domain=https%3A%2F%2Fin.bookmyshow.com&fetch_basic_profile=true&gsiwebsdk=2

			}
		}

	}

}
