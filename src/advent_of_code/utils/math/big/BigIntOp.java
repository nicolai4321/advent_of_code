package advent_of_code.utils.math.big;

import java.math.BigInteger;
import java.util.ArrayList;

import advent_of_code.utils.Log;

public abstract class BigIntOp extends BigInteger {
    public BigIntOp(String s) {
        super(s);
    }
    
    public static BigInt max(BigInt a, BigInt b) {
        if (a.le(b)) {
            return b;
        } else {
            return a;
        }
    }
    
    public static BigInt min(BigInt a, BigInt b) {
        if (a.le(b)) {
            return a;
        } else {
            return b;
        }
    }
    
    public static BigInt floorMod(BigInt a, BigInt b) {
        return a.mod(b);
    }
    
    public static BigInt lcm(BigInt a, BigInt b) {
        return (a.mult(b)).div(new BigInt(a.gcd(b))).getInt();
    }
    
    /**
     * Shoelace formula
     * https://en.wikipedia.org/wiki/Shoelace_formula
     * 
     * Given a list of coordinates where you can draw a line to each of the coordinates,
     * the method will return the inside area.
     * 
     * A = sum_{i=1}^{n} |y_i (x_{i-1} - x_{i+1})|/2
     * 
     * A: area
     * n: number of points
     * 
     * @param points
     * @return area
     */
    public static BigInt getAreaFromCoordinates(ArrayList<BigInt[]> points) {
        BigInt area = BigInt.ZERO;
        int n = points.size();
        for (int i=0; i<n; i++) {
            BigInt[] pointPrev = points.get(Math.floorMod(i-1, points.size()));
            BigInt[] point = points.get(i);
            BigInt[] pointNext = points.get(Math.floorMod(i+1, points.size()));
            
            area = area.add(point[1].mult(pointPrev[0].sub(pointNext[0])));
        }
        return area.absolute().div(2).getInt();
    }
    
    /**
     * Pick's theorem
     * https://en.wikipedia.org/wiki/Pick%27s_theorem
     * 
     * A = i + b/2 - 1
     * <=>
     * i = a - b/2 + 1
     * 
     * A: area
     * b: boundary points
     * i: interior points
     * 
     * @return interior points
     */
    public static BigInt getInterioirPoints(BigInt area, BigInt b) {
        return area.sub(b.div(2)).getInt().add(1);
    }
    
    /**
     * Pick's theorem
     * https://en.wikipedia.org/wiki/Pick%27s_theorem
     * 
     * A = i + b/2 - 1
     * 
     * A: area
     * b: boundary points
     * i: interior points
     * 
     * @return interior points
     */
    public static BigInt getPicksArea(BigInt b, BigInt i) {
        return i.add(b.div(2).getInt()).sub(1);
    }
}
