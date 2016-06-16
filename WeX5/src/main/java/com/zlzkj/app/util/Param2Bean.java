package com.zlzkj.app.util;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.math.BigDecimal;

import javax.servlet.http.HttpServletRequest;

public class Param2Bean {

	/**
	 * 添加时参数到bean操作
	 * 把req中的数据按照对象的属性取出来并set到对象当中并返回
	 * 
	 * @param<E>
	 * @paramreq
	 * @parame
	 * @return <E>
	 */
	public static <E> E add_param2Bean(HttpServletRequest req, E e) {
		Class<?> clazz = e.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Class<?> typeClass = field.getType();
			String value = req.getParameter(field.getName());
			setValue(e, clazz, field, typeClass, value);
		}
		return e;
	}
	/**
	 * 编辑时参数到bean操作
	 * 把req中的数据按照对象的属性取出来并set到对象当中并返回
	 * 
	 * @param<E>
	 * @paramreq
	 * @parame
	 * @return <E>
	 */
	public static <E> E edit_param2Bean(HttpServletRequest req, E e) {
		Class<?> clazz = e.getClass();
		Field[] fields = clazz.getDeclaredFields();
		for (Field field : fields) {
			Class<?> typeClass = field.getType();
			String value = req.getParameter(field.getName());
			if(value!=null && !value.equals(""))//编辑操作时对表单没涉及的字段不修改JavaBean对象属性值
				setValue(e, clazz, field, typeClass, value);
		}
		return e;
	}

	/**
	 * 调用set方法把值set到对象当中
	 * 
	 * @param obj指定对象
	 * @param clazz对象的class
	 * @param field需要设置值的field对象
	 * @param typeClassfield的类型的class
	 * @param value对应的值
	 */
	private static void setValue(Object obj, Class<?> clazz, Field field,
			Class<?> typeClass, String value) {
		String fieldName = field.getName();
		String methodName = "set" + fieldName.substring(0, 1).toUpperCase()
				+ fieldName.substring(1);
		//System.out.println("方法名："+methodName);
		try {
			Method method = clazz.getDeclaredMethod(methodName,
					new Class[] { typeClass });
			method.invoke(obj,
					new Object[] { getClassTypeValue(typeClass, value) });
		} catch (SecurityException e) {
			e.printStackTrace();
		} catch (IllegalArgumentException e) {
			e.printStackTrace();
		} catch (NoSuchMethodException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		} catch (InvocationTargetException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 通过class类型获取获取对应类型的值
	 * 
	 * @param typeClassclass类型
	 * @param value值
	 * @return Object
	 */
	private static Object getClassTypeValue(Class<?> typeClass, String value) {
		
		if (typeClass == int.class || typeClass == Integer.class) {//Integer类型
			return StringUtil.stringToInteger(value, 0);
		}
		
		if (typeClass == short.class || typeClass == Short.class) {
			return StringUtil.stringToShort(value, (short)0);
		} 

		if (typeClass == byte.class || typeClass == Byte.class) {//Byte类型
			return StringUtil.stringToByte(value,(byte)0);
		} 

		if (typeClass == double.class || typeClass == Double.class) {
			return StringUtil.stringToDouble(value,(double)0);
		}


		if (typeClass == float.class || typeClass == Float.class) {
			return StringUtil.stringToFloat(value,(float)0);
		} 

		if (typeClass == long.class || typeClass == Long.class) {
			return StringUtil.stringToLong(value,(long)0);
		} 

		if (typeClass == String.class) {//字符串类型
			return StringUtil.objectToString(value, "-");
		} 

		if (typeClass == BigDecimal.class) {//BigDecimal类型，默认为0
			return StringUtil.stringToBigDecimal(value);
		}
		else {
			return typeClass.cast(value);
		}
	}
}