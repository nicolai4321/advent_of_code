package advent_of_code.year2022.day03;

import java.util.ArrayList;

import advent_of_code.utils.Day;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;

public class Day03 extends Day {
	@Override
	public String run1() {
		String[] rucksacks = input();
		
		int points = 0;
		for(String rucksack : rucksacks) {
			char c = findCommon(rucksack);
			points += getPoint(c);
		}
		
		return points + "";
	}
	
	@Override
	public String run2() {
		String[] rucksacks = input();
		
		ArrayList<ArrayList<String>> groups = new ArrayList<ArrayList<String>>();
		
		int counter = 0;
		for (int i=0; i<rucksacks.length; i++) {
			if (counter == 2) {
				ArrayList<String> group = new ArrayList<String>();
				group.add(rucksacks[i]);
				group.add(rucksacks[i-1]);
				group.add(rucksacks[i-2]);
				
				groups.add(group);
				
				counter = 0;
			} else {
				counter++;	
			}			
		}
		
		int points = 0;
		for (ArrayList<String> group : groups) {
			char c = findCommons(group);
			points += getPoint(c);
		}
		
		return points + "";
	}
	
	public static int getPoint(char c) {
		int point;
		
		if (Character.isLowerCase(c)) {
			point = (int) c - 96;
		} else {
			point = (int) c - 38;	
		}
		
		return point;
	}
	
	
	public static char findCommons(ArrayList<String> group) {
		for (char c : group.get(0).toCharArray()) {
			
			boolean allHaveIt = true;
			for (String elf : group) {
				if (!elf.contains(c + "")) {
					allHaveIt = false;
				}
			}
			if (allHaveIt) {
				return c;
			}
		}
		
		throw new RuntimeException("No char");
	}
	
	
	public static char findCommon(String rucksack) {
		int length = rucksack.length();
		String com1 = rucksack.substring(0, length/2);
		String com2 = rucksack.substring(length/2, length);
		
		for (char c : com1.toCharArray()) {
			if (com2.contains(c+"")) {
				return c;
			}
		}
		
		throw new RuntimeException("No char");
	}

	@Override
	protected boolean test() {
		if (!run1().equals("7716")) {
			Log.show("run1() is false");
			return false;
		}
		
		return true;
	}

	private static String[] example() {
		return Read.getStrings(2022, 3, "example01.txt"); 
	}
	
	private static String[] input() {
		return Read.getStrings(2022, 3, "input01.txt"); 
	}
}
