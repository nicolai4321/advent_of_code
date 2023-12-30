package advent_of_code.years.year2022.day07;

public class File extends DataObj {
    private String name;
    private int size;
    
    public File(String name, int size) {
        this.name = name;
        this.size = size;
    }

    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public int getSize() {
        return size;
    }
        
    @Override
    public String toString(String indentation) {
        return indentation + name + "(" + size + ")";
    }
}
