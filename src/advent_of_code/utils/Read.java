package advent_of_code.utils;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Read {
	private static String BASE_PATH = "src/advent_of_code/";
	/**
	 * prints file to quickly see the content
	 * @param path
	 */
	public static void printFile(int year, int day, String fileName) {
		Log.show(getString(year, day, fileName));
	}
	
	/**
	 * @param path
	 * @return the content of the file as an array of ints
	 */
	public static int[] getInts(int year, int day, String fileName) {
		String[] lines = getStrings(year, day, fileName);
		int[] linesAsInt = new int[lines.length];
		for (int i=0; i<lines.length; i++) {
			String line = lines[i];
			linesAsInt[i] = Integer.parseInt(line);
		}
		return linesAsInt;
	}

	/**
	 * @param path
	 * @return the content of the file as an array of strings
	 */
	public static String[] getStrings(int year, int day, String fileName) {
		String lines = getString(year, day, fileName);
		return lines.split("\n");
	}
	
	/**
	 * @param path
	 * @return the content of the file as a string
	 */
	public static String getString(int year, int day, String fileName) {
		String path = getPath(year, day, fileName);
		try {
			FileReader fileReader = new FileReader(path);
			BufferedReader bufferedReader = new BufferedReader(fileReader);
			
			String output = "";
			String line = null;
			while ((line = bufferedReader.readLine()) != null) {
				output += line + "\n";
			}
			
			bufferedReader.close();
			fileReader.close();
			return output;
		} catch (FileNotFoundException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		} catch (IOException e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	
	/**
	 * @param year
	 * @param day
	 * @param filename
	 * @return the path
	 */
	private static String getPath(int year, int day, String fileName) {
		String dayString = ((day + "").length() == 1) ? "0" + day : "" + day;
		return BASE_PATH + "year" + year + "/day" + dayString + "/" + fileName;
	}
}
