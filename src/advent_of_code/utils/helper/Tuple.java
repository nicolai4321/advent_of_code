package advent_of_code.utils.helper;

public class Tuple<V, U> {
    public final V first;
    public final U second;
    
    public Tuple(V first, U second) {
        this.first = first;
        this.second = second;
    }
    
    public static <V, U> Tuple<V, U> set(V first, U second) {
        return new Tuple<V, U>(first, second);
    }
}
