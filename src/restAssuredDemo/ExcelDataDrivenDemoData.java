package restAssuredDemo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import resources.DataDriven;

public class ExcelDataDrivenDemoData {
  public static String  addBookPayload()
  {
	  return "{\r\n"
	  		+ "    \"name\":\"Chava2\",\r\n"
	  		+ "    \"isbn\":\"dg\",\r\n"
	  		+ "    \"aisle\":\"cs\",\r\n"
	  		+ "    \"author\":\"Akshay\"\r\n"
	  		+ "}";
  }
  
  public static HashMap<String, Object> addBookHashMapPayload() throws IOException
  {
	  DataDriven d=new DataDriven();
	  ArrayList<String> a= d.getData("RestAddBook");
	  HashMap<String, Object> hashMap=new HashMap();
	  hashMap.put("name", a.get(1));
		hashMap.put("isbn", a.get(2));
		hashMap.put("aisle", a.get(3));
		hashMap.put("author", a.get(4));
		return hashMap;
  }
}
