package interview.prep.misc;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Investigating the implications of returning a Stream from a method. It seems
 * to mean we should never modify the backing source (since we don't know when
 * the client may have finally used the returned stream? Therefore any mod may
 * cause ConcurrentModificationException... Bad practice? Create new clone to
 * produce Stream?
 * 
 * @author User
 *
 */
public class IsReturningAStreamAnAntiPattern {

	static Integer[] intArray = { 1, 2, 3, 4, 5, 6, 7, 8 };

	static List<Integer> myL = new ArrayList<>(Arrays.asList(intArray));

	static Stream<Integer> checkDependency() {
		return myL.stream();
	}

	public static void main(String[] args) throws InterruptedException {
		Runnable client = () -> {
			Stream<Integer> t = IsReturningAStreamAnAntiPattern.checkDependency();
			System.out.println("Client recieved stream.");
			t.map(s -> {
				try {
					System.out.println("Client sleeping on input " + s);
					Thread.sleep(50);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				return s;
			}).forEach(System.out::println);
		};

		Thread t = new Thread(client);
		t.start();

		// Modify backing collection of client's stream
		Thread.sleep(130);
		System.out.println("Server Starting Modification");
		myL.set(0, 100);
		myL.add(33);
		System.out.println("Server Finished Modification");
		System.out.println(myL.stream().peek(s -> myL.set(1, s)).collect(Collectors.toList()));

	}

}
