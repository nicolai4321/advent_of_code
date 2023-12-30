package advent_of_code.years.year2023.day19;

import java.util.ArrayList;

import advent_of_code.utils.Interval;
import advent_of_code.utils.Log;

public class Ranges {
    public final int[] x;
    public final int[] m;
    public final int[] a;
    public final int[] s;
    private Option option;
    
    public Ranges(int[] x, int[] m, int[] a, int[] s, Option option) {
        this.x = x;
        this.m = m;
        this.a = a;
        this.s = s;
        this.option = option;
    }
    
    public void setOption(Option option) {
        this.option = option;
    }
    
    public Option getOption() {
        return option;
    }

    /**
     * @param variable
     * @param operation
     * @param value
     * @return a list of ranges split based on the operation
     */
    public ArrayList<Ranges> split(String variable, char operation, int value) {
        int[] range = getRange(variable);
        int[][] splits = Interval.split(range, operation, value);

        Ranges rangesL = new Ranges(getAlt("x", variable, splits[0]),
                                    getAlt("m", variable, splits[0]),
                                    getAlt("a", variable, splits[0]),
                                    getAlt("s", variable, splits[0]),
                                    Option.UNKOWN);
        
        Ranges rangesR = new Ranges(getAlt("x", variable, splits[1]),
                                    getAlt("m", variable, splits[1]),
                                    getAlt("a", variable, splits[1]),
                                    getAlt("s", variable, splits[1]),
                                    Option.UNKOWN);
        
        ArrayList<Ranges> splitRanges = new ArrayList<Ranges>();
        splitRanges.add(rangesL.getNull());
        splitRanges.add(rangesR.getNull());

        return splitRanges;
    }

    /**
     * @return null if any content is null. Otherwise return object
     */
    public Ranges getNull() {
        if (anyNull()) {
            return null;
        } else {
            return this;
        }
    }
    
    public boolean anyNull() {
        return x == null || m == null || a == null || s == null;
    }
    
    private int[] getAlt(String variable, String notVariable, int[] alternative) {
        if (variable.equals(notVariable)) {
            return alternative;
        }
        
        return getRange(variable);
    }

    private int[] getRange(String variable) {
        if (variable.equals("x")) { return x; }
        if (variable.equals("m")) { return m; }
        if (variable.equals("a")) { return a; }
        if (variable.equals("s")) { return s; }
        throw new RuntimeException("Unknown variable");
    }

    public String toString() {
        return "{" + option + ": (x:[" + x[0] + "-" + x[1] + "], m:[" + m[0] + "-" + m[1] + "], a:[" + a[0] + "-" + a[1] + "], s:[" + s[0] + "-" + s[1] + "])}";
    }
    
    public void print() {
        Log.show(toString());
    }
}
