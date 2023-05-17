package utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.apache.commons.codec.digest.DigestUtils;

//we need to import commons-codec-1.9.jar for the following operations
public class SecurityUtil {
	
	/*
	 * xor(异或)：同为0， 异为1. （无进位相加）
	 * a number, xor another number twice, will equal to itself
	 * a - 0110001
	 * 3 - 0000011 ^
	 * 	   0110010
	 * 3 - 0000011 ^
	 *     0110001
	 */
	
	public static String xor(String input) {//"abc" --> ['a','b','c'] -> ['x','y','z'] -> "xyz"
		
		//question: why not input ^ 3000 directly?
		//because String can't xor an int, no such operation. 
		//char and int can be converted to each other.
		
		//"abc" -> ['a','b','c']
		char[]  charArray = input.toCharArray();
		
		for(int i=0; i<charArray.length; i++) {
			
			//encrypt each character a -> x
			//3000 is an int, because char<int, so charArray[i] ^ 30000 will be an int. we need to cast it back to char
			charArray[i] = (char) (charArray[i] ^ 30000);
		}
		
		//['x','y','z'] -> "xyz"
		return new String(charArray);
	}
	
	//md5 and sha256 encryption are both non reversible
	//md5 is faster; sha256 has a higher security
	
	//md5 encryption is non reversible: String -> Hexadecimal(十六进制)
	//it's better to use a byte[] as an input, because any data can be converted to a binary type
	public static String md5Encrypt(byte[] input) {//byte[] ("abc") -> String("121515abc")
		
		//byte[] -> String
		return DigestUtils.md5Hex(input);
	}
	
	//sha256 encryption is non reversible: String - Hexadecimal
	public static String sha256Encrypt(byte[] input) {
		return DigestUtils.sha256Hex(input);
	}
	
	//control+shift+t, type in base64, choose the first one, we can find all the base64 encryption and decryption methods
	
	//base64 depends on JDK, doesn't depend on the third party jar, it's reversible
	//base64 encryption: any data type(byte[]) -> String
	//inside base64 javadoc, control+o, we can find all the methods
	public static String base64Encrypt(byte[] input) {
		
		String encoded = null;
		
		//right click the class name and choose copy qualified name, you will get full path name of the class
		//get the entrance to the class
		try {
			
			Class<?> clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method method = clazz.getMethod("encode", byte[].class);
			
			//encode() is a static method, no need to new an object to call it. so 1st argument is null
			encoded = (String) method.invoke(null, input);//Person per = new Person(); per.sleep();
			
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return encoded;
	}
	
	//Base64 decryption: String -> any data type(byte[])
	public static byte[] base64Decryption(String input) {
		
		byte[] decoded = null;
		
		try {
			
			Class<?> clazz = Class.forName("com.sun.org.apache.xerces.internal.impl.dv.util.Base64");
			Method method = clazz.getMethod("decode", String.class);
		    decoded = (byte[])method.invoke(null, input);
		    
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
		return decoded;
	}
	
	
	
	public static void main(String[] args) {
		
//		String str = "Hello";
//		str = xor(str);//xor first time, encryption
//		System.out.println(str);
//		str = xor(str);//xor second time, decryption, it is a reversible operation
//		System.out.println(str);
		
		String str = "hello";
//		str = md5Encrypt(str.getBytes());
//		System.out.println(str);//5d41402abc4b2a76b9719d911017c592 十六进制串
		
//		str = sha256Encrypt(str.getBytes());
//		System.out.println(str);//2cf24dba5fb0a30e26e83b2ac5b9e29e1b161e5c1fa7425e73043362938b9824
		
		//Base64 encryption and decryption
		str = base64Encrypt(str.getBytes());
		System.out.println(str);//aGVsbG8=
		
		byte[] result = base64Decryption(str);
		System.out.println(new String(result));
		
	}
}
