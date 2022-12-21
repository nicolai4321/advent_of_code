package advent_of_code.year2022.day05;

import java.util.ArrayList;

import advent_of_code.utils.Lists;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day05 extends RootDay {
    public Day05() {
        super(true, true, "QMBMJDFTD", true, true, "NBTVTJNFJ");
    }

    @Override
    public String run1() {
        String input = input();
        ArrayList<ArrayList<String>> stacks = Lists.getDynamic(input, 1, 3, false, false, false, "[a-zA-Z\\s]");
        moveFromInput(stacks, input, false);

        return output(stacks);
    }
    
    @Override
    public String run2() {
        String input = input();
        ArrayList<ArrayList<String>> stacks = Lists.getDynamic(input, 1, 3, false, false, false, "[a-zA-Z\\s]");
        moveFromInput(stacks, input, true);
        
        return output(stacks);
    }
    
    /**
     * @param stacks
     * @return output from stacks
     */
    private String output(ArrayList<ArrayList<String>> stacks) {
        String output = "";
        for (ArrayList<String> col : stacks) {
            output += col.get(col.size() - 1);
        }
        return output;
    }
    
    /**
     * Move values given the input
     * @param stacks
     * @param input
     * @param moveStack
     */
    private static void moveFromInput(ArrayList<ArrayList<String>> stacks, String input, boolean moveStack) {
        for (String line : input.split("\n")) {
            if (line.contains("move")) {
                int nr = Integer.parseInt(line.replaceAll("move ", "").replaceAll("from .*", "").strip());
                int fromIndex = Integer.parseInt(line.replaceAll(".* from", "").replaceAll("to .*", "").strip()) -1;
                int toIndex = Integer.parseInt(line.replaceAll(".* to ", "").strip()) - 1;
                
                if (moveStack) {
                    moveStack(stacks, nr, fromIndex, toIndex);
                } else {
                    move(stacks, nr, fromIndex, toIndex);
                }
            }
        }
    }
    
    /**
     * Move number of values from one column to another column
     * @param stacks
     * @param nr
     * @param fromIndex
     * @param toIndex
     */
    private static void move(ArrayList<ArrayList<String>> stacks, int nr, int fromIndex, int toIndex) {
        for (int i=0; i<nr; i++) {
            String c = remove(stacks, fromIndex);
            if (c != null) {
                insert(stacks, toIndex, c);                
            }
        }
    }
    
    /**
     * Move stack of values from one column to another column
     * @param stacks
     * @param nr
     * @param fromIndex
     * @param toIndex
     */
    private static void moveStack(ArrayList<ArrayList<String>> stacks, int nr, int fromIndex, int toIndex) {
        String[] values = new String[nr];
        for (int i=0; i<nr; i++) {
            String value = remove(stacks, fromIndex);
            values[i] = value;
        }

        for (int i=values.length-1; 0<=i; i--) {
            String value = values[i];
            if (value != null) {
                insert(stacks, toIndex, value);
            }
        }
    }

    /**
     * Insert element to a column
     * @param stacks
     * @param toIndex
     * @param c
     */
    private static void insert(ArrayList<ArrayList<String>> stacks, int toIndex, String c) {
        ArrayList<String> col = stacks.get(toIndex);
        col.add(c);
    }
    
    /**
     * Remove element from a column
     * @param stacks
     * @param fromIndex
     * @return removed element
     */
    private static String remove(ArrayList<ArrayList<String>> stacks, int fromIndex) {
        ArrayList<String> col = stacks.get(fromIndex);
        int index = col.size() - 1;
        String value = col.get(index);
        if (stacks.size() == 0) {
            return null;
        }
        col.remove(index);
        return value;
    }
    
    private static String example() {
        return Read.getString(2022, 5, "example01.txt"); 
    }
    
    private static String input() {
        return Read.getString(2022, 5, "input01.txt"); 
    }
}
