package advent_of_code.utils;

import java.util.ArrayList;

public class Log {
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
		System.out.println(type);
	}
	
	/**
	 * Show content of array
	 * @param <T>
	 * @param types
	 */
	public static <T> void show(T[] types) {
		for (T type : types) {
			System.out.print(type + ",");
		}
		System.out.println();
	}
	
	/**
	 * @param numbers
	 */
	public static void show(int[] numbers) {
		for (int nr : numbers) {
			System.out.print(nr + ",");
		}
		System.out.println();
	}
	
	/**
	 * Show content of array list
	 * @param <T>
	 * @param lst
	 */
	public static <T> void show(ArrayList<T> lst) {
		for (T type : lst) {
			System.out.print(type + ",");
		}
		System.out.println();
	}
}
