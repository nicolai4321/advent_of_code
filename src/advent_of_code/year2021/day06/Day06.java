package advent_of_code.year2021.day06;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.utils.Day;
import advent_of_code.utils.Lists;
import advent_of_code.utils.Read;

public class Day06 extends Day {
	@Override
	public String run1() {
		String[] input = input().split(",");
		ArrayList<Integer> lst = Lists.alStringToAlInts(input);
		int days = 80;
		return getTotalLanternFish(lst, days) + "";
	}

	@Override
	public String run2() {
		String[] input = input().split(",");
		ArrayList<Integer> lst = Lists.alStringToAlInts(input);
		int days = 256;
		return getTotalLanternFish(lst, days) + "";
	}
	
	/**
	 * @param lanternFish
	 * @param days
	 * @return total amount of lantern fish
	 */
	private long getTotalLanternFish(ArrayList<Integer> lanternFish, int days) {
		HashMap<Integer, Long> fishMap = mapFish(lanternFish);
		
		for (int day=0; day<days; day++) {
			HashMap<Integer, Long> tmpFishMap = new HashMap<Integer, Long>();	
			
			for (int state=8; state>=0; state--) {
				Long nrFish = fishMap.get(state);
				if (nrFish == null) {
					continue;
				}

				if (state == 0) {
					Long tmpNrFish = tmpFishMap.get(6);
					tmpNrFish = (tmpNrFish == null) ? 0 : tmpNrFish;
					tmpFishMap.put(6, nrFish + tmpNrFish);
					tmpFishMap.put(8, nrFish);
				} else {
					tmpFishMap.put(state - 1, nrFish);
				}
			}
			fishMap = tmpFishMap;
		}
		
		long sum = 0;
		for (Integer state : fishMap.keySet()) {
			sum += fishMap.get(state);
		}
		return sum;
	}
	
	/**
	 * @param lanternFish
	 * @return map of states and numbers of fish
	 */
	private HashMap<Integer, Long> mapFish(ArrayList<Integer> lanternFish) {
		HashMap<Integer, Long> fishMap = new HashMap<Integer, Long>();
		for (int value : lanternFish) {
			Long nrFish = fishMap.get(value);
			nrFish = (nrFish == null) ? 0 : nrFish;
			fishMap.put(value, 1 + nrFish);
		}
		return fishMap;
	}
	
	@Override
	protected boolean test() {
		return run1().equals("365862") && run2().equals("1653250886439");
	}
	
	private static String example() {
		return Read.getString(2021, 6, "example01.txt"); 
	}
	
	private static String input() {
		return Read.getString(2021, 6, "input01.txt"); 
	}
}
