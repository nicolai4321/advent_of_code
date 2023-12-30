package advent_of_code.years.year2023.day06;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;
import advent_of_code.utils.math.big.BigInt;

public class Day06 extends RootDay {
    public Day06(Year year, int day) {
        super(year, day, "1155175", "35961505");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * Multiply the the number of ways you can beat the record for each race
     */
    @Override
    public String run1(String input) {
        String[] lines = input.split("\n");
        BigInt[][] sets = getRaces(lines, false);
        
        BigInt winningNr = BigInt.ZERO;
        for (BigInt[] i : sets) {
            ArrayList<BigInt> winningMoves = winningMoves(i);
            
            if (winningNr.eq(BigInt.ZERO)) {
                winningNr = new BigInt(winningMoves.size());
            } else {
                winningNr = winningNr.mult(winningMoves.size());
            }
        }
        
        return winningNr + "";
    }

    /**
     * How many ways can you beat the record in the combined race
     */
    @Override
    public String run2(String input) {
        String[] lines = input.split("\n");
        BigInt[][] sets = getRaces(lines, true);
        
        BigInt winningNr = BigInt.ZERO;
        for (BigInt[] i : sets) {
            ArrayList<BigInt> winningMoves = winningMoves(i);
            
            if (winningNr.eq(BigInt.ZERO)) {
                winningNr = new BigInt(winningMoves.size());
            } else {
                winningNr = winningNr.mult(winningMoves.size());
            }
        }
        
        return winningNr + "";

    }

    /**
     * @param race
     * @return winning move
     */
    private ArrayList<BigInt> winningMoves(BigInt[] race) {
        BigInt time = race[0];
        BigInt distance = race[1];
        ArrayList<BigInt> winningMoves = new ArrayList<>();
        
        for (BigInt t=BigInt.ZERO; t.le(time); t = t.add(1)) {
            BigInt speed = t;
            BigInt dis = (time.sub(t)).mult(speed);
            
            if (distance.le(dis)) {
                winningMoves.add(t);
            }
        }
        
        return winningMoves;
    }

    /**
     * @param lines
     * @param combinedRace
     * @return races
     */
    private BigInt[][] getRaces(String[] lines, boolean combinedRace) {
        String replaceValue = (combinedRace) ? "" : " ";
        
        String s = lines[0].replaceAll("Time:", "")
                          .replaceAll("( )+", replaceValue)
                          .trim()
                          .replaceAll("( )$", "");
        BigInt[] times = Lists.stringsToBigInts(s.split(" "));
        
        String d = lines[1].replaceAll("Distance: ", "")
                           .replaceAll("( )+", replaceValue)
                           .trim()
                           .replaceAll("( )$", "");
        BigInt[] distances = Lists.stringsToBigInts(d.split(" "));
        
        BigInt[][] sets = new BigInt[distances.length][];
        
        for (int i=0; i<distances.length; i++) {
            sets[i] = new BigInt[] { times[i], distances[i] };
        }
        
        return sets;
    }
}
