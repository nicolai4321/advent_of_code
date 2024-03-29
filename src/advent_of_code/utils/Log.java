package advent_of_code.utils;

import java.util.ArrayList;

public class Log {
	private static boolean enabled = true;

	/**
	 * Quick way to show file
	 * @param year
	 * @param day
	 * @param fileName
	 */
	public static void showFile(int year, int day, String fileName) {
		String content = Read.getString(year, day, fileName);
		show(content);
	}
	
	/**
	 * Show content
	 * @param <T>
	 * @param type
	 */
	public static <T> void show(T type) {
		println(type);
	}
	
	/**
	 * Show content of array
	 * @param <T>
	 * @param types
	 */
	public static <T> void show(T[] types) {
		String s = "[";
		for (T type : types) {
			s += type + ",";
		}
		s = s.replaceAll(",$", "]\n");
		print(s);
	}
	
	/**
	 * @param numbers
	 */
	public static void show(int[] numbers) {
		for (int nr : numbers) {
			print(nr + ",");
		}
		println();
	}
	
	/**
	 * Show content of array list
	 * @param <T>
	 * @param lst
	 */
	public static <T> void show(ArrayList<T> lst) {
	    if (lst.isEmpty()) {
	        println("[]");
	        return;
	    }
	    
		String s = "[";
		for (T type : lst) {
			s += type + ",";
		}
		s = s.replaceAll(",$", "]");
		println(s);
	}
	
	/**
	 * Show content of array list
	 * @param <T>
	 * @param lst
	 */
	public static <T> void showln(ArrayList<T> lst) {
		String s = "[";
		for (T type : lst) {
			s += type + ",\n";
		}
		s = s.replaceAll(",$", "]");
		println(s);
	}
	/**
	 * Show content of an array of arrays
	 * @param <T>
	 * @param typesOfTypes
	 */
	public static <T> void showMatrix(T[][] typesOfTypes) {
		String s = "[";
		boolean first = true;
		
		for (T[] types : typesOfTypes) {
			if (!first) {
				s += " ";
			}
			
			s += "[";
			for (T type : types) {
				s += type + ",";
			}
			s = s.replaceAll(",$", "],\n");
			first = false;
		}
		s = s.replaceAll(",\n$", "]");
		println(s);
	}
	
	public static void warn(String string) {
		printWarn(string);
	}
	
	/**
	 * Print if enabled
	 * @param s
	 */
	private static void printWarn(String s) {
		if (enabled) {
			System.err.println(s);
		}
	}
	
	/**
	 * Print if enabled
	 * @param s
	 */
	private static void print(String s) {
		if (enabled) {
			System.out.print(s);
		}
	}

	/**
	 * Print if enabled
	 * @param s
	 */
	private static <T> void println(T type) {
		if (enabled) {
			System.out.println(type);
		}
	}

	/**
	 * Print new line if enabled
	 */
	private static void println() {
		if (enabled) {
			System.out.println();
		}
	}
	
	/**
	 * Disable log
	 */
	public static void disable() {
		enabled = false;
	}

	/**
	 * Enable log
	 */
	public static void enable() {
		enabled = true;
	}
}
