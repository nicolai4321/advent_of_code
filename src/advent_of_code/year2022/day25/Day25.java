package advent_of_code.year2022.day25;

import java.math.BigInteger;

import advent_of_code.utils.BigInt;
import advent_of_code.utils.RootDay;

public class Day25 extends RootDay {
    public Day25() {
        super(2022, 25, "2=0=02-0----2-=02-10", null);
        setInput1("input01.txt");
        disableRun2();
    }
    
    @Override
    public String run1(String input) {
        BigInteger[] ints = mapInput(input);
        BigInteger sum = sum(ints);
        return toBase5(sum);
    }

    @Override
    public String run2(String input) {
        //no part 2
        return null;
    }
    
    /**
     * @param ints
     * @return sum of the list
     */
    private BigInteger sum(BigInteger[] ints) {
        BigInteger value = BigInt.get(0);
        for (BigInteger i : ints) {
            value = value.add(i);
        }
        return value;
    }

    /**
     * @param sum
     * @return sum in base5
     */
    private String toBase5(BigInteger base10) {
        String base5 = base10.mod(BigInt.get(5)).toString();
        BigInteger amountOf5 = base10;

        while(true) {
            amountOf5 = amountOf5.divide(BigInt.get(5));
            BigInteger remainder = amountOf5.mod(BigInt.get(5));
            
            if (amountOf5.compareTo(BigInt.get(0)) == 0) {
                return insertNegatives(base5);
            } else {
                base5 = remainder + base5;
            }
        }
    }

    /**
     * @param value
     * @return negatives
     */
    private String insertNegatives(String base5) {
        int carry = 0;
        String output = "";
        for (int i=base5.length()-1; 0<=i; i--) {
            char c = base5.charAt(i);
            int v = c - 48;
            v += carry;
            
            if (carry != 0) {
                c += carry;
            }
            
            if (v == 5) {
                carry = 1;
                output = "0" + output;
            } else if (v == 4) {
                carry = 1;
                output = "-" + output;
            } else if (v == 3) {
                carry = 1;
                output = "=" + output;
            } else {
                carry = 0;
                output = c + output;
            }
        }
        
        if (0 < carry) {
            output = carry + output;
        }
        
        return output;
    }

    /**
     * @param string
     * @return number in base10
     */
    private BigInteger toBase10(String base5) {
        char[] chars = base5.toCharArray();
        BigInteger value = BigInt.get(0);
        
        for (char c : chars) {
            value = value.multiply(BigInt.get(5));
            if (c == '1') {
                value = value.add(BigInt.get(1));
            } else if (c == '2') {
                value = value.add(BigInt.get(2));
            } else if (c == '3') {
                value = value.add(BigInt.get(3));              
            } else if (c == '=') {
                value = value.subtract(BigInt.get(2));
            } else if (c == '-') {
                value = value.subtract(BigInt.get(1));
            }
        }
        
        return value;
    }
    
    /**
     * @param input
     * @return list of big integers
     */
    private BigInteger[] mapInput(String input) {
        String[] strings = input.split("\n");
        BigInteger[] ints = new BigInteger[strings.length];
        
        for (int i=0; i<ints.length; i++) {
            ints[i] = toBase10(strings[i]);
        }
        
        return ints;
    }
}
