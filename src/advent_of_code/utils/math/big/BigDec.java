package advent_of_code.utils.math.big;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;

import advent_of_code.utils.Log;

public class BigDec extends BigDecimal {
    public BigDec(int a) {
        super(a);
    }

    public BigDec(String a) {
        super(a);
    }

    public BigDec(BigDecimal a) {
        super(a.toString());
    }
    
    public BigDec(BigInt a) {
        super(a.toString());
    }

    public BigDec sub(BigDec a) {
        return new BigDec(this.subtract(a));
    }
    
    public BigDec add(BigDec a) {
        return new BigDec(super.add(a));
    }
    
    public BigDec div(BigDec a) {
        return new BigDec(this.divide(a, 10 , RoundingMode.HALF_UP));
    }

    public BigDec mult(BigDec a) {
        return new BigDec(this.multiply(a));
    }
    
    public BigDec mult(BigInt a) {
        return mult(new BigDec(a));
    }

    public boolean le(BigDecimal a) {
        return compareTo(a) < 0;
    }

    public boolean le(BigInt a) {
        return compareTo(new BigDec(a)) < 0;
    }

    public static BigDec min(BigDec a, BigDec b) {
        if (le(a, b)) {
            return a;
        } else {
            return b;
        }
    }
    
    public static BigDecimal min(BigDecimal a, BigDecimal b) {
        if (le(a, b)) {
            return a;
        } else {
            return b;
        }
    }
    
    public static BigDec max(BigDec a, BigDec b) {
        if (le(a, b)) {
            return b;
        } else {
            return a;
        }
    }
    
    public static BigDecimal max(BigDecimal a, BigDecimal b) {
        if (le(a, b)) {
            return b;
        } else {
            return a;
        }
    }

    public static boolean le(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) < 0;
    }
    
    public static boolean le(BigInt a, BigDecimal b) {
        return (new BigDec(a)).compareTo(b) < 0;
    }
    
    public static boolean leq(BigDecimal a, BigDecimal b) {
        return a.compareTo(b) <= 0;
    }
    
    public static boolean eq(BigDecimal a, BigDecimal b) {
        return leq(max(a, b).subtract(min(a, b)), new BigDecimal("0.01"));
    }

    public static BigDec div(BigInt a, BigInt b) {
        return (new BigDec(a)).div(new BigDec(b));
    }

    public static BigDec mult(BigInt a, BigDec b) {
        return (new BigDec(a)).mult(b);
    }

    public static BigDec sub(BigInt a, BigDec b) {
        return (new BigDec(a)).sub(b);
    }

    /**
     * @return BigInt if the BigDec is an BigInt or nearly a BigInt
     */
    public BigInt getInt() {
        BigDec rounded = new BigDec(this.setScale(0, RoundingMode.HALF_UP));
        BigDec delta = max(this, rounded).sub(min(this, rounded));
        
        if (delta.le(new BigDec("0.0001"))) {
            return new BigInt(rounded.toString());
        } else {
            return null;
        }
    }
}
