package interview.prep.crackingcodingbook.chap4trees;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
/**
 * Problem 4.4 of Cracking Coding Interview
 * @author User
 *
 */
public class BinTreeToLevelLists {

	public static class Node {
		Node l, r;
		int val;

		Node(int v) {
			val = v;
		}

		@Override
		public String toString() {
			return "" + val;
		}
	}

	public static void main(String[] args) {
		Node root = new Node(5);

		root.r = new Node(100);
		root.r = new Node(110);
		root.l = new Node(3);
		root.l.l = new Node(1);
		root.l.l.r = new Node(2);

//		List<List<Integer>> levels = binTreeToLevelList(root);
//		for(List<Integer> l : levels) {
//			System.out.println(l);
//		}
	}

	private static List<List<Integer>> binTreeToLevelList(Node root) {

		List<List<Integer>> levels = IntStream.range(0, getMax(root,0) + 1)
				.mapToObj(i -> convertLevelToList(root, i, 0)).collect(Collectors.toList());

		return levels;
	}

	private static List<Integer> convertLevelToList(Node n, int desiredLvl, int currLvl) {

		if (n == null)
			return new ArrayList<>();

		List<Integer> level = new ArrayList<>();
		if (currLvl == desiredLvl) {
			level.add(n.val);
		} else {
			level.addAll(convertLevelToList(n.l, desiredLvl, currLvl + 1));
			level.addAll(convertLevelToList(n.r, desiredLvl, currLvl + 1));
		}

		return level;
	}

	static int getMax(Node n, int currDepth) {
		if (n == null)
			return currDepth - 1;

		int maxR = getMax(n.r, currDepth + 1);
		int maxL = getMax(n.l, currDepth + 1);

		return maxR >= maxL ? maxR : maxL;
	}
}
