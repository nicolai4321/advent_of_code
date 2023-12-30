package advent_of_code.years.year2020.day07;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;

public class Day07 extends RootDay {
    public Day07(Year year, int day) {
        super(year, day, "278", "45157");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    public String run1(String input) {
        ArrayList<Rule> rules = formatRules(input);
        int count = countCanContain(rules, "shiny gold");
        
        return count + "";
    }
    
    @Override
    public String run2(String input) {
        ArrayList<Rule> rules = formatRules(input);
        int count = countBags(rules, "shiny gold");
        
        return count + "";
    }
    
    private int countBags(ArrayList<Rule> rules, String bag) {
        int count = 0;
        
        for (Rule rule : rules) {
            if (rule.getBagName().equals(bag)) {
                count = rule.countBags();
                break;
            }
        }
        
        return count;
    }

    private int countCanContain(ArrayList<Rule> rules, String bag) {
        int count = 0;
        
        for (Rule rule : rules) {
            if (rule.canContainBag(bag)) {
                count++;
            }
        }
        
        return count;
    }

    private ArrayList<Rule> formatRules(String rules) {
        rules = rules.replaceAll("bags", "").replaceAll("bag", "").replaceAll("( )+", " ");
        ArrayList<Rule> allRules = new ArrayList<Rule>();
        ArrayList<HashMap<String, Integer>> rulesReferences = new ArrayList<HashMap<String, Integer>>();
        
        for (String rule : rules.split("\n")) {
            String color = rule.split(" contain ")[0];
            Rule r = new Rule(color);
            HashMap<String, Integer> references = new HashMap<String, Integer>();
            rulesReferences.add(references);
            
            String[] rawContent = rule.split(" contain ")[1].replaceAll("\\.", "").split(", ");
            for (String s : rawContent) {
                if (s.equals("no other ")) {
                    continue;
                }
                int nr = Integer.parseInt(s.split(" ")[0]);
                String subColor = s.replaceAll("^" + nr + " ", "").replaceAll(" $", "");
                references.put(subColor, nr);
            }
            
            allRules.add(r);
        }
        
        // add references
        for (int i=0; i<allRules.size(); i++) {
            Rule rule = allRules.get(i);
            HashMap<String, Integer> references = rulesReferences.get(i);
            
            for (String bagName : references.keySet()) {
                for (Rule r : allRules) {
                    if (r.getBagName().equals(bagName)) {
                        rule.addRule(r, references.get(bagName));
                        break;
                    }
                }
            }
        }
        
        return allRules;
    }
}
