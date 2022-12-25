package advent_of_code.year2022.day15;

import java.math.BigInteger;
import java.util.ArrayList;

import advent_of_code.utils.BigInt;
import advent_of_code.utils.Interval;
import advent_of_code.utils.MathOp;
import advent_of_code.utils.RootDay;

public class Day15 extends RootDay {
    private static int INDEX_SX = 0;
    private static int INDEX_SY = 1;
    private static int INDEX_BX = 2;
    private static int INDEX_BY = 3;
    private static int INDEX_DIS_X = 4;
    private static int INDEX_DIS_Y = 5;
    private static int INDEX_DIS = 6;

    public Day15() {
        super(2022, 15, "5688618", "12625383204261");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * In the row where y=2000000, how many positions cannot contain a beacon?
     */
    @Override
    public String run1(String input) {
        ArrayList<int[]> positions = extractPositions(input);
        return nrOfNotPossibleBeacons(positions, 2000000) + "";
    }

    /**
     * Find the only possible position for the distress beacon. What is its tuning frequency?
     */
    @Override
    public String run2(String input) {
        int max = 4000000;
        
        ArrayList<int[]> positions = extractPositions(input);
        int[] coor = findBeacon(positions, max);
        return getTuning(coor[0], coor[1]);
    }
    
    /**
     * @param positions
     * @param max
     * @return beacon
     */
    private int[] findBeacon(ArrayList<int[]> positions, int max) {
        for (int y=0; y<max; y++) { 
            ArrayList<int[]> intervals = constructIntervals(positions, y);

            ArrayList<int[]> intervalsStripped = new ArrayList<int[]>();
            for (int i=0; i<intervals.size(); i++) {
                int[] interval = intervals.get(i);
                intervalsStripped.add(new int[] {strip(interval[0], 0, max), strip(interval[1], 0, max)});
            }
            
            if (Interval.uniqueNumbersInInterval(intervalsStripped) < (max+1)) {
                ArrayList<int[]> intervalsMerged = Interval.mergeIntervals(intervalsStripped);
                int x = findX(0, intervalsMerged);

                boolean collisionWithBeacon = false;
                for (int[] position : positions) {
                    if (position[INDEX_BX] == x && position[INDEX_BY] == y) {
                        collisionWithBeacon = true;
                        break;
                    }
                }
                
                if (!collisionWithBeacon) {
                    return new int[] { x, y }; 
                }
            }            
        }
        return null;
    }
    
    /**
     * @param x
     * @param intervals
     * @return x that is not contained in the intervals
     */
    private int findX(int x, ArrayList<int[]> intervals) {
        for (int[] interval : intervals) {
            if (Interval.inInterval(x, interval)) {
                return findX(interval[1] + 1, intervals);
            }
        }
        
        return x;
    }
    
    /**
     * @param i
     * @param min
     * @param max
     * @return integer where the value is limited between min and max
     */
    private int strip(int i, int min, int max) {
        return Math.min(Math.max(min, i), max);
    }

    /**
     * @param positions
     * @param y
     * @return intervals
     */
    private ArrayList<int[]> constructIntervals(ArrayList<int[]> positions, int y) {
        ArrayList<int[]> intervals = new ArrayList<int[]>();
        
        for (int[] position : positions) {
            int distanceToLine = MathOp.difference(y, position[INDEX_SY]);
            
            if (distanceToLine == position[INDEX_DIS]) {
                if (position[INDEX_BY] != y) {
                    intervals.add(new int[] { position[INDEX_SX], position[INDEX_SX]});  
                } 
            } else if (distanceToLine < position[INDEX_DIS]) {
                int remainingDistance = position[INDEX_DIS] - distanceToLine;
                if (position[INDEX_BY] == y) {
                    intervals.add(new int[] { position[INDEX_SX] - remainingDistance, position[INDEX_BX] - 1});                    
                    intervals.add(new int[] { position[INDEX_BX] + 1, position[INDEX_SX] + remainingDistance});                    
                } else {
                    intervals.add(new int[] { position[INDEX_SX] - remainingDistance, position[INDEX_SX] + remainingDistance});                    
                }
            }
        }
        return intervals;
    }
    
    /**
     * @param x
     * @param y
     * @return tuning
     */
    private String getTuning(int x, int y) {
        BigInteger bX = BigInt.get(x);
        BigInteger bY = BigInt.get(y);
        BigInteger bV = BigInt.get(4000000);
        return bX.multiply(bV).add(bY).toString();
    }
    
    /**
     * @param positions
     * @param yLine
     * @return nr of not possible beacons
     */
    private int nrOfNotPossibleBeacons(ArrayList<int[]> positions, int y) {
        ArrayList<int[]> intervals = new ArrayList<int[]>();
        
        for (int[] position : positions) {
            int distanceToLine = MathOp.difference(y, position[INDEX_SY]);
            
            if (distanceToLine == position[INDEX_DIS]) {
                if (position[INDEX_BY] != y) {
                    intervals.add(new int[] { position[INDEX_SX], position[INDEX_SX]});  
                } 
            } else if (distanceToLine < position[INDEX_DIS]) {
                int remainingDistance = position[INDEX_DIS] - distanceToLine;
                if (position[INDEX_BY] == y) {
                    intervals.add(new int[] { position[INDEX_SX] - remainingDistance, position[INDEX_BX] - 1});                    
                    intervals.add(new int[] { position[INDEX_BX] + 1, position[INDEX_SX] + remainingDistance});                    
                } else {
                    intervals.add(new int[] { position[INDEX_SX] - remainingDistance, position[INDEX_SX] + remainingDistance});                    
                }
            }
        }
        
        return Interval.uniqueNumbersInInterval(intervals);
    }
    
    /**
     * Extract positions for sensors and beacons
     * @param input
     * @return list of { sX, sY, bX, bY, distanceX, distanceY, distance }
     */
    private ArrayList<int[]> extractPositions(String input) {
        ArrayList<int[]> positions = new ArrayList<int[]>();
        
        for (String line : input.split("\n")) {            
            String[] split = line.split(":");
            String sensorString = split[0];
            String beaconString = split[1];
            
            sensorString = sensorString.replace("Sensor at x=", "");
            int sX = Integer.parseInt(sensorString.split(",")[0]);
            int sY = Integer.parseInt(sensorString.split(", y=")[1]);
            
            beaconString = beaconString.replaceAll(" closest beacon is at x=", "");
            int bX = Integer.parseInt(beaconString.split(",")[0]);
            int bY = Integer.parseInt(beaconString.split(", y=")[1]);
            
            int distanceX = MathOp.difference(sX, bX);
            int distanceY = MathOp.difference(sY, bY);
            int distance = MathOp.manhattenDistance(sX, sY, bX, bY);
            
            int[] pos = new int[7];
            pos[INDEX_SX] = sX;
            pos[INDEX_SY] = sY;
            pos[INDEX_BX] = bX;
            pos[INDEX_BY] = bY;
            pos[INDEX_DIS_X] = distanceX;
            pos[INDEX_DIS_Y] = distanceY;
            pos[INDEX_DIS] = distance;
            
            positions.add(pos);
        }
        return positions;
    }
}
