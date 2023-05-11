package json;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

//download json.jar from https://jar-download.com/artifacts/org.json
//copy file json.jar to src folder
//right click json.jar, choose Build Path

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

	public static void main(String[] args) {
		
		//demo01();//{"01":"Katie","02":"Kevin","03":"Daniel"}
		//demo02();
		demo03();
	}
}
