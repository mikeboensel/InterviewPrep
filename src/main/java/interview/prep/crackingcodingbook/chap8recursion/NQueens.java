package interview.prep.crackingcodingbook.chap8recursion;

import java.util.HashSet;
import java.util.Set;

/**
 * Problem 8.8
 * @author User
 *
 */
public class NQueens {

	static int boardDim = 8;
	
	static class Position {
		int x, y;

		public Position(int i, int j) {
			x = i;
			y = j;
		}

		@Override
		public String toString() {
			return String.format("[x=%d,y=%d]", x, y);
		}

		@Override
		public boolean equals(Object obj) {
			if (obj instanceof Position) {
				Position t = (Position) obj;
				return t.x == this.x && t.y == this.y;
			}
			return false;
		}

		@Override
		public int hashCode() {
			return x * 80 + y;
		}
	}

	static Set<Set<Position>> calcNQueens(int queensToPlace, Set<Position> priorPlacements) {
		Set<Set<Position>> foundArrangements = new HashSet<>();

		if (queensToPlace < 0 || priorPlacements == null)
			throw new IllegalArgumentException();

		if (queensToPlace == 0) {
			foundArrangements.add(priorPlacements);
			return foundArrangements;
		}

		for (int x = 1; x <= boardDim; x++) {
			for (int y = 1; y <= boardDim; y++) {
				if (noConflicts(x, y, priorPlacements)) {
					Position trialPlacement = new Position(x, y);
					
					Set<Position> trial = new HashSet<>(priorPlacements);
					trial.add(trialPlacement);

					//TODO: Maybe flatmap?
					Set<Set<Position>> found = calcNQueens(queensToPlace - 1, trial);
					for (Set<Position> f : found) {
						foundArrangements.add(f);
					}
				//	priorPlacements.remove(trialPlacement);
				}
			}
		}

		return foundArrangements;

	}

	static boolean noConflicts(int x, int y, Set<Position> priorPlacements) {
		return priorPlacements.stream().noneMatch(p -> p.x == x || p.y == y || diagonalsMatch(x, y, p));

	}

	// Slope equation of +/- 1 means on a diagonal (45 degree)
	static boolean diagonalsMatch(int x, int y, Position p) {
		return Math.abs((p.y - y )/ (p.x - x)) == 1;
	}

	public static void main(String[] args) {
		System.out.println(calcNQueens(8, new HashSet<>()));
	}

}
