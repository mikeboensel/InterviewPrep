package interview.prep.misc;

import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class Java8Overview {
	public static void main(String[] args) {

		// Cant call default functions from lambdas!
		FuncInterface f = () -> 1; // f.myDef(), myDef(), etc would all fail to
									// compile
		System.out.println(f.myDef());

		// predicates();

		// functionBasics();

		// functionTypes();

		// String streaming isn't exactly straightforward
		String abc = "abc";
		abc.chars() // intstream at this point (int values of chars)
				.mapToObj(s -> String.valueOf((char) s)); // cast to char, get
															// Strings from

		// optionals();

		streams();


	}

	private static void streams() {
		// Defined on all collections except Maps (can get streams of Keyset or
		// object set)
		Map<String, String> m = new HashMap<>();
		m.values().stream();
		m.keySet().stream();

		// Disappointing, but big upgrades to Maps
		mapUpgrades();

		// To stream arrays
		int[] i = { 1, 2, 3 };
		Arrays.stream(i);

		// Intermediate ops return another Stream
		// Filter, map, sorted(optional Comparator), distinct

		// Terminal ops do a Reduction (Fold) or have a side effect
		// AnyMatch, NoneMatch, AllMatch --All take Predicate
		// Count, reduce,

		// Reduce
		Optional<String> reduced = Arrays.asList("a", "b", "c").stream().sorted().reduce((acc, s) -> acc + "#" + s);
		reduced.ifPresent(System.out::println);

		// From primitive streams max, min, average, sum,

		// parallelStream - uses ForkJoinPool.common() - Potentially slowing
		// down other resources that are using it (think Tomcat or web app
		// container)

		
		//Quick way to generate ranges of numbers 
		IntStream.range(0, 5); //(inclusive, noninclusive) doesnt include 5
		IntStream.rangeClosed(0, 5); //(inclusive,inclusive) includes 5
		
		//Infinite streams
		
		// Stream.generate() to create infinitely long list
		Random random = new Random();
		Stream<Integer> randomNumbers = Stream.generate(() -> random.nextInt());
		
		// Stream.iterate(seed, UnaryOperation(priorSeedVal)) to work based on a seed. Random above keeps track
		// itself, serving effectively as the seed + mutator
		Stream<Integer> inf = Stream.iterate(0, s -> s + 1);
		
		inf.limit(1);//Would use some sort of short-circuit operation to prevent streaming forever
		
	}

	private static void mapUpgrades() {
		Map<String, String> m = new HashMap<>();

		// Improves old awkward ways of having to check for presence/absence of
		// map items

		if (m.containsKey("newK") == false) {// Shitty old way
			m.put("key", "V");
		}
		// Much better
		m.putIfAbsent("newK", "newValue");

		m.computeIfPresent("newK", (k, v) -> "It was here");
		System.out.println(m.get("newK"));

		// We can remove mapping entries by returning null
		m.computeIfPresent("newK", (k, v) -> null);
		System.out.println("Null removes that value! Is it there? " + m.containsKey("newK"));

		// Like putIfAbsent. Non-null gets inserted
		m.computeIfAbsent("newK", s -> "Complex Work son, new map value inserted for absent key");
		System.out.println(m.get("newK"));

		// Conditional remove. Value in map must == value passed in
		m.remove("newK", "Not gonna erase");
		System.out.println("Still in map due to none match? " + m.get("newK"));

		// Default value on no match!
		m.get("notAValidKey");// Returns null. Ugh.
		String out = m.getOrDefault("notAValidKey", "defaultValue");
		System.out.println("We got a default value. Much nicer than null. " + out);

	}

	private static void optionals() {
		// Optionals (prevent NullPointers by forcing clients to consider
		// possibility of Null values)

		// Instantiation via Factory method
		Optional<String> optional = Optional.of("bam");

		optional.isPresent(); // true
		optional.get(); // "bam"
		// Performs operation if there, otherwise no action taken
		optional.ifPresent((s) -> System.out.println(s.charAt(0))); // 'b'

		// Creation of empty Optional. Nothing returned (would be null in
		// traditional style)
		optional = Optional.empty();
		if (optional.isPresent() == false) {
			String out = optional.orElse("fallback"); // "fallback"
			System.out.println("No optional, using fallback val: " + out);
		}
	}

	private static void functionTypes() {
		// Suppliers. No input. Return T on get() call
		Supplier<Object> createEmptyObject = Object::new;
		System.out.println("Empty object ahoy " + createEmptyObject.get());

		// Consumers take an input, provide no output. accept() call
		Consumer<String> printIt = System.out::println; // Equiv: s->
														// System.out.println(s);
		printIt.accept("I'm a consumer");

	}

	private static void functionBasics() {
		// Simple example. Maps input -> output
		Function<String, String> addPrefix = s -> "thePre_" + s;
		// Use apply(T) to call
		System.out.println("Simple func application " + addPrefix.apply("2"));

		Function<String, String> addPrefix2 = s -> "newHotness_" + s;

		// Function composition
		System.out.print("Composition calls the composed function first, then the current func "
				+ addPrefix.compose(addPrefix2).apply("2"));

		// Andthen()
		System.out.print(
				"Andthen() applies current function, then the other " + addPrefix.compose(addPrefix2).apply("2"));

		// And of course you can save these new functions as desired

		Function<String, String> superPrefix = addPrefix.andThen(addPrefix2);

		// Style can save some typing. All 3 that follow use toInteger
		Function<String, Integer> toInteger = s -> Integer.valueOf(s);

		Function<String, String> moreCludgy = s -> {
			Integer i = toInteger.apply(s);
			return String.valueOf(i);
		};

		Function<String, String> slightlyLessCludgy = s -> String.valueOf(toInteger.apply(s));

		Function<String, String> backToString = toInteger.andThen(String::valueOf);

		// Method reference
		// Supplier contract takes no input, provides output of T
		// Object has no input constructor, fits bill.
		Supplier<Object> createEmptyObject = Object::new;

		// The same result, but slightly less nice
		Supplier<Object> createEmptyObject2 = () -> new Object();
	}

	private static void predicates() {
		// Predicates, construct a test.
		Predicate<String> isSingleChar = s -> s.length() == 1;
		// Get a boolean via test method
		System.out.println(isSingleChar.test("A"));
		// Can get inverse as object
		Predicate<String> isNotSingleChar = isSingleChar.negate();
		isNotSingleChar.test("A");
		// Or use inline
		System.out.println(isSingleChar.negate().test("A"));
		// Can construct ORs and ANDs out of smaller Predicates

		Predicate<String> containsZ = s -> s.toLowerCase().contains("z");
		// AND
		Predicate<String> containsOnlySingleZ = containsZ.and(isSingleChar);
		if (containsOnlySingleZ.test("z"))
			System.out.println("We found a Z");

		if (containsOnlySingleZ.test("AVDD") == false)
			System.out.println("No Z here");
		// OR
		Predicate<String> containsSingleCharOrZ = containsZ.or(isSingleChar);

		if (containsSingleCharOrZ.test("A"))
			System.out.println("We're happy with a single char or multiple containing a Z");
	}
}
