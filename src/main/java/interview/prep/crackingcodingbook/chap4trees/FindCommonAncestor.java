package interview.prep.crackingcodingbook.chap4trees;


/**
 * Problem 4.6
 * 
 * @author User
 *
 */
public class FindCommonAncestor {

	public static void main(String[] args) throws NodeNotFoundInTreeException {
		Node root = new Node(5, null);
		root.r = new Node(9, root);
		root.l = new Node(3, root);
		root.l.l = new Node(0, root.l);

		System.out.println(findCommonAncestor(root, root));// Expect
															// 5
															// (root)
		System.out.println(findCommonAncestor(root.r, root.l));// Expect 5
																// (root)
		System.out.println(findCommonAncestor(root.l.l, root.l));// Expect 3
																	// (root.l)
		try {
			findCommonAncestor(new Node(2,null), root);// Expect 5 (root)
		} catch (NodeNotFoundInTreeException e) {
			System.out.println("Exception thrown as expected");
		}

		System.out.println(findCommonAncestor_Optimized(root, root));// Expect
		// 5
		// (root)
		System.out.println(findCommonAncestor_Optimized(root.r, root.l));// Expect
																			// 5
		// (root)
		System.out.println(findCommonAncestor_Optimized(root.l.l, root.l));// Expect
																			// 3
		// (root.l)

	}

	// Does not ever re-search nodes that have already been looked at (If we
	// come from right subtree, only search parent + left subtree)
	private static Node findCommonAncestor_Optimized(Node n1, Node n2) throws NodeNotFoundInTreeException {
		if (treeContainsNode(n1, n2))
			return n1;
		Node temp = n1;
		while (temp.p != null) {
			Node oldTemp = temp;
			temp = temp.p;
			boolean searchLeft = oldTemp == temp.r ? true : false;

			if (directionalTreeSearchFinds(temp, n2, searchLeft))
				return temp;
		}

		throw new NodeNotFoundInTreeException();
	}

	// Only searches the root and one side (the unseen side) of the tree
	private static boolean directionalTreeSearchFinds(Node temp, Node n2, boolean searchLeft) {
		assert (temp != null);
		return temp == n2 || treeContainsNode(searchLeft ? temp.l : temp.r, n2);
	}

	// Dumb first version
	// Moves up the tree from n1 searching subtree until it finds (or fails to
	// find) n2
	private static Node findCommonAncestor(Node n1, Node n2) throws NodeNotFoundInTreeException {
		if (treeContainsNode(n1, n2))
			return n1;
		Node temp = n1;
		while (temp.p != null) {
			temp = temp.p;
			if (treeContainsNode(temp, n2))
				return temp;
		}

		throw new NodeNotFoundInTreeException();
	}

	private static boolean treeContainsNode(Node n1, Node n2) {
		if (n1 == null || n2 == null)
			return false;

		if (n1 == n2)
			return true;
		return treeContainsNode(n1.l, n2) || treeContainsNode(n1.r, n2);

	}

	public static class NodeNotFoundInTreeException extends Exception {

	}

	public static class Node {
		Node l, r, p; // left, right, parent
		int val;

		Node(int v, Node p) {
			val = v;
			this.p = p;
		}

		@Override
		public String toString() {
			return "" + val;
		}
	}
}
