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
