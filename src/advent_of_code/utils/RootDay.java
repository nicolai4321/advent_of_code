package advent_of_code.utils;

public abstract class RootDay {
	private final static String SEPARATOR = "---------------------";

	private boolean enableRun1;
	private boolean enableRun2;
	private boolean enableTest1;
	private boolean enableTest2;
	private String result1;
	private String result2;
	
	public RootDay(boolean enableRun1, boolean enableTest1, String result1, boolean enableRun2, boolean enableTest2, String result2) {
		this.enableRun1 = enableRun1;
		this.enableTest1 = enableTest1;
		this.result1 = result1;
		this.enableRun2 = enableRun2;
		this.enableTest2 = enableTest2;
		this.result2 = result2;
	}

	/**
	 * @return result of part 1
	 */
	protected abstract String run1();

	/**
	 * @return result of part 2
	 */
	protected abstract String run2();
		
	/**
	 * Executes the two parts
	 */
	public void run() {
		if (enableRun1) {
			Log.show("Starting run1...\n");
			String run1 = run1();
			
			if (enableTest1 && result1 != null && !result1.equals(run1)) {
				throw new RuntimeException("Run 1 failed!");
			}
			
			String msg = SEPARATOR + "\n" +
						 "Result part I:'" + run1 + "'\n" +
						 ((enableTest1) ? "" : "## TEST DISABLED ##\n") +
						 SEPARATOR + "\n";
			
			Log.show(msg);
		} else {
			Log.show("Run 1 disabled");
		}
		
		if (enableRun2) {
			Log.show("Starting run2...\n");
			String run2 = run2();

			if (enableTest2 && result2 != null && !result2.equals(run2)) {
				throw new RuntimeException("Run 2 failed!");
			}

			String msg = SEPARATOR + "\n" +
						 "Result part I:'" + run2 + "'\n" +
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
