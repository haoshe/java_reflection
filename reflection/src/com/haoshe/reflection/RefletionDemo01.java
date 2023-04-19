package com.haoshe.reflection;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/*
 * 反射
 * 反射机制是在运行状态中
 * 对于任意一个类，都能够知道这个类都所有属性和方法
 * 对于任意一个对象，都能调用它都任意一个方法和属性
 * 
 * 反射提供都功能
 * 在运行时判断任意一个对象所属的类
 * 在运行时构造任意一个类的对象
 * 在运行时判断任意一个类所具有的成员变量和方法
 * 在运行时调用任意一个对象的方法
 */

public class RefletionDemo01 {
	
	//通过反射获取类
	public static void demo01() {
		//获取反射对象（反射入口）：Class, 不管做什么，这一步必须要做
		//1 Class.forName("全类名")   2 类名.class  3 对象.getClass()， 这三个得到的都是Class对象
		
		//1 Class.forName(全类名，包名加类名),这一种方法有异常，其它两种都没有
		try {
			Class<?> personClazz = Class.forName("com.haoshe.reflection.Person");//control+1/command+1 assign a local variable to the statement
			System.out.println(personClazz);
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//2 类名.class
		Class<?> personClazz2 = Person.class;
		System.out.println(personClazz2);
		
		//3 对象.getClass()
		Person person = new Person();
		Class<?> personClazz3 = person.getClass();
		System.out.println(personClazz3);
	}
	
	//获取公共方法
	public static void demo02() {
		//Class入口，只要是反射，必须拿入口. 第一种是推荐方法。
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName("com.haoshe.reflection.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//获取所有的公共的方法（1.本类以及父类，接口中到所有方法 2.符合访问修饰符规律，私有的方法拿不到）
		//Person类中原有的方法是这样： public              String                               getName();
		//用反射得到的方法是这样：      public   java.lang.String   com.haoshe.reflection.Person.getName()
		//可以看到反射得到的方法显示了全类名和包名
		Method[] methods = personClazz.getMethods();
		for(Method method : methods) {
			System.out.println(method);
		}
		System.out.println("=======================");
		//获取当前类的所有的方法（1.只能是当前类的，没有父类和接口的 2.忽略访问修饰符限制，公有私有都拿）
		Method[] declaredMethods = personClazz.getDeclaredMethods();
		for(Method declaredMethod : declaredMethods) {
			System.out.println(declaredMethod);
		}
	}
	//获取所有的接口
	public static void demo03() {
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName("com.haoshe.reflection.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//单继承，多实现，可能会有多个接口，所以返回一个数组
		Class<?>[] interfaces = personClazz.getInterfaces();
		for(Class<?> inter : interfaces) {
			System.out.println(inter);
		}
	}
	
	//获取所有的父类
	public static void demo04() {
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName("com.haoshe.reflection.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//单继承，多实现，只有一个父类，所以不返回数组，只返回一个
		Class<?> superclass = personClazz.getSuperclass();
		System.out.println(superclass);
	}
	
	//获取所有的构造方法
	public static void demo05() {
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName("com.haoshe.reflection.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Constructor<?>[] constructors = personClazz.getConstructors();
		for(Constructor<?> constructor : constructors) {
			System.out.println(constructor);
		}
	}
	
	//获取所有的公共属性
	public static void demo06() {
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName("com.haoshe.reflection.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Field[] fields = personClazz.getFields();
		for(Field field : fields) {
			System.out.println(field);
		}
		System.out.println("===================");
		//获取当前类都所有属性(公有的和私有的)
		Field[] declaredFields = personClazz.getDeclaredFields();
		for(Field declaredField : declaredFields) {
			System.out.println(declaredField);
		}
	}
	
	//获取当前反射所代表类（接口）的对象（实例）
	public static void demo07() throws InstantiationException, IllegalAccessException {
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName("com.haoshe.reflection.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		Object instance = personClazz.newInstance();
		//Object类型的实例能强转成Person，说明得到的确实是Person实例
		Person person = (Person)instance;
		person.interface2Method();
	}
	
	public static void main(String[] args) throws InstantiationException, IllegalAccessException {
		//demo01();
		//demo02();
		//demo03();
		//demo04();
		//demo05();
		//demo06();
		demo07();
	}
}
