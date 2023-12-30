package advent_of_code.years.year2021.day10;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.math.big.BigInt;

public class Day10 extends RootDay {
	public Day10(Year year, int day) {
		super(year, day, "388713", "3539961434");
        setInput1("input01.txt");
        setInput2("input01.txt");
	}

	@Override
	public String run1(String input) {
	    return getSyntaxErrorScore(input.split("\n")) + "";
	}

	@Override
	public String run2(String input) {
        return getMiddleScore(input.split("\n")) + "";
	}
	
	private BigInteger getMiddleScore(String[] lines) {
	    ArrayList<BigInteger> scores = new ArrayList<BigInteger>();
	    
	    for (String line : lines) {
	        BigInt score = getScore(line);
	        if (score != null) {
	            scores.add(score);
	        }
	    }
	    
	    Collections.sort(scores);
        return scores.get((scores.size() )/2);
    }

    private BigInt getScore(String line) {
        if (findWrongClosing(line) != null) {
            return null;
        }
        
        return scoreClosing(BigInt.get("0"), line);
    }

    private int getSyntaxErrorScore(String[] lines) {
	    int score = 0;
	    
	    for (String line : lines) {
	        Integer i = findWrongClosing(line); 
	        if (i != null) {
	            score += i;
	        }
	    }
	    
	    return score;
	}

    private BigInt scoreClosing(BigInt score, String line) {
        String lastLine = "";
        
        while (!line.equals(lastLine)) {
            lastLine = line;
            line = simplify(line);
        }
        
        char c = line.charAt(line.length() - 1);

        if (c == '(') {
            score = score.mult("5").add("1");
        }
        
        if (c == '[') {
            score = score.mult("5").add("2");
        }

        if (c == '{') {
            score = score.mult("5").add("3");
        }

        if (c == '<') {
            score = score.mult("5").add("4");
        }
        
        if (line.length() == 1) {
            return score;
        }
        
        return scoreClosing(score, line.substring(0, line.length()-1));
    }
    
	private String simplify(String line) {
        return line.replaceAll("\\(\\)", "").replaceAll("\\{\\}", "").replaceAll("\\[\\]", "").replaceAll("<>", "");
    }

    private Integer findWrongClosing(String line) {
	    String tmp = "";
	    
	    char[] chars = line.toCharArray();
	    for (int i=0; i<chars.length ; i++) {
	        char c = chars[i];
	        
	        switch (c) {
                case '(':
                case '[':
                case '{':
                case '<':
                    tmp += c;
                    break;
                case ')':
                    if (tmp.charAt(tmp.length() - 1) == '(') {
                        tmp = tmp.substring(0, tmp.length() - 1);
                        break;
                    } else {
                        return 3;
                    }
                case ']':
                    if (tmp.charAt(tmp.length() - 1) == '[') {
                        tmp = tmp.substring(0, tmp.length() - 1);
                        break;
                    } else {
                        return 57;
                    }
                case '}':
                    if (tmp.charAt(tmp.length() - 1) == '{') {
                        tmp = tmp.substring(0, tmp.length() - 1);
                        break;
                    } else {
                        return 1197;
                    }
                case '>':
                    if (tmp.charAt(tmp.length() - 1) == '<') {
                        tmp = tmp.substring(0, tmp.length() - 1);
                        break;
                    } else {
                        return 25137;
                    }
                default:
                    throw new RuntimeException("unknown error");
	        }
	    }
	    
	    return null;
	}
}
