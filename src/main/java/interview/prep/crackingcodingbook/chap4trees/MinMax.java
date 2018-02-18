package interview.prep.crackingcodingbook.chap4trees;


import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt;
import java.util.function.BiFunction;
import java.util.stream.Collectors;


public class MinMax {

	public static class Tuple<S1, S2> {
		public Tuple(S1 a, S2 b) {
			s1 = a;
			s2 = b;
		}

		S1 s1;
		S2 s2;

		@Override
		public String toString() {
			return String.format("%s %s", s1, s2);
		}
	}

	/**
	 * Can be either min or max depending on implementation of comp operator
	 * 
	 * @author User
	 *
	 */
	public static abstract class MinOrMax {

		int calc(Node n, int d) {
			if (n == null)
				return d - 1;

			int maxR = calc(n.r, d + 1);
			int maxL = calc(n.l, d + 1);

			return comp(maxR, maxL) ? maxR : maxL;
		}

		abstract boolean comp(int maxR, int maxL);
	}

	/**
	 * Redone as functional interface
	 * 
	 * @author User
	 *
	 */
	@FunctionalInterface
	public static interface MinOrMaxInterface {
		default int calc(Node n, int d) {
			if (n == null)
				return d - 1;

			int maxR = calc(n.r, d + 1);
			int maxL = calc(n.l, d + 1);

			return comp(maxR, maxL) ? maxR : maxL;
		}

		abstract boolean comp(int maxR, int maxL);
	}

	/**
	 * Attempted a third way. Didn't work, but seems like it should....
	 * 
	 * @author User
	 *
	 */
//TODO figure out how to do this

	public static void blehDoesntWork() {
		BiFunction<Integer, Integer, Boolean> max = (a, b) -> a >= b;
		
//		 BiFunction<Node, Integer, Integer> minOrMax = (n, d) -> {
//			if (n == null)
//				return d - 1;
//
//			int maxR = minOrMax.apply(n.r, d + 1);
//			int maxL = minOrMax.apply(n.l, d + 1);
//
//			return -1; 
////					max(maxR, maxL) ? maxR : maxL;
//		};
	}

	public static class Node {
		Node l, r; // Binary Tree case
		List<Node> children = new ArrayList<>(); // N-ary Tree case

		boolean hasChildren() {
			return children != null && children.size() > 0;
		}
	}

	public static void main(String[] args) {
		
		//Binary Tree with various implementations
		Node root = new Node();
		root.r = new Node();
		root.l = new Node();
		root.l.l = new Node();

		// Abstract requires clunky old syntax
		MinOrMax abstractImpl = new MinOrMax() {

			@Override
			boolean comp(int maxR, int maxL) {
				// TODO Auto-generated method stub
				return false;
			}
		};
		// Lambda needed default interface method. Don't like, but mb not so bad
		MinOrMaxInterface lambda = (a, b) -> a >= b;

		System.out.println(abstractImpl.calc(root, 1));
		System.out.println(lambda.calc(root, 1));

		System.out.println(getMax(root, 1));
		System.out.println(getMin(root, 1));
		
		//N-ary Tree
		root = new Node();
		root.children.add(new Node());
		Node a = new Node();
		Node b = new Node();
		b.children.add(new Node());
		a.children.add(b);
		root.children.add(a);
		
		Tuple<Integer, Integer> range = getDepthRange(root, 1);
		
		System.out.println(range);


	}

	private static Tuple<Integer, Integer> getDepthRange(Node n, int currDepth) {
		if (n == null) {
			return new Tuple<Integer,Integer>(currDepth-1, currDepth-1);
		}
		if(n.hasChildren()) {
			List<Tuple<Integer,Integer>> childRanges = n.children.stream().map(s-> getDepthRange(s, currDepth+1)).collect(Collectors.toList());
			
			OptionalInt childMin = childRanges.stream().mapToInt(s -> (Integer)s.s1).min();
			
			OptionalInt childMax = childRanges.stream().mapToInt(s -> (Integer)s.s2).max();
			
			return new Tuple<Integer,Integer>(childMin.getAsInt(), childMax.getAsInt());
		}
		

		return new Tuple<Integer,Integer>(currDepth, currDepth);
	}

	static int getMax(Node n, int currDepth) {
		if (n == null)
			return currDepth - 1;

		int maxR = getMax(n.r, currDepth + 1);
		int maxL = getMax(n.l, currDepth + 1);

		return maxR >= maxL ? maxR : maxL;
	}

	static int getMin(Node n, int currDepth) {
		if (n == null)
			return currDepth - 1;

		int maxR = getMin(n.r, currDepth + 1);
		int maxL = getMin(n.l, currDepth + 1);

		return maxR <= maxL ? maxR : maxL;
	}

}
