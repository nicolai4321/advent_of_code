package advent_of_code.years.year2022.day10;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;
import advent_of_code.utils.Log;

public class Day10 extends RootDay {
    public Day10(Year year, int day) {
        super(year, day, "11720", null); //answer for run2=ERCREPCJ
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[][] commands = getCommands(input);
        ArrayList<Integer> signalStrengths = drawSprite(commands);
        return Lists.sum(signalStrengths) + "";
    }

    @Override
    public String run2(String input) {
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
    
    private String[][] getCommands(String input) {
        String[] inputs = input.split("\n");
        String[][] commands = new String[inputs.length][2];
        
        for (int i=0; i<inputs.length; i++) {
            String[] inputArr = inputs[i].split(" ");
            commands[i][0] = inputArr[0];
            if (inputArr.length == 2) {
                commands[i][1] = inputArr[1];                
            }
        }
        
        return commands;
    }
}
