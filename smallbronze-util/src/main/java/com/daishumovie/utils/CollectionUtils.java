package com.daishumovie.utils;

import org.apache.commons.beanutils.PropertyUtils;

import java.lang.reflect.Method;
import java.util.*;

/**
 * 集合操作工具类. Date: 13-6-6 Time: 下午2:13
 */
public final class CollectionUtils {

	private CollectionUtils() {
	}

	public static boolean isNullOrEmpty(Collection collection) {
		return collection == null || collection.isEmpty();
	}

	public static List fetchPropertiesAsList(final Collection collection, final String propertyName) {
		final List list = new ArrayList();
		try {
			for (final Object obj : collection) {
				list.add(PropertyUtils.getProperty(obj, propertyName));
			}
		} catch (Exception e) {
			throw new RuntimeException(e);
		}
		return list;
	}

	public static <T> Map<Integer, T> convert2Map(List<T> objectList, String methodName) throws Exception {
		Map<Integer, T> map = new HashMap<>();
		for (T t : objectList) {
			Method method = t.getClass().getMethod(methodName);
			Integer key = (Integer) method.invoke(t);
			map.put(key, t);
		}
		return map;
	}

	public static <T> Map<String, T> convert2MapUseStringKey(List<T> objectList, String methodName) throws Exception {
		Map<String, T> map = new HashMap<>();
		for (T t : objectList) {
			Method method = t.getClass().getMethod(methodName);
			String key = (String) method.invoke(t);
			map.put(key, t);
		}
		return map;
	}

	@SuppressWarnings("unchecked")
	public static <K, T> Map<K, T> convert2MapGeneric(List<T> objectList, String methodName) throws Exception {
		Map<K, T> map = new HashMap<>();
		for (T t : objectList) {
			Method method = t.getClass().getMethod(methodName);
			K key = (K) method.invoke(t);
			map.put(key, t);
		}
		return map;
	}

	/**
	 * 数组为空
	 * @param array
	 * @return
	 */
	public static boolean arrayIsNullOrEmpty(Object[] array) {

		return null == array || array.length == 0 || null == array[0];
	}
}
