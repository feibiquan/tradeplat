package com.xfpay.utils.common;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.HashSet;
import java.util.Set;

public class ReflectUtil {
	/**
	 * 利用反射获取指定对象的指定属性
	 * 
	 * @param obj
	 *            目标对象
	 * @param fieldName
	 *            目标属性
	 * @return 目标属性的值
	 */
	public static Object getFieldValue(Object obj, String fieldName) {
		Object result = null;
		Field field = ReflectUtil.getField(obj, fieldName);
		if (field != null) {
			field.setAccessible(true);
			try {
				result = field.get(obj);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return result;
	}

	/**
	 * 利用反射获取指定对象里面的指定属性
	 * 
	 * @param obj
	 *            目标对象
	 * @param fieldName
	 *            目标属性
	 * @return 目标字段
	 */
	private static Field getField(Object obj, String fieldName) {
		Field field = null;
		for (Class<?> clazz = obj.getClass(); clazz != Object.class; clazz = clazz.getSuperclass()) {
			try {
				field = clazz.getDeclaredField(fieldName);
				break;
			} catch (NoSuchFieldException e) {
				// 这里不用做处理，子类没有该字段可能对应的父类有，都没有就返回null。
			}
		}
		return field;
	}

	/**
	 * 利用反射设置指定对象的指定属性为指定的值
	 * 
	 * @param obj
	 *            目标对象
	 * @param fieldName
	 *            目标属性
	 * @param fieldValue
	 *            目标值
	 */
	public static void setFieldValue(Object obj, String fieldName, String fieldValue) {
		Field field = ReflectUtil.getField(obj, fieldName);
		if (field != null) {
			try {
				field.setAccessible(true);
				field.set(obj, fieldValue);
			} catch (IllegalArgumentException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IllegalAccessException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static <T> T copy(Class<T> class1, Object obj)
			throws InstantiationException, IllegalAccessException, NoSuchFieldException, SecurityException {
		T t = class1.newInstance();
		Field[] fields = class1.getDeclaredFields();
		@SuppressWarnings("rawtypes")
		Class cls2 = obj.getClass();
		Set<String> fieldSet = getFields(obj);
		for (Field field : fields) {
			if (!Modifier.isFinal(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())
					&& !Modifier.isVolatile(field.getModifiers())) {
				Field rField = null;
				String fieldName = field.getName();
				if (fieldSet.contains(fieldName)) {
					rField = cls2.getDeclaredField(fieldName);
					rField.setAccessible(true);
					Object value = rField.get(obj);
					if (value != null) {
						field.setAccessible(true);
						field.set(t, value);
					}
				}
			}
		}
		return t;
	}

	private static Set<String> getFields(Object obj) {
		Field[] fields = obj.getClass().getDeclaredFields();
		Set<String> fieldSet = new HashSet<>();
		for (Field field : fields) {
			if (!Modifier.isFinal(field.getModifiers()) && !Modifier.isStatic(field.getModifiers())
					&& !Modifier.isVolatile(field.getModifiers())) {
				fieldSet.add(field.getName());
			}
		}
		return fieldSet;
	}
}
