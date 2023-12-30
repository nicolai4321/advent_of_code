package advent_of_code.years.year2023.day04;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Lists;
import advent_of_code.utils.math.big.BigInt;

public class Day04 extends RootDay {
    public Day04(Year year, int day) {
        super(year, day, "26914", "13080971");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[] lines = input.split("\n");
        ArrayList<Card> cards = getCards(lines);
        return getScore(cards) + "";
    }

    @Override
    public String run2(String input) {
        String[] lines = input.split("\n");
        ArrayList<Card> cards = getCards(lines);
        return getScratchCards(cards) + "";
    }
    
    private BigInt getScratchCards(ArrayList<Card> cards) {
        HashMap<Card, Integer> hM = new HashMap<Card, Integer>();
        HashMap<Integer, Card> hMRev = new HashMap<Integer, Card>();
        
        int ii=1;
        for (Card card : cards) {
            hM.put(card, 1);
            hMRev.put(ii, card);
            ii++;
        }
        
        for (Card card : cards) {
            int winnings = 0;
            
            for (int i : card.getNumbers()) {
                if (card.isWinning(i)) {
                    winnings++;
                }
            }
            
            int id = card.getId();
            
            for (int i=1; i<=winnings; i++) {
                int nextId = id + i;
                Card nextCard = hMRev.get(nextId);
                int amount = hM.get(card);
                
                Integer currentValue = hM.get(nextCard);
                hM.put(nextCard, currentValue + amount);
            }
        }
        
        BigInt totalCards = new BigInt("0");
        for (Card card : hM.keySet()) {
            totalCards = BigInt.add(hM.get(card), totalCards);
        }
        
        return totalCards;
    }

    private int getScore(ArrayList<Card> cards) {
        int score = 0;
        
        for (Card card : cards) {
            int localScore = 0;
            for (int i : card.getNumbers()) {
                if (card.isWinning(i)) {
                    if (localScore == 0) {
                        localScore = 1;
                    } else {
                        localScore = localScore * 2;
                    }
                }
            }
            score += localScore;
        }
        
        return score;
    }
    
    private ArrayList<Card> getCards(String[] lines) {
        ArrayList<Card> cards = new ArrayList<Card>();
        
        for (String line : lines) {
            line = line.replaceAll("Card ", "").replaceAll("( )+", " ");
            String name = line.split(":")[0];
            int[] winningNumbers = Lists.stringsToInts(line.split(": ")[1].split(" \\| ")[0].split(" "));
            int[] numbers = Lists.stringsToInts(line.split(": ")[1].split(" \\| ")[1].split(" "));
            
            Card card = new Card(name, winningNumbers, numbers);
            cards.add(card);
        }
        
        return cards;
    }
}
