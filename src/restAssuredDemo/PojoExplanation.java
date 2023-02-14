package restAssuredDemo;
import static io.restassured.RestAssured.*;
public class PojoExplanation {
	private String message;
	private String greet;

	public void setMessage(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}

	public void setGreet(String greet) {
		this.greet = greet;
	}

	public String getGreet() {
		return greet;
	}
	public static void main(String[] args) {
		PojoExplanation p=new PojoExplanation();
		p.setMessage("Hello");
		p.setGreet("Hi");
//		System.out.println(p.getMessage());
//		System.out.println(p.getGreet());

		//Rest Assured will create Json like this in the run time.
		//We need to pass this java object in our body in the RestAssured.
//		{
//			"message":"Hello",
//			"greet":"Hi"
//		}
		
		//given().body(p).when().post("/message").then().assertThat().statusCode(200);
	}
}
