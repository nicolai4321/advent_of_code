package advent_of_code.utils;

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
	 * Quick way to show text
	 * @param number
	 */
	public static void show(int number) {
		show(number + "");
	}
	
	/**
	 * Quick way to show text
	 * @param text
	 */
	public static void show(String text) {
		System.out.println(text);
	}
	
	/**
	 * Quick way to show text
	 * @param text
	 */
	private static void showOneLine(String text) {
		System.out.print(text);
	}

	/**
	 * Quick way to show strings
	 * @param strings
	 */
	public static void show(String[] strings) {
		for (String s : strings) {
			showOneLine(s+ ",");
		}
		System.out.println();
	}
	
	/**
	 * Quick way to show numbers
	 * @param numbers
	 */
	public static void show(int[] numbers) {
		for (int number : numbers) {
			showOneLine(number + ",");
		}
		System.out.println();
	}
}
