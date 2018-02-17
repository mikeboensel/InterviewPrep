package interview.prep.crackingcodingbook.chap8recursion;

import static org.junit.Assert.*;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import interview.prep.crackingcodingbook.chap8recursion.NQueens.Position;

public class NQueensTest {

	public boolean testPos(int x1, int y1, int x2, int y2) {
		Set<Position> priorPlacements = new HashSet<>();
		priorPlacements.add(new Position(x1, y1));
		return NQueens.noConflicts(x2, y2, priorPlacements);
	}
	
	@Test
	public void testConflictDiag1() {
		assertFalse(testPos(1, 1, 2, 2));
	}
	
	@Test
	public void testConflictDiag2() {
		assertFalse(testPos(4, 4, 2, 2));
	}
	
	@Test
	public void testConflictDiag3() {
		assertFalse(testPos(4, 4, 3, 5));
	}
	
	@Test
	public void testConflictDiag4() {
		assertFalse(testPos(4, 4, 5, 3));
	}
	
	@Test
	public void testNonConflictPos() {
		assertTrue(testPos(4, 4, 3, 8));
	}
	

}
