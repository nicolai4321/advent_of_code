package advent_of_code.utils;

import java.util.ArrayList;

import advent_of_code.utils.math.MathOp;
import advent_of_code.utils.math.big.BigInt;

public class Interval {
    /**
     * @param a
     * @param b
     * @return true if range a and b overlap
     */
    public static boolean intervalOverlap(int[] a, int[] b) {
        int a0 = Math.min(a[0], a[1]);
        int a1 = Math.max(a[0], a[1]);

        int b0 = Math.min(b[0], b[1]);
        int b1 = Math.max(b[0], b[1]);
        
        return Math.max(a0, b0) <= Math.min(a1, b1);
    }
    
    /**
     * @param a
     * @param b
     * @return true if range a and b overlap
     */
    public static boolean intervalOverlap(BigInt[] a, BigInt[] b) {
        BigInt a0 = BigInt.min(a[0], a[1]);
        BigInt a1 = BigInt.max(a[0], a[1]);

        BigInt b0 = BigInt.min(b[0], b[1]);
        BigInt b1 = BigInt.max(b[0], b[1]);
        
        return BigInt.max(a0, b0).leq(BigInt.min(a1, b1));
    }
    
    /**
     * @param intervals
     * @return merged intervals
     */
    public static ArrayList<int[]> mergeIntervals(ArrayList<int[]> intervalsOri) {
        ArrayList<int[]> intervals = new ArrayList<int[]>();
        for (int[] i : intervalsOri) {
            intervals.add(i);
        }
        
        ArrayList<int[]> mergedIntervals = new ArrayList<int[]>();
        for (int i=0; i<intervals.size(); i++) {
            int[] interval0 = intervals.get(i);
            intervals.remove(i);
            i--;
            
            if (i+1 < intervals.size()) {
                for (int j=(i+1); j<intervals.size(); j++) {
                    int[] interval1 = intervals.get(j);
                    if (intervalOverlap(interval0, interval1)) {
                        interval0 = new int[] {Math.min(interval0[0], interval1[0]), Math.max(interval0[1], interval1[1])};
                        intervals.remove(j);
                        j--;
                    }
                }
            }
            
            for (int j=0; j<mergedIntervals.size(); j++) {
                int[] interval1 = mergedIntervals.get(j);
                if (intervalOverlap(interval0, interval1)) {
                    interval0 = new int[] {Math.min(interval0[0], interval1[0]), Math.max(interval0[1], interval1[1])};
                    mergedIntervals.remove(j);
                    j--;
                }
            }
            mergedIntervals.add(interval0);
        }
        
        return mergedIntervals;
    }
    
    /**
     * @param intervals
     * @return unique numbers in interval
     */
    public static int uniqueNumbersInInterval(ArrayList<int[]> intervals) {
        ArrayList<int[]> mergedIntervals = mergeIntervals(intervals);

        int value = 0;
        for (int[] r : mergedIntervals) {
            value += MathOp.difference(r[0], r[1]) + 1;
        }
        return value;
    }

    /**
     * @param x
     * @param interval
     * @return true if x is in interval
     */
    public static boolean inInterval(int x, int[] interval) {
        return interval[0] <= x && x <= interval[1];
    }
    
    /**
     * @param ints
     * @return size of interval
     */
    public static int getSize(Integer[] ints) {
        if (ints == null) {
            return 0;
        }
        
        return ints[1] - ints[0] + 1;
    }
    
    /**
     * @param ints
     * @return size of interval
     */
    public static int getSize(int[] ints) {
        return ints[1] - ints[0] + 1;
    }

    /**
     * Splits range in two: [range0] value operator [range1]
     * @param range
     * @param string
     */
    public static int[][] split(int[] range, char operator, int value) {
        if (operator == '<') {
            if (value <= range[0]) {
                return new int[][] { null, range };
            }
            
            if (range[0] < value && value < range[1]) {
                return new int[][] { {range[0], value-1 }, { value, range[1] }};
            }
            
            if (range[1] <= value) {
                return new int[][] { range, null };
            }
        }
                
        if (operator == '>') {
            if (value >= range[1]) {
                return new int[][] { null, range };
            }
            
            if (range[0] < value && value < range[1]) {
                return new int[][] { { value+1, range[1] }, {range[0], value } };
            }
            
            if (range[1] >= value) {
                return new int[][] { range, null };
            }
        }

        throw new RuntimeException("Could not split range");
    }

    public static BigInt[] getOverlap(BigInt[] range0, BigInt[] range1) {
        if (!intervalOverlap(range0, range1)) {
            return null;
        }
        
        BigInt[] overlap = new BigInt[2];
        
        //in between
        if (range1[0].leq(range0[0]) && range0[1].leq(range1[1])) {
            overlap[0] = range0[0];
            overlap[1] = range0[1];

        //in between
        } else if (range0[0].leq(range1[0]) && range1[1].leq(range0[1])) {
            overlap[0] = range1[0];
            overlap[1] = range1[1];
        
        //most left
        } else if (range0[0].leq(range1[0])) {
            overlap[0] = range1[0];
            overlap[1] = BigInt.min(range0[1], range1[1]);

        //most right
        } else if (range1[1].leq(range0[1])) {
            overlap[0] = BigInt.max(range0[0], range1[0]);
            overlap[1] = range1[1];
        } else {
            throw new RuntimeException("Unsupported case");
        }

        return overlap;
    }
    
    public static ArrayList<BigInt[]> cut(BigInt[] range, BigInt[] cut) {
        ArrayList<BigInt[]> cuts = new ArrayList<>();
        if (range[0].leq(cut[0]) && cut[1].leq(range[1])) {
            cuts.add(new BigInt[] { range[0], cut[0].sub(1) });
            cuts.add(new BigInt[] { cut[1].add(1) , range[1] });
            return clean(cuts);
        }
        
        if (cut[0].leq(range[0]) && range[1].leq(cut[1])) {
            return clean(cuts);
        }
        
        if (range[0].leq(cut[0]) && cut[0].leq(range[1])) {
            cuts.add(new BigInt[] { range[0], cut[0].sub(1) });
            return clean(cuts);
        }
        
        if (range[0].leq(cut[1]) && cut[1].leq(range[1])) {
            cuts.add(new BigInt[] { cut[1].add(1), range[1] });
            return clean(cuts);
        }
        
        throw new RuntimeException("Unsupported case");
    }
    
    private static ArrayList<BigInt[]> clean(ArrayList<BigInt[]> raw) {
        ArrayList<BigInt[]> clean = new ArrayList<>();
        
        for (BigInt[] r : raw) {
            if (r[0].leq(r[1])) {
                clean.add(r);
            }
        }
        return clean;
    }
}
