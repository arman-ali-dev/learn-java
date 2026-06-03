# Java 8+ Features - Complete Notes

---

## PART 1 - FUNCTIONAL INTERFACES

### What is a Functional Interface

A functional interface is an interface that has exactly ONE abstract method. That is the only rule.

It can have any number of default methods and static methods. But abstract methods - only one.

Why does this matter? Because Java 8 introduced lambdas, and a lambda is just a short way to implement a functional interface. If an interface has only one method, Java knows exactly which method the lambda is implementing.

```java
// This is a functional interface - one abstract method
interface Greeting {
    void greet(String name); // only one abstract method
}

// This is also a functional interface
interface Calculator {
    int calculate(int a, int b);
}

// This is NOT a functional interface - two abstract methods
interface NotFunctional {
    void methodOne();
    void methodTwo(); // two abstract methods - cannot use lambda
}
```

### @FunctionalInterface annotation

You can add this annotation to tell the compiler this interface should be functional. If you accidentally add a second abstract method, compiler will give an error.

```java
@FunctionalInterface
interface Greeting {
    void greet(String name);

    // default methods are fine - not abstract
    default void greetLoudly(String name) {
        greet(name.toUpperCase());
    }

    // static methods are fine too
    static Greeting createDefault() {
        return name -> System.out.println("Hello, " + name);
    }
}
```

### Built-in Functional Interfaces in Java 8

Java 8 added a whole package of ready-made functional interfaces in java.util.function. You do not need to create your own for common use cases.

The four most important ones:

**Predicate<T>** - takes one input, returns boolean. Used for testing/filtering.

```java
// abstract method: boolean test(T t)
Predicate<String> isLong = s -> s.length() > 5;
System.out.println(isLong.test("Arman"));  // false
System.out.println(isLong.test("Priya Sharma")); // true

Predicate<Integer> isEven = n -> n % 2 == 0;
Predicate<Integer> isPositive = n -> n > 0;

// Combine predicates
Predicate<Integer> isEvenAndPositive = isEven.and(isPositive);
Predicate<Integer> isEvenOrPositive = isEven.or(isPositive);
Predicate<Integer> isNotEven = isEven.negate();

System.out.println(isEvenAndPositive.test(4));  // true
System.out.println(isEvenAndPositive.test(-4)); // false
System.out.println(isEvenOrPositive.test(3));   // true
```

**Function<T, R>** - takes one input of type T, returns output of type R. Used for transforming.

```java
// abstract method: R apply(T t)
Function<String, Integer> getLength = s -> s.length();
System.out.println(getLength.apply("Arman")); // 5

Function<String, String> toUpper = s -> s.toUpperCase();
Function<String, String> addGreeting = s -> "Hello, " + s;

// Chain functions
Function<String, String> combined = toUpper.andThen(addGreeting);
System.out.println(combined.apply("arman")); // Hello, ARMAN

// compose - opposite order of andThen
Function<String, String> composed = toUpper.compose(addGreeting);
System.out.println(composed.apply("arman")); // HELLO, ARMAN
```

**Consumer<T>** - takes one input, returns nothing. Used for performing actions.

```java
// abstract method: void accept(T t)
Consumer<String> print = s -> System.out.println(s);
Consumer<String> printUpper = s -> System.out.println(s.toUpperCase());

print.accept("Arman"); // Arman

// Chain consumers
Consumer<String> printBoth = print.andThen(printUpper);
printBoth.accept("arman");
// arman
// ARMAN
```

**Supplier<T>** - takes no input, returns a value. Used for providing/generating values.

```java
// abstract method: T get()
Supplier<String> greeting = () -> "Hello World";
System.out.println(greeting.get()); // Hello World

Supplier<Double> randomValue = () -> Math.random();
System.out.println(randomValue.get()); // some random number

Supplier<List<String>> listFactory = () -> new ArrayList<>();
List<String> newList = listFactory.get(); // creates a new ArrayList
```

### Other useful functional interfaces

```java
// BiFunction<T, U, R> - two inputs, one output
BiFunction<String, Integer, String> repeat = (s, n) -> s.repeat(n);
System.out.println(repeat.apply("Ha", 3)); // HaHaHa

// BiPredicate<T, U> - two inputs, boolean output
BiPredicate<String, Integer> longerThan = (s, n) -> s.length() > n;
System.out.println(longerThan.test("Arman", 3)); // true

// BiConsumer<T, U> - two inputs, no output
BiConsumer<String, Integer> printRepeat = (s, n) -> System.out.println(s.repeat(n));
printRepeat.accept("Hi", 3); // HiHiHi

// UnaryOperator<T> - input and output are same type (special Function)
UnaryOperator<String> trim = s -> s.trim();
UnaryOperator<Integer> square = n -> n * n;

// BinaryOperator<T> - two inputs and output all same type (special BiFunction)
BinaryOperator<Integer> add = (a, b) -> a + b;
BinaryOperator<String> concat = (a, b) -> a + b;
```

---

## PART 2 - LAMBDA EXPRESSIONS

### What is a Lambda

Before Java 8, if you wanted to pass behavior (a method) to another method, you had to create an anonymous class. It was very verbose.

```java
// Before Java 8 - anonymous class
Runnable r = new Runnable() {
    @Override
    public void run() {
        System.out.println("Running");
    }
};

// Java 8 - lambda
Runnable r = () -> System.out.println("Running");
```

A lambda is a short way to write the implementation of a functional interface. Since the interface has only one method, Java knows which method you are implementing.

### Lambda syntax

```java
// Full syntax
(parameter1, parameter2) -> { statements; return value; }

// If only one statement and it is the return value - no braces, no return keyword
(parameter1, parameter2) -> expression

// If only one parameter - no parentheses needed
parameter -> expression

// If no parameters - empty parentheses
() -> expression
```

### Lambda examples

```java
// No parameters, no return
Runnable r = () -> System.out.println("Hello");

// One parameter, no return
Consumer<String> print = name -> System.out.println("Hi " + name);

// One parameter, returns value
Function<String, Integer> length = s -> s.length();

// Two parameters, returns value
BinaryOperator<Integer> add = (a, b) -> a + b;

// Multiple statements - need braces and explicit return
Function<Integer, String> classify = n -> {
    if (n > 0) return "positive";
    else if (n < 0) return "negative";
    else return "zero";
};

// Using with your own functional interface
@FunctionalInterface
interface StringTransformer {
    String transform(String input);
}

StringTransformer upper = s -> s.toUpperCase();
StringTransformer reverse = s -> new StringBuilder(s).reverse().toString();
StringTransformer exclaim = s -> s + "!";

System.out.println(upper.transform("arman"));   // ARMAN
System.out.println(reverse.transform("arman")); // namrA
System.out.println(exclaim.transform("arman")); // arman!
```

### Lambda captures variables

A lambda can use variables from the surrounding scope. But those variables must be effectively final - meaning they cannot be changed after being used in the lambda.

```java
String prefix = "Hello";
Consumer<String> greet = name -> System.out.println(prefix + " " + name);
greet.accept("Arman"); // Hello Arman

// prefix = "Hi"; // COMPILE ERROR - prefix is used in lambda, cannot change it

// This works - prefix is never changed
String greeting = "Namaste";
Consumer<String> namasteGreet = name -> System.out.println(greeting + " " + name);
```

---

## PART 3 - METHOD REFERENCES

### What is a Method Reference

A method reference is an even shorter way to write a lambda when the lambda just calls an existing method.

Instead of: `s -> s.toUpperCase()`
You write: `String::toUpperCase`

Both do the same thing. Method reference is just cleaner.

Syntax: `ClassName::methodName` or `object::methodName`

### Four types of method references

**Type 1 - Static method reference**

When the lambda calls a static method.

```java
// Lambda
Function<String, Integer> parse = s -> Integer.parseInt(s);

// Method reference - same thing
Function<String, Integer> parse = Integer::parseInt;

System.out.println(parse.apply("42")); // 42

// More examples
Consumer<String> print = s -> System.out.println(s);
Consumer<String> print = System.out::println; // System.out is an object, println is its method

Function<Double, Double> abs = d -> Math.abs(d);
Function<Double, Double> abs = Math::abs;
```

**Type 2 - Instance method reference on a specific object**

When the lambda calls an instance method on a specific known object.

```java
String prefix = "Hello ";
Function<String, String> addPrefix = name -> prefix.concat(name);
Function<String, String> addPrefix = prefix::concat; // prefix is the specific object

// Another example
List<String> names = new ArrayList<>();
Consumer<String> addToList = name -> names.add(name);
Consumer<String> addToList = names::add;
```

**Type 3 - Instance method reference on a parameter**

When the lambda calls an instance method on its own parameter.

```java
// Lambda - calls toUpperCase on the parameter s
Function<String, String> upper = s -> s.toUpperCase();

// Method reference - String is the type, toUpperCase is called on the instance
Function<String, String> upper = String::toUpperCase;

System.out.println(upper.apply("arman")); // ARMAN

// More examples
Function<String, Integer> length = s -> s.length();
Function<String, Integer> length = String::length;

Predicate<String> isEmpty = s -> s.isEmpty();
Predicate<String> isEmpty = String::isEmpty;
```

**Type 4 - Constructor reference**

When the lambda creates a new object.

```java
// Lambda
Supplier<ArrayList> createList = () -> new ArrayList();
Function<String, StringBuilder> createSB = s -> new StringBuilder(s);

// Constructor reference
Supplier<ArrayList> createList = ArrayList::new;
Function<String, StringBuilder> createSB = StringBuilder::new;

ArrayList list = createList.get();
StringBuilder sb = createSB.apply("hello");
```

### Method references in real use

```java
List<String> names = Arrays.asList("Arman", "priya", "RAJ", "sneha");

// Lambda versions
names.stream().map(s -> s.toUpperCase()).forEach(s -> System.out.println(s));

// Method reference versions - cleaner
names.stream().map(String::toUpperCase).forEach(System.out::println);

// Sorting with method reference
List<String> sorted = names.stream()
    .sorted(String::compareToIgnoreCase) // instead of (a, b) -> a.compareToIgnoreCase(b)
    .collect(Collectors.toList());
```

---

## PART 4 - DEFAULT AND STATIC METHODS IN INTERFACES

### Why default methods were added

Before Java 8, if you added a new method to an interface, every single class that implemented that interface had to implement the new method. This would break all existing code.

Default methods solve this. A default method has a body inside the interface. Classes that implement the interface get this method for free. They can use it as is or override it if they want.

```java
interface Vehicle {
    // abstract - must implement
    String getBrand();
    int getSpeed();

    // default - optional to override, has implementation
    default String describe() {
        return "Brand: " + getBrand() + ", Speed: " + getSpeed() + " km/h";
    }

    default void printInfo() {
        System.out.println(describe());
    }
}

class Car implements Vehicle {
    private String brand;
    private int speed;

    Car(String brand, int speed) {
        this.brand = brand;
        this.speed = speed;
    }

    @Override
    public String getBrand() { return brand; }

    @Override
    public int getSpeed() { return speed; }

    // describe() and printInfo() are inherited for free
    // or we can override describe() if we want
}

class SportsCar extends Car {
    SportsCar(String brand, int speed) {
        super(brand, speed);
    }

    @Override
    public String describe() {
        // overriding default method
        return "SPORTS - " + super.describe();
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car("Toyota", 120);
        car.printInfo(); // Brand: Toyota, Speed: 120 km/h

        SportsCar sc = new SportsCar("Ferrari", 300);
        sc.printInfo(); // SPORTS - Brand: Ferrari, Speed: 300 km/h
    }
}
```

### Default method conflict - diamond problem

If a class implements two interfaces that both have a default method with the same name, the class must override it to resolve the conflict.

```java
interface A {
    default void show() {
        System.out.println("A's show");
    }
}

interface B {
    default void show() {
        System.out.println("B's show");
    }
}

class C implements A, B {
    // MUST override show() - compiler forces you
    @Override
    public void show() {
        A.super.show(); // can call specific interface's version
        B.super.show();
        System.out.println("C's show");
    }
}
```

### Static methods in interfaces

Static methods in interfaces belong to the interface itself. They cannot be overridden by implementing classes. You call them using the interface name.

```java
interface MathOperations {
    int calculate(int a, int b);

    // static method - utility related to this interface
    static MathOperations add() {
        return (a, b) -> a + b;
    }

    static MathOperations multiply() {
        return (a, b) -> a * b;
    }

    static boolean isValidInput(int a, int b) {
        return a >= 0 && b >= 0;
    }
}

public class Main {
    public static void main(String[] args) {
        MathOperations adder = MathOperations.add();       // called on interface
        MathOperations multiplier = MathOperations.multiply();

        System.out.println(adder.calculate(5, 3));      // 8
        System.out.println(multiplier.calculate(5, 3)); // 15
        System.out.println(MathOperations.isValidInput(5, 3)); // true
    }
}
```

### default vs static in interfaces

```
default method                   |  static method
---------------------------------|---------------------------------
Called on an instance            |  Called on the interface name
Can be overridden                |  Cannot be overridden
Has access to instance methods   |  No access to instance methods
Inherited by implementing class  |  Not inherited
Useful for: adding new behavior  |  Useful for: utility/factory methods
```

---

## PART 5 - STREAM API

### What is a Stream

A Stream is a sequence of elements that you can process with operations. It is NOT a data structure - it does not store data. It processes data from a source (like a List or array) and produces a result.

Think of it like a pipeline. Data flows in from one end, passes through various operations, and comes out the other end as a result.

Key points:

- Streams do not modify the original collection
- A stream can only be used once - after a terminal operation it is closed
- Operations are lazy - they run only when a terminal operation is called

```
Source -> Intermediate Operations -> Terminal Operation
List   ->  filter, map, sorted   ->  collect, forEach, count
```

### Creating a Stream

```java
import java.util.stream.*;

List<String> names = Arrays.asList("Arman", "Priya", "Raj", "Sneha");

// From a List
Stream<String> s1 = names.stream();

// Parallel stream - uses multiple threads internally
Stream<String> s2 = names.parallelStream();

// From array
String[] arr = {"A", "B", "C"};
Stream<String> s3 = Arrays.stream(arr);

// Stream.of()
Stream<String> s4 = Stream.of("X", "Y", "Z");

// Stream.empty()
Stream<String> empty = Stream.empty();

// Infinite stream - generate
Stream<Double> randoms = Stream.generate(Math::random);

// Infinite stream - iterate
Stream<Integer> numbers = Stream.iterate(0, n -> n + 2); // 0, 2, 4, 6, ...

// IntStream, LongStream, DoubleStream - for primitives
IntStream intStream = IntStream.range(1, 6);    // 1, 2, 3, 4, 5
IntStream intStream2 = IntStream.rangeClosed(1, 5); // 1, 2, 3, 4, 5
```

### Intermediate Operations

These return a new Stream. They are lazy - they do not execute until a terminal operation is called.

**filter() - keep elements that match a condition**

```java
List<String> names = Arrays.asList("Arman", "Priya", "Raj", "Sneha", "Rohan");

List<String> longNames = names.stream()
    .filter(name -> name.length() > 4) // keep names longer than 4 characters
    .collect(Collectors.toList());

System.out.println(longNames); // [Arman, Priya, Sneha, Rohan]
```

**map() - transform each element**

```java
List<String> names = Arrays.asList("arman", "priya", "raj");

List<String> upperNames = names.stream()
    .map(String::toUpperCase)
    .collect(Collectors.toList());

System.out.println(upperNames); // [ARMAN, PRIYA, RAJ]

// map to different type
List<Integer> lengths = names.stream()
    .map(String::length)
    .collect(Collectors.toList());

System.out.println(lengths); // [5, 5, 3]
```

**flatMap() - flatten nested collections**

Use when each element maps to multiple elements and you want one flat stream.

```java
List<List<Integer>> nested = Arrays.asList(
    Arrays.asList(1, 2, 3),
    Arrays.asList(4, 5),
    Arrays.asList(6, 7, 8, 9)
);

List<Integer> flat = nested.stream()
    .flatMap(List::stream) // each list becomes a stream, then all merged
    .collect(Collectors.toList());

System.out.println(flat); // [1, 2, 3, 4, 5, 6, 7, 8, 9]

// Another example - split sentences into words
List<String> sentences = Arrays.asList("Hello World", "Java is great");

List<String> words = sentences.stream()
    .flatMap(sentence -> Arrays.stream(sentence.split(" ")))
    .collect(Collectors.toList());

System.out.println(words); // [Hello, World, Java, is, great]
```

**sorted() - sort elements**

```java
List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9, 3);

// Natural order
List<Integer> sorted = numbers.stream()
    .sorted()
    .collect(Collectors.toList());
System.out.println(sorted); // [1, 2, 3, 5, 8, 9]

// Custom order
List<String> names = Arrays.asList("Arman", "Priya", "Raj", "Sneha");
List<String> byLength = names.stream()
    .sorted(Comparator.comparingInt(String::length))
    .collect(Collectors.toList());
System.out.println(byLength); // [Raj, Arman, Priya, Sneha]

// Descending
List<Integer> desc = numbers.stream()
    .sorted(Comparator.reverseOrder())
    .collect(Collectors.toList());
System.out.println(desc); // [9, 8, 5, 3, 2, 1]
```

**distinct() - remove duplicates**

```java
List<Integer> nums = Arrays.asList(1, 2, 2, 3, 3, 3, 4);
List<Integer> unique = nums.stream()
    .distinct()
    .collect(Collectors.toList());
System.out.println(unique); // [1, 2, 3, 4]
```

**limit() and skip()**

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5, 6, 7, 8, 9, 10);

// take only first 3
List<Integer> first3 = numbers.stream()
    .limit(3)
    .collect(Collectors.toList());
System.out.println(first3); // [1, 2, 3]

// skip first 3, take rest
List<Integer> after3 = numbers.stream()
    .skip(3)
    .collect(Collectors.toList());
System.out.println(after3); // [4, 5, 6, 7, 8, 9, 10]

// skip 2, take next 3
List<Integer> middle = numbers.stream()
    .skip(2)
    .limit(3)
    .collect(Collectors.toList());
System.out.println(middle); // [3, 4, 5]
```

**peek() - look at elements without changing them**

Useful for debugging. Like forEach but returns a Stream.

```java
List<String> result = names.stream()
    .peek(n -> System.out.println("Before filter: " + n))
    .filter(n -> n.length() > 3)
    .peek(n -> System.out.println("After filter: " + n))
    .collect(Collectors.toList());
```

### Terminal Operations

These trigger the pipeline to execute and produce a final result. After calling a terminal operation, the stream is closed.

**forEach() - perform action on each element**

```java
List<String> names = Arrays.asList("Arman", "Priya", "Raj");

names.stream().forEach(System.out::println);
// Arman
// Priya
// Raj
```

**collect() - gather results into a collection**

```java
List<String> names = Arrays.asList("Arman", "Priya", "Raj", "Arman");

// To List
List<String> list = names.stream().collect(Collectors.toList());

// To Set - removes duplicates
Set<String> set = names.stream().collect(Collectors.toSet());

// To specific collection
LinkedList<String> linkedList = names.stream()
    .collect(Collectors.toCollection(LinkedList::new));

// To Map
Map<String, Integer> nameLengths = names.stream()
    .distinct()
    .collect(Collectors.toMap(
        name -> name,       // key
        String::length      // value
    ));
System.out.println(nameLengths); // {Arman=5, Priya=5, Raj=3}

// Joining strings
String joined = names.stream()
    .collect(Collectors.joining(", ")); // separator
System.out.println(joined); // Arman, Priya, Raj, Arman

String joinedWithBrackets = names.stream()
    .collect(Collectors.joining(", ", "[", "]")); // separator, prefix, suffix
System.out.println(joinedWithBrackets); // [Arman, Priya, Raj, Arman]
```

**count() - count elements**

```java
long count = names.stream()
    .filter(n -> n.length() > 3)
    .count();
System.out.println(count); // 2
```

**min() and max()**

```java
List<Integer> numbers = Arrays.asList(5, 2, 8, 1, 9);

Optional<Integer> max = numbers.stream().max(Comparator.naturalOrder());
Optional<Integer> min = numbers.stream().min(Comparator.naturalOrder());

System.out.println(max.get()); // 9
System.out.println(min.get()); // 1

// With objects
List<String> names = Arrays.asList("Arman", "Priya", "Raj");
Optional<String> longest = names.stream().max(Comparator.comparingInt(String::length));
System.out.println(longest.get()); // Arman or Priya (both length 5)
```

**findFirst() and findAny()**

```java
// findFirst - returns first element matching condition
Optional<String> first = names.stream()
    .filter(n -> n.startsWith("P"))
    .findFirst();

System.out.println(first.get()); // Priya

// findAny - returns any element (faster in parallel streams)
Optional<String> any = names.parallelStream()
    .filter(n -> n.length() > 3)
    .findAny();
```

**anyMatch(), allMatch(), noneMatch()**

```java
List<Integer> numbers = Arrays.asList(2, 4, 6, 8, 10);

boolean anyOdd = numbers.stream().anyMatch(n -> n % 2 != 0);
System.out.println(anyOdd); // false

boolean allEven = numbers.stream().allMatch(n -> n % 2 == 0);
System.out.println(allEven); // true

boolean noneNegative = numbers.stream().noneMatch(n -> n < 0);
System.out.println(noneNegative); // true
```

**reduce() - combine all elements into one**

```java
List<Integer> numbers = Arrays.asList(1, 2, 3, 4, 5);

// Sum all elements
Optional<Integer> sum = numbers.stream().reduce((a, b) -> a + b);
System.out.println(sum.get()); // 15

// With identity value (starting value) - no Optional needed
int sumWithIdentity = numbers.stream().reduce(0, (a, b) -> a + b);
System.out.println(sumWithIdentity); // 15

// Product
int product = numbers.stream().reduce(1, (a, b) -> a * b);
System.out.println(product); // 120

// Longest string
List<String> names = Arrays.asList("Arman", "Priya", "Raj");
Optional<String> longest = names.stream()
    .reduce((a, b) -> a.length() >= b.length() ? a : b);
System.out.println(longest.get()); // Arman or Priya
```

**toArray()**

```java
String[] arr = names.stream().toArray(String[]::new);
```

### Collectors - grouping and partitioning

```java
List<String> names = Arrays.asList("Arman", "Priya", "Raj", "Rohan", "Preethi");

// Group by first character
Map<Character, List<String>> grouped = names.stream()
    .collect(Collectors.groupingBy(name -> name.charAt(0)));
System.out.println(grouped);
// {A=[Arman], P=[Priya, Preethi], R=[Raj, Rohan]}

// Group by length
Map<Integer, List<String>> byLength = names.stream()
    .collect(Collectors.groupingBy(String::length));

// Count in each group
Map<Character, Long> countByFirstChar = names.stream()
    .collect(Collectors.groupingBy(
        name -> name.charAt(0),
        Collectors.counting()
    ));
System.out.println(countByFirstChar); // {A=1, P=2, R=2}

// Partition into two groups - true and false
Map<Boolean, List<String>> partitioned = names.stream()
    .collect(Collectors.partitioningBy(name -> name.length() > 4));
System.out.println(partitioned.get(true));  // [Arman, Priya, Rohan, Preethi]
System.out.println(partitioned.get(false)); // [Raj]

// Statistics
IntSummaryStatistics stats = names.stream()
    .collect(Collectors.summarizingInt(String::length));
System.out.println(stats.getMin());     // 3
System.out.println(stats.getMax());     // 7
System.out.println(stats.getAverage()); // average length
System.out.println(stats.getSum());     // total characters
System.out.println(stats.getCount());   // 5
```

### Chaining multiple operations

```java
List<Student> students = Arrays.asList(
    new Student("Arman", 85),
    new Student("Priya", 92),
    new Student("Raj", 71),
    new Student("Sneha", 88),
    new Student("Rohan", 65)
);

// Get names of students who scored above 80, sorted by marks descending
List<String> topStudents = students.stream()
    .filter(s -> s.marks > 80)           // keep marks > 80
    .sorted((a, b) -> b.marks - a.marks) // sort by marks descending
    .map(s -> s.name)                    // get just the name
    .collect(Collectors.toList());

System.out.println(topStudents); // [Priya, Sneha, Arman]

// Average marks of students above 70
OptionalDouble avg = students.stream()
    .filter(s -> s.marks > 70)
    .mapToInt(s -> s.marks)
    .average();

System.out.println(avg.getAsDouble()); // 88.33...
```

### mapToInt, mapToLong, mapToDouble - for numeric operations

```java
List<String> names = Arrays.asList("Arman", "Priya", "Raj");

// IntStream gives you sum, average, min, max directly
int totalLength = names.stream()
    .mapToInt(String::length)
    .sum();
System.out.println(totalLength); // 13

OptionalDouble avgLength = names.stream()
    .mapToInt(String::length)
    .average();
System.out.println(avgLength.getAsDouble()); // 4.33...

IntSummaryStatistics stats = names.stream()
    .mapToInt(String::length)
    .summaryStatistics();
```

---

## PART 6 - OPTIONAL CLASS

### What is Optional and why it exists

Before Optional, methods that might not have a result returned null. The caller had to remember to check for null every time. If they forgot, NullPointerException.

```java
// Old way - returns null if not found
public static String findUser(int id) {
    if (id == 1) return "Arman";
    return null; // caller must check for null
}

String user = findUser(99);
System.out.println(user.toUpperCase()); // NullPointerException! Forgot to check null
```

Optional is a container that may or may not hold a value. It forces the caller to explicitly handle both cases - value present and value absent.

```java
// New way - returns Optional
public static Optional<String> findUser(int id) {
    if (id == 1) return Optional.of("Arman");
    return Optional.empty(); // clearly communicates no value
}

Optional<String> user = findUser(99);
// Cannot just call user.toUpperCase() - must deal with Optional
```

### Creating Optional

```java
// Optional.of() - value must NOT be null (throws NullPointerException if null)
Optional<String> opt1 = Optional.of("Arman");

// Optional.ofNullable() - value CAN be null (returns empty Optional if null)
String name = null;
Optional<String> opt2 = Optional.ofNullable(name); // empty Optional, no exception

String name2 = "Priya";
Optional<String> opt3 = Optional.ofNullable(name2); // Optional with "Priya"

// Optional.empty() - explicitly create empty Optional
Optional<String> empty = Optional.empty();
```

### Checking and getting value

```java
Optional<String> opt = Optional.of("Arman");

// isPresent() - true if value exists
if (opt.isPresent()) {
    System.out.println(opt.get()); // Arman
}

// isEmpty() - Java 11+, true if no value
if (opt.isEmpty()) {
    System.out.println("No value");
}

// get() - returns value, throws NoSuchElementException if empty
// Use only when you are sure value is present
String value = opt.get(); // Arman

// DO NOT do this - defeats the purpose of Optional
if (opt.isPresent()) {
    String val = opt.get(); // same as not using Optional
}
```

### Better ways to use Optional

**orElse() - provide a default value**

```java
Optional<String> opt = Optional.empty();

String value = opt.orElse("Default Name"); // returns "Default Name" if empty
System.out.println(value); // Default Name

Optional<String> opt2 = Optional.of("Arman");
String value2 = opt2.orElse("Default Name"); // returns "Arman" - has value
System.out.println(value2); // Arman
```

**orElseGet() - provide a default using a supplier**

Use when the default value is expensive to compute. orElse() always evaluates the default even if not needed. orElseGet() only evaluates it when needed.

```java
Optional<String> opt = Optional.empty();

// orElse always calls the method even if optional has a value
String val1 = opt.orElse(getDefaultFromDatabase()); // getDefaultFromDatabase called always

// orElseGet only calls the supplier if optional is empty
String val2 = opt.orElseGet(() -> getDefaultFromDatabase()); // only called if empty
```

**orElseThrow() - throw exception if empty**

```java
Optional<String> opt = Optional.empty();

// Throw NoSuchElementException if empty (default)
String val = opt.orElseThrow();

// Throw custom exception if empty
String val2 = opt.orElseThrow(() -> new RuntimeException("User not found"));
```

**ifPresent() - run code only if value exists**

```java
Optional<String> opt = Optional.of("Arman");

// Only runs if value is present
opt.ifPresent(name -> System.out.println("Hello, " + name)); // Hello, Arman

Optional<String> empty = Optional.empty();
empty.ifPresent(name -> System.out.println("Hello, " + name)); // nothing happens
```

**ifPresentOrElse() - Java 9+**

```java
Optional<String> opt = Optional.of("Arman");

opt.ifPresentOrElse(
    name -> System.out.println("Found: " + name),   // runs if present
    () -> System.out.println("Not found")            // runs if empty
);
```

**map() - transform the value if present**

```java
Optional<String> name = Optional.of("arman");

Optional<String> upperName = name.map(String::toUpperCase);
System.out.println(upperName.get()); // ARMAN

// If empty, map returns empty - no NullPointerException
Optional<String> empty = Optional.empty();
Optional<String> result = empty.map(String::toUpperCase); // still empty, no error
System.out.println(result.isPresent()); // false
```

**filter() - keep value only if condition matches**

```java
Optional<Integer> age = Optional.of(25);

Optional<Integer> adult = age.filter(a -> a >= 18);
System.out.println(adult.isPresent()); // true

Optional<Integer> age2 = Optional.of(15);
Optional<Integer> adult2 = age2.filter(a -> a >= 18);
System.out.println(adult2.isPresent()); // false - filtered out
```

**flatMap() - for when map returns Optional**

```java
class User {
    String name;
    Optional<String> email;

    User(String name, Optional<String> email) {
        this.name = name;
        this.email = email;
    }
}

Optional<User> user = Optional.of(new User("Arman", Optional.of("arman@gmail.com")));

// map would give Optional<Optional<String>>
Optional<Optional<String>> wrong = user.map(u -> u.email);

// flatMap flattens it to Optional<String>
Optional<String> email = user.flatMap(u -> u.email);
System.out.println(email.get()); // arman@gmail.com
```

### Optional chaining - real world example

```java
public class UserService {

    public Optional<User> findUser(int id) {
        if (id == 1) return Optional.of(new User("Arman", Optional.of("arman@gmail.com")));
        return Optional.empty();
    }
}

public class Main {
    public static void main(String[] args) {

        UserService service = new UserService();

        // Chain operations cleanly
        String email = service.findUser(1)
            .flatMap(user -> user.email)
            .map(String::toLowerCase)
            .orElse("no email found");

        System.out.println(email); // arman@gmail.com

        // For non-existent user
        String email2 = service.findUser(99)
            .flatMap(user -> user.email)
            .map(String::toLowerCase)
            .orElse("no email found");

        System.out.println(email2); // no email found
    }
}
```

### Important rules for Optional

```
DO                                    |  DO NOT
--------------------------------------|--------------------------------------
Use as return type of methods         |  Use as method parameter
Use orElse, orElseGet, map, filter    |  Use get() without isPresent() check
Return Optional.empty() for no value  |  Return null from Optional method
Use ifPresent for side effects        |  Use for every field in a class
Use flatMap for Optional in Optional  |  Use in collections (List<Optional<T>>)
```

---

## PART 7 - PUTTING IT ALL TOGETHER

### Real world example combining everything

```java
import java.util.*;
import java.util.stream.*;
import java.util.function.*;

class Employee {
    String name;
    String department;
    double salary;
    boolean active;

    Employee(String name, String department, double salary, boolean active) {
        this.name = name;
        this.department = department;
        this.salary = salary;
        this.active = active;
    }
}

public class Java8Demo {
    public static void main(String[] args) {

        List<Employee> employees = Arrays.asList(
            new Employee("Arman", "Engineering", 75000, true),
            new Employee("Priya", "HR", 60000, true),
            new Employee("Raj", "Engineering", 80000, false),
            new Employee("Sneha", "Marketing", 55000, true),
            new Employee("Rohan", "Engineering", 90000, true),
            new Employee("Anita", "HR", 65000, true)
        );

        // 1. Get names of active engineering employees earning > 70000
        List<String> topEngineers = employees.stream()
            .filter(e -> e.active)
            .filter(e -> e.department.equals("Engineering"))
            .filter(e -> e.salary > 70000)
            .map(e -> e.name)
            .sorted()
            .collect(Collectors.toList());
        System.out.println("Top engineers: " + topEngineers);
        // [Arman, Rohan]

        // 2. Average salary per department (active only)
        Map<String, Double> avgSalaryByDept = employees.stream()
            .filter(e -> e.active)
            .collect(Collectors.groupingBy(
                e -> e.department,
                Collectors.averagingDouble(e -> e.salary)
            ));
        System.out.println("Avg salary by dept: " + avgSalaryByDept);

        // 3. Highest paid active employee
        Optional<Employee> highestPaid = employees.stream()
            .filter(e -> e.active)
            .max(Comparator.comparingDouble(e -> e.salary));

        highestPaid.ifPresent(e ->
            System.out.println("Highest paid: " + e.name + " (" + e.salary + ")")
        );

        // 4. Total salary bill for active employees
        double totalSalary = employees.stream()
            .filter(e -> e.active)
            .mapToDouble(e -> e.salary)
            .sum();
        System.out.println("Total salary: " + totalSalary);

        // 5. Group employees by department
        Map<String, List<String>> byDept = employees.stream()
            .collect(Collectors.groupingBy(
                e -> e.department,
                Collectors.mapping(e -> e.name, Collectors.toList())
            ));
        System.out.println("By department: " + byDept);

        // 6. Functional interface + lambda for reusable filter
        Predicate<Employee> isActive = e -> e.active;
        Predicate<Employee> isEngineer = e -> e.department.equals("Engineering");
        Function<Employee, String> getName = e -> e.name;

        List<String> activeEngineers = employees.stream()
            .filter(isActive.and(isEngineer))
            .map(getName)
            .collect(Collectors.toList());
        System.out.println("Active engineers: " + activeEngineers);
    }
}
```

---

## PART 1 - DATE AND TIME API (Java 8)

### Why the old Date and Calendar were bad

Before Java 8, you had java.util.Date and java.util.Calendar. Both were terrible:

- Date was mutable - anyone could change it after creation
- Month in Calendar was 0-based (January = 0, December = 11) - confusing
- No timezone support built in properly
- Thread safety issues
  Java 8 introduced a completely new Date/Time API in java.time package. Immutable, clear, and easy to use.

### LocalDate - date without time

Represents just a date. No time, no timezone.

```java
import java.time.LocalDate;
import java.time.Month;
import java.time.DayOfWeek;

// Creating
LocalDate today = LocalDate.now();               // today's date
LocalDate specific = LocalDate.of(2024, 1, 15);  // 15 Jan 2024
LocalDate specific2 = LocalDate.of(2024, Month.JANUARY, 15); // same, clearer
LocalDate fromString = LocalDate.parse("2024-01-15"); // from string

System.out.println(today);    // 2024-01-15 (ISO format)
System.out.println(specific); // 2024-01-15

// Getting parts
System.out.println(today.getYear());       // 2024
System.out.println(today.getMonth());      // JANUARY
System.out.println(today.getMonthValue()); // 1 (1-based, not 0-based like old API)
System.out.println(today.getDayOfMonth()); // 15
System.out.println(today.getDayOfWeek());  // MONDAY
System.out.println(today.getDayOfYear());  // 15

// Adding and subtracting - returns NEW date (immutable)
LocalDate nextWeek = today.plusDays(7);
LocalDate lastMonth = today.minusMonths(1);
LocalDate nextYear = today.plusYears(1);
LocalDate twoWeeksAgo = today.minusWeeks(2);

// Comparing
LocalDate date1 = LocalDate.of(2024, 1, 15);
LocalDate date2 = LocalDate.of(2024, 6, 20);

System.out.println(date1.isBefore(date2));   // true
System.out.println(date1.isAfter(date2));    // false
System.out.println(date1.isEqual(date2));    // false
System.out.println(date1.compareTo(date2));  // negative number

// Useful checks
System.out.println(today.isLeapYear());        // true/false
System.out.println(today.lengthOfMonth());     // days in current month
System.out.println(today.lengthOfYear());      // 365 or 366

// Modifying specific fields
LocalDate firstDayOfMonth = today.withDayOfMonth(1);
LocalDate lastDayOfYear = today.withDayOfYear(today.lengthOfYear());
```

### LocalTime - time without date

Represents just a time. No date, no timezone.

```java
import java.time.LocalTime;

// Creating
LocalTime now = LocalTime.now();                    // current time
LocalTime specific = LocalTime.of(14, 30);          // 14:30:00
LocalTime specific2 = LocalTime.of(14, 30, 45);     // 14:30:45
LocalTime specific3 = LocalTime.of(14, 30, 45, 500000000); // with nanoseconds
LocalTime fromString = LocalTime.parse("14:30:45");

// Getting parts
System.out.println(now.getHour());   // 14
System.out.println(now.getMinute()); // 30
System.out.println(now.getSecond()); // 45
System.out.println(now.getNano());   // nanoseconds

// Adding and subtracting
LocalTime later = now.plusHours(2);
LocalTime earlier = now.minusMinutes(30);

// Comparing
System.out.println(LocalTime.of(9, 0).isBefore(LocalTime.of(17, 0))); // true

// Useful constants
System.out.println(LocalTime.MIDNIGHT); // 00:00
System.out.println(LocalTime.NOON);     // 12:00
System.out.println(LocalTime.MAX);      // 23:59:59.999999999
```

### LocalDateTime - date and time together, no timezone

```java
import java.time.LocalDateTime;

// Creating
LocalDateTime now = LocalDateTime.now();
LocalDateTime specific = LocalDateTime.of(2024, 1, 15, 14, 30, 45);
LocalDateTime fromParts = LocalDateTime.of(
    LocalDate.of(2024, 1, 15),
    LocalTime.of(14, 30, 45)
);
LocalDateTime fromString = LocalDateTime.parse("2024-01-15T14:30:45");

// Getting parts
System.out.println(now.getYear());
System.out.println(now.getMonth());
System.out.println(now.getDayOfMonth());
System.out.println(now.getHour());
System.out.println(now.getMinute());

// Split into date and time
LocalDate date = now.toLocalDate();
LocalTime time = now.toLocalTime();

// Adding
LocalDateTime tomorrow = now.plusDays(1);
LocalDateTime nextHour = now.plusHours(1);

// Comparing
System.out.println(now.isBefore(tomorrow)); // true
```

### ZonedDateTime - date, time, with timezone

Use this when timezone matters - like scheduling meetings across countries.

```java
import java.time.ZonedDateTime;
import java.time.ZoneId;

// Creating
ZonedDateTime now = ZonedDateTime.now(); // system timezone
ZonedDateTime inIndia = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
ZonedDateTime inNewYork = ZonedDateTime.now(ZoneId.of("America/New_York"));
ZonedDateTime inLondon = ZonedDateTime.now(ZoneId.of("Europe/London"));

System.out.println(inIndia);   // 2024-01-15T20:00:00+05:30[Asia/Kolkata]
System.out.println(inNewYork); // 2024-01-15T09:30:00-05:00[America/New_York]

// Convert between timezones
ZonedDateTime indiaTime = ZonedDateTime.now(ZoneId.of("Asia/Kolkata"));
ZonedDateTime sameTimeInNY = indiaTime.withZoneSameInstant(ZoneId.of("America/New_York"));
// Same moment in time, different local representation

// List all available zone IDs
ZoneId.getAvailableZoneIds().stream()
    .filter(z -> z.contains("India"))
    .forEach(System.out::println);
```

### Duration - amount of time in hours, minutes, seconds

Duration measures time between two LocalTime or LocalDateTime points.

```java
import java.time.Duration;
import java.time.LocalTime;
import java.time.LocalDateTime;

// Creating
Duration twoHours = Duration.ofHours(2);
Duration thirtyMinutes = Duration.ofMinutes(30);
Duration oneHourThirty = Duration.ofHours(1).plusMinutes(30);

// Between two times
LocalTime start = LocalTime.of(9, 0);
LocalTime end = LocalTime.of(17, 30);
Duration workDay = Duration.between(start, end);

System.out.println(workDay.toHours());   // 8
System.out.println(workDay.toMinutes()); // 510
System.out.println(workDay.toSeconds()); // 30600

// Between two date times
LocalDateTime meetingStart = LocalDateTime.of(2024, 1, 15, 10, 0);
LocalDateTime meetingEnd = LocalDateTime.of(2024, 1, 15, 11, 30);
Duration meeting = Duration.between(meetingStart, meetingEnd);
System.out.println(meeting.toMinutes()); // 90

// Adding duration to time
LocalDateTime deadline = LocalDateTime.now().plus(Duration.ofDays(7));
```

### Period - amount of time in years, months, days

Period measures calendar-based differences - years, months, days.

```java
import java.time.Period;
import java.time.LocalDate;

// Creating
Period oneYear = Period.ofYears(1);
Period sixMonths = Period.ofMonths(6);
Period twoWeeks = Period.ofWeeks(2);
Period custom = Period.of(1, 6, 15); // 1 year, 6 months, 15 days

// Between two dates
LocalDate birthDate = LocalDate.of(2000, 3, 15);
LocalDate today = LocalDate.now();
Period age = Period.between(birthDate, today);

System.out.println(age.getYears());  // 24 (example)
System.out.println(age.getMonths()); // months beyond complete years
System.out.println(age.getDays());   // days beyond complete months

// Adding period to date
LocalDate futureDate = today.plus(Period.ofMonths(6));
```

### Duration vs Period

```
Duration                         |  Period
---------------------------------|---------------------------------
For time (hours, minutes, sec)   |  For dates (years, months, days)
Used with LocalTime/DateTime     |  Used with LocalDate
Between(LocalTime, LocalTime)    |  Between(LocalDate, LocalDate)
toHours(), toMinutes()           |  getYears(), getMonths()
Duration.ofHours(2)              |  Period.ofMonths(6)
```

### DateTimeFormatter - formatting and parsing

```java
import java.time.format.DateTimeFormatter;

LocalDate date = LocalDate.of(2024, 1, 15);
LocalDateTime dateTime = LocalDateTime.of(2024, 1, 15, 14, 30, 45);

// Built-in formatters
System.out.println(date.format(DateTimeFormatter.ISO_DATE));        // 2024-01-15
System.out.println(dateTime.format(DateTimeFormatter.ISO_DATE_TIME)); // 2024-01-15T14:30:45

// Custom pattern
DateTimeFormatter indianFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
DateTimeFormatter fullFormat = DateTimeFormatter.ofPattern("dd MMMM yyyy, hh:mm a");
DateTimeFormatter shortFormat = DateTimeFormatter.ofPattern("dd-MMM-yy");

System.out.println(date.format(indianFormat));  // 15/01/2024
System.out.println(dateTime.format(fullFormat)); // 15 January 2024, 02:30 PM
System.out.println(date.format(shortFormat));   // 15-Jan-24

// Parsing string to date
LocalDate parsed = LocalDate.parse("15/01/2024", indianFormat);
System.out.println(parsed); // 2024-01-15

// Pattern letters
// y = year, M = month, d = day
// H = hour 24h, h = hour 12h, m = minute, s = second
// a = AM/PM, E = day name, MMMM = full month name, MMM = short month name
```

### Instant - machine timestamp

Represents a point in time as milliseconds from epoch (1 Jan 1970). Use for logging, measuring elapsed time.

```java
import java.time.Instant;

Instant now = Instant.now();
System.out.println(now); // 2024-01-15T14:30:45.123456789Z

// Measure elapsed time
Instant start = Instant.now();
// some operation
Thread.sleep(1000);
Instant end = Instant.now();

Duration elapsed = Duration.between(start, end);
System.out.println("Took: " + elapsed.toMillis() + "ms"); // ~1000ms

// To/from epoch milliseconds
long epochMilli = now.toEpochMilli();
Instant fromEpoch = Instant.ofEpochMilli(epochMilli);
```

---

## PART 2 - VAR KEYWORD (Java 10)

### What is var

var lets the compiler figure out the type of a local variable automatically. You do not have to write the type explicitly.

This is called local variable type inference. The type is still fixed at compile time - it is not dynamic. It just saves you from writing it out.

```java
// Before var
ArrayList<String> names = new ArrayList<String>();
Map<String, List<Integer>> data = new HashMap<String, List<Integer>>();

// With var - compiler figures out the type
var names = new ArrayList<String>();       // type is ArrayList<String>
var data = new HashMap<String, List<Integer>>(); // type is HashMap<String, List<Integer>>

// Simple types
var name = "Arman";    // String
var age = 25;          // int
var price = 99.99;     // double
var isActive = true;   // boolean
```

### var in loops and streams

```java
var numbers = List.of(1, 2, 3, 4, 5);

// In for-each loop
for (var num : numbers) {
    System.out.println(num);
}

// In regular for loop
for (var i = 0; i < numbers.size(); i++) {
    System.out.println(numbers.get(i));
}

// var makes complex generic types readable
var employeeMap = new HashMap<String, List<Employee>>();
// much cleaner than: HashMap<String, List<Employee>> employeeMap = new HashMap<>();
```

### Rules and limitations of var

```java
// var must be initialized when declared
var name; // COMPILE ERROR - must initialize
var name = "Arman"; // OK

// var cannot be null without casting - compiler cannot infer type
var x = null; // COMPILE ERROR

// var cannot be used for method parameters
public void process(var data) {} // COMPILE ERROR

// var cannot be used for return types
public var getName() {} // COMPILE ERROR

// var cannot be used for class fields
class Person {
    var name = "Arman"; // COMPILE ERROR - only local variables
}

// var can be used in try-with-resources
try (var connection = getConnection()) {
    // connection type inferred
}
```

---

## PART 3 - IMMUTABLE COLLECTIONS (Java 9)

### List.of(), Set.of(), Map.of()

Before Java 9, creating a small immutable collection was verbose:

```java
// Before Java 9
List<String> names = Collections.unmodifiableList(Arrays.asList("Arman", "Priya", "Raj"));
```

Java 9 added factory methods:

```java
// Java 9+
List<String> names = List.of("Arman", "Priya", "Raj");
Set<Integer> scores = Set.of(85, 92, 71, 88);
Map<String, Integer> marks = Map.of("Arman", 85, "Priya", 92, "Raj", 71);
```

### Characteristics of these collections

```java
List<String> names = List.of("Arman", "Priya", "Raj");

// Cannot add
// names.add("Sneha"); // UnsupportedOperationException

// Cannot remove
// names.remove("Arman"); // UnsupportedOperationException

// Cannot set
// names.set(0, "Rohan"); // UnsupportedOperationException

// Can read
System.out.println(names.get(0)); // Arman
System.out.println(names.size()); // 3
System.out.println(names.contains("Priya")); // true

// No null allowed
// List.of("A", null, "B"); // NullPointerException

// Set.of does not allow duplicates
// Set.of("A", "B", "A"); // IllegalArgumentException
```

### Map.of vs Map.ofEntries

Map.of works up to 10 key-value pairs. For more, use Map.ofEntries.

```java
// Map.of - up to 10 entries
Map<String, Integer> small = Map.of(
    "Arman", 85,
    "Priya", 92,
    "Raj", 71
);

// Map.ofEntries - for more than 10
import java.util.Map.Entry;

Map<String, Integer> large = Map.ofEntries(
    Map.entry("Arman", 85),
    Map.entry("Priya", 92),
    Map.entry("Raj", 71),
    Map.entry("Sneha", 88)
    // can have as many as needed
);
```

### List.copyOf(), Set.copyOf(), Map.copyOf() - Java 10

Creates an immutable copy of an existing collection.

```java
List<String> original = new ArrayList<>();
original.add("Arman");
original.add("Priya");

// Creates unmodifiable copy
List<String> copy = List.copyOf(original);
// copy.add("Raj"); // UnsupportedOperationException

// original can still be modified - copy is independent snapshot
original.add("Raj");
System.out.println(original.size()); // 3
System.out.println(copy.size());     // 2 - copy was made before Raj was added
```

---

## PART 4 - STRING METHODS (Java 11)

Java 11 added several very useful String methods.

```java
String text = "  Hello World  ";

// isBlank() - true if string is empty or only whitespace
// isEmpty() only checks for empty string, isBlank() also checks whitespace
System.out.println("".isEmpty());     // true
System.out.println("  ".isEmpty());   // false - has spaces
System.out.println("  ".isBlank());   // true - only whitespace
System.out.println("hi".isBlank());   // false

// strip() - like trim() but handles all Unicode whitespace
// trim() only handles ASCII whitespace (spaces, tabs, newlines)
System.out.println(text.strip());      // "Hello World" - removes leading and trailing
System.out.println(text.stripLeading()); // "Hello World  " - removes only leading
System.out.println(text.stripTrailing()); // "  Hello World" - removes only trailing

// lines() - splits string into stream of lines
String multiLine = "Line 1\nLine 2\nLine 3";
multiLine.lines().forEach(System.out::println);
// Line 1
// Line 2
// Line 3

long lineCount = multiLine.lines().count(); // 3

// repeat() - repeat string n times
String repeated = "Ha".repeat(3);
System.out.println(repeated); // HaHaHa

String separator = "-".repeat(20);
System.out.println(separator); // --------------------
```

---

## PART 5 - SWITCH EXPRESSIONS (Java 14)

### Old switch statement - problems

```java
// Old switch - verbose, fall-through bugs, no return value
int day = 3;
String dayName;

switch (day) {
    case 1:
        dayName = "Monday";
        break; // must remember break
    case 2:
        dayName = "Tuesday";
        break;
    case 3:
        dayName = "Wednesday";
        break;
    default:
        dayName = "Unknown";
}
// dayName is set outside switch
```

### New switch expression - cleaner

```java
// New switch expression - returns a value, no fall-through, no break needed
int day = 3;

String dayName = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    case 4 -> "Thursday";
    case 5 -> "Friday";
    case 6 -> "Saturday";
    case 7 -> "Sunday";
    default -> "Unknown";
};

System.out.println(dayName); // Wednesday
```

### Multiple labels in one case

```java
String typeOfDay = switch (day) {
    case 1, 2, 3, 4, 5 -> "Weekday";
    case 6, 7 -> "Weekend";
    default -> "Invalid";
};
```

### Switch with blocks - when you need multiple statements

```java
int score = 85;

String grade = switch (score / 10) {
    case 10, 9 -> "A";
    case 8 -> "B";
    case 7 -> {
        System.out.println("Just passed with C");
        yield "C"; // use yield to return value from a block
    }
    default -> {
        System.out.println("Failed");
        yield "F";
    }
};
```

### Switch with String and Enum

```java
String command = "START";

String result = switch (command) {
    case "START" -> "Starting the process";
    case "STOP" -> "Stopping the process";
    case "PAUSE" -> "Pausing the process";
    default -> "Unknown command: " + command;
};

// With enum
enum Status { ACTIVE, INACTIVE, PENDING }
Status status = Status.ACTIVE;

String message = switch (status) {
    case ACTIVE -> "User is active";
    case INACTIVE -> "User is inactive";
    case PENDING -> "User approval pending";
};
```

---

## PART 6 - RECORDS (Java 16)

### What is a Record

A Record is a special class for holding immutable data. Before records, creating a simple data class required a lot of boilerplate:

```java
// Before records - lots of code for a simple data holder
class Person {
    private final String name;
    private final int age;

    Person(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public String getName() { return name; }
    public int getAge() { return age; }

    @Override
    public boolean equals(Object obj) { /* ... */ }

    @Override
    public int hashCode() { /* ... */ }

    @Override
    public String toString() {
        return "Person[name=" + name + ", age=" + age + "]";
    }
}
```

With records, all of this is automatic:

```java
// After records - one line!
record Person(String name, int age) {}

// Java automatically generates:
// - constructor: Person(String name, int age)
// - getters: name() and age() (not getName(), just name())
// - equals(), hashCode(), toString()
// - fields are final (immutable)
```

### Using records

```java
record Student(String name, int rollNo, double marks) {}

public class Main {
    public static void main(String[] args) {

        Student s1 = new Student("Arman", 1, 85.5);
        Student s2 = new Student("Priya", 2, 92.0);
        Student s3 = new Student("Arman", 1, 85.5);

        // Access fields using accessor methods (same name as field)
        System.out.println(s1.name());   // Arman (not getName())
        System.out.println(s1.rollNo()); // 1
        System.out.println(s1.marks());  // 85.5

        // toString is auto-generated
        System.out.println(s1); // Student[name=Arman, rollNo=1, marks=85.5]

        // equals compares all fields
        System.out.println(s1.equals(s3)); // true - same field values
        System.out.println(s1.equals(s2)); // false

        // hashCode is based on all fields
        System.out.println(s1.hashCode() == s3.hashCode()); // true

        // Cannot modify fields - immutable
        // s1.name = "Raj"; // COMPILE ERROR - field is final
    }
}
```

### Adding methods to records

```java
record Circle(double radius) {

    // Custom compact constructor - for validation
    Circle {
        if (radius <= 0) {
            throw new IllegalArgumentException("Radius must be positive: " + radius);
        }
    }

    // Instance methods are allowed
    double area() {
        return Math.PI * radius * radius;
    }

    double circumference() {
        return 2 * Math.PI * radius;
    }

    // Static methods are allowed
    static Circle unit() {
        return new Circle(1.0);
    }
}

Circle c = new Circle(5.0);
System.out.println(c.area());         // 78.53...
System.out.println(c.circumference()); // 31.41...
System.out.println(Circle.unit());     // Circle[radius=1.0]

// Circle invalid = new Circle(-3); // IllegalArgumentException
```

### Records with generics

```java
record Pair<A, B>(A first, B second) {
    // swap returns a new Pair with values swapped
    Pair<B, A> swap() {
        return new Pair<>(second, first);
    }
}

Pair<String, Integer> pair = new Pair<>("Arman", 25);
System.out.println(pair.first());  // Arman
System.out.println(pair.second()); // 25
System.out.println(pair.swap());   // Pair[first=25, second=Arman]
```

### Records in streams and collections

```java
record Product(String name, double price, int stock) {}

List<Product> products = List.of(
    new Product("Laptop", 75000, 10),
    new Product("Phone", 35000, 25),
    new Product("Tablet", 45000, 5),
    new Product("Watch", 15000, 30)
);

// Works perfectly with streams
double totalValue = products.stream()
    .mapToDouble(p -> p.price() * p.stock())
    .sum();

List<String> affordable = products.stream()
    .filter(p -> p.price() < 50000)
    .map(Product::name)
    .collect(Collectors.toList());

System.out.println(affordable); // [Phone, Tablet, Watch]
```

### When to use Records

Use records when:

- You need a simple data holder
- Data should be immutable
- You want equals, hashCode, toString for free
- DTOs (Data Transfer Objects), value objects, API responses
  Do NOT use records when:
- You need to extend another class (records implicitly extend Record)
- You need mutable state
- You need complex inheritance

---

## PART 7 - SEALED CLASSES (Java 17)

### What is a Sealed Class

A sealed class controls which classes can extend it. Without sealed, any class from anywhere can extend your class. With sealed, you explicitly list which classes are allowed.

This is useful when you want to represent a fixed set of types - like a Result that can only be Success or Failure, or a Shape that can only be Circle, Rectangle, or Triangle.

```java
// Sealed class - only Circle, Rectangle, Triangle can extend Shape
sealed class Shape permits Circle, Rectangle, Triangle {
    abstract double area();
}

// Each permitted class must be either:
// 1. final - cannot be extended further
// 2. sealed - can be extended but only by permitted subclasses
// 3. non-sealed - can be extended by anyone (opens it up again)

final class Circle extends Shape {
    double radius;

    Circle(double radius) { this.radius = radius; }

    @Override
    double area() { return Math.PI * radius * radius; }
}

final class Rectangle extends Shape {
    double length, width;

    Rectangle(double length, double width) {
        this.length = length;
        this.width = width;
    }

    @Override
    double area() { return length * width; }
}

final class Triangle extends Shape {
    double base, height;

    Triangle(double base, double height) {
        this.base = base;
        this.height = height;
    }

    @Override
    double area() { return 0.5 * base * height; }
}

// This would be a COMPILE ERROR - Hexagon is not permitted
// class Hexagon extends Shape { }
```

### Using sealed classes with switch

Sealed classes work really well with switch expressions. Since the compiler knows all possible subclasses, it can check if you have handled all cases.

```java
public class Main {
    public static void main(String[] args) {

        Shape shape = new Circle(5);

        // Compiler knows Shape can only be Circle, Rectangle, or Triangle
        // If you miss a case, compiler warns you
        double area = switch (shape) {
            case Circle c -> Math.PI * c.radius * c.radius;
            case Rectangle r -> r.length * r.width;
            case Triangle t -> 0.5 * t.base * t.height;
            // no default needed - all cases covered
        };

        System.out.println("Area: " + area);

        String description = switch (shape) {
            case Circle c -> "Circle with radius " + c.radius;
            case Rectangle r -> "Rectangle " + r.length + "x" + r.width;
            case Triangle t -> "Triangle with base " + t.base;
        };

        System.out.println(description);
    }
}
```

### Sealed interfaces

Interfaces can also be sealed:

```java
sealed interface Result<T> permits Success, Failure {
    boolean isSuccess();
}

record Success<T>(T value) implements Result<T> {
    public boolean isSuccess() { return true; }
}

record Failure<T>(String error) implements Result<T> {
    public boolean isSuccess() { return false; }
}

// Usage
Result<String> result = fetchData();

String output = switch (result) {
    case Success<String> s -> "Got: " + s.value();
    case Failure<String> f -> "Error: " + f.error();
};
```

### When to use Sealed Classes

Use when:

- You have a fixed set of subtypes that should not be extended randomly
- You want compiler to verify all cases are handled in switch
- Modeling domain concepts like Result, Status, Shape

---

## PART 8 - STREAM API IMPROVEMENTS (Java 9)

### takeWhile() - take elements while condition is true

Takes elements from the stream as long as condition is true. Stops as soon as condition becomes false.

```java
List<Integer> numbers = List.of(2, 4, 6, 7, 8, 10, 12);

// Take elements while they are even
List<Integer> result = numbers.stream()
    .takeWhile(n -> n % 2 == 0)
    .collect(Collectors.toList());

System.out.println(result); // [2, 4, 6]
// Stops at 7 because 7 is not even - does NOT skip and continue
// Everything after 7 is also dropped even if even
```

### dropWhile() - drop elements while condition is true

Drops elements as long as condition is true. Once condition becomes false, keeps all remaining elements.

```java
List<Integer> numbers = List.of(2, 4, 6, 7, 8, 10, 12);

// Drop elements while they are even
List<Integer> result = numbers.stream()
    .dropWhile(n -> n % 2 == 0)
    .collect(Collectors.toList());

System.out.println(result); // [7, 8, 10, 12]
// Drops 2, 4, 6 (even), stops dropping at 7 (odd), keeps 7 and everything after
```

### Stream.iterate() with predicate (Java 9)

Java 8 iterate() was infinite. Java 9 added a stopping condition.

```java
// Java 8 - infinite, need limit()
Stream.iterate(0, n -> n + 2)
    .limit(5)
    .forEach(System.out::println); // 0 2 4 6 8

// Java 9 - with stopping condition, no limit() needed
Stream.iterate(0, n -> n < 10, n -> n + 2)
    .forEach(System.out::println); // 0 2 4 6 8
// First arg: starting value
// Second arg: condition to continue
// Third arg: how to get next value
```

### Optional improvements (Java 9)

```java
Optional<String> opt = Optional.of("Arman");
Optional<String> empty = Optional.empty();

// ifPresentOrElse() - handle both present and empty case
opt.ifPresentOrElse(
    name -> System.out.println("Found: " + name),
    () -> System.out.println("Not found")
);

// or() - if empty, provide another Optional (different from orElse which provides value)
Optional<String> result = empty.or(() -> Optional.of("Default"));
System.out.println(result.get()); // Default

// stream() - convert Optional to Stream (empty stream if empty Optional)
long count = opt.stream().count();   // 1
long count2 = empty.stream().count(); // 0

// Useful in flatMap with streams
List<Optional<String>> optionals = List.of(
    Optional.of("Arman"),
    Optional.empty(),
    Optional.of("Priya")
);

List<String> names = optionals.stream()
    .flatMap(Optional::stream) // converts each Optional to stream, empty ones disappear
    .collect(Collectors.toList());

System.out.println(names); // [Arman, Priya]
```

---

## PART 9 - PATTERN MATCHING (Java 16+)

### Pattern matching for instanceof

Before Java 16, after instanceof check you had to cast manually:

```java
// Before - verbose
Object obj = "Hello World";

if (obj instanceof String) {
    String s = (String) obj; // manual cast
    System.out.println(s.toUpperCase());
}
```

With pattern matching, the cast is automatic:

```java
// After - clean
Object obj = "Hello World";

if (obj instanceof String s) { // s is automatically cast to String
    System.out.println(s.toUpperCase()); // use s directly
}

// More examples
Object value = 42;

if (value instanceof Integer i && i > 10) { // can use i in condition too
    System.out.println("Large integer: " + i);
}

// In a method handling different types
static String describe(Object obj) {
    if (obj instanceof String s) {
        return "String of length " + s.length();
    } else if (obj instanceof Integer i) {
        return "Integer: " + i;
    } else if (obj instanceof List<?> list) {
        return "List with " + list.size() + " elements";
    }
    return "Unknown: " + obj;
}
```

---

## PART 10 - TEXT BLOCKS (Java 15)

Text blocks let you write multiline strings without escape characters.

```java
// Before text blocks - messy
String json = "{\n" +
    "    \"name\": \"Arman\",\n" +
    "    \"age\": 25\n" +
    "}";

String html = "<html>\n" +
    "    <body>\n" +
    "        <p>Hello</p>\n" +
    "    </body>\n" +
    "</html>";

// With text blocks - clean and readable
String json = """
    {
        "name": "Arman",
        "age": 25
    }
    """;

String html = """
    <html>
        <body>
            <p>Hello</p>
        </body>
    </html>
    """;

String sql = """
    SELECT name, marks
    FROM students
    WHERE marks > 80
    ORDER BY marks DESC
    """;

System.out.println(json);
System.out.println(html);
System.out.println(sql);
```

### Text block rules

```java
// Opening """ must be followed by newline
// Closing """ on its own line sets the indentation baseline
// Indentation equal to closing """ position is stripped

String block = """
        Line 1
        Line 2
        Line 3
        """; // closing """ is at column 8, so 8 spaces stripped from each line

// Use \ to prevent newline
String oneLine = """
        Hello \
        World"""; // Hello World (no newline between Hello and World)

// Use \s to force trailing spaces (normally stripped)
String withSpaces = """
        Hello   \s
        World""";
```

---

## QUICK REFERENCE

```
Java 8:
  LocalDate, LocalTime, LocalDateTime  -> date/time without timezone
  ZonedDateTime                        -> date/time with timezone
  Duration                             -> time-based amount (hours, minutes)
  Period                               -> date-based amount (years, months, days)
  DateTimeFormatter                    -> format and parse dates

Java 9:
  List.of(), Set.of(), Map.of()        -> immutable collections
  Stream.takeWhile()                   -> take while condition true
  Stream.dropWhile()                   -> drop while condition true
  Stream.iterate(seed, predicate, fn)  -> bounded iterate
  Optional.ifPresentOrElse()           -> handle present and empty
  Optional.or()                        -> alternative Optional if empty
  Optional.stream()                    -> Optional to Stream

Java 10:
  var                                  -> local variable type inference
  List.copyOf(), Set.copyOf()          -> immutable copy

Java 11:
  String.isBlank()                     -> true if empty or whitespace
  String.strip()                       -> trim with unicode support
  String.lines()                       -> stream of lines
  String.repeat(n)                     -> repeat string n times

Java 14:
  switch expressions                   -> switch returns value, arrow syntax

Java 15:
  Text blocks                          -> multiline strings with """

Java 16:
  Records                              -> immutable data classes
  Pattern matching instanceof          -> auto cast after instanceof

Java 17:
  Sealed classes                       -> control who can extend
```
