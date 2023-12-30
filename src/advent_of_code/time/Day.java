package advent_of_code.time;

import advent_of_code.utils.Log;

public abstract class Day {
	protected boolean testEnabled = true;
	
	/**
	 * @return result of part 1
	 */
	public abstract String run1();

	/**
	 * @return result of part 2
	 */
	public abstract String run2();
	
	public void run() {
		boolean test;
		if (testEnabled) {
			Log.disable();
			test = test();
			Log.enable();
			Log.show("Test done...\n");
		} else {
			test = true;
		}
		
		if (test) {
			String run1 = run1();

			Log.show("#################### Result part I:'" + run1 + "'");

			String run2 = run2();
			Log.show("#################### Result part II:'" + run2 + "'");
		} else {
			Log.show("#################### Test failed!");
		}
	}

	/**
	 * You can override this method to create some tests
	 * @return true if the test is successful
	 */
	protected boolean test() {
		return true;
	}
}
