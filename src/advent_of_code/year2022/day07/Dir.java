package advent_of_code.year2022.day07;

import java.util.ArrayList;

import advent_of_code.utils.Log;

/**
 * Class that represent a directory
 * @author nicolai
 *
 */
public class Dir extends DataObj {
    private String name;
    private Dir parent;
    private ArrayList<DataObj> content = new ArrayList<DataObj>();

    public Dir(String name, Dir parent) {
        this.name = name;
        this.parent = parent;
    }
    
    /**
     * @return sum of part 1
     */
    public int sum1() {
        int sum = 0;
        
        if (getSize() <= 100000) {
            sum += getSize();
        }
        
        for (Dir directory : getAllDirectories()) {
            if (directory.getSize() <= 100000) {
                sum += directory.getSize();
            }
        }

        return sum;
    }
    
    /**
     * @param neededSpace
     * @return 
     */
    public int smallestSizeOfDirectories(int minSpace) {
        int smallestSpace = getSize();

        for (Dir directory : getAllDirectories()) {
            int directorySize = directory.getSize();
            if (minSpace <= directorySize && directorySize < smallestSpace) {
                smallestSpace = directorySize;
            }
        }

        return smallestSpace;
    }
    
    /**
     * @return all directories and sub directories
     */
    public ArrayList<Dir> getAllDirectories() {
        ArrayList<Dir> directories = new ArrayList<Dir>();
        for (DataObj dataObj : content) {
            if (dataObj instanceof Dir) {
                Dir directory = (Dir) dataObj;
                directories.add(directory);
                
                ArrayList<Dir> subDirectories = directory.getAllDirectories();
                for (Dir subDirectory : subDirectories) {
                    directories.add(subDirectory);
                }
            }
        }
        return directories;
    }
    
    /**
     * @param name
     * @return the directory that matches the name (does not include sub folders)
     */
    public Dir getDir(String name) {
        for (DataObj dataObj : content) {
            if (dataObj instanceof Dir && dataObj.getName().equals(name)) {
                return (Dir) dataObj;
            }
        }
        return null;
    }

    @Override
    public int getSize() {
        int sum = 0;
        for (DataObj dataObj : content) {
            sum += dataObj.getSize();
        }
        return sum;
    }
    
    @Override
    public String toString(String indentation) {
        String s = indentation + name + " {\n";
        for (DataObj dataObj : content) {
            s += indentation + " " + dataObj.toString(indentation + " ") + "\n";
        }
        if (0 < content.size()) {
            s = s.replaceAll("\n$", "");            
        }
        s += "\n" + indentation + "}";
        return s;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    public void add(DataObj dataObj) {
        content.add(dataObj);
    }
    
    public Dir getParent() {
        return parent;
    }
}
