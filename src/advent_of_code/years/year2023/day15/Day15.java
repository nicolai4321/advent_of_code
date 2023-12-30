package advent_of_code.years.year2023.day15;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Grid;
import advent_of_code.utils.Log;
import advent_of_code.utils.math.big.BigInt;

public class Day15 extends RootDay {
    public Day15(Year year, int day) {
        super(year, day, null, null);
        setInput1("input01.txt");
        setInput2("input01.txt");
        this.disableRun1();
    }
   
    @Override
    public String run1(String input) {
        String[] strings = input.split(",");

        BigInt total = new BigInt(0);
        
        for (String s : strings) {
            total = total.add(hash(s));
        }
        
        return total.toString();
    }
    
    HashMap<Integer, ArrayList<Lens>> hashMap = new HashMap<Integer, ArrayList<Lens>>();
    
    @Override
    public String run2(String input) {
        String[] strings = input.split(",");

        for (String s : strings) {
            Lens lens = new Lens(s);
            
            if (lens.getStrength() != null) {
                addLens(lens);
            } else {
                removeLens(lens);
            }
        }
                
        BigInt total = new BigInt(0);
        
        for (int key : hashMap.keySet()) {
            ArrayList<Lens> lenses = hashMap.get(key);
            
            for (int i=0; i<lenses.size(); i++) {
                Lens lens = lenses.get(i);
                
                int v = (key+1) * (i+1) * lens.getStrength();
                total = total.add(v);
            }
        }
        
        return total.toString();
    }
    
    private void removeLens(Lens lens) {
        int hashValue = hash(lens.getLabel());
        ArrayList<Lens> lenses = hashMap.get(hashValue);
        
        if (lenses != null) {
            String removeLabel = lens.getLabel();
            
            for (int i=0; i<lenses.size(); i++) {
                Lens l = lenses.get(i);
                if (removeLabel.equals(l.getLabel())) {
                    lenses.remove(i);
                    return;
                }
            }
        }
    }

    private void addLens(Lens lens) {
        int hashValue = hash(lens.getLabel());
        
        ArrayList<Lens> lenses = hashMap.get(hashValue);
        if (lenses == null) {
            lenses = new ArrayList<Lens>();
        }
        
        String label = lens.getLabel();
        for (int i=0; i<lenses.size(); i++) {
            Lens l = lenses.get(i);
            if (label.equals(l.getLabel())) {
                lenses.set(i, lens);
                return;
            }
        }
        lenses.add(lens);
        hashMap.put(hashValue, lenses);
    }

    private int hash(String s) {
        int value = 0;
        
        for (char c : s.toCharArray()) {
            int charValue = c;
            value += charValue;
            value = value * 17;
            value = value % 256;
        }
        return value;
    }
}
