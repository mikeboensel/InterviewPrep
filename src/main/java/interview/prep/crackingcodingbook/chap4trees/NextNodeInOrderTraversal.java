package interview.prep.crackingcodingbook.chap4trees;


/**
 * Problem 4.5 - TODO think i did this for pre-order. Right idea and execution, wrong ordering
 * @author User
 *
 */
public class NextNodeInOrderTraversal {
	
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

	public static void main(String[] args) {
		Node root = new Node(5, null);
		root.r = new Node(9, root);
		root.l = new Node(3, root);
		root.l.l = new Node(0, root.l);

		System.out.println(getSuccessor(root, null)); // 3 (Root's left child)
		System.out.println(getSuccessor(root, root.l)); // 9 (Root's right
														// child)
		System.out.println(getSuccessor(root.l.l, null)); // 9 (Traverse up to
															// root, then to
															// first right node)
	}

	/**
	 * 
	 * @param curr
	 *            - Current node we are examining
	 * @param prior
	 *            - A node path that we have already printed
	 * @return null OR the next Node to print
	 */
	static Node getSuccessor(Node curr, Node prior) {
		if (curr == null) {
			return null;
		}

		if (curr.l != null && curr.l != prior && curr.r != prior) {
			return curr.l;
		}

		else if (curr.r != null && curr.r != prior) {
			return curr.r;
		}

		else {
			// Proceed to parent if 1) we've printed the right side of this
			// node's children means we've printed everything
			// beneath it.
			// OR 2) We have no children
			return getSuccessor(curr.p, curr);
		}


	}

}

