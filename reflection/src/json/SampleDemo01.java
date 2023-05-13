package json;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.json.JSONObject;

//download json.jar from https://jar-download.com/artifacts/org.json
//copy file json.jar to src folder
//right click json.jar, choose Build Path, then click Add to Build Path

public class SampleDemo01 {
	
	//1. Map collection, JavaBean, String -> JSon Object
	
	//a. Map collection -> JSon Object
	public static void demo01() {
		Map<String,String> map = new HashMap<>();
		
		map.put("01", "Katie");
		map.put("02", "Kevin");
		map.put("03", "Daniel");
		
		//map -> json
		//json form: {"key":value, "key":value, "key":value ...}
		//key is a string, value can be any type
		JSONObject json = new JSONObject(map);
		System.out.println(json);
		
	}
	
	//b. JavaBean(normal object: Person) -> json
	public static void demo02() {
		
		Person person = new Person();
		person.setName("Katie");
		person.setAge(23);
		
		Address address = new Address();
		address.setHomeAddress("Dublin");
		address.setSchoolAddress("Cork");
		person.setAddress(address);
		
		//Person(JavaBean) -> Json
		JSONObject json = new JSONObject(person);
		System.out.println(json);
		//object -> json: {property of object(对象的属性) : value of property（属性值）}
		/*
		 * {"address":{"schoolAddress":"Cork","homeAddress":"Dublin"},
		 * "name":"Katie",
		 * "age":23}
		 */
	}
	
	//c. String -> json
	//on the condition that the string has to be in a form a json {key:value}
	//String str = "aaa" won't work
	public static void demo03() {
		String str = "{\"name\":\"Katie\", \"age\":23}";
		JSONObject json = new JSONObject(str);
		System.out.println(json);
	}
	
	//file -> JSON (file -> String -> JSON)
	//we want to use Object.getClass(), but this method is not a static method
	//static method can only call another static method, so we can't specify demo04() as a static method
	public void demo04() throws IOException {
		//absolute path(绝对路径)
		//new File("C:/abc/a.txt")
		
		//this method can get an input stream using relative path(相对路径) of a file
//		InputStream in = super.getClass().getClassLoader().getResourceAsStream("json/per.json");
//		byte[] buffer = new byte[10];
//		int len = -1;
//		//for concatenating Strings
//		StringBuffer sb = new StringBuffer();
//		while((len = in.read(buffer)) != -1) {
//			//byte[] -> String
//			String str = new String(buffer, 0, len);
//			//concatenate strings(链接字符串)
//			sb.append(str);
// 		}
//		//StringBuffer -> String
//		String s = sb.toString();
		
		//above method is too troublesome, we can use commons-io.jar to convert a file to a String
		//download commons-io.jar, copy it to src folder, right click it, choose Build Path, then choose Add to Build Path
		//new File() needs the absolute path of the file, how to get the absolute path?
		//right click the file, choose Properties, then you can find the location of the file. 
		String s = FileUtils.readFileToString(new File("/Users/haoshe/git/repository/reflection/src/json/per.json"));
		
		//String -> JSON
		JSONObject json = new JSONObject(s);
		System.out.println(json);
	}

	public static void main(String[] args) throws IOException {
		
		//demo01();//{"01":"Katie","02":"Kevin","03":"Daniel"}
		//demo02();
		//demo03();
		SampleDemo01 sample = new SampleDemo01();
		sample.demo04();
	}
}
