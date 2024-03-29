package advent_of_code.years.year2021.day06;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;

public class Day06 extends RootDay {
    public Day06(Year year, int day) {
        super(year, day, "365862", "1653250886439");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    protected String run1(String input) {
        String[] strings = input.split(",");
        ArrayList<Integer> lst = Lists.alStringToAlInts(strings);
        int days = 80;
        return getTotalLanternFish(lst, days) + "";
    }

    @Override
    protected String run2(String input) {
        String[] strings = input.split(",");
        ArrayList<Integer> lst = Lists.alStringToAlInts(strings);
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
}
