package advent_of_code.year2022.day17;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.HashMap;

import advent_of_code.utils.BigInt;

public class Board {
	private static final int MAP_RANGE = 5;

	private HashMap<Integer, ArrayList<Rock>> rocksMap = new HashMap<Integer, ArrayList<Rock>>();
	private BigInteger highestPointOutput = null;
	private int highestPoint = 0;
	private int rockRound = 0;
	private int jetRound = 0;
	private char[] jetPattern;
	
	private ArrayList<Integer> repeatingJetRounds = new ArrayList<Integer>();
	private Integer repeatingHeightStart = null;
	private Integer repeatingJetround = null;
	private Integer repeatingRoundStart = null;
	private Integer repeatingHeightIncrease = null;
	private Integer repeatingRoundIncrease = null;

	private int restRound = 0;
	private Integer maxRestRounds = null;
	
	public Board(char[] jetPattern) {
		this.jetPattern = jetPattern;
	}
	
	/**
	 * Logic for running the board
	 * @param nrRounds
	 * @param mRounds (additional parameter to multiply the amount of rounds with nrRounds)
	 */
	public void start(int nrRounds, int mRounds) {
		for (int round=0; round<nrRounds; round++) {
			analyzeRepeatingPatterns(round, mRounds, nrRounds);
			if(canSkip(nrRounds, mRounds)) {
				return;
			}
			simulateFall(addRock());			
		}
		highestPointOutput = BigInt.get(highestPoint);
	}
	
	/**
	 * @return highest point on the board
	 */
	public String getHighestPoint() {
		return highestPointOutput.toString();
	}
	
	/**
	 * Using the repeating patterns to examine if there is sufficient information to calculate the rest of the simulation
	 * @param nrRounds
	 * @param mRounds
	 * @return true if the rest of rounds can be skipped
	 */
	private boolean canSkip(int nrRounds, int mRounds) {
		if (maxRestRounds != null) {
			if (restRound < maxRestRounds) {					
				restRound++;
			} else {
				BigInteger biRestHeight = BigInt.sub(highestPoint, BigInt.add(repeatingHeightStart, repeatingHeightIncrease));
				BigInteger biRepeatRounds = BigInt.div(BigInt.sub(BigInt.sub(BigInt.mult(nrRounds, mRounds), maxRestRounds), repeatingRoundStart), repeatingRoundIncrease);
				highestPointOutput = BigInt.add(repeatingHeightStart, BigInt.add(BigInt.mult(repeatingHeightIncrease, biRepeatRounds), biRestHeight));
				return true;
			}
		}
		return false;			
	}

	/**
	 * Analyzing repeating patterns in order to optimize the progress
	 * @param round
	 * @param mRounds
	 * @param nrRounds
	 */
	private void analyzeRepeatingPatterns(int round, int mRounds, int nrRounds) {
		if (round % Rock.NR_TYPES == 0) {
			if (repeatingRoundIncrease == null && repeatingJetround != null && jetRound == repeatingJetround) {
				//this is the second point for the repeating jet round
				repeatingHeightIncrease = highestPoint - repeatingHeightStart;
				repeatingRoundIncrease = round - repeatingRoundStart;
				maxRestRounds = Integer.parseInt(BigInt.mod(BigInt.sub(BigInt.mult(nrRounds, mRounds), repeatingRoundStart), repeatingRoundIncrease + "").toString());
			} else if (repeatingJetround == null && 0 < repeatingJetRounds.size() && jetRound < repeatingJetRounds.get(repeatingJetRounds.size()-1)) {
				//this is the first point where a repeating jet round has been identified
				repeatingHeightStart = highestPoint;
				repeatingJetround = jetRound;
				repeatingRoundStart = round;
			} else if (repeatingJetround == null) {
				//adding jet rounds to a list of repeating jet rounds
				repeatingJetRounds.add(jetRound);
			}
		}
	}
	
	/**
	 * Simulate fall of one rock
	 * @param rock
	 */
	private void simulateFall(Rock rock) {
		while (true) {
			pushRock(rock);

			rock.decreasePosY();
			if (rockIsColliding(rock)) {
				rock.increasePosY();
				addRock(rock);
				
				highestPoint = Math.max(highestPoint, rock.highestPoint());
				return;
			}
		}
	}
	
	/**
	 * @param rock
	 * @return true if the rock is colliding the bottom or other rocks (EXCLUDRING collision with the wall)
	 */
	private boolean rockIsColliding(Rock rock) {
		for (Rock otherRock : getRocks(rock)) {
			if (rock.collide(otherRock)) {
				return true;
			}
		}
		
		if (rock.collideBottom()) {
			return true;
		}
		
		return false;
	}
	
	/**
	 * This method stores the rock in a group of other rocks with similar y-coordinates.
	 * @param rock
	 */
	private void addRock(Rock rock) {
		int index = rock.getPosY() / MAP_RANGE;
		ArrayList<Rock> rocks = rocksMap.get(index);
		if (rocks == null) {
			rocks = new ArrayList<Rock>();
			rocksMap.put(index, rocks);
		}
		rocks.add(rock);
	}
	
	/**
	 * All rocks are stored together in groups of similar y-coordinates. This method returns a subset of rocks with y-coordinates in range of rock.
	 * @param rock
	 * @return relevant rocks on the board
	 */
	private ArrayList<Rock> getRocks(Rock rock) {
		int index = rock.getPosY() / MAP_RANGE;
		int index0 = index-1;
		int index1 = index+1;
		ArrayList<Rock> rocks = rocksMap.get(index);
		ArrayList<Rock> rocks0 = rocksMap.get(index0);
		ArrayList<Rock> rocks1 = rocksMap.get(index1);
		
		ArrayList<Rock> rocksCombined = new ArrayList<Rock>();
		if (rocks != null) {
			for (Rock r : rocks) {
				rocksCombined.add(r);
			}
		}
		
		if (rocks0 != null) {
			for (Rock r : rocks0) {
				rocksCombined.add(r);
			}
		}
		
		if (rocks1 != null) {
			for (Rock r : rocks1) {
				rocksCombined.add(r);
			}
		}
		
		return rocksCombined;
	}
	
	/**
	 * Push rock and increases the jet round
	 * @param rock
	 */
	private void pushRock(Rock rock) {
		char c = jetPattern[jetRound];
		if (c == '<') {
			rock.decreasePosX(0);
			
			if (rockIsColliding(rock)) {
				rock.increasePosX(6);
			}
		} else if (c == '>') {
			rock.increasePosX(6);

			if (rockIsColliding(rock)) {
				rock.decreasePosX(0);
			}
		} else {
			throw new RuntimeException("Unknown char '" + c + "'");
		}
		jetRound = (jetRound + 1) % jetPattern.length;
	}

	/**
	 * Add the next rock and increases the round
	 * @return rock
	 */
	private Rock addRock() {
		Rock rock = Rock.generateRock(2, highestPoint + 3, rockRound);
		rockRound++;
		return rock;
	}
}
