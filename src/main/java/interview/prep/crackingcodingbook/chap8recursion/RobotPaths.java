package interview.prep.crackingcodingbook.chap8recursion;

/**
 * Problem 8.2
 * Probably better solved combinatorically
 * 
 * @author User
 *
 */
public class RobotPaths {

	RobotPaths(int boardDim){
		if(n <= 0)
			throw new IllegalArgumentException("Board must have dimensions > 0");
		n = boardDim;
	}
	
	private int n = 2;// Dimensions of our square

	/**
	 * 
	 * @param x
	 *            current x location on square (0 indexed)
	 * @param y
	 *            current y location on square (0 indexed)
	 * @return int - how many subpaths from here make it to our destination bottom
	 *         right corner
	 */
	private int getNumOfPaths(int x, int y) {
		if (x > n - 1 || y > n - 1) // Exceed the square boundaries
			return 0;
		if (x == n - 1 && y == n - 1) // Made it to the destination, bottom right square
			return 1;
		return getNumOfPaths(x + 1, y) + getNumOfPaths(x, y + 1); // Can only move right or down (origin is defined as
																	// top left, so y increase means moving down)
	}

	public int countPaths() {
		return getNumOfPaths(0, 0);
	}
	
	public static void main(String[] args) {
		RobotPaths r = new RobotPaths(1);
		System.out.println(r.countPaths()); //Expect 1
	
		//More tests in JUnit
	}

}
