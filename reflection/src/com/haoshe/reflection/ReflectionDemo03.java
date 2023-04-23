package com.haoshe.reflection;

import java.lang.reflect.Array;
import java.util.Scanner;



/*
 * using reflection to create an array and assign values to it
 * String[] str = new String[3];
 * 
 */
public class ReflectionDemo03 {
	//using reflection to create an array
	public static void array1() throws ClassNotFoundException {
		Scanner input = new Scanner(System.in);
		System.out.println("enter array type: ");
		String type = input.nextLine(); //java.lang.String, has to write full path name
		System.out.println("enter array size");
		int size = input.nextInt();
		
		Class<?> typeClazz = Class.forName(type);
		//this statement is important to know how to dynamically create an array 
		Object arr = Array.newInstance(typeClazz, size);
		
		//assign values to the array
		Array.set(arr, 0, "Katie");
		Array.set(arr, 1, "Daniel");
		Array.set(arr, 2, "Kevin");
		//get the assigned values
		System.out.println(Array.get(arr, 0));
		System.out.println(Array.get(arr, 1));
		System.out.println(Array.get(arr, 2));
	}
	
	//using reflection to create a 2D array
	public static void array2() {
		Class<Integer> typeClazz = Integer.TYPE;//array type is Integer
		//array size   row:3, column:3
		int[] dimension = {3,3};
		Object arr = Array.newInstance(typeClazz, dimension);
		
		//get second row from the 2D array(row and column's index start at 0, 0th row, 1st row...)
		Object secondRow = Array.get(arr, 2);
		//in this 2nd row, assign 369 to the 1st column arr[2][1]=369
		Array.set(secondRow, 1, 369 );
		System.out.println(Array.get(secondRow, 1));
	}
	
	public static void main(String[] args) throws ClassNotFoundException {
		//array1();
		array2();
	}
}
