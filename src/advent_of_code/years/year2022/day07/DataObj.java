package advent_of_code.years.year2022.day07;

public abstract class DataObj {
    public abstract int getSize();
    protected abstract String toString(String indentation);
    public abstract String getName();
    
    public String toString() {
        return toString("");
    }
}
