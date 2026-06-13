# Java 8+ Features - Exercises

---

## Functional Interfaces

**Exercise 1.**
Create a functional interface called Greeting with one method greet(String name) that returns void. Implement it using a lambda that prints "Hello, name".

**Exercise 2.**
Create a functional interface called MathOperation with one method operate(int a, int b) that returns int. Create 4 lambdas for add, subtract, multiply, divide. Call each one and print the result.

**Exercise 3.**
Add @FunctionalInterface annotation to an interface that has two abstract methods. See the compile error.

**Exercise 4.**
Create a Predicate<String> that returns true if the string length is greater than 5. Test it with isBlank(), isPresent() style - use test() method.

**Exercise 5.**
Create two Predicate<Integer> - one checks if number is even, another checks if greater than 10. Combine them using and(), or(), negate(). Test all combinations.

**Exercise 6.**
Create a Function<String, Integer> that returns the length of a string. Chain another Function<Integer, String> that converts the number to "Length is X" using andThen(). Print the result.

**Exercise 7.**
Create a Consumer<String> that prints the value. Create another Consumer<String> that prints it in uppercase. Chain them using andThen(). Call accept() once and see both run.

**Exercise 8.**
Create a Supplier<String> that returns "Hello from Supplier". Call get() and print the result.

**Exercise 9.**
Create a BiFunction<String, Integer, String> that repeats a string n times. Call it with ("Ha", 3) and print "HaHaHa".

**Exercise 10.**
Create a UnaryOperator<String> that trims and uppercases a string. Create a BinaryOperator<Integer> that returns the larger of two numbers. Test both.

---

## Lambda Expressions

**Exercise 11.**
Convert this anonymous class to a lambda:
```java
Runnable r = new Runnable() {
    public void run() {
        System.out.println("Running");
    }
};
```

**Exercise 12.**
Create a lambda with no parameters that returns "Hello World". Assign it to a Supplier<String>.

**Exercise 13.**
Create a lambda with one parameter that prints it. Assign to Consumer<String>. Notice you do not need parentheses around single parameter.

**Exercise 14.**
Create a lambda with two parameters that returns their sum. Assign to BinaryOperator<Integer>. Use both the single-line version and the block version with explicit return.

**Exercise 15.**
Create a lambda that uses an if-else inside a block body. It takes an integer and returns "even" or "odd". Assign to Function<Integer, String>.

**Exercise 16.**
Create a String variable prefix = "Hello". Create a lambda that uses this variable. Then try to change prefix after the lambda - see the compile error. Write a comment explaining why.

**Exercise 17.**
Create a list of 5 integers. Use Collections.sort() with a lambda comparator to sort in descending order.

**Exercise 18.**
Create a list of Student objects with name and marks. Sort the list using lambda - first by marks descending, then alphabetically by name if marks are equal.

---

## Method References

**Exercise 19.**
Create a list of strings. Use forEach with a lambda first: forEach(s -> System.out.println(s)). Then replace it with a method reference: forEach(System.out::println).

**Exercise 20.**
Create a Function<String, Integer> using lambda s -> Integer.parseInt(s). Replace it with a static method reference Integer::parseInt. Apply both to "42".

**Exercise 21.**
Create a list of strings. Use stream().map() with lambda s -> s.toUpperCase(). Replace with instance method reference on parameter String::toUpperCase.

**Exercise 22.**
Create a specific String object prefix = "Hello ". Create a Function<String, String> that calls prefix.concat(name). Use instance method reference on object: prefix::concat.

**Exercise 23.**
Create a Supplier<ArrayList> using lambda () -> new ArrayList(). Replace with constructor reference ArrayList::new. Call get() and verify you get a new list.

**Exercise 24.**
Create a Function<String, StringBuilder> using lambda s -> new StringBuilder(s). Replace with constructor reference StringBuilder::new.

**Exercise 25.**
Create a list of strings. Use stream().map(String::toUpperCase).sorted(String::compareToIgnoreCase).forEach(System.out::println). All method references.

---

## Default and Static Methods in Interfaces

**Exercise 26.**
Create an interface Vehicle with abstract methods getBrand() and getSpeed(). Add a default method describe() that returns "Brand: X, Speed: Y". Create two classes Car and Bike that implement it. Call describe() on both without overriding it.

**Exercise 27.**
Override the default describe() method in Car from Exercise 26. Show that Car uses its own version while Bike still uses the default.

**Exercise 28.**
Create two interfaces A and B both having a default method show(). Create a class C that implements both. See the compile error. Fix it by overriding show() in C. Call A.super.show() inside the override.

**Exercise 29.**
Add a static method create() to an interface that returns a new instance. Call it using InterfaceName.create(). Show that you cannot call it on an instance or override it in implementing class.

**Exercise 30.**
Create an interface Sortable with a default method sort(List list) that uses Collections.sort(). Create a class that implements it and uses the default sort without writing any sort logic.

---

## Stream API - Creating and Basic Operations

**Exercise 31.**
Create a stream from a List of strings using stream(). Print all elements using forEach.

**Exercise 32.**
Create a stream using Stream.of("A", "B", "C", "D"). Print each element.

**Exercise 33.**
Create an IntStream using IntStream.range(1, 6). Print all values. Then use IntStream.rangeClosed(1, 5). Write a comment on the difference.

**Exercise 34.**
Create an infinite stream using Stream.generate(() -> "Hello"). Use limit(5) to take only 5. Print all.

**Exercise 35.**
Create an infinite stream using Stream.iterate(1, n -> n * 2). Take first 8 elements. Print them: 1 2 4 8 16 32 64 128.

---

## Stream API - Intermediate Operations

**Exercise 36.**
Create a list [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]. Use filter() to keep only even numbers. Collect to list and print.

**Exercise 37.**
Create a list of names. Use map() to convert all to uppercase. Collect and print.

**Exercise 38.**
Create a list of names. Use map() to get the length of each name. Collect to List<Integer> and print.

**Exercise 39.**
Create a List<List<Integer>> with 3 inner lists. Use flatMap() to flatten into one List<Integer>. Print the result.

**Exercise 40.**
Create a list of sentences. Use flatMap() with Arrays.stream(sentence.split(" ")) to get a flat list of all words. Print all words.

**Exercise 41.**
Create a list [5, 2, 8, 1, 9, 3]. Use sorted() to sort ascending. Then sort descending using sorted(Comparator.reverseOrder()). Print both.

**Exercise 42.**
Create a list of Student objects. Use sorted() with Comparator.comparingInt() to sort by marks. Print sorted names.

**Exercise 43.**
Create a list [1, 2, 2, 3, 3, 3, 4, 4]. Use distinct() to remove duplicates. Print result.

**Exercise 44.**
Create a list of 10 numbers. Use limit(5) to take first 5. Use skip(3) to skip first 3. Combine skip(2).limit(3) to get elements at index 2, 3, 4. Print each result.

**Exercise 45.**
Create a list of names. Use peek() before and after filter() to print elements at each stage. Use this for debugging.

---

## Stream API - Terminal Operations

**Exercise 46.**
Create a list of numbers. Use count() to count how many are greater than 5.

**Exercise 47.**
Create a list of integers. Use max(Comparator.naturalOrder()) and min(Comparator.naturalOrder()). Print both results using get().

**Exercise 48.**
Create a list of names. Use findFirst() after filtering names that start with "A". Print the result using orElse("Not found").

**Exercise 49.**
Create a list of numbers. Use anyMatch() to check if any is negative. Use allMatch() to check if all are positive. Use noneMatch() to check if none is zero. Print all three results.

**Exercise 50.**
Create a list [1, 2, 3, 4, 5]. Use reduce() to find the sum. Use reduce() with identity 1 to find the product. Print both.

**Exercise 51.**
Create a list of names. Use collect(Collectors.toList()) and collect(Collectors.toSet()). Print both and notice set removes duplicates.

**Exercise 52.**
Create a list of names. Use Collectors.joining(", ") to join them into one string. Use Collectors.joining(", ", "[", "]") to add prefix and suffix. Print both.

**Exercise 53.**
Create a list of names. Use collect(Collectors.toMap(name -> name, String::length)). Print the resulting map.

**Exercise 54.**
Create a list of strings. Use mapToInt(String::length) to get an IntStream. Call sum(), average(), min(), max() on it. Print all results.

---

## Stream API - Grouping and Collecting

**Exercise 55.**
Create a list of names. Use Collectors.groupingBy(name -> name.charAt(0)) to group by first character. Print the resulting map.

**Exercise 56.**
Create a list of Student objects with name and marks. Use groupingBy to group by grade: A=90+, B=75-89, C=below 75. Print each group.

**Exercise 57.**
Use groupingBy with Collectors.counting() as downstream collector to count how many students in each grade group from Exercise 56.

**Exercise 58.**
Create a list of names. Use Collectors.partitioningBy(name -> name.length() > 4) to split into two groups. Print both groups.

**Exercise 59.**
Create a list of integers. Use Collectors.summarizingInt(n -> n) to get summary statistics. Print min, max, sum, average, count.

---

## Optional

**Exercise 60.**
Create an Optional using Optional.of("Arman"). Create another using Optional.ofNullable(null). Create another using Optional.empty(). Print isPresent() for all three.

**Exercise 61.**
Create Optional.of("Arman"). Call get() and print. Create Optional.empty(). Call get() on it. See the NoSuchElementException.

**Exercise 62.**
Create Optional.empty(). Use orElse("Default") and print. Use orElseGet(() -> "From Supplier") and print. Write a comment explaining when to use orElseGet over orElse.

**Exercise 63.**
Create Optional.empty(). Use orElseThrow(() -> new RuntimeException("Not found")). Catch and print the exception message.

**Exercise 64.**
Create Optional.of("Arman"). Use ifPresent() to print the value. Create Optional.empty(). Use ifPresent() - nothing should happen.

**Exercise 65.**
Create Optional.of("arman"). Use map() to convert to uppercase. Print the result. Create Optional.empty(). Use map() on it. Show it returns empty Optional without error.

**Exercise 66.**
Create Optional.of(15). Use filter(age -> age >= 18). Print isPresent() - false. Create Optional.of(25). Use same filter. Print isPresent() - true.

**Exercise 67.**
Create a method findUser(int id) that returns Optional<String>. Return Optional.of("Arman") for id=1, Optional.empty() otherwise. Chain map() to uppercase and orElse("Guest"). Print for id=1 and id=99.

**Exercise 68.**
Use ifPresentOrElse() on a non-empty Optional to print the value, and on an empty Optional to print "No value found".

---

## Stream API - Java 9 Additions

**Exercise 69.**
Create a list [2, 4, 6, 7, 8, 10]. Use takeWhile(n -> n % 2 == 0). Print result - should stop at 7. Write a comment: does it skip 7 and continue or stop completely?

**Exercise 70.**
Use the same list from Exercise 69. Use dropWhile(n -> n % 2 == 0). Print result - should start from 7.

**Exercise 71.**
Create a stream using Stream.iterate(0, n -> n < 20, n -> n + 3). Print all values without using limit(). Compare with Java 8 version that needs limit().

---

## Immutable Collections (Java 9)

**Exercise 72.**
Create a List.of("Arman", "Priya", "Raj"). Print it. Try to add an element. Catch and print the UnsupportedOperationException.

**Exercise 73.**
Create a Set.of(1, 2, 3, 4, 5). Print size and contains(). Try adding duplicate values to Set.of() - see IllegalArgumentException.

**Exercise 74.**
Create a Map.of("name", "Arman", "age", 25). Print the values using get(). Try to put a new key. Catch the exception.

**Exercise 75.**
Create a mutable ArrayList with 3 elements. Use List.copyOf() to create an immutable copy. Add an element to original. Show that copy still has 3 elements.

---

## var Keyword (Java 10)

**Exercise 76.**
Declare 5 local variables using var - String, int, double, boolean, and ArrayList<String>. Print each one. Hover over them in IDE to confirm the inferred types.

**Exercise 77.**
Use var in a for-each loop over a list of strings. Use var in a regular for loop counter. Print elements in both.

**Exercise 78.**
Try to declare var without initialization. See the compile error. Try to declare var as a method parameter. See the compile error.

**Exercise 79.**
Create a complex type like HashMap<String, List<Integer>> using var. Show how it reduces verbosity compared to writing the full type.

---

## String Methods (Java 11)

**Exercise 80.**
Test isBlank() on "", " ", "\t", "hello". Print results. Compare with isEmpty() on same strings.

**Exercise 81.**
Create a string "  Hello World  ". Call strip(), stripLeading(), stripTrailing(). Print each result and show the difference from trim().

**Exercise 82.**
Create a multiline string "Line1\nLine2\nLine3". Call lines() to get a stream. Count lines. Filter lines that contain "1". Collect and print.

**Exercise 83.**
Use repeat() to create a separator "=-" repeated 20 times. Use it to print a formatted header.

---

## Switch Expressions (Java 14)

**Exercise 84.**
Rewrite this old switch statement as a new switch expression:
```java
int day = 3;
String name;
switch(day) {
    case 1: name = "Monday"; break;
    case 2: name = "Tuesday"; break;
    case 3: name = "Wednesday"; break;
    default: name = "Other";
}
```

**Exercise 85.**
Create a switch expression for seasons. Spring = months 3,4,5. Summer = 6,7,8. Autumn = 9,10,11. Winter = 12,1,2. Use multiple labels in one case.

**Exercise 86.**
Create a switch expression where one case needs multiple statements. Use a block with yield to return the value.

**Exercise 87.**
Create a switch expression on a String. Handle "START", "STOP", "PAUSE" commands and return different messages. Add a default case.

---

## Records (Java 16)

**Exercise 88.**
Create a record Point(int x, int y). Create two Point objects. Print them - notice auto-generated toString. Call x() and y() accessors. Check equals() between two points with same values.

**Exercise 89.**
Create a record Student(String name, int rollNo, double marks). Create a list of 5 students. Use stream to filter students with marks > 80 and print their names using the accessor method.

**Exercise 90.**
Add a compact constructor to a record Circle(double radius) that throws IllegalArgumentException if radius is less than or equal to zero. Test it with valid and invalid values.

**Exercise 91.**
Add an instance method area() to the Circle record from Exercise 90. Call it and print the area.

**Exercise 92.**
Create a record Pair<A, B>(A first, B second). Create a Pair<String, Integer> and a Pair<Integer, String>. Print both.

**Exercise 93.**
Try to add a non-final instance field to a record. See the compile error. Write a comment explaining why records cannot have mutable state.

---

## Sealed Classes (Java 17)

**Exercise 94.**
Create a sealed class Shape that permits Circle, Rectangle, Triangle. Make all three final classes extending Shape. Each has an area() method. Create one of each and call area().

**Exercise 95.**
Use a switch expression on a Shape from Exercise 94. Handle all three cases. Notice you do not need a default because compiler knows all permitted subclasses.

**Exercise 96.**
Create a sealed interface Result<T> that permits Success<T> and Failure<T>. Make both as records. Create a method that returns Success or Failure. Handle both using switch expression.

---

## Text Blocks (Java 15)

**Exercise 97.**
Rewrite this string using a text block:
```java
String json = "{\n    \"name\": \"Arman\",\n    \"age\": 25\n}";
```

**Exercise 98.**
Create a text block for an HTML snippet with proper indentation. Print it and verify the indentation is correct.

**Exercise 99.**
Create a text block for a SQL query with multiple lines. Use it in a print statement.

---

## Date and Time API (Java 8)

**Exercise 100.**
Create LocalDate for today. Print year, month, monthValue, dayOfMonth, dayOfWeek. Add 10 days and print. Subtract 2 months and print.

**Exercise 101.**
Create LocalTime for now. Print hour, minute, second. Add 90 minutes and print. Check if 09:00 isBefore 17:00.

**Exercise 102.**
Create LocalDateTime combining a specific date and time. Convert to LocalDate and LocalTime separately. Add 2 hours and 30 minutes. Print the result.

**Exercise 103.**
Create ZonedDateTime for Asia/Kolkata timezone. Convert it to America/New_York timezone using withZoneSameInstant(). Print both and notice the time difference.

**Exercise 104.**
Create two LocalTime objects - 09:00 and 17:30. Calculate Duration between them. Print hours and minutes.

**Exercise 105.**
Create a birthdate using LocalDate. Calculate Period between birthdate and today. Print years, months, days separately.

**Exercise 106.**
Create a DateTimeFormatter with pattern "dd/MM/yyyy". Format today's date using it. Parse the string "25/12/2024" back to LocalDate using the same formatter.

**Exercise 107.**
Use Instant.now() to measure how long a piece of code takes to run. Use Duration.between(start, end).toMillis() to print elapsed time in milliseconds.
