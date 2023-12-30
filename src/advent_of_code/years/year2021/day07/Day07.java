package advent_of_code.years.year2021.day07;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;

public class Day07 extends RootDay {
    public Day07(Year year, int day) {
        super(year, day, "337488", "89647695");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    protected String run1(String input) {
        int[] ints = Lists.stringsToInts(input.split(","));
        int cost = getCheapestAlignment(ints, true);
        return cost + "";
    }

    @Override
    protected String run2(String input) {
        int[] ints = Lists.stringsToInts(input.split(","));
        int cost = getCheapestAlignment(ints, false);
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
}
