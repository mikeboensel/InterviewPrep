package interview.prep.geeks4geeks;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

/**
 * Solves the problem of placing n queens on a chess board with none able to
 * capture or be captured.
 * 
 * Recursion along with filtering of the possible valid remaining board
 * positions is used.
 * 
 * Note, this will always find the same solution because the iterator always
 * returns the remaining positions in the same order. To find others implement
 * some form of shuffling or other bias.
 * 
 * @author User
 *
 */
public class NQueens {

	public static void main(String[] args) {
		long start = System.currentTimeMillis();
		int numOfQueens = 8;
		Set<Position> s = placeQueen(numOfQueens, generateFullBoard(), new HashSet<Position>());

		if (s == null) {
			System.out.println("No Solution");
		} else {
			System.out.println("Is a good solution?" + isGoodSolution(s));
			System.out.println(s);
		}
		System.out.println(String.format("Processing took %d millis with %d instances",
				(System.currentTimeMillis() - start), Position.instanceCount));
	}

	/**
	 * Double checks the solution passed in to be sure it hits the constraints
	 * of the problem
	 * 
	 * @param s
	 * @return
	 */
	private static boolean isGoodSolution(Set<Position> s) {
		Position[] pos = s.toArray(new Position[s.size()]);
		// check each position against every other position (lazy but
		// performance is not a big deal here)

		for (int i = 0; i < pos.length; i++) {
			for (int j = i + 1; j < pos.length; j++) {
				if (conflicts(pos[i], pos[j])) {
					System.out.println(String.format("Conflict amongst %s %s", pos[i], pos[j]));
					return false;
				}
			}
		}

		return true;
	}

	protected static Set<Position> generateFullBoard() {
		Set<Position> s = new HashSet<Position>();
		for (int x = 1; x < 9; x++) {
			for (int y = 1; y < 9; y++) {
				s.add(Position.getPosition(x, y));
			}
		}
		return s;
	}

	static Set<Position> placeQueen(int numOfQueens, Set<Position> availablePos, Set<Position> solution) {
		if (numOfQueens == 0) {
			return solution;
		}
		if (availablePos.size() == 0) {
			// We still have queens to place, but no where to put them. This
			// path failed
			return null;
		}

		for (Position proposedPosition : availablePos) {
			Set<Position> availablePosCopy = createNonConflictingSet(availablePos, proposedPosition);

			solution.add(proposedPosition);

			Set<Position> proposedSolution = placeQueen(numOfQueens - 1, availablePosCopy, solution);
			if (proposedSolution == null) {
				// Queen cannot be placed there, try a new placement in the next
				// loop iteration
				solution.remove(proposedPosition);
			} else {
				return proposedSolution; // solution found
			}
		}

		return null;// could not find a solution after looping through all
					// possible positions
	}

	protected static Set<Position> createNonConflictingSet(Set<Position> availablePos, Position proposedPosition) {
		Set<Position> s = new HashSet<Position>();
		for (Position position : availablePos) {
			if (conflicts(proposedPosition, position) == false)
				s.add(position);
		}
		return s;
	}

	/**
	 * Given 2 queens determine whether they can hit each other
	 * 
	 * @param q1
	 * @param q2
	 * @return
	 */
	protected static boolean conflicts(Position q1, Position q2) {
		if (q1.x == q2.x || q1.y == q2.y) // Cardinal directions
			return true;

		// Check diagonals with slope calculation. If == 1 or -1 its on a
		// diagonal

		int rise = q1.y - q2.y;
		int run = q1.x - q2.x;

		if (Math.abs(rise) == Math.abs(run)) // slope of 1
			return true;

		return false;
	}

	public static class Position {
		@Override
		public int hashCode() {
			final int prime = 31;
			int result = 1;
			result = prime * result + x;
			result = prime * result + y;
			return result;
		}

		@Override
		public boolean equals(Object obj) {
			if (this == obj)
				return true;
			if (obj == null)
				return false;
			if (getClass() != obj.getClass())
				return false;
			Position other = (Position) obj;
			if (x != other.x)
				return false;
			if (y != other.y)
				return false;
			return true;
		}

		@Override
		public String toString() {
			return "Position [x=" + x + ", y=" + y + "]";
		}

		static Map<Integer, Position> cache = new HashMap<Integer, Position>();

		/**
		 * Static factory makes a big difference 
		 * @param x
		 * @param y
		 * @return
		 */
		static Position getPosition(int x, int y) {
			// Encodes into int
			int key = x * 10 + y;
			Position p = cache.get(key);
			if (p == null) {
				p = new Position(x, y);
				cache.put(key, p);
			}
			return p;
		}

		static int instanceCount = 0;

		private Position(int x, int y) {
			super();
			instanceCount++;
			this.x = x;
			this.y = y;
		}

		int x, y;

	}

}

