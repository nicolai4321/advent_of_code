package advent_of_code.years.year2023.day19;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Interval;
import advent_of_code.utils.Lists;
import advent_of_code.utils.math.big.BigInt;


public class Day19 extends RootDay {
    public Day19(Year year, int day) {
        super(year, day, "575412", "126107942006821");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * Find the sum of all the variables x, m, a and s if they are valid.
     */
    @Override
    public String run1(String input) {
        ArrayList<Rule> rules = getRules(input);
        linkRules(rules);
        ArrayList<Ranges> rangesLst = getRanges(input);

        Rule ruleStart = findRule(rules, "in");
        BigInt sum = BigInt.ZERO;
        for (Ranges ranges : rangesLst) {
            ArrayList<Ranges> processedRanges = ruleStart.procesRanges(ranges);
            for (Ranges r : processedRanges) {
                if (r.getOption() == Option.ACCEPT) {
                    sum = sum.add(BigInt.add(BigInt.add(r.x[0], r.m[0]), BigInt.add(r.a[0], r.s[0])));
                }
            }
        }
       
        return sum + "";
    }

    /**
     * Find the total amount of possibilities for (x: [1-4000], m: [1-4000], a: [1-4000], s: [1-4000])
     */
    @Override
    public String run2(String input) {
        ArrayList<Rule> rules = getRules(input);
        linkRules(rules);
        return getCombinations(rules) + "";
    }
    
    /**
     * @param rules
     * @return amount of valid combinations
     */
    private BigInt getCombinations(ArrayList<Rule> rules) {
        Rule ruleStart = findRule(rules, "in");
        
        int[] x = {1, 4000};
        int[] m = {1, 4000};
        int[] a = {1, 4000};
        int[] s = {1, 4000};
        Ranges ranges = new Ranges(x, m, a, s, Option.UNKOWN);
        
        ArrayList<Ranges> rangesLst = ruleStart.procesRanges(ranges);
        
        BigInt sum = BigInt.ZERO;
        for (Ranges r : rangesLst) {
            if (r.getOption() == Option.ACCEPT) {
                BigInt xSize = new BigInt(Interval.getSize(r.x));
                BigInt mSize = new BigInt(Interval.getSize(r.m));
                BigInt aSize = new BigInt(Interval.getSize(r.a));
                BigInt sSize = new BigInt(Interval.getSize(r.s));
                
                sum = sum.add(xSize.mult(mSize).mult(aSize).mult(sSize));
            }
        }
        
        return sum;
    }
    
    /**
     * @param input
     * @return ranges
     */
    private ArrayList<Ranges> getRanges(String input) {
        ArrayList<Ranges> rangesLst = new ArrayList<Ranges>();

        String[] lines = input.split("\n\n")[1].split("\n");
        for (String line : lines) {
            line = line.replaceAll("\\}", "")
                       .replaceAll("\\{", "")
                       .replaceAll("x=", "")
                       .replaceAll("m=", "")
                       .replaceAll("a=", "")
                       .replaceAll("s=", "");
            int[] lst = Lists.stringsToInts(line.split(","));
            int[] x = { lst[0], lst[0] };
            int[] m = { lst[1], lst[1] };
            int[] a = { lst[2], lst[2] };
            int[] s = { lst[3], lst[3] };
            
            rangesLst.add(new Ranges(x, m, a, s, Option.UNKOWN));
        }

        return rangesLst;
    }
    
    private void linkRules(ArrayList<Rule> rules) {
        for (Rule rule : rules) {
            rule.linkRules(rules);
        }
    }
    
    private Rule findRule(ArrayList<Rule> rules, String ruleName) {
        for (Rule rule : rules) {
            if (rule.ruleName.equals(ruleName)) {
                return rule;
            }
        }
        throw new RuntimeException("Could not find rule: " + ruleName);
    }
    
    private ArrayList<Rule> getRules(String input) {
        ArrayList<Rule> rules = new ArrayList<Rule>();
        
        String[] lines = input.split("\n");
        for (String line : lines) {
            if (line.equals("")) {
                return rules;
            }

            String ruleName = line.split("\\{")[0];
            String ruleList = line.split("\\{")[1].replaceAll("}", "");
            String[] lst = ruleList.split(",");
            
            rules.add(new Rule(ruleName, lst));
        }

        return rules;
    }
}
