package advent_of_code.years.year2022.day13;

import java.util.ArrayList;
import java.util.Collections;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.Log;
import advent_of_code.utils.Text;

//TODO refactor
public class Day13 extends RootDay {
    public Day13(Year year, int day) {
        super(year, day, "4809", "22600");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        ArrayList<Item[]> pairs = getPairs(input);
        return examinePairs(pairs) + "";
    }

    @Override
    public String run2(String input) {
        ArrayList<Item[]> pairs = getPairs(input);
        ArrayList<Item> pairsOrdered = orderPairs(pairs);
        return getDecoderIndex(pairsOrdered) +"";
    }
    
    private int getDecoderIndex(ArrayList<Item> pairsOrdered) {
        int sum = 1;
        int nr = 0;
        for (int i=0; i<pairsOrdered.size(); i++) {
            Item item = pairsOrdered.get(i);

            if (item.isList()) {
                Listz lst = (Listz) item;
                ArrayList<Item> arLst = lst.getList();
                if (arLst.size() == 1) {
                    Item subItem = arLst.get(0);
                    if (subItem.isList()) {
                        Listz subLst = (Listz) subItem;
                        ArrayList<Item> subArLst = subLst.getList();
                        if (subArLst.size() == 1) {
                            Item decoderItem = subArLst.get(0);
                            if (decoderItem.isInt()) {
                                Intz decoderInt = (Intz) decoderItem;
                                if (decoderInt.getInt() == 2 || decoderInt.getInt() == 6) {
                                    sum = sum * (i + 1);
                                    nr++;
                                }
                            }
                        }
                    }
                }
            }
        }
        
        
        return sum;
    }

    private ArrayList<Item> orderPairs(ArrayList<Item[]> pairs) {
        Intz decoder2Int = new Intz(2);
        
        Listz decoder2Lst = new Listz();
        decoder2Lst.add(decoder2Int);
        
        Listz decoder2 = new Listz();
        decoder2.add(decoder2Lst);
        
        Intz decoder6Int = new Intz(6);
        
        Listz decoder6Lst = new Listz();
        decoder6Lst.add(decoder6Int);
        
        Listz decoder6 = new Listz();
        decoder6.add(decoder6Lst);
        
        ArrayList<Item> items = new ArrayList<Item>();
        for (Item[] pair : pairs) {
            items.add(pair[0]);
            items.add(pair[1]);
        }
        items.add(decoder2);
        items.add(decoder6);
        
        Collections.sort(items, new MySort());
        
        return items;
    }

    private int examinePairs(ArrayList<Item[]> pairs) {
        int sum = 0;
        for (int i=0; i<pairs.size(); i++) {
            Item[] pair = pairs.get(i);
            Boolean rightOrder = rightOrder(pair[0], pair[1]);
            
            if (rightOrder == null) {
                throw new RuntimeException("Error right order is null: " + pair[0].toString() + " " + pair[1].toString());
            } else if (rightOrder) {
                sum += (i + 1);
            }
        }
        return sum;
    }
    
    private Boolean rightOrder(Item item0, Item item1) {
        if (item0.isInt() && item1.isInt()) {
            int i0 = ((Intz) item0).getInt();
            int i1 = ((Intz) item1).getInt();
            
            if (i0 < i1) {
                return true;
            } else if (i1 < i0) {
                return false;
            } else {
                return null;
            }
        } else if (item0.isList() && item1.isList()) {
            ArrayList<Item> list0 = ((Listz) item0).getList();
            ArrayList<Item> list1 = ((Listz) item1).getList();
            
            int lengthMax = Math.max(list0.size(), list1.size());
            for (int i=0; i<lengthMax; i++) {
                if (i == list0.size()) {
                    return true;
                } else if (i == list1.size()) {
                    return false;
                }
                
                Item subItem0 = list0.get(i);
                Item subItem1 = list1.get(i);
                Boolean b = rightOrder(subItem0, subItem1);
                if (b != null && b) {
                    return true;
                } else if (b != null && !b) {
                    return false;
                }
            }
            return null;
        } else if (item0.isInt() && item1.isList()) {
            Listz newList = new Listz();
            newList.add(item0);
            return rightOrder(newList, item1);
        } else if (item0.isList() && item1.isInt()) {
            Listz newList = new Listz();
            newList.add(item1);
            return rightOrder(item0, newList);
        }

        throw new RuntimeException("Unknown comparision: " + item0.toString() + " & " + item1.toString());
    } 
    
    /**
     * This method maps the input to a list of objects
     * @param input
     * @return
     */
    private ArrayList<Item[]> getPairs(String input) {
        ArrayList<Item[]> pairs = new ArrayList<Item[]>();
        String[] strings = (input + "\n ").split("\n");
        
        for (int i=0; i<strings.length; i++) {
            String s = strings[i];
            if (Text.isBlank(s)) {
                String s0 = strings[i-1];
                String s1 = strings[i-2];
                
                Item[] pair = new Item[2];
                pair[0] = getItem(s1);
                pair[1] = getItem(s0);
                
                pairs.add(pair);
            }
        }
        return pairs;
    }
    
    private int countChar(String text, char c) {
        int count = 0;
        for (char ci : text.toCharArray()) {
            if (c == ci) {
                count++;
            }
        }
        return count;
    }
    
    /**
     * @param expression
     * @return item that contains all the content
     */
    private Item getItem(String expression) {
        //empty
        if (Text.isBlank(expression)) {
            return null;
        }
        
        //list
        Pattern patternList = Pattern.compile("^(\\[)(.*)(\\])$");
        Matcher matchList = patternList.matcher(expression);
        if (matchList.find()) {
            String listContent = matchList.group(2);

            Listz list = new Listz();
            int brackets = 0;
            
            String[] contents = listContent.split(",");
            String contentStretch = "";
            for (String content : contents) {
                brackets += countChar(content, '[');
                brackets -= countChar(content, ']');
                
                if (brackets == 0) {
                    contentStretch += content;
                    Item item = getItem(contentStretch);
                    if (item != null) {
                        list.add(item);                        
                    }
                    contentStretch = "";
                } else {
                    contentStretch += content + ",";
                }
            }
            
            return list;
        }
        
        //int
        Pattern patternInt = Pattern.compile("^[0-9]+$");
        Matcher matchInt = patternInt.matcher(expression);
        if (matchInt.find()) {
            int i = Integer.parseInt(expression);
            return new Intz(i);
        }
        
        throw new RuntimeException("Unknown expression: '" + expression + "'");
    }
    
    private void print(ArrayList<Item> items) {
        for (Item item : items) {
            Log.show(item.toString());
        }
    }
}
