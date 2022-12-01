package advent_of_code.year2021.day03;

import java.util.ArrayList;

import advent_of_code.utils.Day;
import advent_of_code.utils.Read;

public class Day03 implements Day {

	@Override
	public String run1() {		
		ArrayList<int[]> bitPairs = new ArrayList<int[]>();

		String[] bitLines = getInput();
		int bitLength = bitLines[0].length();
		
		for (int i=0; i<bitLength; i++) {
			int[] bitPair = new int[2];

			for (String bit : bitLines) {
				
				int bitValue = Integer.parseInt(bit.toCharArray()[i] + "");
				if (bitValue == 0) {
					bitPair[0]++;
				} else {
					bitPair[1]++;		
				}

			}
			bitPairs.add(bitPair);
		}
		
		int[] gammas = new int[bitPairs.size()];
		int[] epsilons = new int[bitPairs.size()];
		int gammaRate = 0;
		int epsilonRate = 0;
		int multiplier = 1;
		
		for (int i=bitLength-1; i>=0; i--) {
			int[] bitPair = bitPairs.get(i);
			
			if (bitPair[0] < bitPair[1]) {
				gammas[i] = 0;
				epsilons[i] = 1;
			} else {
				gammas[i] = 1;
				epsilons[i] = 0;				
			}
			
			gammaRate += gammas[i] * multiplier;
			epsilonRate += epsilons[i] * multiplier;
			multiplier = multiplier * 2;
		}

		int consumptionRate = gammaRate * epsilonRate;
		return consumptionRate + "";
	}

	@Override
	public String run2() {
		String[] bitLines = getInput();
		int bitLength = bitLines[0].length();
		
		//create candidates
		ArrayList<int[]> oxGeneratorCandidates = new ArrayList<int[]>();
		ArrayList<int[]> scrubberCandidates = new ArrayList<int[]>();
		for (String bit : bitLines) {
			int[] intBits0 = new int[bitLength];
			int[] intBits1 = new int[bitLength];

			char[] chars = bit.toCharArray();
			for (int i=0; i<bitLength; i++) {
				intBits0[i] = Integer.parseInt(chars[i] + "");
				intBits1[i] = Integer.parseInt(chars[i] + "");
			}
			oxGeneratorCandidates.add(intBits0);
			scrubberCandidates.add(intBits1);
		}
		
		//remove candidates
		for (int i=0; i<bitLength; i++) {			
			Integer mostCommonOx = getMostCommon(oxGeneratorCandidates, i);
			Integer mostCommonSc = getMostCommon(scrubberCandidates, i);

			//ox
			for (int j=0; j<oxGeneratorCandidates.size(); j++) {
				if (oxGeneratorCandidates.size() == 1) {
					break;
				}

				int[] bit = oxGeneratorCandidates.get(j);
				if (mostCommonOx != null && bit[i] != mostCommonOx) {
					oxGeneratorCandidates.remove(j);
					j--;
				}
			}
			
			//src
			for (int j=0; j<scrubberCandidates.size(); j++) {
				if (scrubberCandidates.size() == 1) {
					break;
				}

				int[] bit = scrubberCandidates.get(j);
				if (mostCommonSc != null && bit[i] == mostCommonSc) {
					scrubberCandidates.remove(j);
					j--;
				}
			}
		}
		
		int oxygenGeneratorRating = bitToInt(oxGeneratorCandidates.get(0));
		int co2CrubberRating = bitToInt(scrubberCandidates.get(0));
		int lifeSupportRating = oxygenGeneratorRating * co2CrubberRating;
		
		System.out.println(oxygenGeneratorRating);
		System.out.println(co2CrubberRating);
		
		return lifeSupportRating + "";
	}
	
	private Integer getMostCommon(ArrayList<int[]> lst, int index) {
		int zeros = 0;
		int ones = 0;
		
		for (int[] bit : lst) {
			int i = bit[index];
			if (i == 0) {
				zeros++;
			} else {
				ones++;
			}
		}
		
		if (zeros == ones) {
			return 1;
		} else if (zeros < ones) {
			return 1;
		} else {
			return 0;
		}
	}
	
	private int bitToInt(int[] b) {
		int multiplier = 1;
		int output = 0;
		for (int i=b.length-1; i>=0; i--) {
			output += b[i] * multiplier;
			multiplier = multiplier * 2;
		}
		return output;
	}
	
	private static String[] getExample() {
		return Read.getStrings(2021, 3, "example01.txt"); 
	}
	
	private static String[] getInput() {
		return Read.getStrings(2021, 3, "input01.txt"); 
	}
}
