package advent_of_code.year2021.day08;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.utils.Lists;
import advent_of_code.utils.Log;
import advent_of_code.utils.RootDay;

public class Day08 extends RootDay {
	public Day08() {
		super(2021, 8, "532", "1011284");
	}

	@Override
	public String run1(String input) {
		ArrayList<ArrayList<Integer>> outputValues = outputSimpleValues(input.split("\n"));
		return count(outputValues) + "";
	}

	@Override
	public String run2(String input) {
		ArrayList<Integer> outputValues = outputValues(input.split("\n"));
		return Lists.sum(outputValues) + "";
	}

	private int count(ArrayList<ArrayList<Integer>> outputValues) {
		int sum = 0;
		for (ArrayList<Integer> outputLineValues : outputValues) {
			sum += outputLineValues.size();
		}
		return sum;
	}
	
	private ArrayList<ArrayList<Integer>> outputSimpleValues(String[] lines) {
		ArrayList<ArrayList<Integer>> outputValues = new ArrayList<ArrayList<Integer>>();
		boolean outputState;
		String delimeter = "|";
		
		for (String line : lines) {
			ArrayList<Integer> outputLine = new ArrayList<Integer>();
			outputState = false;
			for (String code : line.split(" ")) {
				if (outputState) {
					if (code.length() == 2) {
						outputLine.add(1);
					} else if (code.length() == 3) {
						outputLine.add(7);
					} else if (code.length() == 4) {
						outputLine.add(4);
					} else if (code.length() == 7) {
						outputLine.add(8);
					}
				}
				
				if (code.equals(delimeter)) {
					outputState = true;
				}
			}
			outputValues.add(outputLine);
		}
		return outputValues;
	}
	
	private ArrayList<Integer> outputValues(String[] lines) {
		ArrayList<Integer> outputValues = new ArrayList<Integer>();
		for (String line : lines) {
			String decoded = "";

			ArrayList<Integer> numbers = analyzeLine(line);
			for (int n : numbers) {
				decoded += n + "";
			}
			outputValues.add(Integer.parseInt(decoded));
		}
		
		return outputValues;
	}
	
	private ArrayList<Integer> analyzeLine(String line) {
		String delimeter = "|";
		ArrayList<Integer> output = new ArrayList<Integer>();
		HashMap<Integer, Display> iMap = new HashMap<Integer, Display>();
		int repeat = 3;
		boolean outputState = false;
		String[] codes = line.split(" ");
		
		for (int i=0; i<codes.length; i++) {
			String code = codes[i];
			
			//0
			if (code.length() == 6 && 
					((containAndDifferent(iMap, 6, code) && containAndDifferent(iMap, 9, code)))) {
				iMap.put(0, new Display(code, 0));
			//1
			} else if (code.length() == 2) {
				iMap.put(1, new Display(code, 1));
			//2
			} else if (code.length() == 5 && 
					(shares(iMap, 4, code, 2) || (containAndDifferent(iMap, 3, code) && containAndDifferent(iMap, 5, code)))) {
				iMap.put(2, new Display(code, 2));
			//3
			} else if (code.length() == 5 && 
					(shares(iMap, 1, code, 2) || (containAndDifferent(iMap, 3, code) && containAndDifferent(iMap, 5, code)))) {
				iMap.put(3, new Display(code, 3));
			//4
			} else if (code.length() == 4) {
				iMap.put(4, new Display(code, 4));
			//5
			} else if (code.length() == 5 && 
					(shares(iMap, 6, code, 5) || ((containAndDifferent(iMap, 2, code) && containAndDifferent(iMap, 3, code))))) {
				iMap.put(5, new Display(code, 5));
			//6
			} else if (code.length() == 6 && 
					((containAndDifferent(iMap, 0, code) && containAndDifferent(iMap, 9, code)) || shares(iMap, 1, code, 1))) {
				iMap.put(6, new Display(code, 6));
			//7
			} else if (code.length() == 3) {
				iMap.put(7, new Display(code, 7));
			//8
			} else if (code.length() == 7) {
				iMap.put(8, new Display(code, 8));
			//9
			} else if (code.length() == 6 && 
					((containAndDifferent(iMap, 0, code) && containAndDifferent(iMap, 6, code)) || (shares(iMap, 4, code, 4)))) {
				iMap.put(9, new Display(code, 9));
			}

			if (outputState) {
				Display display = null;
				
				for (int key : iMap.keySet()) {
					Display d = iMap.get(key);
					if (d.eq(code)) {
						display = d;
						break;
					}
				}
				
				if (display == null) {
					printMap(iMap);
					throw new RuntimeException("Unknown code '" + code + "' for line '" + line + "'");
				} else {
					output.add(display.getValue());
				}
			}
			
			if (code.equals(delimeter)) {
				if (repeat < 1) {
					outputState = true;
				} else {
					repeat--;
					i = -1;
				}
			}
		}
		
		return output;
	}
	
	private boolean shares(HashMap<Integer, Display> iMap, int nr, String code, int shares) {
		return iMap.get(nr) != null && iMap.get(nr).shares(code) == shares;
	}
	
	private static void printMap(HashMap<Integer, Display> iMap) {
		for (int key : iMap.keySet()) {
			System.out.print("(" + key + "," + iMap.get(key).getCode() + ")");
		}
		Log.show("");
	}
	
	private static boolean containAndDifferent(HashMap<Integer, Display> map, int nr, String code) {
		return map.get(nr) != null && !map.get(nr).eq(code);
	}
}
