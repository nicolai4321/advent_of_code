package advent_of_code.utils;

import java.util.HashMap;

public class LightGrid<T> {
    private HashMap<Integer, HashMap<Integer, T>> rows;
    private Integer width = null;
    private Integer height = null;
    private T defaultValue;
    
    //TODO can this constructor be removed?
    public LightGrid(int width, int height) {
        this.width = width;
        this.height = height;
        rows = new HashMap<Integer, HashMap<Integer, T>>();
    }
    
    public LightGrid() {
        rows = new HashMap<Integer, HashMap<Integer, T>>();
    }
    
    /**
     * Sets the default value for all cells
     * @param defaultValue
     */
    public void setDefaultValue(T defaultValue) {
        this.defaultValue = defaultValue;
    }
    
    /**
     * Sets a value at (x,y)
     * @param x
     * @param y
     * @param value
     */
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
    
    /**
     * @param x
     * @param y
     * @return value on (x,y) if it exists. Otherwise return default
     */
    public T get(int x, int y) {
        if (rows.get(y) == null) {
            return defaultValue;
        }
        
        T value = rows.get(y).get(x);
        if (value == null) {
            return defaultValue;
        } else {
            return value;
        }
    }
    
    /**
     * @return current width
     */
    public int getWidth() {
        if (width == null) {
            int[] minMax = getMinMaxX();
            return minMax[1] - minMax[0] + 1;
        }
        
        return width;
    }
    
    /**
     * @return current height
     */
    public int getHeight() {
        if (height == null) {
            int[] minMaY = getMinMaxY();
            
            return minMaY[1] - minMaY[0] + 1;
        }
        
        return height;
    }

    /**
     * @return { minX, maxX }
     */
    public int[] getMinMaxX() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (int y : rows.keySet()) {
            HashMap<Integer, T> row = rows.get(y);
            
            for (int x : row.keySet()) {
                min = Math.min(x, min);
                max = Math.max(x, max);
            }
        }
        return new int[] {min, max};
    }
    
    /**
     * @return { minY, maxY }
     */
    public int[] getMinMaxY() {
        int min = Integer.MAX_VALUE;
        int max = Integer.MIN_VALUE;
        
        for (int y : rows.keySet()) {
            min = Math.min(y, min);
            max = Math.max(y, max);
        }
        return new int[] {min, max};
    }

    public void print() {
        int[] minMaxX = getMinMaxX();
        int[] minMaxY = getMinMaxY();
        
        for (int y=minMaxY[0]; y<=minMaxY[1]; y++) {
            String rowString = "";
            for (int x=minMaxX[0]; x<=minMaxX[1]; x++) {
                rowString += get(x, y);
            }
            Log.show(rowString);
        }
    }
}
