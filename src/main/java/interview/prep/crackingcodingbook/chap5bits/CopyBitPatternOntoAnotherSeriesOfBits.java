package interview.prep.crackingcodingbook.chap5bits;

/** Problem 5.1 
 * 
 * @author User
 *
 */
public class CopyBitPatternOntoAnotherSeriesOfBits {

	public static void main(String[] args) {
		//Better, construct via bitwise ops
		int m = 0b1110000000000;
		int n = 0b101;
		int i = 0;
		int j = 4;
		int range = j - i;

		//Get number value if it was positioned with i = 0
		int rangePreservingMask = (int) Math.pow(2, range) - 1;
		//Shift by i
		rangePreservingMask = (int) (rangePreservingMask * Math.pow(2, i));

		int rangeZeroingMask = ~ rangePreservingMask;

		System.out.println(String.format("M: \t\t %08x", m));
		System.out.println(String.format("Zeroing Mask:\t %08x", rangeZeroingMask));

		int mWithMaskApplied = m & rangeZeroingMask;
		System.out.println(String.format("M postMask: \t\t %08x", mWithMaskApplied));
		
		System.out.println(String.format("N: \t\t %08x", n));
		System.out.println(String.format("Preserving Mask: %08x", rangePreservingMask));
		
		int nWithMaskApplied = n & rangePreservingMask;
		System.out.println(String.format("N postMask: \t\t %08x", nWithMaskApplied));

		System.out.println(String.format("Final result: \t %08x", nWithMaskApplied | mWithMaskApplied));

	}

}
