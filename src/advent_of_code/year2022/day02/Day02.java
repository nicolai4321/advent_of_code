package advent_of_code.year2022.day02;

import advent_of_code.utils.RootDay;

public class Day02 extends RootDay {
    public Day02() {
        super(2022, 2, "9177", "12111");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }
    
    @Override
    public String run1(String input) {
        return getTotalScore(input, false) + "";
    }

    @Override
    public String run2(String input) {
        return getTotalScore(input, true) + "";
    }
    
    /**
     * @param rounds
     * @param usePredictionMap
     * @return total score
     */
    private int getTotalScore(String input, boolean usePredictionMap) {
        String[] rounds = input.split("\n");
        int points = 0;
        for (String round : rounds) {
            String symbol0 = round.split(" ")[0];
            String symbol1 = round.split(" ")[1];
            if (usePredictionMap) {
                points += getPoints(map(symbol0), predictionMap(map(symbol0), symbol1));
            } else {
                points += getPoints(map(symbol0), map(symbol1));                
            }
        }
        return points;
    }
    
    /**
     * Maps hand to rock/paper/scissor (r/p/s)
     * @param hand
     * @return r/p/s
     */
    private static String map(String hand) {
        if (hand.equals("A") || hand.equals("X")) {
            return "r";
        } else if(hand.equals("B") || hand.equals("Y")) {
            return "p";
        } else {
            return "s";
        }
    }
    
    /**
     * 
     * @param opponent
     * @param prediction
     * @return hand of prediction
     */
    private static String predictionMap(String opponent, String prediction) {
        if (opponent.equals("r") && prediction.equals("X")) return "s";
        if (opponent.equals("r") && prediction.equals("Y")) return "r";
        if (opponent.equals("r") && prediction.equals("Z")) return "p";
        
        if (opponent.equals("p") && prediction.equals("X")) return "r";
        if (opponent.equals("p") && prediction.equals("Y")) return "p";
        if (opponent.equals("p") && prediction.equals("Z")) return "s";
        
        if (opponent.equals("s") && prediction.equals("X")) return "p";
        if (opponent.equals("s") && prediction.equals("Y")) return "s";
        if (opponent.equals("s") && prediction.equals("Z")) return "r";
        
        throw new RuntimeException("Could not calculate prediction");
    }
    
    /**
     * @param opp
     * @param you
     * @return points
     */
    public static int getPoints(String opp, String you) {
        return getPointsForItem(you) + getWinningOutcomePoints(opp, you);
    }
    
    /**
     * @param you
     * @return points for item
     */
    public static int getPointsForItem(String you) {
        if (you.equals("r")) {
            return 1;
        } else if (you.equals("p")) {
            return 2;
        } else { //s
            return 3;
        }
    }
    
    /**
     * @param opp
     * @param you
     * @return points for winning outcome
     */
    public static int getWinningOutcomePoints(String opp, String you) {        
        if (opp.equals("r") && you.equals("r")) return 3;
        if (opp.equals("r") && you.equals("p")) return 6;
        if (opp.equals("r") && you.equals("s")) return 0;
        
        if (opp.equals("p") && you.equals("r")) return 0;
        if (opp.equals("p") && you.equals("p")) return 3;
        if (opp.equals("p") && you.equals("s")) return 6;    
        
        if (opp.equals("s") && you.equals("r")) return 6;
        if (opp.equals("s") && you.equals("p")) return 0;
        if (opp.equals("s") && you.equals("s")) return 3;
        
        throw new RuntimeException("Could not calculate outcome points");
    }
}
