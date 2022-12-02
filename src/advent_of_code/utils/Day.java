package advent_of_code.utils;

public abstract class Day {
	/**
	 * @return result of part 1
	 */
	public abstract String run1();

	/**
	 * @return result of part 2
	 */
	public abstract String run2();
	
	public void run() {
		if (test()) {
			String run1 = run1();
			Log.show("#################");
			Log.show("Result part I:\n'" + run1 + "'");
			Log.show("#################");

			String run2 = run2();
			Log.show("#################");
			Log.show("Result part II:\n'" + run2 + "'");
			Log.show("#################");
		} else {
			Log.show("#################");
			Log.show("Test failed!");
			Log.show("#################");
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
