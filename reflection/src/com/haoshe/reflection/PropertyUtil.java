package com.haoshe.reflection;

import java.lang.reflect.Field;

public class PropertyUtil {
	//obj.setpropertyName(value);
	//给某个对象（obj)的某个属性（propertyName）赋值(value)，Omni-potent assignment method(万能的赋值方法)
	public static void setProperty(Object obj, String propertyName, Object value) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException {
		//at first, we have to get the reflection entrance of obj
		Class<?> clazz = obj.getClass();
		//private String name 假设我们要给name这个属性赋值
		//get the property which we want to assign a value
		Field field = clazz.getDeclaredField(propertyName);
		//change access modifier(访问修饰符) of this property（属性） from private to public, so we can assign a value to it
		field.setAccessible(true);
		//assign a value to the property of this obj
		field.set(obj, value);
	}
}
