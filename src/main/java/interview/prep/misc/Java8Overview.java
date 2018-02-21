package interview.prep.misc;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.StringJoiner;
import java.util.TreeSet;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.function.BiFunction;
import java.util.function.BinaryOperator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.regex.Pattern;
import java.util.stream.Collector;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import java.util.stream.IntStream;
import java.util.stream.LongStream;
import java.util.stream.Stream;

import org.junit.platform.launcher.listeners.SummaryGeneratingListener;

/**
 * Consolidating various elements as I read different tutorials
 * (http://winterbe.com, JavaDocs)
 * 
 * @author User
 *
 */
public class Java8Overview {
	public static void main(String[] args) {
		threadsAndConcurrency();

		// Can't call default functions from lambdas!
		FuncInterface f = () -> 1; // f.myDef(), myDef(), etc would all fail to
									// compile
		System.out.println(f.myDef());

		// predicates();

		// functionBasics();

		// functionTypes();

		// optionals();

		// streams();

		// reductions();

	}

	private static void threadsAndConcurrency() {
		// Can declare separately
		Runnable r = () -> System.out.println("Triceratops");
		Thread t = new Thread(r);

		// Start() kicks off a separate thread. Run() would run the code in the
		// current thread
		t.start();

		// Or use lambda
		Thread th = new Thread(() -> {
			System.out.println("Dino");
			try {
				Thread.sleep(1000); // Old way of controlling Threads

				TimeUnit.SECONDS.sleep(1); // More convenient Thread methods
											// defined on the TimeUnit class
				// TimeUnit.SECONDS.timedJoin(t, 2);
				// TimeUnit.SECONDS.timedWait(t, 2);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		});

		th.start();

		// Callable (returns value) can't be passed into Thread directly, must
		// use Executor Service
		Callable<Integer> myCallable = () -> -1;

		// Executor Services

		ExecutorService executor = Executors.newSingleThreadExecutor();

		Future<Integer> f3 = executor.submit(myCallable);
		
		Future<?> f = executor.submit(() -> {
			String threadName = Thread.currentThread().getName();
			System.out.println("Hello " + threadName);
		});

		Future<Integer> f2 = executor.submit(() -> -1); // Callable actually
														// returns a value

		f.isDone();

		try {
			Object o = f.get(); // Blocks until task returns
			o = f.get(5, TimeUnit.SECONDS); // Only blocks for timeout. Throws
											// TimeoutException if not ready by
											// that
											// time (because how would it
											// proceed without a value you are
											// saying is required?)
		} catch (InterruptedException | ExecutionException | TimeoutException e) {
			e.printStackTrace();
		}

		try {
			System.out.println("attempt to shutdown executor");
			executor.shutdown(); // Soft shutdown, no new tasks accepted,
									// doesn't wait on tasks to finish
			executor.awaitTermination(5, TimeUnit.SECONDS);// Alternative.
															// Blocks until all
															// tasks finish or
															// timeout ends
		} catch (InterruptedException e) {
			System.err.println("tasks interrupted");
		} finally {
			if (!executor.isTerminated()) {
				System.err.println("cancel non-finished tasks");
			}
			executor.shutdownNow(); // Hard shutdown, no waiting on tasks
			System.out.println("shutdown finished");
		}

	}

	private static void streams() {
		// Defined on all collections, but not Maps (can get streams of Keyset
		// or
		// object set)
		Map<String, String> m = new HashMap<>();
		m.values().stream();
		m.keySet().stream();

		// Disappointing, but big upgrades to Maps
		mapUpgrades();

		// STRING STREAMING isn't exactly straightforward
		String abc = "abc";
		abc.chars() // intStream at this point (int values of chars)
				.mapToObj(s -> String.valueOf((char) s)); // cast to char, get
															// Strings from

		// CREATING

		// To stream arrays
		int[] i = { 1, 2, 3 };
		Arrays.stream(i);

		// Could also take it -> List -> Stream
		List<Integer> l = Arrays.asList(1, 2, 3);
		l.stream();

		// Or Skip creating Collection
		Stream.of(1, 2, 3); // Integer Stream
		Stream.of(new int[] { 1, 2 }, new int[] { 3, 4 });// Stream of int[]s

		// Primitive Streams defined as well (int, long, double)

		// Quick way to generate ranges of numbers
		IntStream.range(0, 5); // (inclusive, non-inclusive) doesn't include 5
		IntStream.rangeClosed(0, 5); // (inclusive,inclusive) includes 5
		// Other have similar
		LongStream.range(0, 1);
		// DoubleStream does not (has generate() and iterate() though)

		// Can used boxed() to get Wrapper objects
		IntStream.range(0, 1).boxed(); // Stream<Integers>

		// Also mapToObj() if going to obj type. (map() is expected to return
		// primitive type)
		IntStream.range(0, 1).mapToObj(s -> "A" + s); // Prefix with "A", now
														// have Stream<String>

		// Can also move between primitive stream types
		IntStream.range(0, 1).mapToDouble(s -> s + .2); // DoubleStream now.
														// (IntStream can't have
														// decimal #s)

		// ---INFINITE STREAMS

		// Stream.generate() to create infinitely long list
		Random random = new Random();
		Stream<Integer> randomNumbers = Stream.generate(() -> random.nextInt());

		// Stream.iterate(seed, UnaryOperation(priorSeedVal)) to work based on a
		// seed. Random above keeps track
		// itself, serving effectively as the seed + mutator
		Stream<Integer> inf = Stream.iterate(0, s -> s + 1);

		// -----LIMITING - Would use some sort of SHORT-CIRCUIT operation to
		// prevent streaming forever

		inf.limit(1);

		// Get an arbitrary value
		Optional<Integer> any = Stream.iterate(0, s -> s + 1).findAny();
		// Get first found value (if non-ordered Stream same as arbitrary)
		Optional<Integer> first = Stream.iterate(0, s -> s + 1).findFirst();

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

		avoidingIllegalState();

		reductions();
		collectMethod();

		// Flatmap flattens (removes a level of nesting), returns 0 or many
		// Streams
		List<Integer> together = Stream.of(Arrays.asList(1, 2), Arrays.asList(3, 4)) // Stream
																						// of
																						// List<Integer>
				.flatMap(List::stream).map(integer -> integer + 1).collect(Collectors.toList());

		// Matchers (terminal), act to short-circuit Stream when possible
		boolean b = IntStream.range(0, 1).anyMatch(s -> s == 0); // There exists
																	// >= 1
																	// element
																	// satisfying
																	// predicate
		// allMatch
		// noneMatch

		// Sum, count, avg, min, max
		IntSummaryStatistics sg = IntStream.range(0, 1).summaryStatistics();

		//---Regex Stream
		Pattern.compile(":")
	    .splitAsStream("foobar:foo:bar")
	    .filter(s -> s.contains("bar"))
	    .sorted()
	    .collect(Collectors.joining(":"));// => bar:foobar
		
		//Predicate usage in Stream pipeline
		
		Pattern pattern = Pattern.compile(".*@gmail\\.com");
		Stream.of("bob@gmail.com", "alice@hotmail.com")
		    .filter(pattern.asPredicate())
		    .count();
		// => 1
		
		//File I/O TODO look at newer APIs (java.nio.file)
		//use Try-With resources to wrap 
		try (Stream<String> stream = Files.lines(Paths.get("res/nashorn1.js"))) {
			//Normal Stream I/O
		    stream
		        .filter(line -> line.contains("print"))
		        .map(String::trim)
		        .forEach(System.out::println);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		Path start = Paths.get("");
		int maxDepth = 5;
		try (Stream<Path> stream = Files.walk(start, maxDepth)) {
		    String joined = stream
		        .map(String::valueOf)
		        .filter(path -> path.endsWith(".js"))
		        .sorted()
		        .collect(Collectors.joining("; "));
		    System.out.println("walk(): " + joined);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		// ORDERING matters

		// When possible arrange Stream ops so as much Filtering can happen
		// early so that fewer Map ops are called
		// on things that will later be filtered

		terribleOrdering();
	}

	// TODO Continue work from here

	private static void reductions() {

		// Note expected Reduce to take a BiFunction (function with 2 input of
		// same type) actually takes a BinaryOperator (restrains output to be
		// same type as well)
		BiFunction<Person, Person, Person> max2Unapplicable = (a, b) -> a.age > b.age ? a : b;

		BinaryOperator<Person> max = (a, b) -> a.age > b.age ? a : b;

		// 3 Reduce Signatures
		// ---SIGNATURE 1: BinaryOperator
		Optional<Person> out = persons.stream().reduce(max);
		System.out.println(out);

		// ---SIGNATURE 2: IdentityValue (Default) and BinaryOperator
		// Doesn't return Optional as we are guaranteed IdentityValue regardless
		// of Stream
		Person pOut = persons.stream().reduce(new Person("Baby", 0), max);
		System.out.println(pOut); // Same output as above
		List<Person> empty = Arrays.asList();
		pOut = empty.stream().reduce(new Person("Baby", 0), max);
		System.out.println(pOut); // Baby default

		// ---SIGNATURE 3: IdentityValue (Default) and BiFunction Accumulator
		// and BinaryOperator
		// Combiner (only called in Parallelized processing)
		// NOTE: Identity value defines what BiFunction can return (0 auto-boxed
		// to Integer)
		Integer ageSum = persons.stream().reduce(0, (sum, p) -> sum += p.age, (sum1, sum2) -> sum1 + sum2);

		// Cannot perform sum as below because BiFunction has different
		// expectation for output type (Person vs Integer)
		// persons.stream().reduce(new Person("Baby", 0), (a, b) -> a.age +
		// b.age, (a, b) -> a + b);

	}

	private static void avoidingIllegalState() {
		// Stream reuse not allowed. IllegalStateException

		// Illegal
		Stream<Integer> inf = Stream.iterate(0, s -> s + 1);
		inf.map(s -> s * 2); // Perform intermediate op on starting Stream
		try {
			inf.limit(1); // Perform another op on starting
		} catch (IllegalStateException e) {
			// Throws exception. Stream is in a different state.
			// Can't apply two ops to beginning state.
			// Terminal op would also result in this

			// Should recreate Stream

			// Legal example
			inf = Stream.iterate(0, s -> s + 1);
			inf = inf.map(s -> s * 2);
			inf.limit(1); // Legal, we captured the updated Stream

			// Nice paradigm, create Supplier
			Supplier<Stream<String>> streamSupplier = () -> Stream.of("d2", "a2", "b1", "b3", "c")
					.filter(s -> s.startsWith("a"));

			streamSupplier.get().anyMatch(s -> true); // OK
			streamSupplier.get().noneMatch(s -> true); // OK
		}
	}

	// Collect --- Collectors
	private static void collectMethod() {

		// Collect accepts a Collector which consists of four different
		// operations: a supplier, an accumulator, a combiner and a finisher.

		List<Integer> intList = Stream.of(1, 2, 3).collect(Collectors.toList());

		// <Age, List<PersonsWhoAreThatAge>>
		Map<Integer, List<Person>> pplByAge = persons.stream().collect(Collectors.groupingBy(p -> p.age));

		// Collectors.joining(delimiter, prefix, suffix)

		String phrase = persons.stream().filter(p -> p.age >= 18).map(p -> p.name)
				.collect(Collectors.joining(" and ", "In Germany ", " are of legal age."));

		// phase -> In Germany Max and Peter and Pamela are of legal age.

		// BREAKDOWN
		/*
		 * public static Collector<CharSequence, ?, String> joining(CharSequence
		 * delimiter, CharSequence prefix, CharSequence suffix) { return new
		 * CollectorImpl<>( () -> new StringJoiner(delimiter, prefix, suffix),
		 * StringJoiner::add, StringJoiner::merge, StringJoiner::toString,
		 * CH_NOID); }
		 */
		// Supplier - () -> new StringJoiner(delimiter, prefix, suffix)
		// Accumulator - StringJoiner::add
		// Combiner - StringJoiner::merge
		// Finisher - StringJoiner::toString

		// By defining in this way with associative, stateless operations we
		// allow Java to break up work potentially using Fork-Join Framework for
		// parallelizing. Divide and Conquer. Each subunit of work gets a
		// Container via the supplier. Does its work via the Accumulator.
		// Returns its final subresult. That is combined with others via the
		// Combiner. If final work has to be done post all subresults combined
		// that is specified in the Finisher

		// Collectors.toCollection(Supplier<T extends Collection>)
		// Super easy, just provide a constructor
		Stream.iterate(0, s -> s + 1).limit(5).collect(Collectors.toCollection(TreeSet::new));

		// Note Map is NOT a Collection Cannot be used with
		// Collectors.toCollection()
		Map<Integer, String> map = persons.stream()
				.collect(Collectors.toMap(p -> p.age, p -> p.name, (name1, name2) -> name1 + ";" + name2));
		// KeyMapper, ValueMapper, MergeFunction(2 identical keys, how to merge
		// values? In this case add ; delimiter

		// EXPLICIT AGAIN:
		// <T,A,R> T-> Input Type (Person), A -> Accumulator (StringJoiner),
		// R->Result (String)
		Collector<Person, StringJoiner, String> personNameCollector = Collector.of(() -> new StringJoiner(" | "), // supplier
				(j, p) -> j.add(p.name.toUpperCase()), // accumulator
				(j1, j2) -> j1.merge(j2), // combiner
				StringJoiner::toString); // finisher
	}

	private static void terribleOrdering() {
		// We sort at cost n * log(n), map n eles, then filter
		Stream.of("d2", "a2", "b1", "b3", "c").sorted((s1, s2) -> {
			System.out.printf("sort: %s; %s\n", s1, s2);
			return s1.compareTo(s2);
		}).map(s -> {
			System.out.println("map: " + s);
			return s.toUpperCase();
		}).filter(s -> {
			System.out.println("filter: " + s);
			return s.startsWith("a");
		})

				.forEach(s -> System.out.println("forEach: " + s));

		// Identical output. Filter n eles, end up sorting only 1, mapping only
		// 1.
		Stream.of("d2", "a2", "b1", "b3", "c").filter(s -> {
			System.out.println("filter: " + s);
			return s.startsWith("a");
		}).sorted((s1, s2) -> {
			System.out.printf("sort: %s; %s\n", s1, s2);
			return s1.compareTo(s2);
		}).map(s -> {
			System.out.println("map: " + s);
			return s.toUpperCase();
		})

				.forEach(s -> System.out.println("forEach: " + s));

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

		// CREATION

		// Instantiation via Factory method
		Optional<String> bamOpt = Optional.of("bam");

		// Wrapping without having to check for null.
		Integer anInt = null;
		try {
			Optional<Integer> npeTroubleOpt = Optional.of(anInt);
		} catch (NullPointerException e) {
			System.out.println("NPE: Should have used ofNullable(anInt); instead");
		}

		Optional<Integer> isEmptyOpt = Optional.ofNullable(anInt);

		anInt = 1;
		Optional<Integer> nonEmptyOpt = Optional.ofNullable(anInt);

		Optional<String> empty = Optional.empty();

		// STANDARD OPERATIONS

		System.out.println(isEmptyOpt.isPresent()); // False;
		System.out.println(nonEmptyOpt.isPresent() + " " + nonEmptyOpt.get()); // True
																				// 1;

		bamOpt.isPresent(); // true
		bamOpt.get(); // "bam"
		// Performs operation if there, otherwise no action taken
		bamOpt.ifPresent((s) -> System.out.println(s.charAt(0))); // 'b'

		// Creation of empty Optional. Nothing returned (would be null in
		// traditional style)
		if (empty.isPresent() == false) {
			String out = empty.orElse("fallback"); // "fallback" - Could have
													// just directly used
													// orElse()
			System.out.println("No optional, using fallback val: " + out);
		}

		// STREAM Ops supported
		// Note returns Optional, not a Stream.
		// TODO: Check probably also not lazily evaluated
		Optional<String> filterOpt = bamOpt.filter(s -> s.contains("You"));
		Optional<Integer> mappedOpt = filterOpt.map(s -> 1);

		// NICE PARADIGM, null check not needed!
		// Old shitty way
		Outer outer = new Outer();
		if (outer != null && outer.nested != null && outer.nested.inner != null) {
			System.out.println(outer.nested.inner.foo);
		}
		// New shiny!
		Optional.of(new Outer()).flatMap(o -> Optional.ofNullable(o.nested))// Each
																			// flatMap
																			// only
																			// occurs
																			// if
																			// input
																			// is
																			// nonnull
				.flatMap(n -> Optional.ofNullable(n.inner)).flatMap(i -> Optional.ofNullable(i.foo))
				.ifPresent(System.out::println);

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

	static class Person {
		String name;
		int age;

		Person(String name, int age) {
			this.name = name;
			this.age = age;
		}

		@Override
		public String toString() {
			return name;
		}
	}

	static List<Person> persons = Arrays.asList(new Person("Max", 18), new Person("Peter", 23),
			new Person("Pamela", 23), new Person("David", 12));

	static class Outer {
		Nested nested;
	}

	static class Nested {
		Inner inner;
	}

	static class Inner {
		String foo;
	}

}
