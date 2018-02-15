package interview.prep.crackingcodingbook.chap8recursion;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Spliterator;
/**
 * Problem 8.3
 * @author User
 *
 */
public class SetOfAllSubsets {

	public static void main(String[] args) {
		
		List<String> list = Arrays.asList("A", "Banana", "Orange","D","E");

		  Spliterator<String> s = list.spliterator();
		  Spliterator<String> s1 = s.trySplit().trySplit();

		  s.forEachRemaining(System.out::println);
		  System.out.println("-- traversing the other half of the spliterator --- ");
		  s1.forEachRemaining(System.out::println);
		
		
		Set<String> subSet = new HashSet<>();
		subSet.add("A");
		subSet.add("B");
		subSet.add("C");
		System.out.println(findAllSets(subSet));

	}

	static Set<Set<String>> findAllSets(Set<String> seed) {
		
		Set<Set<String>> setOfSets = new HashSet<Set<String>>();
		setOfSets.add(seed);

		List<String> setMembers = Arrays.asList(seed.toArray(new String[0]));
		for (int i = 0; i < seed.size(); i++) { // Progressively remove one item from the set and get its subsets

			Set<String> subSet = new HashSet<>();
			for (int j = 0; j < seed.size(); j++) {
				if (i != j)// Not the (1) item per set to be excluded
					subSet.add(setMembers.get(j));
			}
			setOfSets.addAll(findAllSets(subSet));

		}
		return setOfSets;
	}

}

