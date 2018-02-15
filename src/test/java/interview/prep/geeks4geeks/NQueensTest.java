package interview.prep.geeks4geeks;

import static org.junit.Assert.*;

import org.junit.Test;

import java.util.Set;

import interview.prep.geeks4geeks.NQueens.Position;

public class NQueensTest {
	
	@Test
	public void testConflictMethods(){
		assertTrue(NQueens.conflicts(Position.getPosition(1,1), Position.getPosition(1,1)));
		assertTrue(NQueens.conflicts(Position.getPosition(1,1), Position.getPosition(2,2)));
		assertTrue(NQueens.conflicts(Position.getPosition(5,8), Position.getPosition(3,8)));
		assertTrue(NQueens.conflicts(Position.getPosition(2,1), Position.getPosition(1,2)));
		assertFalse(NQueens.conflicts(Position.getPosition(1,1), Position.getPosition(2,8)));
	}
	
	@Test
	public void testRemoveQueenPositions() {
		Set<Position> pos = NQueens.generateFullBoard();
		pos = NQueens.createNonConflictingSet(pos, Position.getPosition(4,4));
		
		assertFalse(pos.contains(Position.getPosition(4,5)));
		assertFalse(pos.contains(Position.getPosition(5,5)));
		assertFalse(pos.contains(Position.getPosition(4,3)));
		assertFalse(pos.contains(Position.getPosition(1,1)));
		assertTrue(pos.contains(Position.getPosition(1,2)));
		assertTrue(pos.size() == (64-28));
	}
}
