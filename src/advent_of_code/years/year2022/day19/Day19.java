package advent_of_code.years.year2022.day19;

import java.util.ArrayList;

import advent_of_code.time.RootDay;
import advent_of_code.time.Year;

public class Day19 extends RootDay {
    public Day19(Year year, int day) {
        super(year, day, "1199", "3510");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    /**
     * Sum the quality level. The quality level is the blueprint id multiplied with
     * the maximum amount of geodes that is possible to obtain in 24 rounds
     */
    @Override
    public String run1(String input) {
        ArrayList<Blueprint> blueprints = generateBlueprints(input);
        ArrayList<int[]> geodesForBlueprints = getGeodesForBlueprints(blueprints, 24);
        return getQualityLevelSum(geodesForBlueprints) + "";
    }

    /**
     * Multiply the maximum amount of geodes that can be obtained in 32 rounds for
     * the three first blueprints
     */
    @Override
    public String run2(String input) {
        ArrayList<Blueprint> blueprints = generateBlueprints(input);
        ArrayList<Blueprint> threeBlueprints = new ArrayList<Blueprint>();
        for (int i = 0; i < 3; i++) {
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
     * For performance optimization: -Always produce geode robot when possible
     * -Never produce anything if there is only 1 minute left, since the robot will
     * not be ready to produce anything -Never produce so many robots of one type
     * that the factory theoretical cannot spend all the resources -If nothing was
     * produced and robotA could be produced, robotA should never be produced until
     * another robot has been produced. If robotA was needed, robotA was produced as
     * soon as possible -Always produce a robot if there are resources to produce
     * all robots
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

        // try producing ore robot
        boolean canProduceOreRobot = bp.getOresForOreRobot() <= ore;
        boolean canProduceGeodeRobot = bp.getOresForGeodeRobot() <= ore && bp.getObsidianForGeodeRobot() <= obsidian;
        int maxOreProd = Math.max(Math.max(bp.getOresForOreRobot(), bp.getOresForClayRobot()), Math.max(bp.getOresForObsidianRobot(), bp.getOresForGeodeRobot()));
        if (canProduceOreRobot && 1 < minutes && !canProduceGeodeRobot && oreRobots < maxOreProd && !skippedOreRobot) {
            maxGeodes = Math.max(maxGeodes, beginCollecting(bp, 
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

        // try producing clay robot
        boolean canProduceClayRobot = bp.getOresForClayRobot() <= ore;
        if (canProduceClayRobot && 1 < minutes && !canProduceGeodeRobot && clayRobots < bp.getClayForObsidianRobot() && !skippedClayRobot) {
            maxGeodes = Math.max(maxGeodes, beginCollecting(bp, 
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

        // try producing obsidian robot
        boolean canProduceObsidianRobot = bp.getOresForObsidianRobot() <= ore && bp.getClayForObsidianRobot() <= clay;
        if (canProduceObsidianRobot && 1 < minutes && !canProduceGeodeRobot && obsidianRobots < bp.getObsidianForGeodeRobot() && !skippedObsidianRobot) {
            maxGeodes = Math.max(maxGeodes, beginCollecting(bp, 
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

        // try producing geode robot
        if (canProduceGeodeRobot && 1 < minutes) {
            maxGeodes = Math.max(maxGeodes, beginCollecting(bp, 
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

        // producing nothing
        if (!(canProduceOreRobot && canProduceClayRobot && canProduceObsidianRobot && canProduceGeodeRobot)) {
            maxGeodes = Math.max(maxGeodes, beginCollecting(bp, 
                                                            oreRobots, 
                                                            clayRobots, 
                                                            obsidianRobots, 
                                                            geodeRobots, 
                                                            ore + oreRobots, 
                                                            clay + clayRobots, 
                                                            obsidian + obsidianRobots, 
                                                            geodes + geodeRobots, 
                                                            minutes - 1,
                                                            canProduceOreRobot, 
                                                            canProduceClayRobot, 
                                                            canProduceObsidianRobot));
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
            int geodes = beginCollecting(blueprint, 1, 0, 0, 0, 0, 0, 0, 0, minutes, false, false, false);
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
    private ArrayList<Blueprint> generateBlueprints(String input) {
        ArrayList<Blueprint> blueprints = new ArrayList<Blueprint>();
        for (String line : input.split("\n")) {
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

            Blueprint blueprint = new Blueprint(id, oreRobotOreCost, clayRobotOreCost, obsidianRobotOreCost,obsidianRobotClayCost, geodeRobotOreCost, geodeRobotObsidianCost);
            blueprints.add(blueprint);
        }

        return blueprints;
    }
}
