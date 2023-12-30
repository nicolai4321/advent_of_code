package advent_of_code.years.year2022.day13;

import java.util.ArrayList;

public class Listz extends Item {
    private ArrayList<Item> list;
    
    public Listz() {
        list = new ArrayList<Item>();
    }

    public ArrayList<Item> getList() {
        return list;
    }

    public void setList(ArrayList<Item> list) {
        this.list = list;
    }

    public void add(Item item) {
        list.add(item);
    }
    
    @Override
    public String toString() {
        String s = "[";
        for (Item item : list) {
            s += item.toString() + ",";
        }
        s += "]";
        return s;
    }

    @Override
    public boolean isInt() {
        return false;
    }

    @Override
    public boolean isList() {
        return true;
    }
}
