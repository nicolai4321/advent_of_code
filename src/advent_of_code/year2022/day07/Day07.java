package advent_of_code.year2022.day07;

import advent_of_code.utils.Day;
import advent_of_code.utils.Read;

public class Day07 extends Day {
	@Override
	public String run1() {
		String[] commands = input();
		Dir directory = generateDirectory(commands);	
		return directory.sum1() + "";
	}

	@Override
	public String run2() {
		String[] commands = input();
		Dir directory = generateDirectory(commands);
		
		int totalSize = directory.getSize();
		int requiredSpace = 30000000;
		int unusedSpace = 70000000 - totalSize;
		int neededSpace = requiredSpace - unusedSpace;
		
		return directory.smallestSizeOfDirectories(neededSpace) + "";
	}
	
	/**
	 * @param commands
	 * @return a directory containing the content extracted from the commands
	 */
	private static Dir generateDirectory(String[] commands) {
		Dir rootDirectory = null;
		Dir currentDirectory = null;
		
		for (String command : commands) {
			//change directory
			if (command.contains("cd")) {
				String directoryName = command.replaceAll("\\$*\\s*cd\\s*", "");

				if (directoryName.equals("..")) {
					currentDirectory = currentDirectory.getParent();
				} else if (directoryName.equals("/")) {
					rootDirectory = new Dir("/", null);
					currentDirectory = rootDirectory;
				} else {					
					Dir directory = currentDirectory.getDir(directoryName);
					if (directory == null) {
						directory = new Dir(directoryName, currentDirectory);
						currentDirectory.add(directory);						
					}
					currentDirectory = directory;						
				}
			}
			
			//directory
			if (command.contains("dir")) {
				String directoryName = command.replaceAll("dir\\s*", "");
				currentDirectory.add(new Dir(directoryName, currentDirectory));
			}
			
			//file
			if (command.matches("[0-9]+\\s+[A-Za-z]+(\\.[A-Za-z]+)?")) {
				String fileName = command.replaceAll("^[0-9]+\\s+", "");
				int fileSize = Integer.parseInt(command.replaceAll("\\s+[A-Za-z]+(\\.[A-Za-z]+)?$", ""));
				File file = new File(fileName, fileSize);
				currentDirectory.add(file);
			}
			
			//list
			if (command.contains("ls")) {
				//ignore
			}
		}
		
		return rootDirectory;
	}
	
	@Override
	protected boolean test() {
		return run1().equals("2031851") && run2().equals("2568781");
	}
	
	private static String[] example() {
		return Read.getStrings(2022, 7, "example01.txt"); 
	}
	
	private static String[] input() {
		return Read.getStrings(2022, 7, "input01.txt"); 
	}
}
