package interview.prep.misc;

import java.util.StringJoiner;

public class Scratchpad {

	public static void main(String[] args) {
StringJoiner sj = new StringJoiner(",", "pre", "suff");
sj.add("A");
sj.add("Z");
sj.add("B");
StringJoiner sj2 = new StringJoiner(",", "pre", "suff");
sj2.add("Q");
sj.merge(sj2);
System.out.println(sj);

	}

}
