package advent_of_code;

import java.util.ArrayList;

import advent_of_code.utils.Log;
import advent_of_code.utils.RootDay;
import advent_of_code.year2022.day01.Day01;
import advent_of_code.year2022.day02.Day02;
import advent_of_code.year2022.day03.Day03;
import advent_of_code.year2022.day04.Day04;
import advent_of_code.year2022.day05.Day05;
import advent_of_code.year2022.day06.Day06;
import advent_of_code.year2022.day07.Day07;
import advent_of_code.year2022.day08.Day08;
import advent_of_code.year2022.day09.Day09;
import advent_of_code.year2022.day10.Day10;
import advent_of_code.year2022.day11.Day11;
import advent_of_code.year2022.day12.Day12;
import advent_of_code.year2022.day13.Day13;
import advent_of_code.year2022.day14.Day14;
import advent_of_code.year2022.day15.Day15;
import advent_of_code.year2022.day16.Day16;
import advent_of_code.year2022.day17.Day17;
import advent_of_code.year2022.day18.Day18;
import advent_of_code.year2022.day19.Day19;
import advent_of_code.year2022.day20.Day20;
import advent_of_code.year2022.day21.Day21;
import advent_of_code.year2022.day22.Day22;
import advent_of_code.year2022.day23.Day23;
import advent_of_code.year2022.day24.Day24;

public class Main {
    public static void main(String[] args) {
        ArrayList<RootDay> days2022 = getDaysFor2022();
        //runAll(days2022);
        
        new Day24().run();
    }
    
    private static void runAll(ArrayList<RootDay> days) {
        Log.show("Run every day");
        Log.disable();
        
        for (RootDay day : days) {
            day.run();
        }
        
        Log.enable();
        Log.show("Done");
    }
    
    private static ArrayList<RootDay> getDaysFor2022() {
        ArrayList<RootDay> days2022 = new ArrayList<RootDay>();
        days2022.add(new Day01());
        days2022.add(new Day02());
        days2022.add(new Day03());
        days2022.add(new Day04());
        days2022.add(new Day05());
        days2022.add(new Day06());
        days2022.add(new Day07());
        days2022.add(new Day08());
        days2022.add(new Day09());
        days2022.add(new Day10());
        days2022.add(new Day11());
        //days2022.add(new Day12());  //TODO optimize
        days2022.add(new Day13());
        days2022.add(new Day14());
        //days2022.add(new Day15());  //TODO optimize
        //days2022.add(new Day16());  //TODO optimize
        days2022.add(new Day17());
        //days2022.add(new Day18());  //TODO optimize
        days2022.add(new Day19());
        days2022.add(new Day20());
        days2022.add(new Day21());
        days2022.add(new Day22());
        days2022.add(new Day23());
        return days2022;
    }
}
