package advent_of_code.utils;

import java.util.ArrayList;
import java.util.Arrays;

public class ListConverter {
	/**
	 * @param strings
	 * @return ints
	 */
	public static int[] stringsToInts(String[] strings) {
		int[] ints = new int[strings.length];
		for (int i=0; i<ints.length; i++) {
			int value = Integer.parseInt(strings[i]);
			ints[i] = value;
		}
		return ints;
	}
	
	/**
	 * @param strings
	 * @return bools
	 */
	public static boolean[] stringsToBools(String[] strings) {
		boolean[] bools = new boolean[strings.length];
		for (int i=0; i<bools.length; i++) {
			boolean b = Boolean.parseBoolean(strings[i]);
			bools[i] = b;
		}
		return bools;
	}
	
	/**
	 * @param strings
	 * @return arraylist of strings
	 */
	public static ArrayList<String> stringsToAlStrings(String[] strings) {
		return new ArrayList<String>(Arrays.asList(strings));
	}
	
	/**
	 * @param ints
	 * @return arraylist of ints
	 */
	public static ArrayList<Integer> intsToAlInts(int[] ints) {
		ArrayList<Integer> integers = new ArrayList<Integer>();
		for (int i : ints) {
			integers.add(i);
		}
		return integers;
	}
	
	/**
	 * @param bools
	 * @return arraylist of booleans
	 */
	public static ArrayList<Boolean> boolsToAlBools(boolean[] bools) {
		ArrayList<Boolean> booleans = new ArrayList<Boolean>();
		for (boolean b : bools) {
			booleans.add(b);
		}
		return booleans;
	}
	
	/**
	 * @param arraylist of strings
	 * @return arraylist of integers
	 */
	public static ArrayList<Integer> alStringsToAlInts(ArrayList<String> strings) {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for (String string : strings) {
			ints.add(Integer.parseInt(string));
		}
		return ints;
	}
	
	/**
	 * @param arraylist of strings
	 * @return arraylist of booleans
	 */
	public static ArrayList<Boolean> alStringsToAlBools(ArrayList<String> strings) {
		ArrayList<Boolean> bools = new ArrayList<Boolean>();
		for (String string : strings) {
			bools.add(Boolean.parseBoolean(string));
		}
		return bools;
	}
	
	/**
	 * @param arraylist of strings
	 * @return arraylist of integers
	 */
	public static ArrayList<Integer> alStringToAlInts(String[] strings) {
		int[] ints = stringsToInts(strings);
		return intsToAlInts(ints);
	}
	
	/**
	 * @param arraylist of strings
	 * @return arraylist of booleans
	 */
	public static ArrayList<Boolean> alStringToAlBools(String[] strings) {
		boolean[] bools = stringsToBools(strings);
		return boolsToAlBools(bools);
	}
	
	/**
	 * @param stringsOfStrings
	 * @return intsOfInts
	 */
	public static int[][] stringssToIntss(String[][] stringsOfStrings) {
		int rowLength = stringsOfStrings.length;
		int colLength = stringsOfStrings[0].length;
		int[][] intsOfInts = new int[rowLength][colLength];
		
		for (int r=0; r<rowLength; r++) {
			String[] row = stringsOfStrings[r];
			for (int c=0; c<colLength; c++) {
				intsOfInts[r][c] = Integer.parseInt(row[c]);
			}
		}
		return intsOfInts;
	}
	
	/**
	 * @param stringsOfStrings
	 * @return boolsOfBools
	 */
	public static boolean[][] stringssToBools(String[][] stringsOfStrings) {
		int rowLength = stringsOfStrings.length;
		int colLength = stringsOfStrings[0].length;
		boolean[][] boolsOfBools = new boolean[rowLength][colLength];
		
		for (int r=0; r<rowLength; r++) {
			String[] row = stringsOfStrings[r];
			for (int c=0; c<colLength; c++) {
				boolsOfBools[r][c] = Boolean.parseBoolean(row[c]);
			}
		}
		return boolsOfBools;
	}
	
	/**
	 * @param stringsOfStrings
	 * @return boolsOfBools
	 */
	public static ArrayList<ArrayList<Boolean>> alStringssToAlBoolss(ArrayList<ArrayList<String>> stringsOfStrings) {
		ArrayList<ArrayList<Boolean>> boolsOfBools = new ArrayList<ArrayList<Boolean>>();
		for (ArrayList<String> strings: stringsOfStrings) {
			ArrayList<Boolean> bools = alStringsToAlBools(strings);
			boolsOfBools.add(bools);
		}
		return boolsOfBools;
	}
	
	/**
	 * @param stringsOfStrings
	 * @return intsOfInts
	 */
	public static ArrayList<ArrayList<Integer>> alStringssToAlInts(ArrayList<ArrayList<String>> stringsOfStrings) {
		ArrayList<ArrayList<Integer>> intsOfInts = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<String> strings : stringsOfStrings) {
			ArrayList<Integer> ints = alStringsToAlInts(strings);
			intsOfInts.add(ints);
		}
		return intsOfInts;
	}
}
