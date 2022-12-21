package advent_of_code.year2022.day13;

import java.util.ArrayList;
import java.util.Comparator;

public class MySort implements Comparator<Item> {
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
    
    @Override
    public int compare(Item item0, Item item1) {
        Boolean b = rightOrder(item0, item1);
        if (b == null) {
            throw new RuntimeException("No order?");
        } else if (b) {
            return -1;        
        } else {
            return 1;
        }
    }
}
