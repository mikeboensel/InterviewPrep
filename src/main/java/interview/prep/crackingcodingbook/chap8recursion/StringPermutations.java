package interview.prep.crackingcodingbook.chap8recursion;

import java.util.ArrayList;
import java.util.List;

/**
 * Problem 8.4
 * @author User
 *
 */
public class StringPermutations {

	private static List<String> recursivelyGeneratePerms(String seed){
		List<String> perms = new ArrayList<>();
		if(seed.length() == 2) {
			perms.add(seed);
			perms.add(seed.substring(1, 2) + seed.substring(0, 1) );
			return perms;
		}
		for(int i = 0; i< seed.length(); i ++ ) {
			//Working with substring always a bit dicey on the bounds.
			List<String> subPerms = recursivelyGeneratePerms(seed.substring(0, i) + seed.substring(i+1));
			for(String subPerm: subPerms) {
				perms.add(seed.substring(i, i+1) + subPerm);
			}
		}
		return perms;
	}
	
	public static List<String> getPermutations(String s){
		if(s == null)
			throw new IllegalArgumentException();
		List<String> perms = new ArrayList<>();
		if(s.length() == 0 || s.length() == 1) {
			perms.add(s);
			return perms;
		}
		return recursivelyGeneratePerms(s);
			
	}
	
	static String removeOne(String s, int i) {
//		s.
		return s.substring(0, i) + s.substring(i+1, s.length());
	}
	
	public static void main(String[] args) {
		System.out.println(getPermutations("A"));
		System.out.println(getPermutations("AB"));
		System.out.println(getPermutations("ABC"));
	}

}
