package advent_of_code.year2021.day05;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import advent_of_code.utils.Day;
import advent_of_code.utils.Read;

public class Day05 extends Day {
	@Override
	public String run1() {
		String input = getInput();
		return atLeastTwo(input, false) + "";
	}

	@Override
	public String run2() {
		String input = getInput();
		return atLeastTwo(input, true) + "";
	}
	
	private int atLeastTwo(String input, boolean allowDiagonal) {
		HashMap<String, Integer> markedGrid = new HashMap<String, Integer>();
		ArrayList<int[]> coords = getCoords(input);
		for (int[] coordPair : coords) {
			ArrayList<int[]> markedCoords = getMarkedCoords(coordPair, allowDiagonal);
			for (int[] markedCoord : markedCoords) {
				String key = markedCoord[0] + "," + markedCoord[1];
				Integer value = markedGrid.get(key);
				if (value == null) {
					markedGrid.put(key, 1);
				} else {
					markedGrid.put(key, value + 1);
				}
			}
		}
		
		int nrOfTwosMarked = 0;
		for (String key : markedGrid.keySet()) {
			if (2 <= markedGrid.get(key)) {
				nrOfTwosMarked++;
			}
		}
		return nrOfTwosMarked;
	}

	private ArrayList<int[]> getMarkedCoords(int[] coordPair, boolean allowDiagonal) {
		int x1 = coordPair[0];
		int y1 = coordPair[1];
		int x2 = coordPair[2];
		int y2 = coordPair[3];

		int minX = Math.min(x1, x2);
		int maxX = Math.max(x1, x2);
		int minY = Math.min(y1, y2);
		int maxY = Math.max(y1, y2);

		ArrayList<int[]> markedCoords = new ArrayList<int[]>();
		boolean horizontal = y1 == y2;
		boolean vertical = x1 == x2;
		boolean diagonal = (horizontal && vertical) || (!horizontal && !vertical);
		
		
		if (diagonal) {
			if (maxX - minX != maxY - minY) {
				return markedCoords;
			} else if (allowDiagonal) {
				boolean right = coordPair[0] < coordPair[2];
				boolean down = coordPair[1] < coordPair[3];

				if (right) {
					//right down
					if (down) {
						for (int i=0; i<maxX - minX + 1; i++) {
							markedCoords.add(new int[] {x1 + i, y1 + i});
						}
					//right up
					} else {
						for (int i=0; i<maxX - minX + 1; i++) {
							markedCoords.add(new int[] {x1 + i, y1 - i});
						}						
					}
				} else {
					//left down
					if (down) {
						for (int i=0; i<maxX - minX + 1; i++) {
							markedCoords.add(new int[] {x1 - i, y1 + i});
						}
					//left up
					} else {
						for (int i=0; i<maxX - minX + 1; i++) {
							markedCoords.add(new int[] {x1 - i, y1 - i});
						}
					}					
				}
			} else {
				return markedCoords;
			}
		} else if (horizontal) {
			for (int x=minX; x<=maxX; x++) {
				markedCoords.add(new int[] {x, y1});
			}
		} else {
			for (int y=minY; y<=maxY; y++) {
				markedCoords.add(new int[] {x1, y});
			}
		}
		return markedCoords;
	}
	
	private ArrayList<int[]> getCoords(String input) {
		ArrayList<int[]> coords = new ArrayList<int[]>();
		for (String line : input.split("\n")) {
			Pattern pattern = Pattern.compile("([0-9]+),([0-9]+) -> ([0-9]+),([0-9]+)");
			Matcher matcher = pattern.matcher(line);
			if (matcher.find()) {
				coords.add(new int[] {
					Integer.parseInt(matcher.group(1)),
					Integer.parseInt(matcher.group(2)),
					Integer.parseInt(matcher.group(3)),
					Integer.parseInt(matcher.group(4))
				});
			}
		}
		return coords;
	}
	
	private static String getExample() {
		return Read.getString(2021, 5, "example01.txt"); 
	}
	
	private static String getInput() {
		return Read.getString(2021, 5, "input01.txt"); 
	}
}
