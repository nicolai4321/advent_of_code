package advent_of_code.year2022.day19;

public class Blueprint {
	private int id;
	private int oreRobotOreCost;
	private int clayRobotOreCost;
	private int obsidianRobotOreCost;
	private int obsidianRobotClayCost;
	private int geodeRobotOreCost;
	private int geodeRobotObsidianCost;
	
	public Blueprint(int id, 
					 int oreRobotOreCost, 
					 int clayRobotOreCost, 
					 int obsidianRobotOreCost,
					 int obsidianRobotClayCost, 
					 int geodeRobotOreCost, 
					 int geodeRobotObsidianCost) {
		this.id = id;
		this.oreRobotOreCost = oreRobotOreCost;
		this.clayRobotOreCost = clayRobotOreCost;
		this.obsidianRobotOreCost = obsidianRobotOreCost;
		this.obsidianRobotClayCost = obsidianRobotClayCost;
		this.geodeRobotOreCost = geodeRobotOreCost;
		this.geodeRobotObsidianCost = geodeRobotObsidianCost;
	}

	public String toString() {
		return "{" + id + 
				"(ore robot: " + oreRobotOreCost + " ores), " +
				"(clay robot: " + clayRobotOreCost + " ores), " +
				"(obsidian robot: " + obsidianRobotOreCost + " ores + " + obsidianRobotClayCost + " clay), " +
				"(geode robot: " + geodeRobotOreCost + " ores and " + geodeRobotObsidianCost + " obsidian)}";
	}

	public int getId() {
		return id;
	}
	
	public int getOresForOreRobot() {
		return oreRobotOreCost;
	}
	
	public int getOresForClayRobot() {
		return clayRobotOreCost;
	}
	
	public int getOresForObsidianRobot() {
		return obsidianRobotOreCost;
	}
	
	public int getClayForObsidianRobot() {
		return obsidianRobotClayCost;
	}

	public int getOresForGeodeRobot() {
		return geodeRobotOreCost;
	}

	public int getObsidianForGeodeRobot() {
		return geodeRobotObsidianCost;
	}
}
