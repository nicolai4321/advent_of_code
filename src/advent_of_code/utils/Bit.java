package advent_of_code.utils;

public class Bit {
	/**
	 * @param bits
	 * @return value of bits
	 */
	public static int getValue(int[] bits) {
		int multiplier = 1;
		int value = 0;
		
		for (int i = (bits.length - 1); i>=0; i--) {
			int bit = bits[i];
			value += multiplier * bit;
			multiplier = 2 * multiplier;
		}
		return value;
	}
	
	/**
	 * @param number
	 * @return bits
	 */
	public static int[] getBit(int number) {		
		int largestBitIndex = getLargestBitIndex(number);
		int largestBitValue = getValueOfBitIndex(largestBitIndex);
		int remainingValue = number - largestBitValue;

		int[] bits = new int[largestBitIndex + 1];
		bits[0] = 1;

		for (int index=1; index<=largestBitIndex; index++) {
			int bitIndex = largestBitIndex - index;
			int value = getValueOfBitIndex(bitIndex);
			int difference = remainingValue - value;
			
			if (difference < 0) {
				bits[index] = 0;
			} else {
				remainingValue = difference;
				bits[index] = 1;
			}
		}
		
		return bits;
	}
	
	/**
	 * @param bitIndex
	 * @return value of bitIndex
	 */
	private static int getValueOfBitIndex(int bitIndex) {
		return (int) Math.pow(2, bitIndex);
	}
	
	/**
	 * @param number
	 * @return largest bit index
	 */
	private static int getLargestBitIndex(int number) {
		int difference;
		int multiplier = 1;
		int bitIndex = 0;
		
		while (true) {
			difference = number - multiplier;
			
			if (difference == 0) {
				return bitIndex;
			} else if (difference < 0) {
				return bitIndex - 1;
			}
			
			bitIndex++;
			multiplier = 2 * multiplier;
		}
	}
}
