package interview.prep.skytree;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class PangramCheckerTest {

	
	@Test
	void testCompletePangram() {
		assertEquals("", PangramChecker.check("A quick brown fox jumps over the lazy dog"));

	}
	
	@Test
	void testNearlyCompletePangram() {
		assertEquals("bjkmqz", PangramChecker.check("A slow yellow fox crawls under the proactive dog"));

	}

	@Test
	void test2() {
		assertEquals("cfjkpquvwxz", PangramChecker.check("Lions, and tigers, and bears, oh my!"));
		
	}

	@Test
	void testMissingAll() {
		assertEquals("abcdefghijklmnopqrstuvwxyz", PangramChecker.check(""));
		
	}
}
