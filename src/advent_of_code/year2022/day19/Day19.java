package advent_of_code.year2022.day19;

import java.util.ArrayList;

import advent_of_code.utils.Read;
import advent_of_code.utils.RootDay;

public class Day19 extends RootDay {
	public Day19() {
		super(true, true, "1199", true, true, "3510");
	}

	@Override
	public String run1() {
		String[] input = input();
		ArrayList<Blueprint> blueprints = generateBlueprints(input);
		ArrayList<int[]> geodesForBlueprints = getGeodesForBlueprints(blueprints, 24);
		return getQualityLevelSum(geodesForBlueprints) + "";
	}

	@Override
	public String run2() {
		String[] input = input();
		ArrayList<Blueprint> blueprints = generateBlueprints(input);		
		ArrayList<Blueprint> threeBlueprints = new ArrayList<Blueprint>();
		for (int i=0; i<3; i++) {
			threeBlueprints.add(blueprints.get(i));
		}
		int sum = 1;
		ArrayList<int[]> geodesForBlueprints = getGeodesForBlueprints(threeBlueprints, 32);
		for (int[] pair : geodesForBlueprints) {
			sum = sum * pair[1];
		}
		
		return sum + "";
	}

	/**
	 * This method returns the maximum collection of geode
	 * 
	 * For performance optimization:
	 * 	-Always buy geode robot when possible
	 * 	-Never produce anything if there is only 1 minute left, since the robot will not be ready to produce anything
	 * 	-Never produce so many robots of one type that the factory theoretical cannot spend all the resources
	 * 	-If nothing was produced but robot A could be produced. Don't produce robot A until another robot has been produced. If robot A was needed, it was produced as soon as possible
	 * 	-Never skip producing anything if everything can be produced
	 * 
	 * @param bp
	 * @param oreRobots
	 * @param clayRobots
	 * @param obsidianRobots
	 * @param geodeRobots
	 * @param ore
	 * @param clay
	 * @param obsidian
	 * @param geodes
	 * @param minutes
	 * @param skippedOreRobot
	 * @param skippedClayRobot
	 * @param skippedObsidianRobot
	 * @return maximum collection of geode
	 */
	private int beginCollecting(Blueprint bp,
								int oreRobots, 
								int clayRobots, 
								int obsidianRobots, 
								int geodeRobots, 
								int ore, 
								int clay, 
								int obsidian, 
								int geodes, 
								int minutes,
								boolean skippedOreRobot,
								boolean skippedClayRobot,
								boolean skippedObsidianRobot) {
		if (minutes <= 0) {
			return geodes;
		}
		
		int maxGeodes = 0;
		
		//try buying ore robot
		boolean canBuyOreRobot = bp.getOresForOreRobot() <= ore;
		boolean canBuyGeodeRobot = bp.getOresForGeodeRobot() <= ore && bp.getObsidianForGeodeRobot() <= obsidian;
		int maxOreProd = Math.max(Math.max(bp.getOresForOreRobot(), bp.getOresForClayRobot()), Math.max(bp.getOresForObsidianRobot(), bp.getOresForGeodeRobot()));
		if (canBuyOreRobot && 1 < minutes && !canBuyGeodeRobot && oreRobots<maxOreProd && !skippedOreRobot) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(bp,
									oreRobots + 1,
									clayRobots,
									obsidianRobots,
									geodeRobots,
									ore + oreRobots - bp.getOresForOreRobot(), 
									clay + clayRobots, 
									obsidian + obsidianRobots, 
									geodes + geodeRobots, 
									minutes - 1,
									false,
									false,
									false));
		}

		//try buying clay robot
		boolean canBuyClayRobot = bp.getOresForClayRobot() <= ore;
		if (canBuyClayRobot && 1 < minutes && !canBuyGeodeRobot && clayRobots<bp.getClayForObsidianRobot() && !skippedClayRobot) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(bp,
									oreRobots,
									clayRobots + 1,
									obsidianRobots,
									geodeRobots,
									ore + oreRobots - bp.getOresForClayRobot(), 
									clay + clayRobots, 
									obsidian + obsidianRobots, 
									geodes + geodeRobots, 
									minutes - 1,
									false,
									false,
									false));
		}
		
		//try buying obsidian robot
		boolean canBuyObsidianRobot = bp.getOresForObsidianRobot() <= ore && bp.getClayForObsidianRobot() <= clay;
		if (canBuyObsidianRobot && 1 < minutes && !canBuyGeodeRobot && obsidianRobots<bp.getObsidianForGeodeRobot() && !skippedObsidianRobot) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(bp,
									oreRobots,
									clayRobots,
									obsidianRobots + 1,
									geodeRobots,
									ore + oreRobots - bp.getOresForObsidianRobot(), 
									clay + clayRobots - bp.getClayForObsidianRobot(), 
									obsidian + obsidianRobots, 
									geodes + geodeRobots, 
									minutes - 1,
									false,
									false,
									false));
		}
		
		//try buying geode robot
		if (canBuyGeodeRobot && 1 < minutes) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(bp,
									oreRobots,
									clayRobots,
									obsidianRobots,
									geodeRobots + 1,
									ore + oreRobots - bp.getOresForGeodeRobot(), 
									clay + clayRobots, 
									obsidian + obsidianRobots - bp.getObsidianForGeodeRobot(), 
									geodes + geodeRobots, 
									minutes - 1,
									false,
									false,
									false));
		}  

		//producing nothing
		if (!(canBuyOreRobot && canBuyClayRobot && canBuyObsidianRobot && canBuyGeodeRobot)) {
			maxGeodes = Math.max(maxGeodes, 
					beginCollecting(bp,
									oreRobots,
									clayRobots,
									obsidianRobots,
									geodeRobots,
									ore + oreRobots, 
									clay + clayRobots, 
									obsidian + obsidianRobots, 
									geodes + geodeRobots, 
									minutes - 1,
									canBuyOreRobot,
									canBuyClayRobot,
									canBuyObsidianRobot));
		}
				
		return maxGeodes;
	}
	
	/**
	 * 
	 * @param blueprints
	 * @param minutes
	 * @return geodesForBlueprints
	 */
	private ArrayList<int[]> getGeodesForBlueprints(ArrayList<Blueprint> blueprints, int minutes) {
		ArrayList<int[]> geodesForBlueprints = new ArrayList<int[]>();
		for (Blueprint blueprint : blueprints) {
			int geodes = beginCollecting(blueprint,
										 1, 
										 0, 
										 0, 
										 0, 
										 0, 
										 0, 
										 0, 
										 0, 
										 minutes,
										 false,
										 false,
										 false);
			geodesForBlueprints.add(new int[] { blueprint.getId(), geodes });
		}
		return geodesForBlueprints;
	}
	
	/**
	 * @param geodesForBlueprints
	 * @return qualityLevelSum
	 */
	private int getQualityLevelSum(ArrayList<int[]> geodesForBlueprints) {
		int sum = 0;
		for (int[] pair : geodesForBlueprints) {
			sum += pair[0] * pair[1];
		}
		return sum;
	}
	
	/**
	 * @param input
	 * @return list of blueprints
	 */
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
