package interview.prep.crackingcodingbook.chap4trees;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Problem 4.3 
 * @author User
 *
 */
public class SortedArrayToBinaryTree {

	public static  class Node {
		Node L,R;
		int val;
	
		@Override
		public String toString() {
			return "" + val;
		}
	}
	
	
	
	public static  class Tuple {
		int a,b;
		Tuple(int _a, int _b){
			a = _a;
			b = _b;
		}
	}

	public static void main(String[] args) {
		int[] arr = {0, 1,2,3,4,5,6,7,8,9,10};
		ops.add(new Tuple(0, arr.length-1));
		
		queueBased(arr);
		
		Node n = recurseObj(arr, 0 , arr.length-1);
		System.out.println(n);
		
//		toBinTree(arr, 0, arr.length-1);
		
	}

	private static Node recurseObj(int[] arr, int L, int R) {
		if(L>R)
			return null;

		int mid = (int) Math.floor((L+R)/2);

		Node n = new Node();
		n.val = arr[mid];
		n.L = recurseObj(arr, L, mid-1);
		n.R = recurseObj(arr, mid+1, R);
		return n;
	}

	static Queue<Tuple> ops = new LinkedList<>(); 
	
	private static void queueBased(int[] arr) {
		while(ops.peek() != null) {
			Tuple t = ops.poll();
			if(t.a > t.b){
				continue;
			}
			
			int mid = (int) Math.floor((t.a+t.b)/2);
			System.out.println(arr[mid]);
			ops.add(new Tuple(t.a, mid-1));//Left
			ops.add(new Tuple(mid+1, t.b));//Right
			
			
		}
	}
	
	
	
	
	//Recursive
	private static void toBinTree(int[] arr, int L, int R) {
		if(L>R)
			return;
		
		int mid = (int) Math.floor((L+R)/2);
		
		//Preorder traversal
		System.out.println(arr[mid]);
		toBinTree(arr, L, mid-1);
		toBinTree(arr, mid+1, R);
		

		
	}

}
