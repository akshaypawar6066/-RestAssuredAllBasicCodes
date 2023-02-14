package restAssuredDemo;

import files.Payload;
import io.restassured.path.json.JsonPath;

public class ComplexJsonParse {
	public static void main(String[] args) {
		JsonPath js = new JsonPath(Payload.courseDetails());
		// No of courses returned by API
		int coursesCount = js.getInt("courses.size()"); // Give no of elements present in the "courses" array
		System.out.println("Number of courses returned by the API are:" + coursesCount);
		// Print Purchase Amount
		int purchaseAmount = js.getInt("dashboard.purchaseAmount");
		System.out.println("Purchase Amount is:" + purchaseAmount);
		// Print title of the first course
		String firstCourseTitle = js.get("courses[0].title"); // get() method by default pull up the string
		String secondCourseTitle = js.getString("courses[1].title");
		String thirdCourseTitle = js.getString("courses[2].title");
//	System.out.println("First course title is:"+firstCourseTitle);
//	System.out.println("Second course title is:"+secondCourseTitle);
//	System.out.println("Third course title is:"+thirdCourseTitle);
		// Print all course titles and their respective prices and copies.

		for (int i = 0; i < coursesCount; i++) {
			String Titles = js.getString("courses[" + i + "].title");
			int prices = js.getInt("courses[" + i + "].price");
			int copies = js.getInt("courses[" + i + "].copies");
			System.out.println(js.getInt("courses[" + i + "].price"));
			System.out.println("Title of the course is:" + Titles + " & price of the course is:" + prices
					+ " & Copies of the course are:" + copies);
			// Print number of copies sold by RPA course
		}
		for (int i = 0; i < coursesCount; i++) {
			String Titles = js.getString("courses[" + i + "].title");
			if (Titles.equalsIgnoreCase("RPA")) {
				// Return copies sold
				int copies = js.getInt("courses[" + i + "].copies");
				System.out.println("Copies sold by RPA course is:" + copies);
				break;
			}
			
		}
	}

}
