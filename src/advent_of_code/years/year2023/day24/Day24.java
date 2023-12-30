package advent_of_code.years.year2023.day24;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Log;
import advent_of_code.utils.math.big.BigDec;
import advent_of_code.utils.math.big.BigInt;
import advent_of_code.utils.math.geometry.Geo;
import advent_of_code.utils.math.geometry.Line2D;

public class Day24 extends RootDay {
    public Day24(Year year, int day) {
        super(year, day, "18651", "546494494317645");
        setInput1("input01.txt");
        setInput2("input01.txt");
        this.disableTest2();
    }
    
    @Override
    public String run1(String input) {
        ArrayList<Stone> stones = getStones(input);
        BigInt collisions = check2DCollision(stones, new BigInt("200000000000000"), new BigInt("400000000000000"));
        return collisions + "";
    }
    
    @Override
    public String run2(String input) {
        ArrayList<Stone> stones = getStones(input);
        
        if (10 < stones.size()) {
            return "Just use math for the bigger input";
        }
        
        BigInt[] data = getData(stones);
        
        return data[0].add(data[1]).add(data[2]).toString();
    }

    /**
     * Tries different setups and returns the data that can penetrate all coordinates
     * 
     * @param stones
     * @return data
     */
    private BigInt[] getData(ArrayList<Stone> stones) {
        int maxTime = 100;
        for (int t0=0; t0<maxTime; t0++) {
            for (int t1=t0+1; t1<maxTime; t1++) {
                t1 = (t1<=t0) ? t0+1 : t1;

                for (int i=0; i<stones.size(); i++) {
                    for (int j=0; j<stones.size(); j++) {
                        if (i == j) {
                            continue;
                        }
                        
                        Stone stone0 = stones.get(i);
                        Stone stone1 = stones.get(j);
                        
                        BigInt[] candidate = stone0.timeCollide(t0, t1, stone1);
                        if (candidate == null) {
                            continue;
                        }

                        if (PenetrateAll2D(candidate, stones)) {
                            return candidate;
                        }
                    }
                }
            }
        }
     
        return null;
    }
    
    /**
     * If a candidate can penetrate all x and y coordinates it is more than likely
     * that z is also penetrated.
     * 
     * @param candidate
     * @param stones
     * @return true if the candidate can penetrate all x and y coordinates
     */
    private boolean PenetrateAll2D(BigInt[] candidate, ArrayList<Stone> stones) {
        BigInt x = candidate[0];
        BigInt y = candidate[1];
        BigInt vX = candidate[3];
        BigInt vY = candidate[4];
        Line2D line;
        
        try {
            line = new Line2D(new BigInt[] { x, y }, new BigInt[] { x.add(vX), y.add(vY) });
        } catch(Exception e) {
            return false;
        }
        
        if (vX.eq(BigInt.ZERO)) {
            return false;
        }
        
        for (int i=0; i<stones.size(); i++) {
            Stone stone = stones.get(i);
            Line2D stoneLine = stone.getInfLine();
            
            BigDec[] point = Geo.getIntersectionPoint(line, stoneLine);
            
            if (point == null) {
                return false;
            }
            
            BigInt xInt = point[0].getInt();
            if (xInt == null) {
                return false;
            }
            
            BigInt yInt = point[1].getInt();
            if (yInt == null) {
                return false;
            }
            
            BigDec timeX = (xInt.sub(stone.getX())).div(stone.getVx());
            BigInt timeIntX = timeX.getInt();
            if (timeIntX == null || timeIntX.leq(BigInt.ZERO)) {
                return false;
            }
            
            if (timeIntX.le(BigInt.ZERO)) {
                return false;
            }

            BigDec timeY = (yInt.sub(stone.getY())).div(stone.getVy());
            BigInt timeIntY = timeY.getInt();
            if (timeIntY == null || timeIntY.leq(BigInt.ZERO)) {
                return false;
            }
            
            if (timeIntY.le(BigInt.ZERO)) {
                return false;
            }
            
            if (!timeIntX.eq(timeIntY)) {
                return false;
            }
        }
        
        return true;
    }

    /**
     * @param stones
     * @param testMin
     * @param testMax
     * @return 2d collisions
     */
    private BigInt check2DCollision(ArrayList<Stone> stones, BigInt testMin, BigInt testMax) {
        BigInt collisions = BigInt.ZERO;
        for (int i=0; i<stones.size(); i++) {
            for (int j=i+1; j<stones.size(); j++) {
                Stone stone0 = stones.get(i);
                Stone stone1 = stones.get(j);
                
                BigDec[] point = stone0.get2DCollisionPoint(stone1, testMin, testMax);
                if (point != null) {
                    collisions = collisions.add(BigInt.ONE);
                }
            }
        }
        return collisions;
    }

    /**
     * @param input
     * @return the stones
     */
    private ArrayList<Stone> getStones(String input) {
        ArrayList<Stone> stones = new ArrayList<Stone>();
        
        for (String line : input.split("\n")) {
            line = line.replaceAll(" ", "").replaceAll("@", ",");
            String[] nrs = line.split(",");
            stones.add(new Stone(new BigInt(nrs[0]), new BigInt(nrs[1]), new BigInt(nrs[2]), 
                                 new BigInt(nrs[3]), new BigInt(nrs[4]), new BigInt(nrs[5])));
        }
        
        return stones;
    }
}
