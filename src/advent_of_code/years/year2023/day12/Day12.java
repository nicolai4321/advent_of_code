package advent_of_code.years.year2023.day12;

import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;
import advent_of_code.utils.math.big.BigInt;

public class Day12 extends RootDay {
    private HashMap<String, BigInt> cache = new HashMap<String, BigInt>();

    public Day12(Year year, int day) {
        super(year, day, "7916", "37366887898686");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    public String run1(String input) {
        String[] lines = input.split("\n");
        return findNrArrangements(lines) + "";
    }


    @Override
    public String run2(String input) {
        input = modifyInput(input);
        String[] lines = input.split("\n");
        return findNrArrangements(lines) + "";
    }
    
    private BigInt findNrArrangements(String[] lines) {
        BigInt total = new BigInt("0");
        for (String line : lines) {
            String cfg = line.split(" ")[0];
            int[] nums = Lists.stringsToInts(line.split(" ")[1].split(","));
            total = total.add(count(cfg, nums));
        }
        
        return total;
    }
    
    private BigInt count(String pattern, int[] nums) {
        if (pattern.equals("")) {
            if (nums.length == 0) {
                return new BigInt(1);
            } else {
                return new BigInt(0);
            }
            
        }

        if (nums.length == 0) {
            if (pattern.contains("#")) {
                return new BigInt(0);
            } else {
                return new BigInt(1);
            }
        }
        
        String key = pattern + intsToString(nums);
        if (cache.containsKey(key)) {
            return cache.get(key);
        }
        
        BigInt result = new BigInt(0);
        
        char c = pattern.charAt(0);
        if (c == '.' || c == '?') {
            result = result.add(count(pattern.substring(1, pattern.length()) , nums));
        }
        
        if (c == '#' || c == '?') {
            if (nums[0] <= pattern.length() && 
                !pattern.substring(0, nums[0]).contains(".") &&
                (nums[0] == pattern.length() || pattern.charAt(nums[0]) != '#')) {
                String newCfg = pattern.substring(Math.min(nums[0] + 1, pattern.length()), pattern.length());
                result = result.add(count(newCfg, intsCut(nums)));
            }
        }
        
        cache.put(key, result);
        return result;
    }
    
    private int[] intsCut(int[] ints) {
        int[] cut = new int[ints.length - 1];
        
        for (int i=0; i<cut.length; i++) {
           cut[i] = ints[i + 1];
        }
        
        return cut;
    }
    
    private String intsToString(int[] nums) {
        String s = "";
        
        for (int i : nums) {
            s += i + ",";
        }
        
        return s;
    }

    private String modifyInput(String input) {
        String newInput = "";
        
        for (String line : input.split("\n")) {
            String pattern = line.split(" ")[0];
            pattern = pattern + "?" + pattern + "?" + pattern + "?" + pattern + "?" + pattern;

            String numbers = line.split(" ")[1];
            numbers = numbers + "," + numbers + "," + numbers + "," + numbers + "," + numbers;
        
            newInput += pattern + " " + numbers + "\n";
        }
        
        return newInput.replaceAll("\n$", "");
    }
}
