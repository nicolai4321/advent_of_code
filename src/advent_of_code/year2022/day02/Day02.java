package advent_of_code.year2022.day02;

import advent_of_code.utils.Day;
import advent_of_code.utils.Read;

public class Day02 implements Day {
	@Override
	public String run1() {
		String[] input = input();
		return getScore(input, true) + "";
	}

	@Override
	public String run2() {
		String[] input = input();
		return getScore(input, false) + "";
	}
	
	private int getScore(String[] rounds, boolean map1) {
		int points = 0;
		for (String round : rounds) {
			String opp = round.split(" ")[0];
			String you = round.split(" ")[1];
			if (map1) {
				points += getPoints(map1(opp), map1(you));				
			} else {
				points += getPoints(map1(opp), map2(opp, you));
			}
		}
		return points;
	}
	
	private static String map1(String s) {
		if (s.equals("A") || s.equals("X")) {
			return "r";
		} else if(s.equals("B") || s.equals("Y")) {
			return "p";
		} else {
			return "s";
		}
	}
	
	private static String map2(String opp, String you) {
		if (opp.equals("A")) {
			if (you.equals("X")) {
				return "s";
			} else if (you.equals("Y")) {
				return "r";
			} else { //Z
				return "p";
			}
		} else if(opp.equals("B")) {
			if (you.equals("X")) {
				return "r";
			} else if (you.equals("Y")) {
				return "p";
			} else { //Z
				return "s";
			}
		} else { //C
			if (you.equals("X")) {
				return "p";
			} else if (you.equals("Y")) {
				return "s";
			} else { //Z
				return "r";
			}
		}
	}
	
	private static int getPoints(String opp, String you) {
		return getPointsForItem(you) + getWinningOutcome(opp, you);
	}
	
	public static int getPointsForItem(String you) {
		if (you.equals("r")) {
			return 1;
		} else if (you.equals("p")) {
			return 2;
		} else { //s
			return 3;
		}
	}
	
	public static int getWinningOutcome(String opp, String you) {
		if (opp.equals("r")) {
			if (you.equals("r")) {
				return 3;
			} else if (you.equals("p")) {
				return 6;
			} else { //s
				return 0;
			}
		} else if (opp.equals("p")) {
			if (you.equals("r")) {
				return 0;
			} else if (you.equals("p")) {
				return 3;
			} else { //s
				return 6;
			}
		} else { //s
			if (you.equals("r")) {
				return 6;
			} else if (you.equals("p")) {
				return 0;
			} else { //s
				return 3;
			}
		}
	}
	
	private static String[] example() {
		return Read.getStrings(2022, 2, "example01.txt"); 
	}
	
	private static String[] input() {
		return Read.getStrings(2022, 2, "input01.txt"); 
	}
}
