package com.haoshe.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ReflectionDemo02 {
	//获取对象的实例，并操作该对象
	public static void demo01() throws InstantiationException, IllegalAccessException {
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName("com.haoshe.reflection.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//这儿会抛两个异常 1. InstantiationException 2. IllegalAccessException
		Person person = (Person)personClazz.newInstance();
		person.setName("Katie");
		person.setAge(12);
		System.out.println(person.getName()+","+person.getAge());
	}
	
	//通过反射操作属性
	public static void demo02() throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName("com.haoshe.reflection.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//先要拿到实例，并强转成Person类
		Person person = (Person)personClazz.newInstance();
		//拿到id属性，会抛NoSuchFieldException异常
		//control/command+1,发现返回值是Filed类型的
		Field idField = personClazz.getDeclaredField("id");
		//id属性是private类型，所以要先修改访问权限为public
		//如果因为访问修饰符限制造成异常，可以通过Field/Method.setAccessible(true)来修改
		idField.setAccessible(true);
		//赋值 不通过反射：person.setId(1); 对象.方法(值);
		//      通过反射：idField.set(Obj,value);  通过反射拿到的id属性.方法(对象，值);
		//访问的是private修饰的id,只能本类才能访问，所以要先修改访问权限
		idField.set(person, 12);
		System.out.println(person.getId());
		//Field.setAccessible(true)只需用一次即可，以后就都可以访问了
		idField.set(person, 10);
		System.out.println(person.getId());
		
		System.out.println("================");
		//反射入口.getDeclaredMethod(name/方法名，parameterTypes/参数类型)；
		//这个方法没有参数，就填null
		//此方法会抛NoSuchMethodException
		//control/command+1会得到一个Method类型的返回值
		Method method = personClazz.getDeclaredMethod("privateMethod", null);
		//privateMethod是个私有方法，要先把它改为public才能调用
		method.setAccessible(true);
		//方法的调用是invoke(obj/对象，args/参数值),如果没有参数值就写null
		//会抛IllegalArgumentException, InvocationTargetException
		method.invoke(person, null);
		System.out.println("=========================");
		//用反射调用有多个参数的私有方法
		Method method2 = personClazz.getDeclaredMethod("privateMethod2", String.class, int.class);//类名.class就是反射入口
		method2.setAccessible(true);
		method2.invoke(person, "Daniel", 15);
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
		//执行这个方法需要抛两个异常 1. InstantiationException 2. IllegalAccessException
		// demo01();
		//用反射调属性会抛NoSuchFieldException, SecurityException
		//用反射调方法会抛IllegalArgumentException, InvocationTargetException
		demo02();
	}
}
