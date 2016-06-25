package com.ailk.sets.grade.grade.config;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

public class ParamElement {

	public static final int TYPE_OBJECT = -1;
	public static final int TYPE_BOOLEAN = 1;
	public static final int TYPE_BYTE = 2;
	public static final int TYPE_CHAR = 3;
	public static final int TYPE_SHORT = 4;
	public static final int TYPE_INT = 5;
	public static final int TYPE_LONG = 6;
	public static final int TYPE_FLOAT = 7;
	public static final int TYPE_DOUBLE = 8;
	public static final int TYPE_STRING = 9;

	public static final int SUB_TYPE_UNKNOWN = -1;
	public static final int SUB_TYPE_ATOMIC = 0x1; // 原子类型，如：int
	public static final int SUB_TYPE_ARRAY = 0x2; // 数组
	public static final int SUB_TYPE_LIST = 0x4; // 列表

	public static final int GRAPH_MIN = 0x21;
	public static final int GRAPH_MAX = 0x7e;
	public static final int GRAPH_SIZE = GRAPH_MAX - GRAPH_MIN + 1;

	private String name;
	private String origType;
	private int type;
	private int subType;
	private Integer capacity; // 元素容量
	private String min; // 最小值（包括）
	private String max; // 最大值（不包括)
	/**
	 * 自定义正则表达式
	 * <p>
	 * yyyyMMdd：四位年+2位月+2位日
	 * <p>
	 * yyyyMMddHHmmss：四位年+2位月+2位日+2位时+2位分+2位秒
	 * <p>
	 * HHmmss：2位时+2位分+2位秒
	 * <p>
	 * yyMMdd：二位年+2位月+2位日
	 * <p>
	 * yyMMddHHmmss：二位年+2位月+2位日+2位时+2位分+2位秒
	 * <p>
	 * A：大写字母
	 * <p>
	 * a：小写字母
	 * <p>
	 * B：大写字母+数字
	 * <p>
	 * b：小写字母+数字
	 * <p>
	 * D：数字
	 * <p>
	 * W：所有字符
	 * <p>
	 * w：字母+数字
	 */
	private String regex;

	public void toType() {
		if (origType.equalsIgnoreCase("boolean")) {
			type = TYPE_BOOLEAN;
			subType = SUB_TYPE_ATOMIC;
		} else if (origType.equalsIgnoreCase("byte")) {
			type = TYPE_BYTE;
			subType = SUB_TYPE_ATOMIC;
		} else if (origType.equalsIgnoreCase("char")) {
			type = TYPE_CHAR;
			subType = SUB_TYPE_ATOMIC;
		} else if (origType.equalsIgnoreCase("short")) {
			type = TYPE_SHORT;
			subType = SUB_TYPE_ATOMIC;
		} else if (origType.equalsIgnoreCase("int")) {
			type = TYPE_INT;
			subType = SUB_TYPE_ATOMIC;
		} else if (origType.equalsIgnoreCase("long")) {
			type = TYPE_LONG;
			subType = SUB_TYPE_ATOMIC;
		} else if (origType.equalsIgnoreCase("float")) {
			type = TYPE_FLOAT;
			subType = SUB_TYPE_ATOMIC;
		} else if (origType.equalsIgnoreCase("double")) {
			type = TYPE_DOUBLE;
			subType = SUB_TYPE_ATOMIC;
		} else if (origType.equalsIgnoreCase("String")) {
			type = TYPE_STRING;
			subType = SUB_TYPE_ATOMIC;
		} else if (origType.equalsIgnoreCase("boolean[]")) {
			type = TYPE_BOOLEAN;
			subType = SUB_TYPE_ARRAY;
		} else if (origType.equalsIgnoreCase("byte[]")) {
			type = TYPE_BYTE;
			subType = SUB_TYPE_ARRAY;
		} else if (origType.equalsIgnoreCase("char[]")) {
			type = TYPE_CHAR;
			subType = SUB_TYPE_ARRAY;
		} else if (origType.equalsIgnoreCase("short[]")) {
			type = TYPE_SHORT;
			subType = SUB_TYPE_ARRAY;
		} else if (origType.equalsIgnoreCase("int[]")) {
			type = TYPE_INT;
			subType = SUB_TYPE_ARRAY;
		} else if (origType.equalsIgnoreCase("long[]")) {
			type = TYPE_LONG;
			subType = SUB_TYPE_ARRAY;
		} else if (origType.equalsIgnoreCase("float[]")) {
			type = TYPE_FLOAT;
			subType = SUB_TYPE_ARRAY;
		} else if (origType.equalsIgnoreCase("double[]")) {
			type = TYPE_DOUBLE;
			subType = SUB_TYPE_ARRAY;
		} else if (origType.equalsIgnoreCase("String[]")) {
			type = TYPE_STRING;
			subType = SUB_TYPE_ARRAY;
		} else if (origType.equalsIgnoreCase("List<Boolean>")) {
			type = TYPE_BOOLEAN;
			subType = SUB_TYPE_LIST;
		} else if (origType.equalsIgnoreCase("List<Byte>")) {
			type = TYPE_BYTE;
			subType = SUB_TYPE_LIST;
		} else if (origType.equalsIgnoreCase("List<Charactor>")) {
			type = TYPE_CHAR;
			subType = SUB_TYPE_LIST;
		} else if (origType.equalsIgnoreCase("List<Short>")) {
			type = TYPE_SHORT;
			subType = SUB_TYPE_LIST;
		} else if (origType.equalsIgnoreCase("List<Integer>")) {
			type = TYPE_INT;
			subType = SUB_TYPE_LIST;
		} else if (origType.equalsIgnoreCase("List<Long>")) {
			type = TYPE_LONG;
			subType = SUB_TYPE_LIST;
		} else if (origType.equalsIgnoreCase("List<Float>")) {
			type = TYPE_FLOAT;
			subType = SUB_TYPE_LIST;
		} else if (origType.equalsIgnoreCase("List<Double>")) {
			type = TYPE_DOUBLE;
			subType = SUB_TYPE_LIST;
		} else if (origType.equalsIgnoreCase("List<String>")) {
			type = TYPE_STRING;
			subType = SUB_TYPE_LIST;
		} else {
			type = TYPE_OBJECT;
			subType = SUB_TYPE_UNKNOWN;
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getOrigType() {
		return origType;
	}

	public void setOrigType(String origType) {
		this.origType = origType;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public int getSubType() {
		return subType;
	}

	public void setSubType(int subType) {
		this.subType = subType;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	public String getMin() {
		return min;
	}

	public void setMin(String min) {
		this.min = min;
	}

	public String getMax() {
		return max;
	}

	public void setMax(String max) {
		this.max = max;
	}

	public String getRegex() {
		return regex;
	}

	public void setRegex(String regex) {
		this.regex = regex;
	}

	public Object newInstance(String value) {
		switch (type) {
		case TYPE_BOOLEAN:
			return newBoolean(value);
		case TYPE_BYTE:
			return newByte(value);
		case TYPE_CHAR:
			return newChar(value);
		case TYPE_SHORT:
			return newShort(value);
		case TYPE_INT:
			return newInt(value);
		case TYPE_LONG:
			return newLong(value);
		case TYPE_FLOAT:
			return newFloat(value);
		case TYPE_DOUBLE:
			return newDouble(value);
		case TYPE_STRING:
			return newString(value);
		case TYPE_OBJECT:
			return newObject(value);
		default:
			return null;
		}
	}

	public Object newInstance(Random random) {
		if (regex != null)
			return createByRegex(random);

		switch (type) {
		case TYPE_BOOLEAN:
			return newBoolean(random);
		case TYPE_BYTE:
			return newByte(random);
		case TYPE_CHAR:
			return newChar(random);
		case TYPE_SHORT:
			return newShort(random);
		case TYPE_INT:
			return newInt(random);
		case TYPE_LONG:
			return newLong(random);
		case TYPE_FLOAT:
			return newFloat(random);
		case TYPE_DOUBLE:
			return newDouble(random);
		case TYPE_STRING:
			return newString(random);
		case TYPE_OBJECT:
			return newObject(random);
		default:
			return null;
		}
	}

	private Object newBoolean(String value) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			Boolean obj = Boolean.parseBoolean(value);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			String[] fields = trimBracket(value).split(",");
			boolean[] obj = new boolean[fields.length];
			for (int i = 0; i < fields.length; i++) {
				obj[i] = Boolean.parseBoolean(fields[i]);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			String[] fields = trimBracket(value).split(",");
			List<Boolean> obj = new ArrayList<Boolean>();
			for (int i = 0; i < fields.length; i++) {
				obj.add(Boolean.parseBoolean(fields[i]));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newByte(String value) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			Byte obj = Byte.parseByte(value);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			String[] fields = trimBracket(value).split(",");
			byte[] obj = new byte[fields.length];
			for (int i = 0; i < fields.length; i++) {
				obj[i] = Byte.parseByte(fields[i]);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			String[] fields = trimBracket(value).split(",");
			List<Byte> obj = new ArrayList<Byte>();
			for (int i = 0; i < fields.length; i++) {
				obj.add(Byte.parseByte(fields[i]));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newChar(String value) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			Character obj = value.charAt(0);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			String[] fields = trimBracket(value).split(",");
			char[] obj = new char[fields.length];
			for (int i = 0; i < fields.length; i++) {
				obj[i] = fields[i].charAt(0);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			String[] fields = trimBracket(value).split(",");
			List<Character> obj = new ArrayList<Character>();
			for (int i = 0; i < fields.length; i++) {
				obj.add(fields[i].charAt(0));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newShort(String value) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			Short obj = Short.parseShort(value);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			String[] fields = trimBracket(value).split(",");
			short[] obj = new short[fields.length];
			for (int i = 0; i < fields.length; i++) {
				obj[i] = Short.parseShort(fields[i]);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			String[] fields = trimBracket(value).split(",");
			List<Short> obj = new ArrayList<Short>();
			for (int i = 0; i < fields.length; i++) {
				obj.add(Short.parseShort(fields[i]));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newInt(String value) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			Integer obj = Integer.parseInt(value);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			String[] fields = trimBracket(value).split(",");
			int[] obj = new int[fields.length];
			for (int i = 0; i < fields.length; i++) {
				obj[i] = Integer.parseInt(fields[i]);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			String[] fields = trimBracket(value).split(",");
			List<Integer> obj = new ArrayList<Integer>();
			for (int i = 0; i < fields.length; i++) {
				obj.add(Integer.parseInt(fields[i]));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newLong(String value) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			Long obj = Long.parseLong(value);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			String[] fields = trimBracket(value).split(",");
			long[] obj = new long[fields.length];
			for (int i = 0; i < fields.length; i++) {
				obj[i] = Long.parseLong(fields[i]);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			String[] fields = trimBracket(value).split(",");
			List<Long> obj = new ArrayList<Long>();
			for (int i = 0; i < fields.length; i++) {
				obj.add(Long.parseLong(fields[i]));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newFloat(String value) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			Float obj = Float.parseFloat(value);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			String[] fields = trimBracket(value).split(",");
			float[] obj = new float[fields.length];
			for (int i = 0; i < fields.length; i++) {
				obj[i] = Float.parseFloat(fields[i]);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			String[] fields = trimBracket(value).split(",");
			List<Float> obj = new ArrayList<Float>();
			for (int i = 0; i < fields.length; i++) {
				obj.add(Float.parseFloat(fields[i]));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newDouble(String value) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			Double obj = Double.parseDouble(value);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			String[] fields = trimBracket(value).split(",");
			double[] obj = new double[fields.length];
			for (int i = 0; i < fields.length; i++) {
				obj[i] = Double.parseDouble(fields[i]);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			String[] fields = trimBracket(value).split(",");
			List<Double> obj = new ArrayList<Double>();
			for (int i = 0; i < fields.length; i++) {
				obj.add(Double.parseDouble(fields[i]));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newString(String value) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			String obj;
			if (value.equals("null"))
				obj = null;
			else
				obj = value.substring(1, value.length() - 1);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			String[] fields = trimBracket(value).split(",");
			String[] obj = new String[fields.length];
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i];
				if (field.equals("null"))
					obj[i] = null;
				else
					obj[i] = field.substring(1, field.length() - 1);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			String[] fields = trimBracket(value).split(",");
			List<String> obj = new ArrayList<String>();
			for (int i = 0; i < fields.length; i++) {
				String field = fields[i];
				if (field.equals("null"))
					obj.add(null);
				else
					obj.add(field.substring(1, field.length() - 1));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private static Object newObject(String value) {
		try {
			return Class.forName(value).newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	private static String trimBracket(String value) {
		int first = value.indexOf('[');
		int last = value.lastIndexOf(']');

		return value.substring(first + 1, last);
	}

	private String createByRegex(Random random) {
		if (regex.equals("yyyyMMdd") || regex.equals("yyyyMMddHHmmss")
				|| regex.equals("HHmmss") || regex.equals("yyMMdd")
				|| regex.equals("yyMMddHHmmss")) {
			SimpleDateFormat sdf = new SimpleDateFormat(regex);
			return sdf.format(new Date());
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < regex.length(); i++) {
			char ch = '\0';
			int value;

			switch (regex.charAt(i)) {
			case 'A':
				value = random.nextInt(26);
				ch = (char) ('A' + value);
				break;
			case 'a':
				value = random.nextInt(26);
				ch = (char) ('a' + value);
				break;
			case 'B':
				value = random.nextInt(36);
				if (value < 10)
					ch = (char) ('0' + value);
				else
					ch = (char) ('A' + value - 10);
				break;
			case 'b':
				value = random.nextInt(36);
				if (value < 10)
					ch = (char) ('0' + value);
				else
					ch = (char) ('a' + value - 10);
				break;
			case 'D':
				value = random.nextInt(10);
				ch = (char) ('0' + value);
				break;
			case 'W':
				value = random.nextInt(256);
				ch = (char) value;
				break;
			case 'w':
				value = random.nextInt(62);
				if (value < 10)
					ch = (char) ('0' + value);
				else if (value < 36)
					ch = (char) ('A' + value - 10);
				else
					ch = (char) ('a' + value - 36);
				break;
			default:
				throw new RuntimeException("不可识别的正则表达式");
			}

			builder.append(ch);
		}

		return builder.toString();
	}

	private Object newBoolean(Random random) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			Boolean obj = random.nextBoolean();
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			boolean[] obj = new boolean[capacity];
			for (int i = 0; i < capacity; i++) {
				obj[i] = random.nextBoolean();
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			List<Boolean> obj = new ArrayList<Boolean>();
			for (int i = 0; i < capacity; i++) {
				obj.add(random.nextBoolean());
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newByte(Random random) {
		int minValue = (min == null ? Byte.MIN_VALUE : Byte.parseByte(min));
		int maxValue = (max == null ? Byte.MAX_VALUE : Byte.parseByte(max));
		int total = (maxValue - minValue + 1);

		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			int value = random.nextInt(total);
			return (byte) (minValue + value);
		}
		case SUB_TYPE_ARRAY: {
			byte[] obj = new byte[capacity];
			for (int i = 0; i < capacity; i++) {
				int value = random.nextInt(total);
				obj[i] = (byte) (minValue + value);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			List<Byte> obj = new ArrayList<Byte>();
			for (int i = 0; i < capacity; i++) {
				int value = random.nextInt(total);
				obj.add((byte) (minValue + value));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newChar(Random random) {
		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			char obj = newGraph(random);
			return obj;
		}
		case SUB_TYPE_ARRAY: {
			char[] obj = new char[capacity];
			for (int i = 0; i < capacity; i++) {
				obj[i] = newGraph(random);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			List<Character> obj = new ArrayList<Character>();
			for (int i = 0; i < capacity; i++) {
				obj.add(newGraph(random));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newShort(Random random) {
		int minValue = (min == null ? Short.MIN_VALUE : Short.parseShort(min));
		int maxValue = (max == null ? Short.MAX_VALUE : Short.parseShort(max));
		int total = (maxValue - minValue + 1);

		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			int value = random.nextInt(total);
			return (short) (minValue + value);
		}
		case SUB_TYPE_ARRAY: {
			short[] obj = new short[capacity];
			for (int i = 0; i < capacity; i++) {
				int value = random.nextInt(total);
				obj[i] = (short) (minValue + value);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			List<Short> obj = new ArrayList<Short>();
			for (int i = 0; i < capacity; i++) {
				int value = random.nextInt(total);
				obj.add((short) (minValue + value));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newInt(Random random) {
		long minValue = (min == null ? Integer.MIN_VALUE : Integer
				.parseInt(min));
		long maxValue = (max == null ? Integer.MAX_VALUE : Integer
				.parseInt(max));
		long total = (maxValue - minValue + 1);

		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			int value = random.nextInt();
			return (int) (minValue + Math.abs(value % total));
		}
		case SUB_TYPE_ARRAY: {
			int[] obj = new int[capacity];
			for (int i = 0; i < capacity; i++) {
				int value = random.nextInt();
				obj[i] = (int) (minValue + Math.abs(value % total));
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			List<Integer> obj = new ArrayList<Integer>();
			for (int i = 0; i < capacity; i++) {
				int value = random.nextInt();
				obj.add((int) (minValue + Math.abs(value % total)));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newLong(Random random) {
		BigDecimal minValue = new BigDecimal(min == null ? Long.MIN_VALUE
				: Long.parseLong(min));
		BigDecimal maxValue = new BigDecimal(max == null ? Long.MAX_VALUE
				: Long.parseLong(max));
		BigDecimal total = (maxValue.subtract(minValue).add(new BigDecimal(1)));

		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			BigDecimal value = new BigDecimal(random.nextLong());
			return minValue.add(value.remainder(total).abs()).longValue();
		}
		case SUB_TYPE_ARRAY: {
			long[] obj = new long[capacity];
			for (int i = 0; i < capacity; i++) {
				BigDecimal value = new BigDecimal(random.nextLong());
				obj[i] = minValue.add(value.remainder(total).abs()).longValue();
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			List<Long> obj = new ArrayList<Long>();
			for (int i = 0; i < capacity; i++) {
				BigDecimal value = new BigDecimal(random.nextLong());
				obj.add(minValue.add(value.remainder(total).abs()).longValue());
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newFloat(Random random) {
		BigDecimal minValue = new BigDecimal(min == null ? Float.MIN_VALUE
				: Float.parseFloat(min));
		BigDecimal maxValue = new BigDecimal(max == null ? Float.MAX_VALUE
				: Float.parseFloat(max));
		BigDecimal total = (maxValue.subtract(minValue).add(new BigDecimal(
				Float.MIN_NORMAL)));

		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			BigDecimal value = new BigDecimal(random.nextFloat());
			return minValue.add(value.multiply(total)).floatValue();
		}
		case SUB_TYPE_ARRAY: {
			float[] obj = new float[capacity];
			for (int i = 0; i < capacity; i++) {
				BigDecimal value = new BigDecimal(random.nextFloat());
				obj[i] = minValue.add(value.multiply(total)).floatValue();
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			List<Float> obj = new ArrayList<Float>();
			for (int i = 0; i < capacity; i++) {
				BigDecimal value = new BigDecimal(random.nextFloat());
				obj.add(minValue.add(value.multiply(total)).floatValue());
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newDouble(Random random) {
		BigDecimal minValue = new BigDecimal(min == null ? Double.MIN_VALUE
				: Double.parseDouble(min));
		BigDecimal maxValue = new BigDecimal(max == null ? Double.MAX_VALUE
				: Double.parseDouble(max));
		BigDecimal total = (maxValue.subtract(minValue).add(new BigDecimal(
				Double.MIN_NORMAL)));

		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			BigDecimal value = new BigDecimal(random.nextDouble());
			return minValue.add(value.multiply(total)).doubleValue();
		}
		case SUB_TYPE_ARRAY: {
			double[] obj = new double[capacity];
			for (int i = 0; i < capacity; i++) {
				BigDecimal value = new BigDecimal(random.nextDouble());
				obj[i] = minValue.add(value.multiply(total)).doubleValue();
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			List<Double> obj = new ArrayList<Double>();
			for (int i = 0; i < capacity; i++) {
				BigDecimal value = new BigDecimal(random.nextDouble());
				obj.add(minValue.add(value.multiply(total)).doubleValue());
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newString(Random random) {
		int minValue = (min == null ? 0 : Short.parseShort(min));
		int maxValue = (max == null ? 10 : Short.parseShort(max));
		int total = (maxValue - minValue + 1);

		switch (subType) {
		case SUB_TYPE_ATOMIC: {
			int length = minValue + random.nextInt(total);
			return newGraphs(random, length);
		}
		case SUB_TYPE_ARRAY: {
			String[] obj = new String[capacity];
			for (int i = 0; i < capacity; i++) {
				int length = minValue + random.nextInt(total);
				obj[i] = newGraphs(random, length);
			}
			return obj;
		}
		case SUB_TYPE_LIST: {
			List<String> obj = new ArrayList<String>();
			for (int i = 0; i < capacity; i++) {
				int length = minValue + random.nextInt(total);
				obj.add(newGraphs(random, length));
			}
			return obj;
		}
		default:
			return null;
		}
	}

	private Object newObject(Random random) {
		Class<?>[] classes = { Object.class, Boolean.class, Byte.class,
				Character.class, Short.class, Integer.class, Long.class,
				Float.class, Double.class, String.class, Class.class };
		int index = random.nextInt(classes.length);
		try {
			return classes[index].newInstance();
		} catch (Exception e) {
			return null;
		}
	}

	private char newGraph(Random random) {
		return (char) (GRAPH_MIN + random.nextInt(GRAPH_SIZE));
	}

	private String newGraphs(Random random, int length) {
		StringBuilder builder = new StringBuilder();

		for (int i = 0; i < length; i++)
			builder.append(newGraph(random));

		return builder.toString();
	}
}
