package advent_of_code.utils;

import java.util.ArrayList;
import java.util.Collections;

public class Lists {
	/**
	 * @param listOfStrings
	 * @return list of integers
	 */
	public static ArrayList<ArrayList<Integer>> toListOfInts(ArrayList<ArrayList<String>> listOfStrings) {
		ArrayList<ArrayList<Integer>> ints = new ArrayList<ArrayList<Integer>>();
		for (ArrayList<String> strings : listOfStrings) {
			ints.add(toInts(strings));
		}
		return ints;
	}
	
	/**
	 * @param strings
	 * @return integers
	 */
	public static ArrayList<Integer> toInts(ArrayList<String> strings) {
		ArrayList<Integer> ints = new ArrayList<Integer>();
		for (String string : strings) {
			ints.add(Integer.parseInt(string));
		}
		return ints;
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
					return getList(dynamicGrid, cellsNormalOrder);
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
		
		return getList(dynamicGrid, cellsNormalOrder);
	}
	
	/**
	 * @param <T>
	 * @param list
	 * @param reverse
	 * @return A list of lists. The sub list can be reverted
	 */
	private static <T> ArrayList<ArrayList<T>> getList(ArrayList<ArrayList<T>> list, boolean cellsNormalOrder) {
		if (!cellsNormalOrder) {
			for (ArrayList<T> subList : list) {
				Collections.reverse(subList);
			}
		}
		return list;
	}
}
