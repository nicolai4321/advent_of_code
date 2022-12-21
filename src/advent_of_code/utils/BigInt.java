package advent_of_code.utils;

import java.math.BigInteger;

/**
 * Helper class to reduce the amount of text required for BigInteger
 * @author nicol
 *
 */
public class BigInt {	
	public static BigInteger get(int i) {
		return new BigInteger(i + "");
	}
	
	public static BigInteger get(String s) {
		return new BigInteger(s);
	}
	
	public static BigInteger add(int a, int b) {
		return get(a).add(get(b));
	}

	public static BigInteger add(BigInteger[] bigInts) {
		BigInteger bigInt = get(0);
		for (BigInteger bI : bigInts) {
			bigInt = bigInt.add(bI);
		}
		return bigInt;
	}
	
	public static BigInteger add(BigInteger a, BigInteger b) {
		return a.add(b);
	}

	public static BigInteger add(Integer a, BigInteger b) {
		return get(a).add(b);
	}
	
	public static BigInteger div(BigInteger a, int b) {
		return a.divide(get(b));
	}

	public static BigInteger div(BigInteger a, BigInteger b) {
		return a.divide(b);
	}

	public static BigInteger div(int a, int b) {
		return get(a).divide(get(b));
	}
	
	public static BigInteger mod(BigInteger a, int b) {
		return a.mod(get(b));
	}
	
	public static BigInteger mod(BigInteger a, String b) {
		return a.mod(get(b));
	}
	
	public static BigInteger mult(int a, int b) {
		return mult(a + "", b + "");
	}
	
	public static BigInteger mult(String a, String b) {
		return get(a).multiply(get(b));
	}

	public static BigInteger mult(BigInteger a, BigInteger b) {
		return a.multiply(b);
	}
	
	public static BigInteger mult(Integer a, BigInteger b) {
		return get(a).multiply(b);
	}
	
	public static BigInteger mult(BigInteger a, int b) {
		return a.multiply(get(b));
	}
	
	public static BigInteger sub(BigInteger a, int b) {
		return a.subtract(get(b));
	}
	
	public static BigInteger sub(BigInteger a, String b) {
		return a.subtract(get(b));
	}
	
	public static BigInteger sub(int a, BigInteger b) {
		return get(a).subtract(b);
	}
}
