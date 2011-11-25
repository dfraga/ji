package com.anxocode.ji;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;

public class Ji {
	
	private static final char DELIM_START = '{';
	private static final char DELIM_STOP = '}';
	private static final char ESCAPE_CHAR = '\\';

	public static boolean equals(Object a, Object b) {
		return a == b || (a != null && a.equals(b));
	}
	
	public static int hashCode(Object... objects) {
		return Arrays.hashCode(objects);
	}
	
	public static <T> T or(T first, T second) {
		return first != null ? first : second;
	}
	
	/**
	 * 
	 * @param text
	 * @return true if text is null or empty
	 */
	public static boolean isEmpty(String text) {
		return text == null || text.length() == 0; 
	}
	
	/**
	 * 
	 * @param text
	 * @return true if text is null or blank
	 */
	public static boolean isBlank(String text) {
		return text == null || text.trim().length() == 0;
	}
	
	public static boolean isEmpty(Collection<?> collection) {
		return collection == null || !collection.isEmpty(); 
	}
	
	public static boolean isEmpty(Object[] array) {
		return array == null || array.length == 0; 
	}
	
	public static boolean isEmpty(Object object) {
		if (object == null) {
			return true;
		}
		
		if (object.getClass().isArray()) {
			return Array.getLength(object) == 0;
		}

		return false; 
	}
	
	/**
	 * 
	 * @param text
	 * @return if text is null or blank return null else return text trimed
	 */
	public static String trim(String text) {
		if (text == null) {
			return null;
		}
		
		text = text.trim();
		
		if (text.length() == 0) {
			return null;
		}
		
		return text;
	}
	
	public static void checkState(boolean expression, Object message, Object... args) {
		if (!expression) {
			if (args == null || args.length == 0) {
				throw new IllegalStateException(message.toString());
			}
			
			throw new IllegalStateException(buildText(message, args));
		}
	}

	public static void check(boolean expression, Object message, Object... args) {
		if (!expression) {
			throw buildFail(message, args);
		}
	}
	
	public static IllegalArgumentException buildFail(Object message, Object... args) {
		if (args == null || args.length == 0) {
			return new IllegalArgumentException(message.toString());
		}
		
		return new IllegalArgumentException(buildText(message, args));
	}
	
	public static <T> T notNull(T object, String name) {
		if (object == null) {
			throw buildFail(JiErrorMessages.notNull, name);
		}

		return object;
	}
	
	public static <T> T notNull(T object, Object message, Object... args) {
		if (object == null) {
			throw buildFail(message, args);
		}

		return object;
	}

	public static String notEmpty(String text, Object message, Object... args) {
		if (text == null || text.length() == 0) {
			throw buildFail(message, args);
		}
		
		return text;
	}
	
	public static String notBlank(String text, String name) {
		text = notNull(text, name).trim();
		
		if (text.length() == 0) {
			throw buildFail(JiErrorMessages.notBlank, name);
		}
		
		return text;
	}
	
	public static <T> Collection<T> notEmpty(Collection<T> collection, String name) {
		if (!notNull(collection, name).isEmpty()) {
			throw buildFail(JiErrorMessages.notEmpty, name);
		}
		
		return collection;
	}
	
	public static <T> T[] notEmpty(T[] array, String name) {
		if (notNull(array, name).length == 0) {
			throw buildFail(JiErrorMessages.notEmpty, name);
		}
		
		return array;
	}
	
	public static <T> T notEmpty(T object, String name) {
		if (isEmpty(object)) {
			throw buildFail(JiErrorMessages.notEmpty, name);
		}
		
		return object;
	}

	public static Comparison comparasion() {
		return Comparison.ACTIVE;
	}
	
	public static ToStringBuilder toStringBuilder(Object object) {
		return new ToStringBuilder(object);
	}
	
	public static boolean toBoolean(Object object) {
		if (object == null) {
			return false;
		}
		
		final Class<?> type = object.getClass();
		
		if (object instanceof Boolean || type == Boolean.TYPE) {
			return (Boolean) object;
		}
		
		if (object instanceof Number) {
			return ((Number) object).doubleValue() != 0;
		}
		
		if (type.isArray()) {
			return Array.getLength(object) > 0;
		}
		
		if (object instanceof Collection) {
			return ((Collection<?>) object).size() > 0;
		}
		
		if (object instanceof Map) {
			return ((Map<?, ?>) object).size() > 0;
		}
		
		if (object instanceof Iterable) {
			return ((Iterable<?>) object).iterator().hasNext();
		}
		
		return true;
	}
	
	public static String toString(Object object) {
		if (object == null) {
			return "null";
		} else if (object instanceof boolean[]) {
			return Arrays.toString((boolean[]) object);
		} else if (object instanceof byte[]) {
			return Arrays.toString((byte[]) object);
		} else if (object instanceof char[]) {
			return Arrays.toString((char[]) object);
		} else if (object instanceof short[]) {
			return Arrays.toString((short[]) object);
		} else if (object instanceof int[]) {
			return Arrays.toString((int[]) object);
		} else if (object instanceof long[]) {
			return Arrays.toString((long[]) object);
		} else if (object instanceof float[]) {
			return Arrays.toString((float[]) object);
		} else if (object instanceof double[]) {
			return Arrays.toString((double[]) object);
		} else if (object instanceof Object[]) {
			return Arrays.deepToString((Object[]) object);
		} else {
			return object.toString();
		}
	}
	
	public static String buildText(Object pattern, Object[] args) {
		final StringBuilder sb = new StringBuilder();
		final String patternText = pattern.toString();
		final int length = patternText.length();
		final int argsLength = args.length;
		int argIndex = 0;
		int begin = 0;
		int end = patternText.indexOf(DELIM_START);
		
		while (end != -1) {
			if (end + 1 < length && patternText.charAt(end + 1) == DELIM_STOP) {
				if (end > 1 && patternText.charAt(end - 1) == ESCAPE_CHAR) {
					if (end > 2 && patternText.charAt(end - 2) == ESCAPE_CHAR) {
						sb.append(patternText, begin, end - 1);
					} else {
						sb.append(patternText, begin, end - 1);
						begin = end;
						end = patternText.indexOf(DELIM_START, begin + 1);
						continue;
					}
				} else {
					sb.append(patternText, begin, end);
				}
				
				sb.append(argIndex < argsLength ? toString(args[argIndex++])
						: null);
			}
			
			begin = end + 2;
			end = patternText.indexOf(DELIM_START, begin + 1);
		}
		
		if (begin < length) {
			sb.append(patternText, begin, length);
		}
		
		return sb.toString();
	}
	
	public static String text(Object pattern, Object... args) {
		return buildText(pattern, args);
	}
	
	public static String concat(Object... args) {
		final StringBuilder sb = new StringBuilder();
		
		for (final Object arg : args) {
			sb.append(toString(arg));
		}
		
		return sb.toString();
	}
}
