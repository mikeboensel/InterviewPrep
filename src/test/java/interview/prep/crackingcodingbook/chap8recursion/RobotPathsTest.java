package interview.prep.crackingcodingbook.chap8recursion;

import static org.junit.Assert.*;

import org.junit.Test;
import org.junit.jupiter.api.Disabled;

public class RobotPathsTest {

	@Test
	public void oneSquareGrid() {
		RobotPaths r = new RobotPaths(1);
		assertEquals(1, r.countPaths());
	}
	@Test
	public void fourSquareGrid() {
		RobotPaths r = new RobotPaths(2);
		assertEquals(2, r.countPaths());
	}

	@Test
	public void nineSquareGrid() {
		RobotPaths r = new RobotPaths(3);
		assertEquals(6, r.countPaths());
	}
	
	
}
