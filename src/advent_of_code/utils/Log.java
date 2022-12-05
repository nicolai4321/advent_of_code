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
		for (T type : types) {
			print(type + ",");
		}
		println();
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
		for (T type : lst) {
			print(type + ",");
		}
		println();
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
