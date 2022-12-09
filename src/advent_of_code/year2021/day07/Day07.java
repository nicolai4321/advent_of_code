package advent_of_code.year2021.day07;

import advent_of_code.utils.Day;
import advent_of_code.utils.Lists;
import advent_of_code.utils.Read;

public class Day07 extends Day {
	@Override
	public String run1() {
		int[] input = Lists.stringsToInts(input());
		int cost = getCheapestAlignment(input, true);
		return cost + "";
	}

	@Override
	public String run2() {
		int[] input = Lists.stringsToInts(input());
		int cost = getCheapestAlignment(input, false);
		return cost + "";
	}
	
	/**
	 * @param positions
	 * @param simple
	 * @return cheapest alignment
	 */
	private int getCheapestAlignment(int[] positions, boolean simpleCost) {
		int highestAlignment = 0;
		int bestCost = Integer.MAX_VALUE;
		
		for (int i : positions) {
			if (highestAlignment < i) {
				highestAlignment = i;
			}
		}
		
		for (int alignment=0; alignment<highestAlignment; alignment++) {
			int cost = 0;
			for (int position : positions) {
				if (simpleCost) {
					cost += getSimpleCost(alignment, position); 					
				} else {
					cost += getComplicatedCost(alignment, position); 										
				}
			}
			if (cost < bestCost) {
				bestCost = cost;
			}
		}
		
		return bestCost;
	}
	
	/**
	 * @param alignment
	 * @param position
	 * @return simple cost
	 */
	private int getSimpleCost(int alignment, int position) {
		return Math.max(alignment, position) - Math.min(alignment, position);
	}
	
	/**
	 * @param alignment
	 * @param position
	 * @return complicated cost
	 */
	private int getComplicatedCost(int alignment, int position) {
		int difference = Math.max(alignment, position) - Math.min(alignment, position);
		int cost = 0;
		
		for (int i=1; i<=difference; i++) {
			cost += i;
		}
		
		return cost;
	}
	
	@Override
	protected boolean test() {
		return run1().equals("337488") && run2().equals("89647695");
	}
	
	private static String[] example() {
		return Read.getString(2021, 7, "example01.txt").split(","); 
	}
	
	private static String[] input() {
		return Read.getString(2021, 7, "input01.txt").split(","); 
	}
}
