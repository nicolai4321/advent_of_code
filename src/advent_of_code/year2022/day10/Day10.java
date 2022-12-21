package advent_of_code.year2022.day10;

import java.util.ArrayList;

import advent_of_code.utils.Lists;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day10 extends RootDay {
    public Day10() {
        super(true, true, "11720", true, true, null); //answer for run2=ERCREPCJ
    }

    @Override
    public String run1() {
        String[] input = input();
        String[][] commands = getCommands(input);
        ArrayList<Integer> signalStrengths = drawSprite(commands);
        return Lists.sum(signalStrengths) + "";
    }

    @Override
    public String run2() {
        String[] input = input();
        String[][] commands = getCommands(input);
        drawSprite(commands);
        return null;
    }
    
    /**
     * Draw sprite given the commands
     * @param commands
     * @return signalStrengths
     */
    private ArrayList<Integer> drawSprite(String[][] commands) {
        ArrayList<Integer> signalStrengths = new ArrayList<Integer>();
        String pixels = "";
        int cycle = 1;
        int x = 1;
        
        for (String[] command : commands) {
            
            if (command[0].equals("noop")) {
                pixels = addPixel(cycle, x, pixels);
            } else if (command[0].equals("addx")) {
                pixels = addPixel(cycle, x, pixels);
                cycle++;
                calculateSignalStrengts(cycle, x, signalStrengths);
                x += Integer.parseInt(command[1]);
                pixels = addPixel(cycle, x, pixels);
            }
            
            cycle++;
            calculateSignalStrengts(cycle, x, signalStrengths);
        }
        Log.show(pixels);
        return signalStrengths;
    }
    
    /**
     * Add signal strengths
     * @param cycle
     * @param x
     * @param signalStrengths
     */
    private void calculateSignalStrengts(int cycle, int x, ArrayList<Integer> signalStrengths) {
        if ((cycle + 20) % 40 == 0) {
            signalStrengths.add(cycle * x);
        }
    }
    
    /**
     * @param cycle
     * @param x
     * @param pixels
     * @return pixels
     */
    private String addPixel(int cycle, int x, String pixels) {
        int index = cycle % 40;
        if (index == (x-1) % 40 || index == x % 40 || index == (x+1) % 40) {
            pixels += "#";
        } else {
            pixels += ".";
        }

        if (index == 0) {
            pixels += "\n";
        }
        
        return pixels;
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
    
    private static String[] example() {
        return Read.getStrings(2022, 10, "example01.txt"); 
    }
    
    private static String[] input() {
        return Read.getStrings(2022, 10, "input01.txt"); 
    }
}
