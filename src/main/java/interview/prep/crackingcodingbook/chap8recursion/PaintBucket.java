package interview.prep.crackingcodingbook.chap8recursion;

import java.util.Arrays;

/**
 * Question 8.6 Based on clicking on a point, fill in the desired color until
 * you hit a containing barrier of the color or the edge of the canvas
 * 
 * @author User
 *
 */
public class PaintBucket {

	/**
	 * 
	 * @param desiredColor
	 * @param x
	 * @param y
	 * @param pixels
	 *            2-d array [x][y] (col-row)
	 */
	static void setColor(int desiredColor, int x, int y, int[][] pixels) {
		// If outside bounds or color already set no work to be done
		if (badBounds(x, y, pixels) || pixels[x][y] == desiredColor)
			return;

		pixels[x][y] = desiredColor;

		// Only considers cardinal directions (not diagonals that could happen in
		// anti-aliasing)
		setColor(desiredColor, x + 1, y, pixels);
		setColor(desiredColor, x - 1, y, pixels);
		setColor(desiredColor, x, y + 1, pixels);
		setColor(desiredColor, x, y - 1, pixels);
	}

	private static boolean badBounds(int x, int y, int[][] p) {
		// Accounts for non-square dimensions by getting specific column size
		return x < 0 || y < 0 || x > p.length - 1 || y > p[x].length - 1;
	}

	public static void main(String[] args) {

		int[][] pixels = new int[10][10]; // Viewed as x, then y

		setColor(22, 0, 0, pixels);

		System.out.println(pixels[5][5]);
	}

}
