package utils;

import java.util.Scanner;

public class SecurityDemo {
	
	String username;
	String password;
	Scanner input = new Scanner(System.in);
	
	public boolean register() {
		boolean flag = false;
		
		try {
			
			System.out.println("input your user name: ");
			username = input.next();
			System.out.println("input your password");
			password = input.next();
			
			//encode the password, so the password in the server is encoded
			password = SecurityUtil.md5Encrypt(password.getBytes());
			
			System.out.println("register info: username:" + username + ", password:" + password);
			
			//if there is no exception after above steps, make flag = true
			flag = true;
			
		}catch(Exception e) {
			e.printStackTrace();
		}
		return flag;
	}
	
	public boolean login() {
		boolean result = false;
		System.out.println("enter your user name: ");
		String loginUname = input.next();
		System.out.println("enter your password");
		String loginPwd = input.next();
		
		//encode the loginPassword, comparing it with the one in the server
		//abc --> xyz(server)
		//abc --> xyz
		loginPwd = SecurityUtil.md5Encrypt(loginPwd.getBytes());
		
		System.out.println("login info: username:" + username + ", password:" + password);
		
		if(loginUname.equals(username) && loginPwd.equals(password)) {
			result = true;
		}
		return result;
	}
	
	public static void main(String[] args) {
		SecurityDemo demo = new SecurityDemo();
		demo.register();
		
		boolean result = demo.login();
		if(result) {
			System.out.println("login successfully");
		}else {
			System.out.println("login failure");
		}
	}
}
