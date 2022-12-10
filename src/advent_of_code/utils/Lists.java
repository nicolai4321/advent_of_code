package advent_of_code.utils;

import java.util.ArrayList;
import java.util.Collections;

public class Lists extends ListConverter {
	/**
	 * @param input
	 * @param rowsAreHorizontal
	 * @return intsOfInts
	 */
	public static int[][] getStaticToIntss(String input, boolean rowsAreHorizontal) {
		String[][] stringsOfStrings = getStatic(input.split("\n"), rowsAreHorizontal);
		return stringssToIntss(stringsOfStrings);
	}

	/**
	 * @param input
	 * @param rowsAreHorizontal
	 * @return stringsOfStrings
	 */
	public static String[][] getStaticToStringss(String input, boolean rowsAreHorizontal) {
		return getStatic(input.split("\n"), rowsAreHorizontal);
	}

	/**
	 * @param input
	 * @return stringsOfStrings
	 */
	public static String[][] getStatic(String[] input, boolean rowsAreHorizontal) {
		int rowLength = input[0].length();
		
		for (int i=1; i<input.length; i++) {
			String row = input[i];
			if (row.length() != rowLength) {
				throw new RuntimeException("The input is not static. Row 0 has length '" + rowLength + "' and row " + i + " has length '" + row.length() + "'");
			}
		}

		String[][] listOfStrings;
		if (rowsAreHorizontal) {
			listOfStrings = new String[input.length][rowLength];
			for (int r=0; r<input.length; r++) {
				char[] row = input[r].toCharArray();
				for (int c=0; c<rowLength; c++) {
					listOfStrings[r][c] = row[c] + "";
				}
			}
		} else {
			listOfStrings = new String[rowLength][input.length];
			for (int r=0; r<input.length; r++) {
				char[] row = input[r].toCharArray();
				for (int c=0; c<rowLength; c++) {
					listOfStrings[c][r] = row[c] + "";
				}
			}
		}
		
		return listOfStrings;
	}
	
	/**
	 * This is a flexible method that maps input to a list of lists
	 * 
	 * @param input
	 * @param valueWidth - the width of the value
	 * @param voidWidth - the width of the void in a cell
	 * @param cellsHorizontal - returning lists of cells with horizontal direction
	 * @param cellsReverse - returning lists in normal order
	 * @param match - the legal value for a cell if not null
	 * @param includeEmpty - empty values will be added if true
	 * @return a list of lists
	 */
	public static ArrayList<ArrayList<String>> getDynamic(String input, int valueWidth, int voidWidth, boolean cellsHorizontal, boolean cellsNormalOrder, boolean includeEmpty, String match) {
		ArrayList<ArrayList<String>> dynamicGrid = new ArrayList<ArrayList<String>>();

		for (String line : input.split("\n")) {
			if (cellsHorizontal) {
				dynamicGrid.add(new ArrayList<String>());
			}
			
			for (int i=0; i<line.length(); i += (voidWidth + valueWidth)) {
				int halfVoidLength = (voidWidth - 1) / 2;
				String value = line.substring(i + halfVoidLength, i + halfVoidLength + valueWidth);
				if (match != null && !value.matches(match)) {
					if (cellsHorizontal) {
						dynamicGrid.remove(dynamicGrid.size() - 1);
					}
					return orderList(dynamicGrid, cellsNormalOrder);
				}
				
				int index;
				if (cellsHorizontal) {
					index = dynamicGrid.size() - 1;
				} else {
					index = i / (voidWidth + valueWidth);
					if (dynamicGrid.size() <= index) {
						dynamicGrid.add(new ArrayList<String>());
					}
				}
				
				if (!includeEmpty && (value == null || value.isBlank())) {
					continue;
				}
				
				ArrayList<String> cells = dynamicGrid.get(index);
				cells.add(value);
			}
		}
		
		return orderList(dynamicGrid, cellsNormalOrder);
	}
	
	/**
	 * @param ints
	 * @return sum of the integers
	 */
	public static int sum(ArrayList<Integer> ints) {
		int sum = 0;
		for (int i : ints) {
			sum += i;
		}
		return sum;
	}
	
	/**
	 * @param ints
	 * @return sum of the integers
	 */
	public static int sum(int[] ints) {
		int sum = 0;
		for (int i : ints) {
			sum += i;
		}
		return sum;
	}
	
	/**
	 * @param <T>
	 * @param list
	 * @param reverse
	 * @return A list of lists. The sub list can be reverted
	 */
	private static <T> ArrayList<ArrayList<T>> orderList(ArrayList<ArrayList<T>> list, boolean cellsNormalOrder) {
		if (!cellsNormalOrder) {
			for (ArrayList<T> subList : list) {
				Collections.reverse(subList);
			}
		}
		return list;

	}
}
