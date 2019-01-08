package rsquest.kr.tpsw.api.publica;

import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

public class ObjectAPI {
	
	//1.0
	
	public static List<String> insertList(List<String> list, int index, String str) {
		List<String> list2 = new LinkedList<String>();
		for (int i = 0; i < index; i++) {
			list2.add(list.get(i));
		}
		list2.add(str);
		for (int i = index; i < list.size(); i++) {
			list2.add(list.get(i));
		}
		return list2;
	}// 예외 처리 안함
	
	public static boolean isArrayHasIndex(Object arr, int index) {
		if (index < 0) {
			return false;
		} else if (Array.getLength(arr) <= index) {
			return false;
		} else {
			return true;
		}
	}// 예외 처리 안함

	public static boolean isListHasIndex(List<?> list, int index) {
		if (index < 0) {
			return false;
		} else if (list.size() <= index) {
			return false;
		} else {
			return true;
		}
	}// 예외 처리 안함

	public static List<String> toList(Set<String> set) {
		List<String> list = new LinkedList<String>();
		for (String entry : set) {
			list.add(entry);
		}
		return list;
	}// 예외 처리 안함

	public static int toInteger(BigDecimal big) {
		try {
			return big.intValueExact();
		} catch (Exception e) {
			return 0;
		}
	}// 예외 자체 처리

	public static int toInteger(String str) {
		return Integer.valueOf(str);
	}

	public static long toLong(String str) {
		return Long.valueOf(str);
	}

	public static double toDouble(String str) {
		return Double.valueOf(str);
	}

	public static float toFloat(String str) {
		return Float.valueOf(str);
	}

	public static boolean toBoolean(String str) {
		return Boolean.valueOf(str);
	}

	public static boolean toBoolean(int i) {
		return i == 1;
	}
}
