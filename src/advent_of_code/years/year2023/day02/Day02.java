package advent_of_code.years.year2023.day02;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;
import advent_of_code.utils.math.big.BigInt;

public class Day02 extends RootDay {
    public Day02(Year year, int day) {
        super(year, day, "2105", "72422");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * What is the sum of id's for possible games if the Elf only has 12 red, 13 green and 14 blue cubes?
     */
    @Override
    public String run1(String input) {
        String[] lines = input.split("\n");
        ArrayList<Game> games = getGames(lines);
        
        int sum = 0;
        for (Game game : games) {
            int red = game.getRed();
            int blue = game.getBlue();
            int green = game.getGreen();
            
            if (red<=12 && green<=13 && blue<=14) {
                sum += game.getId();
            }
        }
        
        return sum + "";
    }

    /**
     * What is the sum of the rounds if you multiply the amount of minimum possible cube colors for each round
     */
    @Override
    public String run2(String input) {
        String[] lines = input.split("\n");
        ArrayList<Game> games = getGames(lines);
        
        BigInt sum = new BigInt("0");
        for (Game game : games) {
            int red = game.getRed();
            int blue = game.getBlue();
            int green = game.getGreen();
            
            int mul = red * blue * green;
            sum = sum.add(mul);
        }
        
        return sum + "";
    }
    
    private ArrayList<Game> getGames(String[] lines) {
        ArrayList<Game> games = new ArrayList<Game>();
        for (String line : lines) {
            line = line.replaceAll(";", ",");
            int id = Integer.parseInt(line.split("Game ")[1].split(":")[0]);
            String[] rounds = line.split(": ")[1].split(", ");
            
            Game game = new Game(id, rounds);
            games.add(game);
        }
        return games;
    }
}
