package advent_of_code.years.year2023.day19;

import java.util.ArrayList;
import java.util.HashMap;

public class Rule {
    private HashMap<String, Rule> hM;
    public final String ruleName;
    public final String[] lst;
    private final static String ACCEPT = "A";
    private final static String REJECT = "R";
    
    public Rule(String ruleName, String[] lst) {
        this.ruleName = ruleName;
        this.lst = lst;
        hM = new HashMap<String, Rule>();
    }
    
    /**
     * Proces ranges and decide if they are accepted or rejected
     * @param ranges
     * @return a list of ranges
     */
    public ArrayList<Ranges> procesRanges(Ranges ranges) {
        ArrayList<Ranges> rangesLst = new ArrayList<Ranges>();
        
        for (String s : lst) {
            if (ranges == null) {
                return rangesLst;
            }

            if (s.equals(ACCEPT)) {
                ranges.setOption(Option.ACCEPT);
                rangesLst.add(ranges);
                
                return rangesLst;
            }
            
            if (s.equals(REJECT)) {
                ranges.setOption(Option.REJECT);
                rangesLst.add(ranges);
                
                return rangesLst;
            }
            
            if (s.contains(":")) {
                String result = s.split(":")[1];
                char operator = s.contains("<") ? '<' : '>';
                
                String compareVariable = s.split(operator + "")[0];
                int compareValue = Integer.parseInt(s.split(operator + "")[1].split(":")[0]);
                
                ArrayList<Ranges> rangesSplit = ranges.split(compareVariable, operator, compareValue);
                Ranges rangesApplied = rangesSplit.get(0);
                ranges = rangesSplit.get(1);
                
                if (rangesApplied == null) {
                    continue;
                } else if (result.equals(ACCEPT)) {
                    rangesApplied.setOption(Option.ACCEPT);
                    rangesLst.add(rangesApplied);
                } else if (result.equals(REJECT)) {
                    rangesApplied.setOption(Option.REJECT);
                    rangesLst.add(rangesApplied);
                } else {
                    Rule refRule = hM.get(result);
                    rangesLst.addAll(refRule.procesRanges(rangesApplied));
                }
            } else {
                Rule refRule = hM.get(s);
                rangesLst.addAll(refRule.procesRanges(ranges));
                return rangesLst;
            }
        }
        
        return rangesLst;
    }
    
    public void linkRules(ArrayList<Rule> rules) {
        for (Rule rule : rules) {
            if (!rule.ruleName.equals(ruleName)) {
                hM.put(rule.ruleName, rule);
            }
        }
    }
    
    public String toString() {
        String lstString = "[";
        
        for (String s : lst) {
            lstString += s + "; ";
        }
        
        return ruleName + ":" + lstString + "]";
    }
}
