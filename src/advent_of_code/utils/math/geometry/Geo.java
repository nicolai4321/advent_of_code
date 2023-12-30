package advent_of_code.utils.math.geometry;

import advent_of_code.utils.math.big.BigDec;
import advent_of_code.utils.math.big.BigInt;

public class Geo {
    public static BigDec getSlope(BigInt[] point0, BigInt[] point1) {
        BigInt deltaX = point1[0].sub(point0[0]);
        BigInt deltaY = point1[1].sub(point0[1]);
        
        if (deltaX.eq(BigInt.ZERO)) {
            throw new RuntimeException("Does not have a slope");
        }
        
        return BigDec.div(deltaY, deltaX);
    }
    
    /**
     * Finds the intersection point between line0 and line1:
     * 
     * line0: y = x * a0 + b0
     * line1: y = x * a1 + b1
     * 
     * @param line0
     * @param line1
     * @return intersection point between line0 and line1. null if none exists
     */
    public static BigDec[] getIntersectionPoint(Line2D line0, Line2D line1) {
        BigDec a0 = line0.getA();
        BigDec b0 = line0.getB();
        
        BigDec a1 = line1.getA();
        BigDec b1 = line1.getB();
        
        BigDec x;
        BigDec y;
        try {
            x = (b1.sub(b0)).div(a0.sub(a1));
            y = (a0.mult((b1.sub(b0)).div(a0.sub(a1)))).add(b0);
        } catch (ArithmeticException e) {
            return null;
        }
        
        BigInt[] min0 = line0.getMin();
        if (min0 != null && (x.le(min0[0]) || y.le(min0[1]))) {
            return null;
        }
        
        BigInt[] max0 = line0.getMax();
        if (max0 != null && (max0[0].le(x) || max0[1].le(y))) {
            return null;
        }
        
        BigInt[] min1 = line1.getMin();
        if (min1 != null && (x.le(min1[0]) || y.le(min1[1]))) {
            return null;
        }
        
        BigInt[] max1 = line1.getMax();
        if (max1 != null && (max1[0].le(x) || max1[1].le(y))) {
            return null;
        }

        return new BigDec[] { x, y };
    }
}
