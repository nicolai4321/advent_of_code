package advent_of_code.years.year2023.day09;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;

public class Day09 extends RootDay {
    public Day09(Year year, int day) {
        super(year, day, "1974913025", "884");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * What is the sum of the next number for each sequence?
     */
    @Override
    public String run1(String input) {
        String[] lines = input.split("\n");
        ArrayList<int[]> sequences = format(lines);
        return extrapolateSequences(sequences, true) + "";
    }

    /**
     * What is the sum of the previous number for each sequence?
     */
    @Override
    public String run2(String input) {
        String[] lines = input.split("\n");
        ArrayList<int[]> sequences = format(lines);
        return extrapolateSequences(sequences, false) + "";
    }
    
    /**
     * @param sequences
     * @param forwards
     * @return value for all sequences
     */
    private int extrapolateSequences(ArrayList<int[]> sequences, boolean forwards) {
        int extrapolatedValues = 0;
        
        for (int[] sequence : sequences) {
            ArrayList<int[]> history = new ArrayList<>();
            history.add(sequence);
            extrapolatedValues += extrapolateSequence(sequence, history, forwards);
        }
        
        return extrapolatedValues;
    }
    
    /**
     * @param sequence
     * @param history
     * @param forwards
     * @return value for the given sequence
     */
    private int extrapolateSequence(int[] sequence, ArrayList<int[]> history, boolean forwards) {
        int[] nextSequence = new int[sequence.length - 1];
        
        //Prepare next sequence
        boolean allZero = true;
        for (int i=0; i<(sequence.length - 1); i++) {
            int diff = sequence[i + 1] - sequence[i];
            nextSequence[i] = diff;
            if (diff != 0) {
                allZero = false;
            }
        }
        
        if (allZero) {
            return getSequenceValue(history, forwards);
        } else {
            history.add(nextSequence);
            return extrapolateSequence(nextSequence, history, forwards);
        }
    }
    
    /**
     * Calculates the value given the history of a sequence
     * @param history
     * @param forwards
     * @return value
     */
    private int getSequenceValue(ArrayList<int[]> history, boolean forwards) {
        int value = 0;
        
        for (int i=(history.size()-1); i>=0; i--) {
            int[] row = history.get(i);
            if (forwards) {
                value += row[row.length-1];
            } else {
                value = row[0] - value;
            }
        }

        return value;
    }

    private ArrayList<int[]> format(String[] lines) {
        ArrayList<int[]> sequences = new ArrayList<>();
        
        for (String line : lines) {
            String[] strings = line.split(" ");
            sequences.add(Lists.stringsToInts(strings));
        }
        
        return sequences;
    }
}
