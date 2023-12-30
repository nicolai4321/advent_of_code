package advent_of_code.years.year2023.day07;

import java.util.ArrayList;
import java.util.Collections;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.math.big.BigInt;

public class Day07 extends RootDay {
    public Day07(Year year, int day) {
        super(year, day, "246424613", "248256639");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[] lines = input.split("\n");
        ArrayList<Hand> hands = getHands(lines);
        Collections.sort(hands);
        
        for (int i=0; i<hands.size(); i++) {
            int rank = i + 1;
            Hand hand = hands.get(i);
            hand.setRank(rank);
        }
        
        
        BigInt winnings = BigInt.ZERO;
        for (int i=0; i<hands.size(); i++) {
            Hand hand = hands.get(i);
            winnings = winnings.add(hand.getBid().mult(hand.getRank()));
        }
        
        return winnings + "";
    }

    @Override
    public String run2(String input) {
        String[] lines = input.split("\n");
        ArrayList<HandJoker> hands = getJokerHand(lines);
        Collections.sort(hands);
        
        for (int i=0; i<hands.size(); i++) {
            int rank = i + 1;
            HandJoker hand = hands.get(i);
            hand.setRank(rank);
        }
        
        
        BigInt winnings = BigInt.ZERO;
        for (int i=0; i<hands.size(); i++) {
            HandJoker hand = hands.get(i);
            winnings = winnings.add(hand.getBid().mult(hand.getRank()));
        }
        
        return winnings + "";
    }

    private ArrayList<HandJoker> getJokerHand(String[] lines) {
        ArrayList<HandJoker> hands = new ArrayList<>();
        
        for (String line : lines) {
            String cards = line.split(" ")[0];
            int bid = Integer.parseInt(line.split(" ")[1]);
            hands.add(new HandJoker(cards, bid));
        }
        
        return hands;
    }
    
    private ArrayList<Hand> getHands(String[] lines) {
        ArrayList<Hand> hands = new ArrayList<>();
        
        for (String line : lines) {
            String cards = line.split(" ")[0];
            int bid = Integer.parseInt(line.split(" ")[1]);
            hands.add(new Hand(cards, bid));
        }
        
        return hands;
    }
}
