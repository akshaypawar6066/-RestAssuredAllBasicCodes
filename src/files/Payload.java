package files;

public class Payload {
	public static String addPlace()
	{
		return "{\r\n"
				+ "    \"location\": {\r\n"
				+ "        \"lat\": \"-123.25\",\r\n"
				+ "        \"lng\": \"25.25\"\r\n"
				+ "    },\r\n"
				+ "    \"accuracy\": 50,\r\n"
				+ "    \"name\": \"AKSHAY PAWAR\",\r\n"
				+ "    \"phone_number\": \"4664655466\",\r\n"
				+ "    \"address\": \"29,ABC Chowk Near MG Road\",\r\n"
				+ "    \"types\": [\r\n"
				+ "        \"Book Shop\",\r\n"
				+ "        \"Market\"\r\n"
				+ "    ],\r\n"
				+ "    \"website\": \"abcbooks.com\",\r\n"
				+ "    \"language\": \"English\"\r\n"
				+ "}";
		
	}
	public static String courseDetails()
	{
		return "{\r\n"
				+ "    \"dashboard\": {\r\n"
				+ "        \"purchaseAmount\": 1162,\r\n"
				+ "        \"website\": \"rahulshettyacademy.com\"\r\n"
				+ "    },\r\n"
				+ "    \"courses\": [\r\n"
				+ "        {\r\n"
				+ "            \"title\": \"Selenium Python\",\r\n"
				+ "            \"price\": 50,\r\n"
				+ "            \"copies\": 6\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"title\": \"Cypress\",\r\n"
				+ "            \"price\": 40,\r\n"
				+ "            \"copies\": 4\r\n"
				+ "        },\r\n"
				+ "        {\r\n"
				+ "            \"title\": \"RPA\",\r\n"
				+ "            \"price\": 45,\r\n"
				+ "            \"copies\": 10\r\n"
				+ "        },\r\n"
				+ "                {\r\n"
				+ "            \"title\": \"Appium\",\r\n"
				+ "            \"price\": 36,\r\n"
				+ "            \"copies\": 7\r\n"
				+ "        }\r\n"
				+ "    ]\r\n"
				+ "}";
	}
	public static String addBookPayload(String isbn, String aisle)
	{
		String addPayload="{\r\n"
				+ "    \"name\":\"Learn Appium Automation With Java\",\r\n"
				+ "    \"isbn\":\""+isbn+"\",\r\n"
				+ "    \"aisle\":\""+aisle+"\",\r\n"
				+ "    \"author\":\"John foe\"\r\n"
				+ "}";
		return addPayload;
	}

}
