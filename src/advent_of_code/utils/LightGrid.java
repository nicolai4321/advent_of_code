package advent_of_code.utils;

import java.util.HashMap;

public class LightGrid<T> {
    private HashMap<Integer, HashMap<Integer, T>> rows;
    private Integer width = null;
    private Integer height = null;
    private T defaultValue;
    
    public LightGrid(int width, int height) {
        this.width = width;
        this.height = height;
        rows = new HashMap<Integer, HashMap<Integer, T>>();
    }
    
    public LightGrid() {
        rows = new HashMap<Integer, HashMap<Integer, T>>();
    }
    
    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    public void set(int x, int y, T value) {
        HashMap<Integer, T> row = rows.get(y);
        if (row == null) {
            row = new HashMap<Integer, T>();
            row.put(x, value);
            rows.put(y, row);
        } else {
            row.put(x, value);
        }
    }
    
    public T get(int x, int y) {
        T value = rows.get(y).get(x);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }
    
    public int getWidth() {
        return width;
    }
    
    public int getHeight() {
        return height;
    }
}
