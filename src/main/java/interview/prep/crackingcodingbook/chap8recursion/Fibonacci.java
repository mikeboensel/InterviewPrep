package interview.prep.crackingcodingbook.chap8recursion;

import java.util.HashMap;
import java.util.Map;

/**
 * Problem 8.1
 * 
 * @author User
 *
 */
class Fibonacci {

	/**
	 * Done without memoization. Should be factorial time.
	 * 
	 * @param fibNum
	 *            - The desired number in the sequence ( 0 1 1 2 3 5 ...) Zero based
	 *            ( 0 is the 0th fib num)
	 * @return
	 */
	private int getFibNumNaive(int fibNum) {
		if (fibNum == 0)
			return 0;
		if (fibNum == 1)
			return 1;
		return getFibNumNaive(fibNum - 1) + getFibNumNaive(fibNum - 2);
	}

	Map<Integer, Integer> cache = new HashMap<>();

	/**
	 * Uses a cache to prevent factorial runtime (due to recomputation of each sub
	 * tree)
	 * 
	 * @param fibNum
	 * @return
	 */
	private int getFibNumMemoized(int fibNum) {
		if (cache.containsKey(fibNum))
			return cache.get(fibNum);
		if (fibNum == 0)
			return 0;
		if (fibNum == 1)
			return 1;
		int result = getFibNumMemoized(fibNum - 1) + getFibNumMemoized(fibNum - 2);
		// We didn't have the result pre-computed, so store it now for subsequent use
		cache.put(fibNum, result);
		return result;
	}

	/**
	 * Probably a bit faster than memoized, due to lack of function calls.
	 * 
	 * @param fibNum
	 * @return
	 */
	private int getFibNumIterative(int fibNum) {
		if (fibNum == 0)
			return 0;
		if (fibNum == 1)
			return 1;

		int prev = 0;
		int curr = 1;
		// Loop bound starts at 1, the last base case defined fibNum. Continues until we
		// get the FibNum
		for (int i = 1; i < fibNum; i++) {
			// Need to shuffle things along. Curr moves to the next value. Prev takes the
			// value curr was at
			int temp = curr;
			curr = curr + prev;
			prev = temp;
		}

		return curr;
	}

	public int getFibNum(int fibNum, boolean useRecursion, boolean memoize) {
		// Handles input checking, dispatches to relevant implementation
		if (fibNum < 0)
			throw new IllegalArgumentException(
					"Fibononacci sequence is only defined for positive numbers and zero, not for input: " + fibNum);
		if (useRecursion)
			if (memoize)
				return getFibNumMemoized(fibNum);
			else
				return getFibNumNaive(fibNum);
		else
			return getFibNumIterative(fibNum);

	}

	public static void main(String[] args) {
		Fibonacci f = new Fibonacci();

		System.out.println(f.getFibNum(5, true, true));
		System.out.println(f.getFibNum(5, true, false));
		System.out.println(f.getFibNum(5, false, false));
		// All should agree

	}

}
