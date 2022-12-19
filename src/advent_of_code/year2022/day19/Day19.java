package advent_of_code.year2022.day19;

import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.utils.Log;
import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day19 extends RootDay {
	public Day19() {
		super(true, true, null, false, true, null);
	}

	@Override
	public String run1() {
		String[] input = input();
		ArrayList<Blueprint> blueprints = generateBlueprints(input);		
		return getQualityLevelSum(blueprints, 24) + "";
	}

	@Override
	public String run2() {
		String[] input = input();
		
		return null;
	}

	private int beginCollecting(Blueprint bp, int oreRobots, int clayRobots, int obsidianRobots, int geodeRobots, int ore, int clay, int obsidian, int geodes, int minutes) {
		if (minutes <= 0) {
			return geodes;
		}
				
		int maxGeodes = 0;
		//try buying ore robot
		if (bp.getOresForOreRobot() <= ore) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(
						bp, 
						oreRobots + 1,
						clayRobots,
						obsidianRobots,
						geodeRobots,
						ore + oreRobots - bp.getOresForOreRobot(), 
						clay + clayRobots, 
						obsidian + obsidianRobots, 
						geodes + geodeRobots, 
						minutes - 1));
		}

		//try buying clay robot
		if (bp.getOresForClayRobot() <= ore) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(
						bp, 
						oreRobots,
						clayRobots + 1,
						obsidianRobots,
						geodeRobots,
						ore + oreRobots - bp.getOresForClayRobot(), 
						clay + clayRobots, 
						obsidian + obsidianRobots, 
						geodes + geodeRobots, 
						minutes - 1));
		}
		
		//try buying obsidian robot
		if (bp.getOresForObsidianRobot() <= ore && bp.getClayForObsidianRobot() <= clay) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(
						bp, 
						oreRobots,
						clayRobots,
						obsidianRobots + 1,
						geodeRobots,
						ore + oreRobots - bp.getOresForObsidianRobot(), 
						clay + clayRobots - bp.getClayForObsidianRobot(), 
						obsidian + obsidianRobots, 
						geodes + geodeRobots, 
						minutes - 1));
		}
		
		//try buying geode robot
		if (bp.getOresForGeodeRobot() <= ore && bp.getObsidianForGeodeRobot() <= obsidian) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(
						bp, 
						oreRobots,
						clayRobots,
						obsidianRobots,
						geodeRobots + 1,
						ore + oreRobots - bp.getOresForGeodeRobot(), 
						clay + clayRobots, 
						obsidian + obsidianRobots - bp.getObsidianForGeodeRobot(), 
						geodes + geodeRobots, 
						minutes - 1));
		}  

		//buying nothing
		boolean canBuyOreRobot = bp.getOresForOreRobot() <= ore;
		boolean canBuyClayRobot = bp.getOresForClayRobot() <= ore;
		boolean canBuyObsidianRobot = bp.getOresForObsidianRobot() <= ore && bp.getClayForObsidianRobot() <= clay;
		boolean canBuyGeodeRobot = bp.getOresForGeodeRobot() <= ore && bp.getObsidianForGeodeRobot() <= obsidian;
		if (!(canBuyOreRobot && canBuyClayRobot && canBuyObsidianRobot && canBuyGeodeRobot)) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(
						bp, 
						oreRobots,
						clayRobots,
						obsidianRobots,
						geodeRobots,
						ore + oreRobots, 
						clay + clayRobots, 
						obsidian + obsidianRobots, 
						geodes + geodeRobots, 
						minutes - 1));
		}
				
		return maxGeodes;
	}
	
	private int getQualityLevelSum(ArrayList<Blueprint> blueprints, int minutes) {
		int qualityLevelSum = 0;
		for (Blueprint blueprint : blueprints) {			
			HashMap<Robot, Integer> robots = new HashMap<Robot, Integer>();
			robots.put(Robot.ORE_ROBOT, 1);
			robots.put(Robot.CLAY_ROBO, 0);
			robots.put(Robot.GEODE_ROBOT, 0);
			robots.put(Robot.OBSIDIAN_ROBOT, 0);

			int geodes = beginCollecting(blueprint, 1, 0, 0, 0, 0, 0, 0, 0, minutes);
			Log.show(blueprint.getId() + " - geodes: " + geodes);
			qualityLevelSum += qualityLevel(blueprint.getId(), geodes);
		}
		return qualityLevelSum;
	}
	
	private int qualityLevel(int blueprintId, int largestNumberOfGeodes) {
		return blueprintId * largestNumberOfGeodes;
	}
	
	private ArrayList<Blueprint> generateBlueprints(String[] input) {
		ArrayList<Blueprint> blueprints = new ArrayList<Blueprint>();
		for (String line : input) {
			line = line.replaceAll("\\.$", "");
			String[] sections = line.split(": ");
			String[] robotSections = sections[1].split("\\. ");
			int id = Integer.parseInt(sections[0].replaceAll("Blueprint ", ""));
			
			int oreRobotOreCost = Integer.parseInt(robotSections[0].replaceAll("Each ore robot costs ", "").replaceAll(" ore", ""));
			int clayRobotOreCost = Integer.parseInt(robotSections[1].replaceAll("Each clay robot costs ", "").replaceAll(" ore", ""));
			int obsidianRobotOreCost = Integer.parseInt(robotSections[2].replaceAll("Each obsidian robot costs ", "").replaceAll(" ore and [0-9]+ clay", ""));
			int obsidianRobotClayCost = Integer.parseInt(robotSections[2].replaceAll("Each obsidian robot costs [0-9]+ ore and ", "").replaceAll(" clay", ""));
			int geodeRobotOreCost = Integer.parseInt(robotSections[3].replaceAll("Each geode robot costs ", "").replaceAll(" ore and [0-9]+ obsidian", "")); 
			int geodeRobotObsidianCost = Integer.parseInt(robotSections[3].replaceAll("Each geode robot costs [0-9]+ ore and ", "").replaceAll(" obsidian", "")); 
			
			Blueprint blueprint = new Blueprint(id, oreRobotOreCost, clayRobotOreCost, obsidianRobotOreCost, obsidianRobotClayCost, geodeRobotOreCost, geodeRobotObsidianCost);
			blueprints.add(blueprint);
		}
		
		return blueprints;
	}

	
	private static String[] example() {
		return (Read.getStrings(2022, 19, "example01.txt")); 
	}
	
	private static String[] input() {
		return (Read.getStrings(2022, 19, "input01.txt")); 
	}
}
