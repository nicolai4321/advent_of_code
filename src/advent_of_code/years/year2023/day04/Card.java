package advent_of_code.years.year2023.day04;

public class Card {
    private String name;
    private int[] winningNumbers;
    private int[] numbers;
    
    public Card(String name, int[] winningNumbers, int[] numbers) {
        this.name = name;
        this.winningNumbers = winningNumbers;
        this.numbers = numbers;
    }

    public int[] getNumbers() {
        return numbers;
    }

    public boolean isWinning(int i) {
        for (int j : winningNumbers) {
            if (i == j) {
                return true;
            }
        }
        return false;
    }

    public int getId() {
        return Integer.parseInt(name.replaceAll(" ", ""));
    }
}
