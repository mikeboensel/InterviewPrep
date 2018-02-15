package interview.prep.crackingcodingbook.chap8recursion;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class FibonacciTest {

	Fibonacci f;
	@Before
	public void setUp() throws Exception {
		f = new Fibonacci(); //Prevent any caching between tests 
	}

	/**
	 * We implemented 3 different ways of calculating. Utility method for testing all three easily.
	 * @param fibNum
	 */
	private void tripleTest(int fibNum, int expected) {
		assertEquals(expected, f.getFibNum(fibNum, true, true));
		assertEquals(expected, f.getFibNum(fibNum, true, false));
		assertEquals(expected, f.getFibNum(fibNum, false, false));
	}
	
	@Test
	public void test0Case() {
		tripleTest(0, 0);
	}
	
	@Test
	public void test1Case() {
		tripleTest(1, 1);
	}
	
	@Test
	public void test2Case() {
		tripleTest(2, 1);
	}
	
	@Test
	public void test5Case() {
		tripleTest(5, 5);
	}
	
}
