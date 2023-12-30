package advent_of_code.utils.math.big;

import java.math.BigInteger;

/**
 *  Helper class for BigInteger
 */
public class BigInt extends BigIntOp {
    public static final BigInt M_ONE = new BigInt("-1");
    public static BigInt ZERO = new BigInt("0");
    public static BigInt ONE = new BigInt("1");
    public static BigInt TWO = new BigInt("2");
    
    public BigInt(String s) {
        super(s);
    }

    public BigInt(int i) {
        super(i + "");
    }
    
    public BigInt(BigInteger bigInteger) {
        super(bigInteger.toString());
    }
    
    public static BigInt get(String s) {
        return new BigInt(s);
    }
    
    public static BigInt get(int i) {
        return new BigInt(i);
    }

    public static BigInt add(BigInt a, BigInt b) {
        return a.add(b);
    }
    
    public static BigInt add(BigInt a, Integer b) {
        return a.add(get(b));
    }
    
    public static BigInt add(Integer a, BigInt b) {
        return get(a).add(b);
    }
    
    public static BigInt add(Integer a, Integer b) {
        return get(a).add(b + "");
    }
    
    public static BigInt sub(Integer a, Integer b) {
        return get(a).sub(b + "");
    }
    
    public static BigInt sub(Integer a, BigInt b) {
        return get(a).sub(b);
    }

    public static BigInt sub(BigInt a, Integer b) {
        return a.sub(get(b));
    }
    
    public static BigInt mult(Integer a, Integer b) {
        return get(a).mult(b + "");
    }
    
    public static BigInt mult(BigInt a, Integer b) {
        return a.mult(b + "");
    }

    public static BigInt mult(Integer a, BigInt b) {
        return get(a).mult(b + "");
    }

    public static BigDec div(BigInt a, BigInt b) {
        return a.div(b);
    }

    public static BigDec div(BigInt a, Integer b) {
        return a.div(get(b));
    }

    public static BigDec div(Integer a, Integer b) {
        return get(a).div(get(b));
    }
    
    public static BigInt mod(BigInt a, String b) {
        return a.mod(get(b));
    }
    
    public static BigInt mod(BigInt a, BigInt b) {
        return a.mod(b);
    }
    
    public static boolean eq(BigInt a, BigInt b) {
        return a.eq(b);
    }
    
    public static boolean le(BigInt a, BigInt b) {
        return a.le(b);
    }
    
    public BigInt mult(BigInt a) {
        return new BigInt(multiply(a));
    }

    public BigInt mult(String s) {
        return mult(get(s));
    }
    
    public BigInt mult(int i) {
        return mult(get(i + ""));
    }
    
    public BigDec mult(BigDec a) {
        return BigDec.mult(this, a);
    }
    
    public BigDec div(BigInt a) {
        return BigDec.div(this, a);
    }

    public BigDec div(String s) {
        return div(get(s));
    }

    public BigDec div(int i) {
        return div(get(i + ""));
    }

    public BigInt add(BigInt a) {
        return new BigInt(super.add(a));
    }

    public BigInt add(String s) {
        return add(get(s));
    }

    public BigInt add(int i) {
        return add(get(i + ""));
    }
    
    public BigInt sub(BigInt a) {
        return new BigInt(subtract(a));
    }
    
    public BigInt sub(String s) {
        return sub(get(s));
    }
    
    public BigInt sub(int i) {
        return sub(get(i + ""));
    }
    
    public BigDec sub(BigDec a) {
        return BigDec.sub(this, a);
    }
    
    public BigInt mod(BigInt a) {
        return new BigInt(super.mod(a));
    }
    
    public BigInt mod(String s) {
        return mod(get(s));
    }

    public BigInt mod(int i) {
        return mod(get(i + ""));
    }
    
    public boolean le(BigInt a) {
        return this.compareTo(a) < 0;
    }
    
    public boolean le(BigDec a) {
        return BigDec.le(this, a);
    }

    public boolean leq(BigInt a) {
        return this.compareTo(a) <= 0;
    }
    
    public boolean gr(BigInt a) {
        return a.compareTo(this) < 0;
    }

    public boolean geq(BigInt a) {
        return a.compareTo(this) <= 0;
    }
    
    public boolean eq(BigInt a) {
        return super.compareTo(a) == 0;
    }

    public boolean eq(int a) {
        return super.compareTo(get(a)) == 0;
    }
    
    public BigInt absolute() {
        return new BigInt(super.abs());
    }
}
