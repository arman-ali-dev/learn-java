# Java Fundamentals & Core Syntax - Complete Notes

---

## PART 1 - JAVA PROGRAM STRUCTURE

Every Java program has a fixed structure. You cannot just write code anywhere. There are rules.

### The basic skeleton

```java
package com.myapp.utils; // optional - tells which package this file belongs to

import java.util.Scanner;  // import - bring in classes from other packages
import java.util.ArrayList;

public class HelloWorld {  // class name must match file name exactly

    public static void main(String[] args) {  // entry point - JVM starts here
        System.out.println("Hello, World!");
    }
}
```

### package

A package is just a folder for your Java files. It organizes your code and avoids naming conflicts.

If you have two classes both named `User` in different packages, Java knows which is which because of the package name.

```java
package com.company.models;   // this file is in models folder
package com.company.services; // this file is in services folder
```

Convention: package names are always lowercase, usually your domain name reversed: `com.google.search`, `org.apache.commons`.

You do NOT have to declare a package. If you skip it, the class goes into the "default package". Fine for small practice programs, bad for real projects.

### import

import tells Java where to find classes you are using. Without it, you would have to write the full name every single time.

```java
// without import
java.util.Scanner sc = new java.util.Scanner(System.in); // painful

// with import
import java.util.Scanner;
Scanner sc = new Scanner(System.in); // clean
```

`java.lang` is automatically imported. That is why you can use `String`, `System`, `Math`, `Integer` without importing them - they are all in `java.lang`.

```java
// these do NOT need import - java.lang is auto-imported
String name = "Arman";
System.out.println(name);
Math.abs(-5);
Integer x = 10;
```

Wildcard import: `import java.util.*` imports everything from java.util. Works but bad practice - it is not clear what your code actually depends on. Always import specific classes.

### class

Everything in Java must be inside a class. There is no code outside a class. The class name must exactly match the file name including case.

File named `BankAccount.java` → class must be named `BankAccount`.

```java
public class BankAccount { // class name = file name
    // all code goes here
}
```

### main method

`public static void main(String[] args)` is the entry point. When you run a Java program, JVM looks for this exact signature and starts execution from here.

Breaking it down:

- `public` - JVM needs to call it from outside, so it must be public
- `static` - JVM calls it without creating an object of the class
- `void` - returns nothing to JVM
- `String[] args` - command line arguments passed when running the program

```java
// running with arguments
// java Main hello world

public static void main(String[] args) {
    System.out.println(args[0]); // hello
    System.out.println(args[1]); // world
    System.out.println(args.length); // 2
}
```

### Printing output

```java
System.out.println("Hello");     // prints and moves to new line
System.out.print("Hello");       // prints WITHOUT new line
System.out.printf("Name: %s, Age: %d%n", "Arman", 22); // formatted output

// printf format specifiers
// %s = String
// %d = integer
// %f = float/double
// %n = newline (platform independent, preferred over \n in printf)
// %b = boolean
// %.2f = float with 2 decimal places
System.out.printf("Balance: %.2f%n", 1234.5678); // Balance: 1234.57
```

---

## PART 2 - PRIMITIVE DATA TYPES

Java has 8 primitive types. These are NOT objects - they directly store values in memory. This makes them fast.

### All 8 primitives

```java
byte   b = 127;          // 1 byte,  range: -128 to 127
short  s = 32000;        // 2 bytes, range: -32,768 to 32,767
int    i = 2147483647;   // 4 bytes, range: -2 billion to 2 billion (most common integer type)
long   l = 9876543210L;  // 8 bytes, range: very large numbers - add L suffix
float  f = 3.14f;        // 4 bytes, ~7 decimal digits precision - add f suffix
double d = 3.14159265;   // 8 bytes, ~15 decimal digits precision (most common decimal type)
char   c = 'A';          // 2 bytes, a single character, uses single quotes
boolean flag = true;     // 1 bit in practice, only true or false
```

### Default values

Instance variables (fields in a class) get default values automatically. Local variables inside methods do NOT get defaults - you must initialize them.

```java
class Demo {
    int x;        // default: 0
    double d;     // default: 0.0
    boolean flag; // default: false
    char c;       // default: '\u0000' (null character)
    String name;  // default: null (String is not primitive, it is an object)

    void method() {
        int local; // NO default value
        // System.out.println(local); // COMPILE ERROR - variable might not be initialized
        int local2 = 0; // must initialize before use
    }
}
```

### Important points about each type

**int** - use this for all integers unless you have a reason not to. Do not overthink byte or short.

**long** - when your number can exceed 2 billion (IDs, file sizes, timestamps). Always add `L` at the end.

```java
long population = 7800000000L; // 7.8 billion - needs long
long timestamp = System.currentTimeMillis(); // milliseconds since 1970 - always long
```

**double** - use this for all decimal numbers. float has less precision and is rarely used in modern Java.

```java
double price = 99.99;
double pi = 3.141592653589793;

// NEVER do this with money - floating point cannot represent all decimals exactly
double money = 0.1 + 0.2;
System.out.println(money); // 0.30000000000000004 - floating point error!
// Use BigDecimal for money
```

**char** - single character, uses single quotes. Behind the scenes it is a number (Unicode value).

```java
char grade = 'A';
char newline = '\n';
char tab = '\t';

// char is actually a number
char c = 65;
System.out.println(c); // prints A (65 is ASCII code for A)

// char arithmetic
char next = (char)(grade + 1); // 'B'
```

**boolean** - only true or false. No 0 or 1 trick like in C. Java is strict.

```java
boolean isLoggedIn = true;
boolean isEmpty = false;

// int result = 1; if (result) { } // COMPILE ERROR - Java does not allow this
```

### Literals and underscores (Java 7+)

Long numbers are hard to read. Java lets you use underscores as separators.

```java
int million = 1_000_000;
long creditCard = 1234_5678_9012_3456L;
double pi = 3.141_592_653;
```

---

## PART 3 - TYPE CASTING

Casting = converting one type to another.

### Widening (Implicit) - automatic, safe

Going from smaller type to larger type. No data is lost. Java does this automatically.

```java
//  byte -> short -> int -> long -> float -> double
//  (smaller)                              (larger)

int x = 100;
long y = x;       // automatic - int fits inside long, no data lost
double d = x;     // automatic - int 100 becomes double 100.0

byte b = 42;
int i = b;        // automatic
double big = b;   // automatic

// char can be widened to int (gives Unicode value)
char c = 'A';
int ascii = c;    // 65
```

### Narrowing (Explicit) - manual, risky

Going from larger type to smaller type. Data might be lost. You must tell Java you know the risk by putting the target type in parentheses.

```java
double d = 9.99;
int i = (int) d;   // you must write (int) - explicit cast
System.out.println(i); // 9 - decimal part is chopped off (not rounded)

long bigNumber = 1234567890123L;
int small = (int) bigNumber; // data will be lost - int cannot hold this number
System.out.println(small);   // some garbage value due to overflow

// char and int
int code = 65;
char c = (char) code; // 'A'
```

### Casting with expressions

```java
int a = 5, b = 2;
double result = a / b;              // WRONG - both are int, so int division happens first: 5/2 = 2, then 2.0
double result2 = (double) a / b;    // CORRECT - cast a to double first, then 5.0/2 = 2.5
double result3 = a / (double) b;    // also CORRECT - 5/2.0 = 2.5
double result4 = (double)(a / b);   // WRONG - int division 5/2=2 first, then cast to 2.0
```

This is one of the most common mistakes in Java. Always cast before the division if you want decimal result.

---

## PART 4 - OPERATORS

### Arithmetic operators

```java
int a = 10, b = 3;

a + b   // 13 - addition
a - b   // 7  - subtraction
a * b   // 30 - multiplication
a / b   // 3  - integer division (decimal part dropped)
a % b   // 1  - modulo (remainder after division)

// Modulo use cases
int n = 17;
System.out.println(n % 2 == 0); // false - odd number check
System.out.println(n % 5);      // 2 - last digit in base 5
// Modulo is used constantly in DSA problems
```

### Increment and Decrement

```java
int x = 5;

x++;  // post-increment: use x then increment. x becomes 6
++x;  // pre-increment: increment first then use. x becomes 7
x--;  // post-decrement: use x then decrement
--x;  // pre-decrement: decrement first then use

// The difference matters when used in expressions
int a = 5;
int b = a++;  // b = 5, then a becomes 6 (b gets OLD value)
int c = ++a;  // a becomes 7, then c = 7 (c gets NEW value)
```

### Relational operators - returns boolean

```java
a == b  // equal to
a != b  // not equal to
a > b   // greater than
a < b   // less than
a >= b  // greater than or equal to
a <= b  // less than or equal to

// WARNING: == for objects compares memory address, not content
String s1 = new String("hello");
String s2 = new String("hello");
System.out.println(s1 == s2);       // false - different objects in memory
System.out.println(s1.equals(s2));  // true - same content
// Always use .equals() for String comparison
```

### Logical operators

```java
boolean a = true, b = false;

a && b   // AND - true only if BOTH are true. Short-circuits: if a is false, b is NOT evaluated
a || b   // OR  - true if AT LEAST ONE is true. Short-circuits: if a is true, b is NOT evaluated
!a       // NOT - flips the boolean

// Short-circuit is useful and important
int x = 0;
if (x != 0 && 10/x > 1) { } // safe - if x is 0, second part is never evaluated (no division by zero)
if (x == 0 || someExpensiveMethod()) { } // if x is 0, someExpensiveMethod is never called (optimization)

// Bitwise logical (no short-circuit - both sides always evaluated)
a & b    // AND without short-circuit
a | b    // OR without short-circuit
a ^ b    // XOR - true if exactly ONE is true
```

### Assignment operators

```java
int x = 10;

x += 5;   // x = x + 5 = 15
x -= 3;   // x = x - 3 = 12
x *= 2;   // x = x * 2 = 24
x /= 4;   // x = x / 4 = 6
x %= 4;   // x = x % 4 = 2
```

### Ternary operator

Shorthand for if-else when you need to assign one of two values.

```java
// syntax: condition ? valueIfTrue : valueIfFalse
int age = 20;
String status = age >= 18 ? "Adult" : "Minor";

int a = 5, b = 10;
int max = a > b ? a : b; // max = 10

// Can be nested but avoid it - becomes unreadable
String grade = marks >= 90 ? "A" : marks >= 80 ? "B" : marks >= 70 ? "C" : "F";
// Better to use if-else for complex conditions
```

### Bitwise operators

Used in low-level programming, flags, and optimization. Also appears in DSA.

```java
int a = 5;  // binary: 0101
int b = 3;  // binary: 0011

a & b   // AND:  0101 & 0011 = 0001 = 1  (both bits must be 1)
a | b   // OR:   0101 | 0011 = 0111 = 7  (at least one bit is 1)
a ^ b   // XOR:  0101 ^ 0011 = 0110 = 6  (bits must be different)
~a      // NOT:  flips all bits of a      = -6 (two's complement)
a << 1  // left shift:  0101 -> 1010 = 10  (multiply by 2)
a >> 1  // right shift: 0101 -> 0010 = 2   (divide by 2)
a >>> 1 // unsigned right shift: fills with 0 even for negative numbers

// Practical DSA uses of bitwise
int n = 6;
System.out.println(n & 1);         // 0 = even, 1 = odd
System.out.println(n >> 1);        // n / 2 = 3 (faster than division)
System.out.println(n << 1);        // n * 2 = 12 (faster than multiplication)
System.out.println(n ^ n);         // 0 (XOR with itself = 0)
System.out.println(n ^ 0);         // n (XOR with 0 = same number)
```

### Operator precedence (simplified, high to low)

```
() brackets
++ -- (unary), !
* / %
+ -
< > <= >=
== !=
&&
||
? : (ternary)
= += -= *= /= %=
```

When in doubt, use parentheses. Clarity beats cleverness.

---

## PART 5 - CONTROL FLOW

### if / else if / else

```java
int marks = 85;

if (marks >= 90) {
    System.out.println("A grade");
} else if (marks >= 80) {
    System.out.println("B grade");  // this runs
} else if (marks >= 70) {
    System.out.println("C grade");
} else {
    System.out.println("Fail");
}

// Single line - no braces needed (but always use braces, avoids bugs)
if (marks > 50) System.out.println("Pass"); // works but avoid this style
```

### switch

Use switch when you are comparing ONE variable against multiple EXACT values.

```java
int day = 3;
switch (day) {
    case 1:
        System.out.println("Monday");
        break;  // WITHOUT break, it falls through to the next case
    case 2:
        System.out.println("Tuesday");
        break;
    case 3:
        System.out.println("Wednesday");
        break;
    default:    // runs when no case matches (like else)
        System.out.println("Invalid day");
}

// Fall-through (intentional, without break)
int month = 4;
switch (month) {
    case 1: case 3: case 5: case 7: case 8: case 10: case 12:
        System.out.println("31 days");
        break;
    case 4: case 6: case 9: case 11:
        System.out.println("30 days");  // month 4 lands here
        break;
    case 2:
        System.out.println("28 or 29 days");
        break;
}
```

switch works with: `int`, `byte`, `short`, `char`, `String` (Java 7+), `enum`.
switch does NOT work with: `long`, `float`, `double`, `boolean`.

### switch expression (Java 14+)

Cleaner syntax. No need for break. No fall-through by default.

```java
int day = 3;
String dayName = switch (day) {
    case 1 -> "Monday";
    case 2 -> "Tuesday";
    case 3 -> "Wednesday";
    case 4 -> "Thursday";
    case 5 -> "Friday";
    default -> "Weekend";
};
System.out.println(dayName); // Wednesday
```

### When to use if-else vs switch

Use switch when comparing one variable against 3+ exact values. Use if-else for ranges, conditions, boolean checks.

```java
// Good for switch - exact values
switch (command) {
    case "start" -> startEngine();
    case "stop"  -> stopEngine();
    case "pause" -> pauseEngine();
}

// Bad for switch - use if-else for ranges
if (marks >= 90) { ... }      // cannot do this in switch
else if (marks >= 80) { ... }
```

---

## PART 6 - LOOPS

### for loop

Use when you know exactly how many times to loop.

```java
// basic for loop
for (int i = 0; i < 5; i++) {
    System.out.println(i); // 0 1 2 3 4
}

// three parts: initialization; condition; update
// all three are optional
for (;;) { // infinite loop - same as while(true)
    // runs forever
}

// multiple variables
for (int i = 0, j = 10; i < j; i++, j--) {
    System.out.println(i + " " + j);
}

// going backwards
for (int i = 10; i >= 0; i--) {
    System.out.println(i); // 10 9 8 7 ... 0
}
```

### while loop

Use when you do not know how many iterations you need - keep going until a condition becomes false.

```java
int n = 1;
while (n <= 5) {
    System.out.println(n);
    n++;
}

// real use case - reading until valid input
Scanner sc = new Scanner(System.in);
int age = -1;
while (age < 0 || age > 120) {
    System.out.println("Enter valid age:");
    age = sc.nextInt();
}
```

### do-while loop

Same as while but the body runs AT LEAST ONCE before checking the condition. Condition is checked at the end.

```java
int n = 10;
do {
    System.out.println(n); // prints 10 even though 10 < 5 is false
    n++;
} while (n < 5);

// Good for: menu systems where you show the menu at least once
do {
    showMenu();
    int choice = getChoice();
    processChoice(choice);
} while (choice != 0); // 0 = exit
```

### enhanced for loop (for-each)

Clean way to iterate over arrays and collections. Cannot access index, cannot go backwards, cannot skip elements.

```java
int[] numbers = {10, 20, 30, 40, 50};

for (int num : numbers) {
    System.out.println(num); // 10 20 30 40 50
}

String[] names = {"Arman", "Priya", "Raj"};
for (String name : names) {
    System.out.println(name.toUpperCase());
}

// also works with any collection
List<Integer> list = Arrays.asList(1, 2, 3, 4, 5);
for (int val : list) {
    System.out.println(val);
}
```

### break and continue

```java
// break - exit the loop immediately
for (int i = 0; i < 10; i++) {
    if (i == 5) break;
    System.out.println(i); // 0 1 2 3 4
}

// continue - skip this iteration, go to next
for (int i = 0; i < 10; i++) {
    if (i % 2 == 0) continue; // skip even numbers
    System.out.println(i); // 1 3 5 7 9
}

// break with labels - break out of nested loops
outer:
for (int i = 0; i < 3; i++) {
    for (int j = 0; j < 3; j++) {
        if (i == 1 && j == 1) break outer; // exits BOTH loops
        System.out.println(i + "," + j);
    }
}
```

### Nested loops

```java
// multiplication table
for (int i = 1; i <= 5; i++) {
    for (int j = 1; j <= 5; j++) {
        System.out.printf("%4d", i * j);
    }
    System.out.println();
}

// pattern printing (common in interviews)
// right triangle
for (int i = 1; i <= 5; i++) {
    for (int j = 1; j <= i; j++) {
        System.out.print("* ");
    }
    System.out.println();
}
// *
// * *
// * * *
// * * * *
// * * * * *
```

---

## PART 7 - ARRAYS

An array is a fixed-size container that holds multiple values of the same type. Size cannot change after creation.

### Declaring and creating arrays

```java
// Declaration (no memory allocated yet)
int[] numbers;
String[] names;

// Creation (memory allocated, default values assigned)
numbers = new int[5];     // creates array of 5 integers, all initialized to 0

// Declaration + creation together
int[] scores = new int[10];
String[] cities = new String[3]; // 3 null values

// Declaration + creation + initialization
int[] primes = {2, 3, 5, 7, 11};    // size is automatically 5
int[] evens = new int[]{2, 4, 6, 8}; // same thing, explicit new

// Access elements (index starts at 0)
primes[0] = 2; // first element
primes[4] = 11; // fifth element (last, since size is 5)
// primes[5]   // ArrayIndexOutOfBoundsException - no index 5 in size-5 array

// Length
System.out.println(primes.length); // 5 (note: length is a property, not a method - no parentheses)
```

### Array traversal

```java
int[] arr = {10, 20, 30, 40, 50};

// Method 1: for loop with index
for (int i = 0; i < arr.length; i++) {
    System.out.println("Index " + i + ": " + arr[i]);
}

// Method 2: for-each (when you don't need index)
for (int val : arr) {
    System.out.println(val);
}

// Method 3: while loop
int i = 0;
while (i < arr.length) {
    System.out.println(arr[i]);
    i++;
}
```

### Common array operations

```java
import java.util.Arrays;

int[] arr = {5, 2, 8, 1, 9, 3};

// Sort
Arrays.sort(arr); // {1, 2, 3, 5, 8, 9} - modifies original

// Search (array must be sorted first)
int index = Arrays.binarySearch(arr, 5); // returns index of 5 = 3

// Copy
int[] copy = Arrays.copyOf(arr, arr.length);   // full copy
int[] partial = Arrays.copyOfRange(arr, 1, 4); // elements at index 1, 2, 3

// Fill
int[] filled = new int[5];
Arrays.fill(filled, 7); // {7, 7, 7, 7, 7}

// Compare
int[] a = {1, 2, 3};
int[] b = {1, 2, 3};
System.out.println(a == b);             // false - compares addresses
System.out.println(Arrays.equals(a, b)); // true - compares values

// Print (arrays don't print nicely with System.out.println)
System.out.println(arr);                  // [I@7b23ec81 - garbage
System.out.println(Arrays.toString(arr)); // [1, 2, 3, 5, 8, 9] - readable
```

### Multi-dimensional arrays

A 2D array is an array of arrays. Think of it as a table with rows and columns.

```java
// 2D array - 3 rows, 4 columns
int[][] matrix = new int[3][4];

// initialization
int[][] grid = {
    {1, 2, 3},
    {4, 5, 6},
    {7, 8, 9}
};

// access
System.out.println(grid[0][0]); // 1 - row 0, col 0
System.out.println(grid[1][2]); // 6 - row 1, col 2

// row count
System.out.println(grid.length);    // 3 rows
// column count
System.out.println(grid[0].length); // 3 columns in row 0

// traversal
for (int row = 0; row < grid.length; row++) {
    for (int col = 0; col < grid[row].length; col++) {
        System.out.print(grid[row][col] + " ");
    }
    System.out.println();
}

// for-each on 2D
for (int[] row : grid) {
    for (int val : row) {
        System.out.print(val + " ");
    }
    System.out.println();
}

// print nicely
System.out.println(Arrays.deepToString(grid)); // [[1, 2, 3], [4, 5, 6], [7, 8, 9]]

// Jagged array - rows can have different lengths
int[][] jagged = new int[3][];
jagged[0] = new int[2];
jagged[1] = new int[4];
jagged[2] = new int[1];
```

---

## PART 8 - METHODS

A method is a block of code that does one specific job. You define it once and call it whenever needed.

### Defining and calling methods

```java
public class Calculator {

    // method that returns a value
    int add(int a, int b) {
        return a + b; // return exits the method and sends back a value
    }

    // method that returns nothing
    void printResult(int result) {
        System.out.println("Result: " + result);
        // no return needed, but you can write bare return; to exit early
    }

    // method with no parameters
    void greet() {
        System.out.println("Hello");
    }

    public static void main(String[] args) {
        Calculator calc = new Calculator(); // create object to call non-static methods

        int sum = calc.add(5, 3);           // call method, store result
        calc.printResult(sum);              // call method, pass result
        calc.greet();                       // call method, no arguments
    }
}
```

### Static methods

Static methods belong to the class, not to any object. You can call them without creating an object.

```java
public class MathUtils {

    // static method - no need to create MathUtils object to use this
    public static int square(int n) {
        return n * n;
    }

    public static double circleArea(double radius) {
        return Math.PI * radius * radius;
    }

    public static void main(String[] args) {
        int result = MathUtils.square(5); // called with class name, no object needed
        System.out.println(result); // 25

        // Inside the same class, can call directly
        System.out.println(square(4)); // 16
    }
}

// main() itself is static - that is why you can call static methods from main directly
```

### Method overloading

Same method name, different parameter list. Java figures out which one to call based on the arguments you pass.

```java
class Printer {

    void print(int n) {
        System.out.println("Printing int: " + n);
    }

    void print(double d) {
        System.out.println("Printing double: " + d);
    }

    void print(String s) {
        System.out.println("Printing String: " + s);
    }

    void print(int a, int b) {
        System.out.println("Printing two ints: " + a + ", " + b);
    }
}

Printer p = new Printer();
p.print(5);           // print(int n) is called
p.print(3.14);        // print(double d) is called
p.print("Hello");     // print(String s) is called
p.print(1, 2);        // print(int a, int b) is called
```

Overloading is resolved at compile time based on parameter types. The return type alone cannot differentiate overloaded methods.

```java
// COMPILE ERROR - same parameters, only return type differs
int getValue() { return 1; }
double getValue() { return 1.0; } // ERROR - Java cannot distinguish these
```

### Varargs - variable number of arguments

```java
// ... means "zero or more int arguments"
int sum(int... numbers) {
    int total = 0;
    for (int n : numbers) total += n;
    return total;
}

sum(1, 2);             // works
sum(1, 2, 3, 4, 5);   // works
sum();                 // works - zero arguments
sum(new int[]{1,2,3}); // can also pass an array
```

### Pass by value

Java always passes by value. For primitives, a copy of the value is passed. For objects, a copy of the reference is passed - meaning you can modify the object, but you cannot make the variable point to a different object.

```java
void doubleValue(int n) {
    n = n * 2; // modifies local copy, not original
}

void addToArray(int[] arr) {
    arr[0] = 999; // modifies the actual array (reference was copied, object is same)
}

int x = 5;
doubleValue(x);
System.out.println(x); // still 5 - original unchanged

int[] arr = {1, 2, 3};
addToArray(arr);
System.out.println(arr[0]); // 999 - array was modified through reference
```

---

## PART 9 - SCOPE AND LIFETIME OF VARIABLES

### Local variables

Declared inside a method (or any block). Exist only while that block is executing. Destroyed when the block ends.

```java
void method() {
    int x = 10; // local variable, born here

    if (true) {
        int y = 20;  // also local, but only exists inside this if block
        System.out.println(x); // can access x from outer scope
        System.out.println(y); // can access y here
    }

    // System.out.println(y); // COMPILE ERROR - y is out of scope here
    System.out.println(x); // x still alive here
} // x dies here
```

### Instance variables (fields)

Declared inside a class but outside any method. Belong to each object. Each object has its own copy.

```java
class Person {
    String name;   // instance variable - each Person has their own name
    int age;       // instance variable - each Person has their own age

    void introduce() {
        System.out.println("I am " + name + ", age " + age);
        // can access name and age without passing them - they belong to this object
    }
}

Person p1 = new Person();
p1.name = "Arman";
p1.age = 22;

Person p2 = new Person();
p2.name = "Priya";
p2.age = 24;

// p1.name and p2.name are separate - each object has its own copy
```

### Static variables (class variables)

Declared with `static` keyword inside a class. Only ONE copy exists, shared by ALL objects of that class.

```java
class Counter {
    static int count = 0; // ONE copy, shared by all Counter objects
    int id;               // instance variable - each object has its own

    Counter() {
        count++; // every time a Counter is created, shared count increases
        id = count;
    }

    static void showCount() {
        System.out.println("Total counters: " + count);
    }
}

Counter c1 = new Counter(); // count = 1
Counter c2 = new Counter(); // count = 2
Counter c3 = new Counter(); // count = 3

System.out.println(c1.id);      // 1
System.out.println(c2.id);      // 2
Counter.showCount();             // Total counters: 3
System.out.println(Counter.count); // 3 - access with class name (preferred)
```

### Variable shadowing

Local variable with same name as instance variable hides the instance variable. Use `this` to access instance variable.

```java
class Person {
    String name = "Arman"; // instance variable

    void setName(String name) { // parameter named same as instance variable
        this.name = name; // this.name = instance variable, name = parameter
    }

    void greet() {
        String name = "Local"; // local variable shadows instance variable
        System.out.println(name);      // "Local" - local variable
        System.out.println(this.name); // "Arman" - instance variable
    }
}
```

---

## PART 10 - STRING BASICS

String is not a primitive - it is an object. But it is used so often that Java gives it special treatment.

### Creating Strings

```java
String s1 = "Hello";           // string literal - stored in String pool
String s2 = new String("Hello"); // new object in heap - avoid this

// String pool: Java reuses string literals to save memory
String a = "Hello";
String b = "Hello";
System.out.println(a == b);       // true - same object from pool
System.out.println(a == s2);      // false - s2 is separate object in heap
System.out.println(a.equals(s2)); // true - same content

// ALWAYS use .equals() to compare string content, never ==
```

### String is immutable

Once created, a String cannot be changed. Every operation that "modifies" a String actually creates a new String.

```java
String s = "Hello";
s.toUpperCase(); // creates NEW string "HELLO" - original s is unchanged
System.out.println(s); // still "Hello"

s = s.toUpperCase(); // now s points to the new "HELLO" string
System.out.println(s); // "HELLO"
```

### Important String methods

```java
String s = "  Hello, World!  ";

// Case
s.toUpperCase()           // "  HELLO, WORLD!  "
s.toLowerCase()           // "  hello, world!  "

// Whitespace
s.trim()                  // "Hello, World!" - removes leading/trailing spaces
s.strip()                 // same as trim() but handles Unicode spaces too (Java 11+)
s.stripLeading()          // removes only leading spaces
s.stripTrailing()         // removes only trailing spaces

// Checking content
s.length()                // 17 - total characters including spaces
s.isEmpty()               // false - isEmpty() returns true only for "" (length 0)
s.isBlank()               // false - isBlank() returns true for "" or whitespace-only strings (Java 11+)
s.contains("World")       // true - checks if substring exists
s.startsWith("  Hello")   // true
s.endsWith("  ")          // true

// Finding characters
s.indexOf('o')            // index of first 'o'
s.lastIndexOf('o')        // index of last 'o'
s.indexOf("World")        // index where "World" starts
// returns -1 if not found

// Extracting parts
s.charAt(2)               // character at index 2 = 'H' (after 2 spaces)
s.substring(2)            // from index 2 to end: "Hello, World!  "
s.substring(2, 7)         // from index 2 to 6 (end is exclusive): "Hello"

// Modifying (returns new String)
s.replace('l', 'L')                // "  HeLLo, WorLd!  "
s.replace("World", "Java")         // "  Hello, Java!  "
s.replaceAll("\\s+", " ")          // replace multiple spaces with one (uses regex)

// Splitting
String csv = "Arman,Priya,Raj,Amit";
String[] parts = csv.split(",");    // ["Arman", "Priya", "Raj", "Amit"]
String[] limited = csv.split(",", 2); // ["Arman", "Priya,Raj,Amit"] - max 2 parts

// Joining
String joined = String.join("-", "a", "b", "c"); // "a-b-c"
String.join(", ", parts); // "Arman, Priya, Raj, Amit"

// Conversion
String.valueOf(42)        // "42" - int to String
String.valueOf(3.14)      // "3.14"
String.valueOf(true)      // "true"
Integer.parseInt("42")    // 42 - String to int
Double.parseDouble("3.14") // 3.14 - String to double

// Comparison
"hello".equals("HELLO")           // false - case sensitive
"hello".equalsIgnoreCase("HELLO") // true
"apple".compareTo("banana")       // negative (apple comes before banana)
```

### String concatenation

```java
String name = "Arman";
int age = 22;

// + operator - creates many intermediate String objects (inefficient in loops)
String s1 = "Name: " + name + ", Age: " + age;

// String.format - cleaner for complex strings
String s2 = String.format("Name: %s, Age: %d", name, age);

// Concatenating in loops - NEVER use + in a loop
String result = "";
for (int i = 0; i < 1000; i++) {
    result += i; // creates 1000 new String objects - very slow
}
// Use StringBuilder instead (see next section)
```

### StringBuilder

Mutable string. Use when you need to build a string step by step, especially in loops.

```java
StringBuilder sb = new StringBuilder();

sb.append("Hello");       // Hello
sb.append(", ");          // Hello,
sb.append("World");       // Hello, World
sb.append("!");           // Hello, World!

sb.insert(5, " Java");    // Hello Java, World!
sb.delete(5, 10);         // Hello, World! (deleted " Java")
sb.reverse();             // !dlroW ,olleH
sb.replace(0, 5, "Bye");  // Bye!dlroW ,
sb.deleteCharAt(3);       // Bye dlroW ,

// Get final String
String result = sb.toString(); // convert StringBuilder to String

// Useful methods
sb.length();              // current length
sb.charAt(0);             // char at index
sb.indexOf("World");      // find substring
sb.setCharAt(0, 'h');    // modify char at index

// In loops - much faster than String concatenation
StringBuilder builder = new StringBuilder();
for (int i = 0; i < 1000; i++) {
    builder.append(i); // reuses same object, no new String created
}
String finalResult = builder.toString();
```

### StringBuilder vs StringBuffer

```
StringBuilder   |  StringBuffer
----------------|----------------
Not thread safe |  Thread safe (synchronized methods)
Faster          |  Slower (due to synchronization overhead)
Use in most cases|  Use only in multithreaded code
Java 5+         |  Java 1.0+
```

In practice: always use StringBuilder unless you specifically need thread safety with strings.

---

## PART 11 - WRAPPER CLASSES

Every primitive type has a corresponding Wrapper class - an object version of the primitive. They live in `java.lang` so no import needed.

```
Primitive  |  Wrapper Class
-----------|---------------
int        |  Integer
double     |  Double
float      |  Float
long       |  Long
short      |  Short
byte       |  Byte
char       |  Character
boolean    |  Boolean
```

### Why do wrapper classes exist?

Collections like ArrayList cannot hold primitives - they hold objects. So you need Integer instead of int.

```java
ArrayList<int> list;      // COMPILE ERROR - cannot use primitive
ArrayList<Integer> list2; // CORRECT - use wrapper class
```

Also, wrapper classes come with useful utility methods.

### Autoboxing and Unboxing

Java automatically converts between primitive and wrapper - you do not have to do it manually.

```java
// Autoboxing - primitive to wrapper (automatic)
int x = 5;
Integer obj = x;          // Java automatically does: Integer obj = Integer.valueOf(x)
Integer obj2 = 42;        // direct assignment also works

// Unboxing - wrapper to primitive (automatic)
Integer wrapped = Integer.valueOf(100);
int primitive = wrapped;  // Java automatically does: int primitive = wrapped.intValue()

// In collections - autoboxing happens automatically
List<Integer> list = new ArrayList<>();
list.add(5);              // 5 is autoboxed to Integer.valueOf(5)
int val = list.get(0);    // Integer is unboxed back to int
```

### Wrapper class utility methods

```java
// Integer methods
Integer.parseInt("42")           // String to int = 42
Integer.valueOf(42)              // int to Integer object
Integer.toString(42)             // int to String = "42"
Integer.toBinaryString(10)       // int to binary String = "1010"
Integer.toHexString(255)         // int to hex String = "ff"
Integer.toOctalString(8)         // int to octal String = "10"
Integer.MAX_VALUE                // 2147483647
Integer.MIN_VALUE                // -2147483648
Integer.compare(5, 10)           // -1 (5 < 10)
Integer.max(5, 10)               // 10
Integer.min(5, 10)               // 5
Integer.sum(5, 10)               // 15
Integer.bitCount(7)              // 3 (count of 1 bits in 7 = 111)

// Double methods
Double.parseDouble("3.14")       // String to double
Double.isNaN(0.0 / 0.0)         // true (Not a Number)
Double.isInfinite(1.0 / 0.0)    // true
Double.MAX_VALUE
Double.MIN_VALUE                 // smallest POSITIVE double (not most negative)

// Character methods
Character.isLetter('A')          // true
Character.isDigit('5')           // true
Character.isWhitespace(' ')      // true
Character.isUpperCase('A')       // true
Character.isLowerCase('a')       // true
Character.toUpperCase('a')       // 'A'
Character.toLowerCase('A')       // 'a'
Character.isAlphabetic('A')      // true (better than isLetter for Unicode)

// Boolean methods
Boolean.parseBoolean("true")     // true
Boolean.parseBoolean("TRUE")     // true (case insensitive)
Boolean.parseBoolean("yes")      // false (only "true" string returns true)
```

### Autoboxing gotcha - null pointer

```java
Integer count = null; // wrapper can be null, primitive cannot
int value = count;    // NullPointerException - cannot unbox null

// Always check for null before unboxing
if (count != null) {
    int v = count;
}
```

### Autoboxing gotcha - == comparison

```java
Integer a = 127;
Integer b = 127;
System.out.println(a == b); // true - Java caches -128 to 127

Integer x = 128;
Integer y = 128;
System.out.println(x == y); // false - outside cache range, different objects
System.out.println(x.equals(y)); // true - always use equals() for wrapper comparison
```

Java caches Integer values from -128 to 127. For values outside this range, new objects are created. This is why `== 127` returns true but `== 128` returns false. This is a classic interview question.

---

## PART 12 - GENERICS BASICS

Generics let you write code that works with any type, while still being type-safe. The type is specified when you use the class/method.

### Why generics exist

Without generics:

```java
// old way - no generics
ArrayList list = new ArrayList();
list.add("Hello");
list.add(42);           // accidentally added an int!
String s = (String) list.get(0); // need explicit cast
String s2 = (String) list.get(1); // ClassCastException at runtime - crash!
```

With generics:

```java
// with generics
ArrayList<String> list = new ArrayList<>();
list.add("Hello");
// list.add(42); // COMPILE ERROR - caught at compile time, not runtime
String s = list.get(0); // no cast needed
```

The `<String>` tells Java: this list only holds Strings. Anything else is a compile error.

### Generic class

You write the class with a type parameter `<T>`. When someone uses your class, they replace `T` with the actual type.

```java
// T is a placeholder - could be any name, but T (Type), E (Element), K (Key), V (Value) are conventions
class Box<T> {
    private T content;

    void put(T item) {
        content = item;
    }

    T get() {
        return content;
    }

    boolean isEmpty() {
        return content == null;
    }
}

// Using the generic class
Box<String> stringBox = new Box<>();   // T becomes String
stringBox.put("Hello Java");
String s = stringBox.get();            // returns String, no cast needed
// stringBox.put(42);                  // COMPILE ERROR

Box<Integer> intBox = new Box<>();     // T becomes Integer
intBox.put(100);
int value = intBox.get();              // returns Integer, unboxed to int

Box<Double> doubleBox = new Box<>();
doubleBox.put(3.14);
```

### Generic method

A method can have its own type parameter, independent of the class.

```java
class Utils {

    // <T> before return type declares the type parameter for this method
    public static <T> void printArray(T[] arr) {
        for (T element : arr) {
            System.out.print(element + " ");
        }
        System.out.println();
    }

    // returns the first element of any array
    public static <T> T getFirst(T[] arr) {
        if (arr.length == 0) return null;
        return arr[0];
    }

    // swap two elements in an array
    public static <T> void swap(T[] arr, int i, int j) {
        T temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }
}

Integer[] ints = {1, 2, 3, 4, 5};
String[] strs = {"a", "b", "c"};
Double[] dbls = {1.1, 2.2, 3.3};

Utils.printArray(ints); // 1 2 3 4 5
Utils.printArray(strs); // a b c
Utils.printArray(dbls); // 1.1 2.2 3.3

Integer first = Utils.getFirst(ints); // 1
```

### Multiple type parameters

```java
class Pair<K, V> {  // two type parameters
    K first;
    V second;

    Pair(K first, V second) {
        this.first = first;
        this.second = second;
    }

    @Override
    public String toString() {
        return "(" + first + ", " + second + ")";
    }
}

Pair<String, Integer> entry = new Pair<>("age", 25);
System.out.println(entry); // (age, 25)
System.out.println(entry.first);  // age (String)
System.out.println(entry.second); // 25 (Integer)

Pair<Integer, Boolean> result = new Pair<>(404, false);
```

### Bounded type parameters

You can restrict what type can be used with `extends`.

```java
// T must be a Number or subclass of Number (Integer, Double, Float, etc.)
class NumberBox<T extends Number> {
    T value;

    NumberBox(T value) {
        this.value = value;
    }

    double doubleValue() {
        return value.doubleValue(); // can call Number methods since T is a Number
    }
}

NumberBox<Integer> intBox = new NumberBox<>(42);
NumberBox<Double> dblBox = new NumberBox<>(3.14);
// NumberBox<String> strBox = new NumberBox<>("hi"); // COMPILE ERROR - String is not a Number

System.out.println(intBox.doubleValue()); // 42.0
```

### Wildcards

`?` is the wildcard - represents an unknown type. Used when you want to accept multiple different generic types.

```java
// accepts List of any type
void printList(List<?> list) {
    for (Object item : list) {
        System.out.print(item + " ");
    }
}

printList(new ArrayList<Integer>());  // works
printList(new ArrayList<String>());   // works
printList(new ArrayList<Double>());   // works

// upper bounded wildcard - List of Number or any subtype
void sumList(List<? extends Number> list) {
    double sum = 0;
    for (Number n : list) sum += n.doubleValue();
    System.out.println("Sum: " + sum);
}

sumList(Arrays.asList(1, 2, 3));         // works with Integer
sumList(Arrays.asList(1.1, 2.2, 3.3));   // works with Double
```

---

## PART 13 - SCANNER CLASS

Scanner reads input from keyboard (or files, strings). It is in `java.util`.

```java
import java.util.Scanner;

public class InputDemo {
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in); // System.in = keyboard input

        // Reading different types
        System.out.print("Enter an integer: ");
        int n = sc.nextInt();

        System.out.print("Enter a double: ");
        double d = sc.nextDouble();

        System.out.print("Enter a string (no spaces): ");
        String word = sc.next(); // reads one word (stops at space)

        System.out.print("Enter a line: ");
        sc.nextLine(); // consume leftover newline after nextInt/nextDouble (important!)
        String line = sc.nextLine(); // reads entire line including spaces

        System.out.print("Enter a boolean (true/false): ");
        boolean b = sc.nextBoolean();

        // Print back
        System.out.printf("Int: %d, Double: %.2f, Word: %s%n", n, d, word);
        System.out.println("Line: " + line);
        System.out.println("Boolean: " + b);

        sc.close(); // close scanner when done (good practice)
    }
}
```

### The nextLine() after nextInt() problem

This is a very common bug.

```java
Scanner sc = new Scanner(System.in);

int age = sc.nextInt(); // user types "22" and presses Enter
// Input buffer now contains: "22\n"
// nextInt() reads "22" but leaves "\n" in buffer

String name = sc.nextLine(); // reads the leftover "\n" immediately, user never gets to type!
System.out.println(name); // empty string - bug!

// FIX: add sc.nextLine() after nextInt/nextDouble/nextBoolean to consume the leftover newline
int age2 = sc.nextInt();
sc.nextLine(); // consume leftover newline
String name2 = sc.nextLine(); // now user can type a proper name
```

### Scanner useful methods

```java
Scanner sc = new Scanner(System.in);

sc.nextInt()        // reads int
sc.nextLong()       // reads long
sc.nextDouble()     // reads double
sc.nextFloat()      // reads float
sc.nextBoolean()    // reads true/false
sc.next()           // reads one token (word) - stops at whitespace
sc.nextLine()       // reads entire line including spaces - stops at newline
sc.hasNext()        // true if more input available
sc.hasNextInt()     // true if next token is an int (useful for validation loops)
sc.hasNextLine()    // true if another line of input exists

// Reading from a String (useful for testing)
Scanner strSc = new Scanner("42 3.14 hello");
int i = strSc.nextInt();      // 42
double d = strSc.nextDouble(); // 3.14
String s = strSc.next();      // hello
```

### Reading until end of input

Useful when you do not know how many values will be entered.

```java
Scanner sc = new Scanner(System.in);

int sum = 0;
while (sc.hasNextInt()) {
    sum += sc.nextInt();
}
System.out.println("Sum: " + sum);
// User can type as many ints as they want
// Stop by pressing Ctrl+D (Linux/Mac) or Ctrl+Z (Windows)
```

---

## PART 14 - QUICK REFERENCE

```
PRIMITIVES:
  byte  (1 byte)  -128 to 127
  short (2 bytes) -32768 to 32767
  int   (4 bytes) -2 billion to 2 billion (use for all integers)
  long  (8 bytes) very large numbers - add L suffix
  float (4 bytes) ~7 decimal digits - add f suffix (rarely used)
  double(8 bytes) ~15 decimal digits (use for all decimals)
  char  (2 bytes) single character, single quotes
  boolean         true or false only

TYPE CASTING:
  widening  - automatic, no data loss (int to long, float to double)
  narrowing - explicit (int x = (int) 3.99 = 3), may lose data

OPERATORS:
  arithmetic  + - * / %
  relational  == != > < >= <=
  logical     && || !   (short-circuit)
  bitwise     & | ^ ~ << >> >>>
  ternary     condition ? value1 : value2
  assignment  = += -= *= /= %=

CONTROL FLOW:
  if/else if/else
  switch (int, byte, short, char, String, enum)
  switch expression (Java 14+): value = switch(x) { case y -> result; }

LOOPS:
  for loop       - when you know iteration count
  while loop     - when condition-based, check before
  do-while       - check after, runs at least once
  for-each       - iterate over array/collection without index
  break          - exit loop
  continue       - skip current iteration

ARRAYS:
  int[] arr = new int[5];
  int[] arr = {1,2,3};
  arr.length     - property (no parentheses)
  Arrays.sort(arr)
  Arrays.toString(arr)   - readable print
  int[][] matrix - 2D array

METHODS:
  returnType name(params) { return value; }
  overloading    - same name, different parameters
  static         - belongs to class, no object needed

SCOPE:
  local variable  - inside method/block, no default value
  instance field  - inside class, has default value, per object
  static field    - inside class with static, shared across all objects

STRING (immutable):
  Always use .equals() for comparison, never ==
  Common: length() charAt() substring() contains() split() trim()
  replace() indexOf() toUpperCase() startsWith() endsWith()
  StringBuilder - use for building strings in loops

WRAPPER CLASSES:
  int -> Integer, double -> Double, char -> Character, boolean -> Boolean
  Autoboxing: int x = 5; Integer obj = x;   (automatic)
  Unboxing:   Integer obj = 5; int x = obj; (automatic)
  Cache: Integer -128 to 127 (== works), outside range use .equals()
  Parsing: Integer.parseInt("42"), Double.parseDouble("3.14")

GENERICS:
  class Box<T> { T item; }         - generic class
  <T> void print(T[] arr) { }      - generic method
  <T extends Number>               - bounded (T must be Number or subclass)
  List<?>                          - wildcard, any type

SCANNER:
  Scanner sc = new Scanner(System.in);
  sc.nextInt() / sc.nextDouble() / sc.next() / sc.nextLine()
  Always add sc.nextLine() after nextInt/nextDouble to consume leftover newline
```
