package restAssuredDemo;

import org.testng.Assert;
import org.testng.annotations.Test;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class sumValidation {

	@Test
	public void sumOfCourses() {
		
		JsonPath js = new JsonPath(Payload.courseDetails());
		int coursesCount = js.getInt("courses.size()");
		int amount=0;
		for(int i=0;i<coursesCount;i++)
		{
			int price=js.get("courses["+i+"].price");
			int copies=js.get("courses["+i+"].copies");
			amount=amount+(price*copies);
		}
		System.out.println(amount);
		int purchaseAmount=js.getInt("dashboard.purchaseAmount");
		Assert.assertEquals(amount, purchaseAmount);
	}
}
