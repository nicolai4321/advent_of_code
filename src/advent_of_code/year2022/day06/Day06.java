package advent_of_code.year2022.day06;

import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day06 extends RootDay {
    public Day06() {
        super(true, true, "1538", true, true, "2315");
    }

    @Override
    public String run1() {
        String input = input();
        return getMarker(input, 4) + "";
    }

    @Override
    public String run2() {
        String input = input();
        return getMarker(input, 14) + "";
    }
    
    private int getMarker(String signal, int nr) {
        for (int i=0; i<(signal.length()-nr); i++) {
            String marker = signal.substring(i, i+nr);
            if (contiansUniquie(marker)) {
                return i + nr;
            }
        }
        throw new RuntimeException("Could not find marker");
    }
    
    private boolean contiansUniquie(String marker) {
        char[] chars = marker.toCharArray();
        
        for (int i=0; i<chars.length; i++) {
            char c0 = chars[i];
            for (int j=0; j<chars.length; j++) {
                char c1 = chars[j];
                if (j != i && c0 == c1) {
                    return false;
                }
            }
        }
        
        return true;
    }
    
    private static String example() {
        return Read.getString(2022, 6, "example01.txt"); 
    }
    
    private static String input() {
        return Read.getString(2022, 6, "input01.txt"); 
    }
}
