package interview.prep.crackingcodingbook.chap8recursion;

import static org.junit.Assert.*;

import java.util.Arrays;

import org.junit.Test;

public class PaintBucketTest {
	
	static void setArray(int val, int[][] pixels) {
		for (int i = 0; i < pixels.length; i++) {
			Arrays.fill(pixels[i], val);
		}
		
	}
	@Test
	public void testNoBoundary() {
		int[][] pixels = new int[10][10];

		setArray(0, pixels);
		
		PaintBucket.setColor(11, 0,0, pixels);
		
		for (int[] cols : pixels) {
			for (int i : cols) {
				assertEquals("Expect the entire region to be 11s " ,i, 11);
			}
		}
	}

	@Test
	public void testWithPartitioningBoundary() {
		int[][] pixels = new int[10][10];
	
		setArray(0, pixels);
		
		int SEPARATING_LINE = 4;
		//Separating line of desiredColor
		Arrays.fill(pixels[SEPARATING_LINE], 11);
		
		PaintBucket.setColor(11, 6,1, pixels);
		
		for (int i = 0; i< SEPARATING_LINE; i++) {
			for (int j : pixels[i]) {
				assertEquals("Expect the entire region LEFT of bounding line to be 0s " ,j, 0);
			}
		}
		
		for (int i = SEPARATING_LINE; i<pixels.length-1 ; i++) {
			for (int j : pixels[i]) {
				assertEquals("Expect the entire region to RIGHT of bounding line and bounding line to be 11s " ,j, 11);
			}
		}
				
	}
	
	
}
