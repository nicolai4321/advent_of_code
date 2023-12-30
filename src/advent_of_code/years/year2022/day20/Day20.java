package advent_of_code.years.year2022.day20;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;
import advent_of_code.utils.math.big.BigInt;

public class Day20 extends RootDay {
    public Day20(Year year, int day) {
        super(year, day, "2215", "8927480683");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * Mix the numbers and sum the values of the three groove coordinates at 1000, 2000 and 3000.
     */
    @Override
    public String run1(String input) {
        int[] ints = Lists.stringsToInts(input.split("\n"));
        ArrayList<Value> mixed = mix(ints, 1);
        
        int g0 = grooveCoord(mixed, 1000);
        int g1 = grooveCoord(mixed, 2000);
        int g2 = grooveCoord(mixed, 3000);
        return (g0 + g1 + g2) + "";
    }

    /**
     * Multiply the numbers with the decryption key, mix them and sum the values of the tree groove coordinates at 1000, 2000 and 3000
     */
    @Override
    public String run2(String input) {
        int[] ints = Lists.stringsToInts(input.split("\n"));
        int decryptionKey = 811589153;
        applyDecryptionKey(ints, decryptionKey);
        ArrayList<Value> mixed = mix(ints, 10);

        BigInt g0 = decryptGrooveCoord(mixed, 1000, decryptionKey);
        BigInt g1 = decryptGrooveCoord(mixed, 2000, decryptionKey);
        BigInt g2 = decryptGrooveCoord(mixed, 3000, decryptionKey);
        BigInt g = g0.add(g1).add(g2);
        return g.toString();
    }
    
    /**
     * Apply decryptionKey to the given list of ints
     * @param ints
     * @param decryptionKey
     */
    private void applyDecryptionKey(int[] ints, int decryptionKey) {
        int decryptionKeySmall = (decryptionKey % (ints.length - 1));
        
        for (int i=0; i<ints.length; i++) {
            ints[i] = ints[i] * decryptionKeySmall;
        }
    }
    
    /**
     * @param lst
     * @param index
     * @param decryptionKey
     * @return the value of the groovy coordinate at the index
     */
    private BigInt decryptGrooveCoord(ArrayList<Value> lst, int index, int decryptionKey) {
        int decryptionKeySmall = (decryptionKey % (lst.size() - 1));
        return BigInt.mult(BigInt.div(grooveCoord(lst, index), decryptionKeySmall).getInt(), decryptionKey);
    }
    
    /**
     * @param lst
     * @param index
     * @return the value of the groovy coordinate at the index
     */
    private int grooveCoord(ArrayList<Value> lst, int index) {
        int index0 = 0;
        for (int i=0; i<lst.size(); i++) {
            if (lst.get(i).getValue() == 0) {
                index0 = i;
                break;
            }
        }
        
        int grooveCoord = Math.floorMod(index0 + index, lst.size());
        return lst.get(grooveCoord).getValue();
    }
    
    /**
     * @param original
     * @param rounds
     * @return mixed list
     */
    private ArrayList<Value> mix(int[] lst, int rounds) {
        //create a list of values since each value stores the original index
        ArrayList<Value> mixLst = new ArrayList<Value>();
        for (int i=0; i<lst.length; i++) {
            mixLst.add(i, new Value(i, lst[i]));
        }
        
        //mix the list
        Integer index = null;
        for (int r=0; r<rounds; r++) {
            for (int i=0; i<mixLst.size(); i++) {
                //find index
                for (int j=0; j<mixLst.size(); j++) {
                    if (mixLst.get(j).getOriginalIndex() == i) {
                        index = j;
                    }
                }
                
                moveIndex(mixLst, index);
            }
        }
                
        return mixLst;
    }

    /**
     * Move index
     * @param ints
     * @param index
     */
    private void moveIndex(ArrayList<Value> ints, int index) {
        int value = ints.get(index).getValue();
        if (value == 0) {
            return;
        }
        
        int newIndex = Math.floorMod(index + value, ints.size() - 1);
        boolean swapUp = index < newIndex;        
        int nrOfSwaps = Math.max(index, newIndex) - Math.min(index, newIndex);
        
        if (swapUp) {
            for (int i=0; i<nrOfSwaps; i++) {
                swap(ints, index + i, index + i + 1);
            }            
        } else {
            for (int i=0; i<nrOfSwaps; i++) {
                swap(ints, index - i, index - i - 1);
            }
        }
    }
    
    /**
     * Swap index0 with index1
     * @param ints
     * @param index0
     * @param index1
     */
    private void swap(ArrayList<Value> ints, int index0, int index1) {
        Value i0 = ints.get(index0);
        Value i1 = ints.get(index1);
        ints.set(index0, i1);
        ints.set(index1, i0);
    }
}
