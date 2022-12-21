package advent_of_code.year2022.day15;

import java.math.BigInteger;
import java.util.ArrayList;

import advent_of_code.utils.BigInt;
import advent_of_code.utils.Log;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day15 extends RootDay {
    public Day15() {
        super(true, true, "5688618", true, true, "12625383204261");
    }

    @Override
    public String run1() {
        String[] input = input();
        ArrayList<int[]> machines = generateMachines(input);
        ArrayList<ArrayList<int[]>> distanceInfo = getDistanceInfo(machines);
        return excludeNr(distanceInfo, 2000000) + "";
    }

    @Override
    public String run2() {
        int max = 4000000;
        String[] input = input();
        
        ArrayList<int[]> machines = generateMachines(input);
        ArrayList<ArrayList<int[]>> distanceInfo = getDistanceInfo(machines);        
        ArrayList<int[]> solutionLines = getSolutionLines(distanceInfo);
        int[] coor = part2Skip(distanceInfo, max);
        Log.show(coor[0] + "," + coor[1]);
        return getTuning(coor[0], coor[1]);
    }
    
    private ArrayList<int[]> getSolutionLines(ArrayList<ArrayList<int[]>> distanceInfo) {
        for (ArrayList<int[]> pair : distanceInfo) {
            int[] position = pair.get(0);
            int[] distances = pair.get(0);
            
            //TODO
            
        }
        return null;
    }

    //TODO optimize or find better method
    private int[] part2Skip(ArrayList<ArrayList<int[]>> distanceInfo, int max) {
        //for (int yLine=0; yLine<max; yLine++) {
        for (int yLine=3204000; yLine<max; yLine++) { //TODO hardcoded:
            if (yLine % 100 == 0) {
                Log.show("y: " + yLine);
            }
            
            for (int xLine=0; xLine<max; xLine++) {
                boolean couldBeBeacon = true;
                
                for (ArrayList<int[]> machineInfo : distanceInfo) {
                    int[] pair = machineInfo.get(0);
                    int[] distances = machineInfo.get(1);
                    
                    int distanceToMachine = getDistance(pair[0], pair[1], xLine, yLine);
                    
                    if (distanceToMachine <= distances[0]) {
                        couldBeBeacon = false;
                        int yDiverse = Math.max(pair[1], yLine) - Math.min(pair[1], yLine);
                        int skipTo = pair[0] + distances[1] - yDiverse;
                        if (xLine < skipTo) {
                            xLine = skipTo;
                            break;
                        }
                    }
                }
                
                if (couldBeBeacon) {
                    return new int[] { xLine, yLine };                    
                }
            }
        }
        return null;
    }

    private String getTuning(int x, int y) {
        BigInteger bX = BigInt.get(x);
        BigInteger bY = BigInt.get(y);
        BigInteger bV = BigInt.get(4000000);
        return bX.multiply(bV).add(bY).toString();
    }

    private int excludeNr(ArrayList<ArrayList<int[]>> distanceInfo, int yLine) {
        int count = 0;
        
        for (int xLine=-100000000; xLine<100000000; xLine++) {
            if (xLine % 10000000 == 0) {
                Log.show(xLine);
            }
            
            boolean couldThisBeABeacon = true;
            
            for (ArrayList<int[]> machineInfo : distanceInfo) {
                int[] pair = machineInfo.get(0);
                int[] distances = machineInfo.get(1);
                int distance = getDistance(pair[0], pair[1], xLine, yLine);
                
                if (distance <= distances[0]) {
                    if (pair[2] == xLine && pair[3] == yLine) {
                        couldThisBeABeacon = true;
                    } else {
                        couldThisBeABeacon = false;
                    }
                }
            }
            if(!couldThisBeABeacon) {
                count++;
            }
        }
            
        return count;
    }

    private ArrayList<ArrayList<int[]>> getDistanceInfo(ArrayList<int[]> machines) {
        ArrayList<ArrayList<int[]>> distanceInfo = new ArrayList<ArrayList<int[]>>();
        
        for (int[] pair : machines) {
            int sX = pair[0];
            int sY = pair[1];
            
            int bX = pair[2];
            int bY = pair[3];
            
            int distanceX = Math.max(sX,  bX) - Math.min(sX, bX);
            int distanceY = Math.max(sY,  bY) - Math.min(sY, bY);
            int distance = getDistance(sX, sY, bX, bY);
            
            ArrayList<int[]> extraLst = new ArrayList<int[]>();
            extraLst.add(pair);
            extraLst.add(new int[] {distance, distanceX, distanceY});
            
            distanceInfo.add(extraLst);
        }
        
        return distanceInfo;
    }

    private int getDistance(int x0, int y0, int x1, int y1) {
        int distanceX = Math.max(x0, x1) - Math.min(x0, x1);
        int distanceY = Math.max(y0, y1) - Math.min(y0, y1);
        return distanceX + distanceY;
    }

    private ArrayList<int[]> generateMachines(String[] input) {
        ArrayList<int[]> positions = new ArrayList<int[]>();
        
        for (String line : input) {
            int[] poss = new int[4];
            
            String[] split = line.split(":");
            String sensorString = split[0];
            String beaconString = split[1];
            
            sensorString = sensorString.replace("Sensor at x=", "");
            poss[0] = Integer.parseInt(sensorString.split(",")[0]);
            poss[1] = Integer.parseInt(sensorString.split(", y=")[1]);
            
            beaconString = beaconString.replaceAll(" closest beacon is at x=", "");
            poss[2] = Integer.parseInt(beaconString.split(",")[0]);
            poss[3] = Integer.parseInt(beaconString.split(", y=")[1]);
            
            positions.add(poss);
        }
        return positions;
    }

    private static String[] example() {
        return (Read.getStrings(2022, 15, "example01.txt")); 
    }
    
    private static String[] input() {
        return (Read.getStrings(2022, 15, "input01.txt")); 
    }
}
