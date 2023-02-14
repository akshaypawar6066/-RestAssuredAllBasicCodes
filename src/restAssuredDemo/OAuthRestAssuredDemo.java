package restAssuredDemo;

import static io.restassured.RestAssured.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;

import files.ReusableMethods;
import io.restassured.parsing.Parser;
import io.restassured.path.json.JsonPath;
import pojo.Api;
import pojo.GetCourse;
import pojo.WebAutomation;

public class OAuthRestAssuredDemo {
	public static void main(String[] args) throws InterruptedException {

		String[] coureTitles = { "Selenium Webdriver Java", "Cypress", "Protractor" };

		// Google not allowing to automate Authentication part through any testing
		// framework.

//		System.setProperty("webdriver.chrome.driver",
//				"C:/Users/aksha/Downloads/chromedriver_win32 (3)/chromedriver.exe");
//		WebDriver driver = new ChromeDriver();
//		driver.manage().timeouts().implicitlyWait(10,TimeUnit.SECONDS);
//		driver.get(
//				"https://accounts.google.com/o/oauth2/v2/auth?scope=https://www.googleapis.com/auth/userinfo.email&auth_url=https://accounts.google.com/o/oauth2/v2/auth&client_id=692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com&response_type=code&redirect_uri=https://rahulshettyacademy.com/getCourse.php&state=verifyfjdss");
//		driver.findElement(By.xpath("//input[@type='email']")).sendKeys("akshaypawar6066@gmail.com");
//		driver.findElement(By.xpath("//span[text()='Next']")).click();
//		Thread.sleep(5000);
//		driver.findElement(By.xpath("//input[@type='password']")).sendKeys("akshay6066");
//		driver.findElement(By.xpath("//span[text()='Next']")).click();
//		Thread.sleep(8000);
//		String browserURL = driver.getCurrentUrl();
		// Out of whole browserURL String we need to parse(get) only code value.
		String browserURL = "https://rahulshettyacademy.com/getCourse.php?state=verifyfjdss&code=4%2F0AWtgzh4r6V6KgVTyqiJmsJdjxvSObZsaCEGgwVSuqCu3VvCzG64iBoZbdhQmUEMYrY8xHg&scope=email+openid+https%3A%2F%2Fwww.googleapis.com%2Fauth%2Fuserinfo.email&authuser=0&prompt=none";
		String partialCode = browserURL.split("code=")[1];
		String AuthCode = partialCode.split("&scope")[0];
		System.out.println("Authorization code is:" + AuthCode);
		String accessTokenResponse = given().log().all().urlEncodingEnabled(false).queryParams("code", AuthCode)
				.queryParams("client_id", "692183103107-p0m7ent2hk7suguv4vq22hjcfhcr43pj.apps.googleusercontent.com")
				.queryParams("client_secret", "erZOWM9g3UtwNRj340YYaK_W")
				.queryParams("redirect_uri", "https://rahulshettyacademy.com/getCourse.php")
				.queryParams("grant_type", "authorization_code").when()
				.post("https://www.googleapis.com/oauth2/v4/token").asString();
		JsonPath js = ReusableMethods.rawToJson(accessTokenResponse);
		String accessToken = js.getString("access_token");
		System.out.println("Acees token is:" + accessToken);

		GetCourse getCourse = given().log().all().queryParam("access_token", accessToken).expect()
				.defaultParser(Parser.JSON).when().get("https://rahulshettyacademy.com/getCourse.php")
				.as(GetCourse.class);
		// System.out.println("response of getCourses is:"+getCourse);
		System.out.println("LinkedIn Url is:" + getCourse.getLinkedIn());
		System.out.println("Instructor name is:" + getCourse.getInstructor());

		// Que->Get the price of the Soap UI Testing course

		// System.out.println("SOAP UI Course title
		// is:"+getCourse.getCourses().getApi().get(1).getCourseTitle()); //Dynamically
		// how to get price for this course???
		// getCourse.getCourses().getApi().get(1).getPrice(); //But every time SOAP UI
		// Course will not on first index.
		List<Api> apiCourses = getCourse.getCourses().getApi();
		String price = "";
		System.out.println("API corses are:" + apiCourses); // It will return the address of API courses List.

		for (int i = 0; i < apiCourses.size(); i++) {
			if (apiCourses.get(i).getCourseTitle().equalsIgnoreCase("SoapUI Webservices testing")) {
				price = apiCourses.get(i).getPrice();
			}
		}
		System.out.println("Price for SoapUI Webservises testing course is:" + price);

		// Que->Print the title of all the courses under WebAutomation Type

		List<WebAutomation> autoCourses = getCourse.getCourses().getWebAutomation();
		for (int i = 0; i < autoCourses.size(); i++) {
			String courseTitle = autoCourses.get(i).getCourseTitle();
			System.out.println(courseTitle);

		}

		// Verify Whatever courses we added under WebAutomation are present in the
		// response.

		ArrayList<String> a = new ArrayList<String>();

		for (int i = 0; i < autoCourses.size(); i++) {
			a.add(autoCourses.get(i).getCourseTitle());

		}

		// Covert array into ArrayList
		List<String> expectedList = Arrays.asList(coureTitles);
		System.out.println(coureTitles);
		Assert.assertTrue(a.equals(expectedList));
		System.out.println("All Tests are passed:");
		System.out.println(expectedList);
		System.out.println(a);

	}

}
