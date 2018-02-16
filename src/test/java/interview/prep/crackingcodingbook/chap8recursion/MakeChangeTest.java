package interview.prep.crackingcodingbook.chap8recursion;

import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Test;

public class MakeChangeTest {

	List<Integer> standardCoins = Collections.unmodifiableList(Arrays.asList(25, 10, 5, 1));

	@Test
	public void testPenny() {
		assertEquals("Penny is the only way to make 1 cent", 1, MakeChange.waysToMakeChange(1, standardCoins));
	}

	@Test
	public void testNickel() {
		assertEquals("Can make a Nickel 2 ways. 1 Nickel or 5 pennies", 2,
				MakeChange.waysToMakeChange(5, standardCoins));
	}

	@Test
	public void testDime() {
		assertEquals("Can make a Dime 4 ways. 1 Dime, 2 Nickel, 1 nickel + 5 pennies, or 10 pennies", 4,
				MakeChange.waysToMakeChange(10, standardCoins));
	}

	@Test
	public void testQuarter() {
		assertEquals(
				"Can make a Quarter 13 ways. 1 Q, 2d + 1n, 2d + 5p, 1d + 3n, 1d + 2n + 5p, 1d + 1n + 10p, 1d + 15p, 5N, 4N + 5p, 3N + 10p, 2N + 15p, 1N + 20p, 25 pennies",
				13, MakeChange.waysToMakeChange(25, standardCoins));
	}

	@Test
	public void testNonCoinAmount() {
		assertEquals(
				"Can make a 11cents 4 ways. 1d + 1p, 2N + 1p, 1N + 6p, 11p",
				4, MakeChange.waysToMakeChange(11, standardCoins));
	}
	
}
