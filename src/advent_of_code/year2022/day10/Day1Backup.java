package advent_of_code.year2022.day10;

import java.util.ArrayList;

import advent_of_code.utils.Grid;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day1Backup extends RootDay {
	private String initialLine = "........................................";
	
	public Day1Backup() {
		super(false, "11720", true, null);
	}

	/**
	 * The sum of these signal strengths is 13140.
	Find the signal strength during the 20th, 60th, 100th, 140th, 180th, and 220th cycles. What is the sum of these six signal strengths?
	 */
	@Override
	public String run1() {
		String[] input = input();
		String[][] commands = getCommands(input);
		ArrayList<Integer> signalStrengths = getSignalStrengths(commands, new int[] {20, 60, 100, 140, 180, 220});
		Log.show(input);
		return sum(signalStrengths) + "";
	}

	@Override
	public String run2() {
		String[] input = input();
		String[][] commands = getCommands(input);
		drawSprite(commands, new int[] {20, 60, 100, 140, 180, 220});
		return null;
	} //not ERCREPJ

	private String printLine(int cycle, int x, int[] signalStrengths, String line) {
		for (int i : signalStrengths) {
			if (i == cycle) {
				Log.show(line);
				return initialLine;
			}
		}
		return line;
	}
	
	private void drawSprite(String[][] commands, int[] signalStrengths) {
		String pixelLine = "";
		String sprite = "";
		int cycle = 0;
		int x = 1;
		
		debug(cycle, x, pixelLine);
		for (String[] command : commands) {
			
			if (command[0].equals("noop")) {
				cycle++;
				pixelLine = drawPixel(cycle, x, pixelLine);	
			} else if (command[0].equals("addx")) {
				cycle++;
				pixelLine = drawPixel(cycle, x, pixelLine);
				cycle++;
				x += Integer.parseInt(command[1]);
				pixelLine = drawPixel(cycle, x, pixelLine);
			}
			//debug(cycle, x, pixelLine);
		}
		Log.show(pixelLine);
	}
	
	private void debug(int cycle, int x, String pixelLine) {
		String sprite = getSprite(x);
		Log.show(cycle + ":" + pixelLine);
		Log.show(cycle + ":" + sprite);
		Log.show("");
	}
	
	private String drawPixel(int cycle, int x, String currentPixels) {
		StringBuilder pixels = new StringBuilder(currentPixels);
		
		int index = mod(cycle);
		if (index == mod(x-1) || index == mod(x) || index == mod(x+1)) {
			pixels.append("#");
		} else {
			pixels.append(".");
		}

		if (cycle != 0 && mod(cycle) == 0) {
			pixels.append("\n");
		}
		
		return pixels.toString();
	}

	private String getSprite(int x) {
		int index0 = mod(x-1);
		int index1 = mod(x);
		int index2 = mod(x+1);
		
		StringBuilder newLine = new StringBuilder(initialLine);
		newLine.setCharAt(index0, '#');
		newLine.setCharAt(index1, '#');
		newLine.setCharAt(index2, '#');
		
		return newLine.toString();
	}
	
	private int mod(int i) {
		return Math.floorMod(i, 40);
	}

	private int sum(ArrayList<Integer> signalStrengths) {
		int sum = 0;
		for (int i : signalStrengths) {
			sum += i;
		}
		return sum;
	}
	
	private String[][] getCommands(String[] input) {
		String[][] commands = new String[input.length][2];
		
		for (int i=0; i<input.length; i++) {
			String[] inputArr = input[i].split(" ");
			commands[i][0] = inputArr[0];
			if (inputArr.length == 2) {
				commands[i][1] = inputArr[1];				
			}
		}
		
		return commands;
	}	
	
	private ArrayList<Integer> getSignalStrengths(String[][] commands, int[] signalStrengths) {
		ArrayList<Integer> valuesAtSignalStrengths = new ArrayList<Integer>();
		int cycle = 1;
		int x = 1;
		fillSignalStrengths(cycle, x, signalStrengths, valuesAtSignalStrengths);
		
		for (String[] command : commands) {
			
			if (command[0].equals("noop")) {
				cycle++;
			} else if (command[0].equals("addx")) {
				cycle++;
				fillSignalStrengths(cycle, x, signalStrengths, valuesAtSignalStrengths);
				cycle++;
				x += Integer.parseInt(command[1]);
			}
			fillSignalStrengths(cycle, x, signalStrengths, valuesAtSignalStrengths);

		}
		
		return valuesAtSignalStrengths;
	}
	
	private void fillSignalStrengths(int cycle, int x, int[] signalStrengths, ArrayList<Integer> valuesAtSignalStrengths) {
		for (int i : signalStrengths) {
			if (i == cycle) {
				valuesAtSignalStrengths.add(x * cycle);
			}
		}
	}
	
	private static String[] example() {
		return Read.getStrings(2022, 10, "example01.txt"); 
	}
	
	private static String[] input() {
		return Read.getStrings(2022, 10, "input01.txt"); 
	}
}
