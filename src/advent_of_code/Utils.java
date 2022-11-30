package advent_of_code;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class Utils {
	private static String BASE_PATH = "src/advent_of_code/";
	/**
	 * prints file to quickly see the content
	 * @param path
	 */
	public static void printFile(String path) {
		System.out.println(readFile(path));
	}
	
	/**
	 * @param path
	 * @return the content of the file as an array of ints
	 */
	public static int[] readFileAsInts(String path) {
		String[] lines = readFileAsStrings(path);
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
	public static String[] readFileAsStrings(String path) {
		String lines = readFile(path);
		return lines.split("\n");
	}
	
	/**
	 * @param path
	 * @return the content of the file as a string
	 */
	public static String readFile(String path) {
		String fullPath = BASE_PATH + path;
		try {
			FileReader fileReader = new FileReader(fullPath);
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
}
