package advent_of_code.utils.math.geometry;

import advent_of_code.utils.math.big.BigDec;
import advent_of_code.utils.math.big.BigInt;

public class Line2D {
    private BigDec a = null;
    private BigDec b = null;
    private BigInt[] min = null;
    private BigInt[] max = null;
    
    /**
     * Line represented by two points
     * @param pointA
     * @param pointB
     */
    public Line2D(BigInt[] point0, BigInt[] point1) {
        a = Geo.getSlope(point0, point1);
        b = point0[1].sub(a.mult(point0[0]));
    }
    
    /**
     * Infinite line represented by the equation below:
     * y = ax + b
     */
    public Line2D(BigDec a, BigDec b) {
        this.a = a;
        this.b = b;
    }
    
    /**
     * Infinite line represented by the equation below:
     * y = ax + b
     */
    public Line2D(int a, int b) {
        this.a = new BigDec(a);
        this.b = new BigDec(b);
    }

    /**
     * Line represented by the equation below but within a limit of min and max:
     * y = ax + b
     */
    public Line2D(BigInt a, BigInt b, BigInt[] min, BigInt[] max) {
        this.a = new BigDec(a);
        this.b = new BigDec(b);
        this.min = min;
        this.max = max;
    }
    
    /**
     * Line represented by the equation below but within a limit of min and max:
     * y = ax + b
     */
    public Line2D(BigDec a, BigDec b, BigInt[] min, BigInt[] max) {
        this.a = a;
        this.b = b;
        this.min = min;
        this.max = max;
    }
    
    public BigDec getA() {
        if (a == null) {
            throw new RuntimeException("Not calculated");
        }
        return a;
    }

    public BigDec getB() {
        if (b == null) {
            throw new RuntimeException("Not calculated");
        }
        return b;
    }
    
    public BigInt[] getMin() {
        return min;
    }

    public BigInt[] getMax() {
        return max;
    }
}
