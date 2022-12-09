package advent_of_code.utils;

public abstract class RootDay {
	private boolean enableRun1;
	private boolean enableRun2;
	private String result1;
	private String result2;
	
	public RootDay(boolean enableRun1, String result1, boolean enableRun2, String result2) {
		this.enableRun1 = enableRun1;
		this.result1 = result1;
		this.enableRun2 = enableRun2;
		this.result2 = result2;
	}
	
	public RootDay(boolean enableRun1, Integer result1, boolean enableRun2, Integer result2) {
		this.enableRun1 = enableRun1;
		this.result1 = result1 == null ? null : result1 + "";
		this.enableRun2 = enableRun2;
		this.result2 = result2 == null ? null : result2 + "";
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
			Log.show("Starting run1");
			String run1 = run1();
			
			if (result1 != null && !result1.equals(run1)) {
				throw new RuntimeException("Run 1 failed!");
			}
			
			Log.show("#################### Result part I:'" + run1 + "'");
		} else {
			Log.show("Run 1 disabled");
		}
		
		if (enableRun2) {
			Log.show("Starting run2");
			String run2 = run2();

			if (result2 != null && !result2.equals(run2)) {
				throw new RuntimeException("Run 2 failed!");
			}

			Log.show("#################### Result part II:'" + run2 + "'");
		} else {
			Log.show("Run 2 disabled");
		}
	}
}
