package advent_of_code.utils;

public class Text {
    public static boolean isBlank(String s) {
        return s.isEmpty() || s.replaceAll("( )+", " ").equals(" ");
    }
}
