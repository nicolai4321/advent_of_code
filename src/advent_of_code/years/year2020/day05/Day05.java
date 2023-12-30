package advent_of_code.years.year2020.day05;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;
import advent_of_code.utils.Log;
import advent_of_code.utils.math.MathOp;

public class Day05 extends RootDay {
    public Day05(Year year, int day) {
        super(year, day, "933", "711");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    public String run1(String input) {
        ArrayList<Integer> seatIds = new ArrayList<Integer>();
        
        for (String boardingPass : input.split("\n")) {
            String rowString = boardingPass.replaceAll("L", "").replaceAll("R", "");
            int row = getRow(rowString);
            
            String colString = boardingPass.replaceAll("F", "").replaceAll("B", "");
            int col = getCol(colString);
            
            seatIds.add(getSeatId(row, col));
        }
        
        int max = Lists.max(seatIds);
        
        return max + "";
    }
    
    @Override
    public String run2(String input) {
        HashMap<String, Integer> hM = new HashMap<String, Integer>();
        for (String boardingPass : input.split("\n")) {
            String rowString = boardingPass.replaceAll("L", "").replaceAll("R", "");
            int row = getRow(rowString);
            
            String colString = boardingPass.replaceAll("F", "").replaceAll("B", "");
            int col = getCol(colString);
            
            hM.put(boardingPass, getSeatId(row, col));
        }
        
        int seatId = findSeat(hM);
        return seatId + "";
    }

    
    private Integer findSeat(HashMap<String, Integer> hM) {
        for (String boardingPass : hM.keySet()) {
            int seatId = hM.get(boardingPass);

            if (!exists(hM, seatId - 1) && exists(hM, seatId - 2)) {
                return seatId - 1;
            }
            
            if (!exists(hM, seatId + 1) && exists(hM, seatId + 2)) {
                return seatId + 1;
            }
        }
        
        Log.show("Did not find seat");
        return null;
    }

    private boolean exists(HashMap<String, Integer> hM, int seatId) {
        for (Integer i : hM.values()) {
            if (i == seatId) {
                return true;
            }
        }
        return false;
    }

    private int getSeatId(int row, int col) {
        return row * 8 + col;
    }
    
    private int getRow(String rowString) {
        return binarySearch(0, 127, rowString, 'F', 'B');
    }
    
    private int getCol(String rowString) {
        return binarySearch(0, 7, rowString, 'L', 'R');
    }
    
    private int binarySearch(int min, int max, String rowString, char decrease, char increase) {
        if (min == max) {
            return min;
        }
        
        char c = rowString.charAt(0);
        String subString = rowString.substring(1, rowString.length());
        int diff = (MathOp.difference(min, max) + 1);
        
        if (c == decrease) {
            max -= diff / 2;
        } else if (c == increase) {
            min += diff / 2;
        }
        
        return binarySearch(min, max, subString, decrease, increase);
    }
}
