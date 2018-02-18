package interview.prep.crackingcodingbook.chap4trees;

import interview.prep.crackingcodingbook.chap4trees.FindCommonAncestor.Node;

public class TreeIsSubTree {

	public static void main(String[] args) {
		Node root = new Node(5, null);
		root.r = new Node(9, root);
		root.l = new Node(3, root);
		root.l.l = new Node(0, root.l);
		
		//Is subtree at root.l in root
		Node root2 = new Node(3, null);
		root2.l = new Node(0, root2);
		
		System.out.println(isSubTree(root, root2));
		
		//Shouldnt match
		root2 = new Node(3, null);
		root2.l = new Node(1, root2);
		System.out.println(isSubTree(root, root2));

		//Simple case. Single node. Matches on root
		root2 = new Node(5, null);
		System.out.println(isSubTree(root, root2));

	}

	private static boolean isSubTree(Node n1, Node n2) {
		if(n1 == null || n2 == null)
			return false;
		if(n1.val == n2.val)
			if(checkForMatch(n1,n2))
				return true;

		//If no match found continue applying "search template" across different nodes of bigger tree 
		return isSubTree(n1.l, n2) || isSubTree(n1.r, n2);
	}

	private static boolean checkForMatch(Node n1, Node n2) {
		if (n2 == null) { //exhausted a branch of search template. This is good. Means matched on all that!
			return true;
		}
		if(n1 == null) { //exhausted bigger tree b4 search template. Means template no longer matches from here down. Bad!
			return false;
		}

		//Both sides must match for there to be a match
		if(n1.val == n2.val)
			return checkForMatch(n1.l, n2.l) && checkForMatch(n1.r, n2.r);
					
		return false;
	}

}
