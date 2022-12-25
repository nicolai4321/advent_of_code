package advent_of_code.utils;

public abstract class RootDay {
	private final static String SEPARATOR = "---------------------";

	private int year;
	private int day;
    private String input1 = null;
    private String input2 = null;
	private boolean enableRun1 = true;
	private boolean enableRun2 = true;
	private boolean enableTest1 = true;
	private boolean enableTest2 = true;
	private String result1;
	private String result2;
	
	public RootDay(int year, int day, String result1, String result2) {
	    this.year = year;
	    this.day = day;
		this.result1 = result1;
		this.result2 = result2;
	}
	
    /**
     * @return result of part 1
     */
    protected abstract String run1(String input);

    /**
     * @return result of part 2
     */
    protected abstract String run2(String input);

    /**
     * Disable run1
     */
    protected void disableRun1() {
        enableRun1 = false;
    }
    
    /**
     * Disable run2
     */
    protected void disableRun2() {
        enableRun2 = false;
    }
    
    /**
     * Disable test1
     */
    protected void disableTest1() {
        enableTest1 = false;
    }
    
    /**
     * Disable test2
     */
    protected void disableTest2() {
        enableTest2 = false;
    }
    
    /**
     * sets the input for run1()
     * @param year
     * @param day
     * @param fileName
     */
    protected void setInput1(String fileName) {
	    input1 = Read.getString(year, day, fileName); 
	}
	
    /**
     * sets the input for run2()
     * @param year
     * @param day
     * @param fileName
     */
	protected void setInput2(String fileName) {
        input2 = Read.getString(year, day, fileName); 
    }

	/**
	 * Executes the two parts
	 */
	public void run() {	    
		if (enableRun1) {
		    if (input1 == null) {
                throw new RuntimeException("No input defined for run1(). Use the method setInput1(fileName)");
            }
		    
		    Log.show("Starting run1...\n");
		    long timeStartRun1 = System.nanoTime();
			String run1 = run1(input1);
			long timeEndRun1 = System.nanoTime();
			
			if (enableTest1 && result1 != null && !result1.equals(run1)) {
				throw new RuntimeException("Run 1 failed for " + day + "/" + year + "! Got '" + run1 + "' but '" + result1 + "' was expected");
			}
			
			String msg = SEPARATOR + "\n" +
						 "Result part I:'" + run1 + "'\n" +
						 ((timeEndRun1 - timeStartRun1)/1000000) + " ms\n" +
						 ((enableTest1) ? "" : "## TEST DISABLED ##\n") +
						 SEPARATOR + "\n";
			
			Log.show(msg);
		} else {
			Log.show("Run 1 disabled");
		}
		
		if (enableRun2) {
		    if (input2 == null) {
                throw new RuntimeException("No input defined for run2(). Use the method setInput2(fileName)");
            }
		    
			Log.show("Starting run2...\n");
            long timeStartRun2 = System.nanoTime();
			String run2 = run2(input2);
	         long timeEndRun2 = System.nanoTime();


			if (enableTest2 && result2 != null && !result2.equals(run2)) {
				throw new RuntimeException("Run 2 failed for " + day + "/" + year + "! Got '" + run2 + "' but '" + result2 + "' was expected");
			}

			String msg = SEPARATOR + "\n" +
						 "Result part II:'" + run2 + "'\n" + 
						 ((timeEndRun2 - timeStartRun2)/1000000) + " ms\n" +
						 ((enableTest2) ? "" : "## TEST DISABLED ##\n") +
						 SEPARATOR;
			
			Log.show(msg);
			
			if (!enableTest1) {
				Log.warn("WARNING: Test 1 is disabled!");
			}
			
			if (!enableTest2) {
				Log.warn("WARNING: Test 2 is disabled!");
			}
		} else {
			Log.show("Run 2 disabled");
		}
	}
}
