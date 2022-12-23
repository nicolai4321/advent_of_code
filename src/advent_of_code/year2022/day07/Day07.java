package advent_of_code.year2022.day07;

import advent_of_code.utils.RootDay;

public class Day07 extends RootDay {
    public Day07() {
        super(2022, 7, "2031851", "2568781");
        setInput1("input01.txt");
        setInput2("input01.txt");
    }

    @Override
    public String run1(String input) {
        String[] commands = input.split("\n");
        Dir directory = generateDirectory(commands);    
        return directory.sum1() + "";
    }

    @Override
    public String run2(String input) {
        String[] commands = input.split("\n");
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
}
