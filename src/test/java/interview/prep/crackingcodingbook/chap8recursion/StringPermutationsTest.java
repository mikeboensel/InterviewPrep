package interview.prep.crackingcodingbook.chap8recursion;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

public class StringPermutationsTest {

	@Test
	public void testEmptyString() {
		List<String> s =  StringPermutations.getPermutations("");
		assertEquals(s.size(), 1);
		assertTrue(s.contains(""));
	}
	
	@Test
	public void testSingleCharacterString() {
		List<String> s =  StringPermutations.getPermutations("Z");
		assertEquals(s.size(), 1);
		assertTrue(s.contains("Z"));
	}
	
	@Test
	public void test2CharacterString() {
		List<String> s =  StringPermutations.getPermutations("AZ");
		assertEquals(s.size(), 2);
		assertTrue(s.contains("AZ"));
		assertTrue(s.contains("ZA"));
	}

}
