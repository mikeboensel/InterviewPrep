package interview.prep.skytree;

import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;

/*



At time 0 (init) there are 8 particles. At time 1, there are still 6 particles, but only 4 positions are occupied since particles are passing through each other.



4)  1,  "..."

Returns:

  { "..." }

5)  1,  "LRRL.LR.LRR.R.LRRL."

Returns:

  { "XXXX.XX.XXX.X.XXXX.",

    "..XXX..X..XX.X..XX.",

    ".X.XX.X.X..XX.XX.XX",

    "X.X.XX...X.XXXXX..X",

    ".X..XXX...X..XX.X..",

    "X..X..XX.X.XX.XX.X.",

    "..X....XX..XX..XX.X",

    ".X.....XXXX..X..XX.",

    "X.....X..XX...X..XX",

    ".....X..X.XX...X..X",

    "....X..X...XX...X..",

    "...X..X.....XX...X.",

    "..X..X.......XX...X",

    ".X..X.........XX...",

    "X..X...........XX..",

    "..X.............XX.",

    ".X...............XX",

    "X.................X",

   "..................." }
 */
class LinearChamberAnimationTest {

	/*
	  The single particle starts at the 3rd position, moves to the 5th, then
	  7th, and then out of the chamber.
	  
	  1) 3, "RR..LRL"
	  
	  Returns:
	  
	  { "XX..XXX",
	    ".X.XX..",
	    "X.....X", 
	    "......." 
	  }
	 */

	@Test
	void test() {
		List<String> l = Arrays.asList(
				"XX..XXX",
				".X.XX..",
				"X.....X",
				".......");

		assertEquals(l, LinearChamberAnimation.runFullAnimation(3, "RR..LRL"));

	}
	
	
	/*
	 Note that, at the first time step after init, there are actually 4 particles in the chamber, but two are passing through each other at the 4th position

2)  2,  "LRLR.LRLR"

Returns:

  { "XXXX.XXXX",
    "X..X.X..X",
    ".X.X.X.X.",
    ".X.....X.",
    "........." }
	 */

	
	@Test
	void test2() {
		List<String> l = Arrays.asList(
				 "XXXX.XXXX",
				 "X..X.X..X",
				 ".X.X.X.X.",
				 ".X.....X.",
				 ".........");

		assertEquals(l, LinearChamberAnimation.runFullAnimation(2,  "LRLR.LRLR"));

	}
	
	/*
	 3)  10,  "RLRLRLRLRL"

Returns:

  { "XXXXXXXXXX",
    ".........." }

These particles are moving so fast that they all exit the chamber by time 1.
	 */
	
	
	@Test
	void testFastParticles() {
		List<String> l = Arrays.asList(
				"XXXXXXXXXX",
			    "..........");

		assertEquals(l, LinearChamberAnimation.runFullAnimation(10,  "RLRLRLRLRL"));

	}
}
