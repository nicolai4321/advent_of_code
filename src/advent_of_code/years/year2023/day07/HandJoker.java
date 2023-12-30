package advent_of_code.years.year2023.day07;

import java.util.HashMap;
import java.util.HashSet;

import advent_of_code.utils.math.big.BigInt;

public class HandJoker implements Comparable<HandJoker> {
    private String cards;
    private BigInt bid;
    private BigInt rank;
    
    public HandJoker(String cards, int bid) {
        this.cards = cards;
        this.bid = new BigInt(bid);
        this.rank = null;
    }

    @Override
    public int compareTo(HandJoker oHand) {
        int cardType = getCardtype();
        int oCardType = oHand.getCardtype();
        
        if (cardType == oCardType) {
            char[] chars = cards.toCharArray();
            char[] oChars = oHand.getCards().toCharArray();
            
            for (int i=0; i<5; i++) {
                int cardValue = getCardValue(chars[i]);
                int oCardValue = oHand.getCardValue(oChars[i]);
                
                if (cardValue == oCardValue) {
                    continue;
                }
                
                return cardValue - oCardValue;
            }
            return 0;
        } else {
            return cardType - oCardType;
        }
    }

    private int getCardValue(char c) {
        switch(c) {
            case 'A':
                return 14;
            case 'K':
                return 13;
            case 'Q':
                return 12;
            case 'J':
                return 1;
            case 'T':
                return 10;
            case '9':
                return 9;
            case '8':
                return 8;
            case '7':
                return 7;
            case '6':
                return 6;
            case '5':
                return 5;
            case '4':
                return 4;
            case '3':
                return 3;
            case '2':
                return 2;
            default:
                throw new RuntimeException("Unknown card value: " + c);
        }
    }

    private int getCardtype() {
        char[] chars = cards.toCharArray();
        HashSet<Character> set = new HashSet<>();
        
        for (char c : chars) {
            set.add(c);
        }
        set.remove('J');
        if (set.size() == 0) {
            set.add('J');
        }
        
        if (set.size() == 1) {
            return 7;
        }
        
        if (set.size() == 2 && nrSameCard() == 4) {
            return 6;
        }
        
        if (set.size() == 2) {
            return 5;
        }
        
        if (nrSameCard() == 3) {
            return 4;
        }
        
        if (nrPairs() == 2) {
            return 3;
        }
        
        if (nrPairs() == 1) {
            return 2;
        }

        return 1;
    }

    private int nrPairs() {
        HashMap<Character, Integer> map = new HashMap<>();
        int jokers = 0;
        
        for (char c : cards.toCharArray()) {
            if (c == 'J') {
                jokers++;
                continue;
            }
            
            Integer value = map.get(c);
            
            if (value == null) {
                value = 0;
            }
            
            map.put(c, value + 1);
        }
        
        int pairs = 0;
        for (char c : map.keySet()) {
            int value = map.get(c);
            if (2 <= value) {
                pairs++;
            } else if (1 == value && 0<jokers) {
                pairs++;
                jokers--;
            }
        }
        
        return pairs;
    }

    private int nrSameCard() {
        HashMap<Character, Integer> map = new HashMap<>();
        int jokers = 0;
        
        for (char c : cards.toCharArray()) {
            if (c == 'J') {
                jokers++;
                continue;
            }
            Integer value = map.get(c);
            
            if (value == null) {
                value = 0;
            }
            
            map.put(c, value + 1);
        }
        
        int highest = 0;
        for (char c : map.keySet()) {
            int value = map.get(c);
            highest = Math.max(value, highest);
        }
        
        return highest + jokers;
    }

    public void setRank(int rank) {
        this.rank = new BigInt(rank);
    }
    
    public BigInt getBid() {
        return bid;
    }
    
    public String getCards() {
        return cards;
    }
    
    public BigInt getRank() {
        return rank;
    }
}
