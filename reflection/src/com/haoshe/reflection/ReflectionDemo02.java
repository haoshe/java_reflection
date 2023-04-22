package com.haoshe.reflection;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Properties;

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
		//如果因为访问修饰符限制造成异常，可以通过Field/Method/Constructor.setAccessible(true)来修改
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
	
	//操作构造方法
	public static void demo03() throws NoSuchMethodException, SecurityException, InstantiationException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName("com.haoshe.reflection.Person");
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		
		//get all the public constructors, can't get private constructors
		Constructor<?>[] constructors = personClazz.getConstructors();
		for(Constructor<?> constructor : constructors) {
			System.out.println(constructor);
		}
		
		System.out.println("==========================");
		
		//get public and private constructors of this class
		Constructor<?>[] declaredConstructors = personClazz.getDeclaredConstructors();
		for(Constructor<?> declaredConstructor : declaredConstructors) {
			System.out.println(declaredConstructor);
		}
		
		System.out.println("===========================");
		
		//get specified constructor, only for public
		//in the reflection, get method/constructor by it's paremeterType, basic type(int,char...) and wrapping type(Integer, Character...) are different types
		Constructor<?> constructor = personClazz.getConstructor(int.class);
		System.out.println(constructor);
		
		System.out.println("===========================");
		
		//get private constructor
		Constructor<?> declaredConstructor = personClazz.getDeclaredConstructor(String.class);
		System.out.println(declaredConstructor);
		
		System.out.println("============================");
		
		//Person person = new Person();
		Person person = (Person)constructor.newInstance(12);//constructor has a int parameter, so we has to pass a int value
		System.out.println(person);
		
		System.out.println("============================");
		
		//get an object instance using the private constructor which we get from reflection
		//because this constructor is private, we have to change private to public
		declaredConstructor.setAccessible(true);
		Person person3 = (Person)declaredConstructor.newInstance("Katie");
		System.out.println(person3);
		
		//get constructor without parameters
		Constructor<?> constructor2 = personClazz.getConstructor();
		Person person2 = (Person)constructor2.newInstance();//constructor2 has no parameter, so no need to pass the value
		System.out.println(person2);
	}
	
	//dynamically load class name and method from a file
	//create a file named class.txt in in reflection folder
	//class.txt has to be in the main project(reflection) folder, otherwise it can't be found 
	public static void demo04() throws FileNotFoundException, IOException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		//通过这个属性类可以得到class.txt中的类名和方法名
		Properties prop = new Properties();
		//加载class.txt文件
		//inside class.txt, to get the proper classname path, we have to open the class, right click the class name and choose "copy qualified name",then paste it like className=paste here. Otherwise the class can't be found
		prop.load(new FileReader("class.txt"));
		//通过key拿value from class.txt
		String classname = prop.getProperty("className");
		//in the same way we can get method name from class.txt
		String methodname = prop.getProperty("methodName");
		
		Class<?> personClazz = null;
		try {
			personClazz = Class.forName(classname);//这个classname就是用属性类实例从class.txt中拿到的类名
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		//use reflection to get the method we want to use
		//you can simply have access to a imported or in-built method by clicking on the method name while pressing the control(CTRL)/command(mac) key. 
		//Then you can see what is inside that code.
		//if we don't know the parameters of the method, we don't need to write it
		Method method = personClazz.getMethod(methodname);//这个methodname就是用属性类实例从class.txt中拿到的方法名
		//after we get the method, we invoke it
		//invoke(Object obj, Object... args)
		//用对象调方法，对象就是personClazz.newInstance()；参数列表是可变的，我们知道就写，不知道也可以不写
		method.invoke(personClazz.newInstance());
	}
	
	//reflection may overlook generic check(反射可以越过泛型检查）
	//although using reflection can visit property/method with private access modifier(访问修饰符)，it can also overlook generic restriction
	//but it is not recommended to do so in real life situation
	public static void demo05() throws NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, InstantiationException {
		ArrayList<Integer> list = new ArrayList<>();
		list.add(123);
		list.add(456);
		list.add(789);
		//this list is Integer type, can't add String type
//	    list.add("Katie");
		//get the reflection entrance of this list
		Class<?> listClazz = list.getClass();
		//Method getMethod(String name, Class<?>... parameterTypes)
		//                 method name, generic type (Object means any generic type)
		Method method = listClazz.getMethod("add", Object.class);
		//Object invoke(Object obj, Object... args)
		method.invoke(list, "Katie");
		System.out.println(list);
	}
	
	//check the self-defined assignment method
	public static void demo06() throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		Person person = new Person();
		PropertyUtil.setProperty(person, "name", "Katie");
		PropertyUtil.setProperty(person, "age", 12);
		Student student = new Student();
		PropertyUtil.setProperty(student, "score", 98);
		System.out.println(person.getName() + "," + person.getAge());
		System.out.println(student.getScore());
	}
	
	public static void main(String[] args) throws InstantiationException, 
												IllegalAccessException, 
												NoSuchFieldException, 
												SecurityException, 
												NoSuchMethodException, 	
												IllegalArgumentException, 
												InvocationTargetException, 
												FileNotFoundException, 
												IOException {
		//执行这个方法需要抛两个异常 1. InstantiationException 2. IllegalAccessException
		// demo01();
		//用反射调属性会抛NoSuchFieldException, SecurityException
		//用反射调方法会抛IllegalArgumentException, InvocationTargetException
		//demo02();
		//demo03();
		//demo04();
		//demo05();
		demo06();
	}
}
