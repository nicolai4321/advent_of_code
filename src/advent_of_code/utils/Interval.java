package advent_of_code.utils;

import java.util.ArrayList;

public class Interval {
    /**
     * @param a
     * @param b
     * @return true if interval a and b overlap
     */
    public static boolean intervalOverlap(int[] a, int[] b) {
        return (a[0] <= b[0] && b[0] <= a[1]) || (a[0] <= b[1] && b[1] <= a[1]) || (b[0] <= a[0] && a[0] <= b[1]) || (b[0] <= a[1] && a[1] <= b[1]);
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
}
