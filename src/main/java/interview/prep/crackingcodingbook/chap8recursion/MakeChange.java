package interview.prep.crackingcodingbook.chap8recursion;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Problem 8.7
 * 
 * @author User
 *
 */
public class MakeChange {

	/**
	 * 
	 * @param n
	 *            - Cents to make via coins
	 * @param coinVals
	 *            - different coins we have (Quarter = 25, Dime = 10, etc. ) Can
	 *            make your own of arbitrary value
	 * @return
	 */
	static int waysToMakeChange(int n, List<Integer> coinVals) {
		if (coinVals == null || coinVals.size() == 0)
			return 0;

		// Get largest, Definitely shouldn't have dupes
		coinVals = coinVals.stream().distinct().sorted((a, b) -> a > b ? -1 : a == b ? 0 : 1)
				.collect(Collectors.toList());
		int coinVal = coinVals.get(0);

		int maxOfThisCoin = (int) Math.floor(n / coinVal);

		int ctr = 0;
		for (int i = 0; i <= maxOfThisCoin; i++) {
			int remainder = n - i * coinVal;
			if (remainder == 0)
				ctr++;
			else
				ctr += waysToMakeChange(remainder, coinVals.subList(1, coinVals.size()));
		}

		return ctr;
	}

	public static void main(String[] args) {

		List<Integer> coins = Arrays.asList(25, 10, 5, 1);
		System.out.println(waysToMakeChange(10, coins));

	}

}
