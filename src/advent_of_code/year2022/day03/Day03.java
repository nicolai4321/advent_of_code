package advent_of_code.year2022.day03;

import java.util.ArrayList;

import advent_of_code.utils.RootDay;

public class Day03 extends RootDay {
    public Day03() {
        super(2022, 3, "7716", "2973");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[] rucksacks = input.split("\n");
        
        int points = 0;
        for(String rucksack : rucksacks) {
            char c = findCommon(rucksack);
            points += getPoint(c);
        }
        
        return points + "";
    }
    
    @Override
    public String run2(String input) {
        String[] rucksacks = input.split("\n");
        
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
    
    /**
     * @param c
     * @return points given char c
     */
    public static int getPoint(char c) {
        int point;
        
        if (Character.isLowerCase(c)) {
            point = (int) c - 96;
        } else {
            point = (int) c - 38;    
        }
        
        return point;
    }
    
    /**
     * @param group
     * @return common char in group
     */
    public static char findCommons(ArrayList<String> group) {
        for (char c : group.get(0).toCharArray()) {
            
            boolean common = true;
            for (String elf : group) {
                if (!elf.contains(c + "")) {
                    common = false;
                }
            }
            if (common) {
                return c;
            }
        }
        
        throw new RuntimeException("No common item between groups");
    }
    
    /**
     * @param rucksack
     * @return common char in rucksack
     */
    public static char findCommon(String rucksack) {
        int length = rucksack.length();
        String rucksack0 = rucksack.substring(0, length/2);
        String rucksack1 = rucksack.substring(length/2, length);
        
        for (char c : rucksack0.toCharArray()) {
            if (rucksack1.contains(c + "")) {
                return c;
            }
        }
        
        throw new RuntimeException("No common item in rucksack");
    }
}
