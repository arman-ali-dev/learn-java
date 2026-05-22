# Java OOP - Complete Notes

---

## PART 1 - WHAT IS OOP AND WHY IT EXISTS

Without OOP, everything is just variables and functions scattered around. There is no structure.

With OOP, you group related data and behavior together into one unit called a class. This makes code organized, reusable, and easy to understand.

Java is built on 4 OOP concepts:

- Encapsulation
- Inheritance
- Polymorphism
- Abstraction

---

## PART 2 - CLASS AND OBJECT

A class is a blueprint. An object is a real thing made from that blueprint.

```java
// Blueprint
class Student {
    String name;
    int age;
    int marks;

    void study() {
        System.out.println(name + " is studying");
    }
}

// Real objects made from blueprint
Student s1 = new Student();
s1.name = "Arman";
s1.age = 20;
s1.marks = 85;

Student s2 = new Student();
s2.name = "Priya";
s2.age = 19;
s2.marks = 92;

s1.study(); // Arman is studying
s2.study(); // Priya is studying
```

Every object gets its own copy of the fields. s1 and s2 are separate objects with separate data.

---

## PART 3 - CONSTRUCTOR

A constructor runs automatically when an object is created. It is used to set initial values.

Rules:

- Same name as the class
- No return type, not even void
- If you don't write one, Java gives you a default empty constructor

```java
class Student {
    String name;
    int age;
    int marks;

    // Default constructor - no arguments
    Student() {
        this.name = "Unknown";
        this.age = 0;
        this.marks = 0;
    }

    // Parameterized constructor
    Student(String name, int age, int marks) {
        this.name = name;
        this.age = age;
        this.marks = marks;
    }
}

Student s1 = new Student();                    // calls default
Student s2 = new Student("Arman", 20, 85);    // calls parameterized
```

### Constructor Overloading

Multiple constructors with different parameters in the same class.

```java
class BankAccount {
    String accountNumber;
    String holderName;
    double balance;

    BankAccount(String accountNumber, String holderName) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = 0; // default balance
    }

    BankAccount(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }
}
```

### this() - calling one constructor from another

```java
class BankAccount {
    String accountNumber;
    String holderName;
    double balance;

    BankAccount(String accountNumber, String holderName) {
        this(accountNumber, holderName, 0); // calls 3-argument constructor
    }

    BankAccount(String accountNumber, String holderName, double balance) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.balance = balance;
    }
}
```

---

## PART 4 - ENCAPSULATION

Encapsulation means keeping your data private and only allowing access through methods (getters and setters). This protects your data from invalid values.

Real life example: ATM machine. You cannot directly access the bank's database. You go through the ATM which has rules - correct PIN, withdrawal limits, etc.

```java
class Student {
    // private - no one can access directly from outside
    private String name;
    private int age;
    private int marks;

    // getter - read the value
    public String getName() {
        return name;
    }

    // setter - write the value with validation
    public void setName(String name) {
        if (name == null || name.isEmpty()) {
            System.out.println("Name cannot be empty");
            return;
        }
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        if (age < 5 || age > 30) {
            System.out.println("Age must be between 5 and 30");
            return;
        }
        this.age = age;
    }

    public int getMarks() {
        return marks;
    }

    public void setMarks(int marks) {
        if (marks < 0 || marks > 100) {
            System.out.println("Marks must be between 0 and 100");
            return;
        }
        this.marks = marks;
    }

    public char getGrade() {
        if (marks >= 90) return 'A';
        else if (marks >= 75) return 'B';
        else if (marks >= 60) return 'C';
        else return 'F';
    }
}

public class Main {
    public static void main(String[] args) {
        Student s = new Student();
        s.setName("Arman");
        s.setAge(20);
        s.setMarks(85);

        System.out.println(s.getName());   // Arman
        System.out.println(s.getGrade());  // B

        s.setAge(-5);      // Age must be between 5 and 30
        s.setMarks(150);   // Marks must be between 0 and 100
    }
}
```

### this keyword

this refers to the current object. Use it when field name and parameter name are the same.

```java
class Student {
    private String name;

    public void setName(String name) {
        this.name = name; // this.name = field, name = parameter
    }
}
```

---

## PART 5 - INHERITANCE

Inheritance means one class gets all the fields and methods of another class. The child does not need to rewrite what the parent already has.

Use extends keyword.

Real life: A Car is a Vehicle. Car gets all Vehicle properties automatically, plus its own extra ones.

```java
// Parent class
class Vehicle {
    String brand;
    int speed;
    double fuelLevel;

    Vehicle(String brand, int speed, double fuelLevel) {
        this.brand = brand;
        this.speed = speed;
        this.fuelLevel = fuelLevel;
    }

    void refuel(double amount) {
        this.fuelLevel += amount;
        System.out.println("Refueled. Fuel level: " + fuelLevel);
    }

    void describe() {
        System.out.println("Brand: " + brand + ", Speed: " + speed);
    }
}

// Child class - gets everything from Vehicle
class Car extends Vehicle {
    int numberOfSeats;

    Car(String brand, int speed, double fuelLevel, int seats) {
        super(brand, speed, fuelLevel); // must call parent constructor first
        this.numberOfSeats = seats;
    }

    void honk() {
        System.out.println(brand + " says beep beep"); // brand is from parent
    }
}

class Bike extends Vehicle {
    boolean hasSidecar;

    Bike(String brand, int speed, double fuelLevel, boolean hasSidecar) {
        super(brand, speed, fuelLevel);
        this.hasSidecar = hasSidecar;
    }
}

public class Main {
    public static void main(String[] args) {
        Car car = new Car("Toyota", 120, 40, 5);
        car.describe();    // from parent
        car.refuel(20);    // from parent
        car.honk();        // car's own method

        Bike bike = new Bike("Honda", 80, 10, false);
        bike.describe();   // from parent
        bike.refuel(5);    // from parent
    }
}
```

### super keyword

super is used to access the parent class constructor or methods from the child class.

```java
class Animal {
    String name;

    Animal(String name) {
        this.name = name;
    }

    void eat() {
        System.out.println(name + " is eating");
    }
}

class Dog extends Animal {
    String breed;

    Dog(String name, String breed) {
        super(name);         // calls Animal constructor
        this.breed = breed;
    }

    void eat() {
        super.eat();         // calls Animal's eat()
        System.out.println("Dog eats fast");
    }
}
```

### Method Overriding

Child class provides its own version of a method that already exists in the parent.

Rules:

- Same method name
- Same parameters
- Same or more accessible modifier
- Use @Override annotation - it's not required but it's good practice

```java
class Shape {
    String color;

    Shape(String color) {
        this.color = color;
    }

    double area() {
        return 0;
    }

    void printInfo() {
        System.out.println("Color: " + color + ", Area: " + area());
    }
}

class Circle extends Shape {
    double radius;

    Circle(String color, double radius) {
        super(color);
        this.radius = radius;
    }

    @Override
    double area() {
        return Math.PI * radius * radius; // overrides parent's area()
    }
}

class Rectangle extends Shape {
    double length, width;

    Rectangle(String color, double length, double width) {
        super(color);
        this.length = length;
        this.width = width;
    }

    @Override
    double area() {
        return length * width;
    }
}
```

### Types of Inheritance in Java

```
Single:       A -> B
              (B extends A)

Multilevel:   A -> B -> C
              (B extends A, C extends B)

Hierarchical: A -> B
              A -> C
              (B and C both extend A)

Multiple:     NOT allowed with classes in Java
              Java uses interfaces for this
```

```java
// Single
class Animal {}
class Dog extends Animal {}

// Multilevel
class Animal {}
class Dog extends Animal {}
class GoldenRetriever extends Dog {}

// Hierarchical
class Animal {}
class Dog extends Animal {}
class Cat extends Animal {}
```

### final keyword with inheritance

```java
// final class - cannot be extended
final class MathUtils {
    static int add(int a, int b) { return a + b; }
}
// class MyMath extends MathUtils {} // compile error

// final method - cannot be overridden
class Parent {
    final void show() {
        System.out.println("Cannot override this");
    }
}
class Child extends Parent {
    // void show() {} // compile error
}
```

---

## PART 6 - POLYMORPHISM

Polymorphism means one thing taking many forms. In Java, the same method name does different things based on the object or the arguments.

Two types:

- Compile time polymorphism - Method Overloading
- Runtime polymorphism - Method Overriding

### Method Overloading (Compile time)

Same method name, different parameters in the same class. Java decides which one to call based on what you pass.

```java
class Calculator {

    int add(int a, int b) {
        return a + b;
    }

    double add(double a, double b) {
        return a + b;
    }

    int add(int a, int b, int c) {
        return a + b + c;
    }

    String add(String a, String b) {
        return a + b;
    }
}

Calculator calc = new Calculator();
calc.add(5, 10);           // calls int version
calc.add(5.5, 2.3);        // calls double version
calc.add(1, 2, 3);         // calls 3-argument version
calc.add("Hello", " Java"); // calls String version
```

### Runtime Polymorphism (Method Overriding)

Parent reference holds a child object. When you call a method, the child's version runs - not the parent's. This is decided at runtime.

```java
class PaymentMethod {
    void processPayment(int amount) {
        System.out.println("Processing payment of Rs." + amount);
    }
}

class CreditCard extends PaymentMethod {
    @Override
    void processPayment(int amount) {
        System.out.println("Charging Rs." + amount + " to credit card");
    }
}

class UPI extends PaymentMethod {
    @Override
    void processPayment(int amount) {
        System.out.println("Sending Rs." + amount + " via UPI");
    }
}

public class Main {
    public static void main(String[] args) {

        // Parent reference, child object
        PaymentMethod p1 = new CreditCard();
        PaymentMethod p2 = new UPI();

        p1.processPayment(5000); // CreditCard's version runs
        p2.processPayment(1500); // UPI's version runs

        // This is the power - same code handles all types
        PaymentMethod[] payments = {
            new CreditCard(),
            new UPI(),
            new CreditCard()
        };

        for (PaymentMethod p : payments) {
            p.processPayment(1000); // correct version runs for each
        }
    }
}
```

---

## PART 7 - ABSTRACTION

Abstraction means hiding the internal details and only showing what is necessary.

Two ways to achieve abstraction in Java:

- Abstract class
- Interface

### Abstract Class

An abstract class is a class that cannot be instantiated (you cannot create an object of it directly). It can have abstract methods (no body) that child classes must implement.

Use when: multiple related classes share some common code but also need their own specific behavior.

```java
abstract class Animal {
    String name;
    int age;

    Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    // abstract method - no body, child MUST implement
    abstract void makeSound();

    // concrete method - has body, inherited by all children
    void eat() {
        System.out.println(name + " is eating");
    }

    void describe() {
        System.out.println("Name: " + name + ", Age: " + age);
        makeSound(); // calls child's version
    }
}

class Dog extends Animal {
    String breed;

    Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }

    @Override
    void makeSound() {
        System.out.println("Woof Woof");
    }
}

class Cat extends Animal {
    boolean isIndoor;

    Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }

    @Override
    void makeSound() {
        System.out.println("Meow");
    }
}

public class Main {
    public static void main(String[] args) {

        // Animal a = new Animal(); // compile error - cannot instantiate abstract class

        Animal dog = new Dog("Bruno", 3, "Labrador");
        Animal cat = new Cat("Whiskers", 2, true);

        dog.describe(); // eat() from Animal, makeSound() from Dog
        cat.describe(); // eat() from Animal, makeSound() from Cat
    }
}
```

### Interface

An interface is a pure contract. It only says WHAT to do, not HOW. Every method in an interface is by default public and abstract (before Java 8).

Use when: unrelated classes need to follow the same contract.

```java
interface Printable {
    void print(); // public abstract by default
}

interface Saveable {
    void save(String filename);
}

interface Exportable {
    void exportToPDF();
    void exportToExcel();
}

// A class can implement multiple interfaces
class Report implements Printable, Saveable, Exportable {
    String title;
    String content;

    Report(String title, String content) {
        this.title = title;
        this.content = content;
    }

    @Override
    public void print() {
        System.out.println("Printing: " + title);
        System.out.println(content);
    }

    @Override
    public void save(String filename) {
        System.out.println("Saving report to: " + filename);
    }

    @Override
    public void exportToPDF() {
        System.out.println("Exporting " + title + " to PDF");
    }

    @Override
    public void exportToExcel() {
        System.out.println("Exporting " + title + " to Excel");
    }
}
```

### Interface with default and static methods (Java 8+)

```java
interface Greetable {

    void greet(String name); // abstract - must implement

    // default method - has body, optional to override
    default void greetFormal(String name) {
        System.out.println("Good morning, " + name);
    }

    // static method - belongs to interface, not the object
    static void printVersion() {
        System.out.println("Greetable Interface v1.0");
    }
}

class EnglishGreeter implements Greetable {
    @Override
    public void greet(String name) {
        System.out.println("Hello, " + name);
    }
    // greetFormal is inherited, no need to override
}

class HindiGreeter implements Greetable {
    @Override
    public void greet(String name) {
        System.out.println("Namaste, " + name);
    }

    @Override
    public void greetFormal(String name) {
        System.out.println("Pranam, " + name); // overriding default
    }
}

// calling static method
Greetable.printVersion();
```

### Abstract Class vs Interface

```
Abstract Class                   |  Interface
---------------------------------|---------------------------------
Use extends keyword              |  Use implements keyword
Can have constructor             |  No constructor
Can have instance fields         |  Only constants (public static final)
Can have any access modifier     |  Methods are public by default
Can have concrete methods        |  Methods are abstract by default (before Java 8)
A class can extend only ONE      |  A class can implement MANY
Use when: shared code + override |  Use when: multiple unrelated classes need same contract
```

---

## PART 8 - ACCESS MODIFIERS

Controls who can access fields and methods.

```input
Modifier     Same Class   Same Package   Subclass (same pkg)   Subclass (diff pkg)   Anywhere
-------------|------------|--------------|---------------------|---------------------|----------
private      |    YES     |      NO      |         NO          |         NO          |   NO
default      |    YES     |      YES     |         YES         |         NO          |   NO
protected    |    YES     |      YES     |         YES         |         YES         |   NO
public       |    YES     |      YES     |         YES         |         YES         |   YES
```

```java
class Example {
    private int a = 1;     // only inside this class
    int b = 2;             // same package
    protected int c = 3;   // same package + subclasses
    public int d = 4;      // everywhere
}
```

---

## PART 9 - STATIC KEYWORD

static means it belongs to the class, not to any specific object. All objects share the same static field or method.

```java
class Student {
    String name;
    int marks;
    static int totalStudents = 0; // shared by all Student objects

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
        totalStudents++; // every time a new student is created
    }

    static void printTotal() {
        System.out.println("Total students: " + totalStudents);
        // cannot use this.name here - static method has no 'this'
    }
}

Student s1 = new Student("Arman", 85);
Student s2 = new Student("Priya", 92);
Student s3 = new Student("Raj", 71);

Student.printTotal();           // Total students: 3
System.out.println(Student.totalStudents); // 3
```

### static block

Runs once when the class is loaded, before any object is created.

```java
class Config {
    static String appName;
    static int maxUsers;

    static {
        appName = "MyApp";
        maxUsers = 100;
        System.out.println("Config loaded");
    }
}
```

---

## PART 10 - FINAL KEYWORD

Three uses of final:

```java
// 1. final variable - value cannot change
final int MAX_MARKS = 100;
// MAX_MARKS = 150; // compile error

// 2. final method - cannot be overridden in child class
class Parent {
    final void show() {
        System.out.println("Cannot override");
    }
}

// 3. final class - cannot be extended
final class MathUtils {
    static int square(int n) { return n * n; }
}
// class Extended extends MathUtils {} // compile error
```

---

## PART 11 - INSTANCEOF OPERATOR

Checks if an object is an instance of a class.

```java
Animal animal = new Dog("Bruno", 3, "Labrador");

System.out.println(animal instanceof Animal); // true
System.out.println(animal instanceof Dog);    // true
System.out.println(animal instanceof Cat);    // false

// Useful when you need to access child-specific methods
if (animal instanceof Dog) {
    Dog dog = (Dog) animal; // downcast
    System.out.println(dog.breed);
}
```

---

## PART 12 - UPCASTING AND DOWNCASTING

Upcasting - child object stored in parent reference. Happens automatically. Safe.

Downcasting - parent reference converted back to child. Must be done manually. Can throw ClassCastException if wrong.

```java
// Upcasting - automatic
Animal animal = new Dog("Bruno", 3, "Labrador"); // Dog -> Animal

// Downcasting - manual
Dog dog = (Dog) animal; // Animal -> Dog
System.out.println(dog.breed); // can access Dog-specific field now

// Wrong downcast - ClassCastException at runtime
Animal cat = new Cat("Whiskers", 2, true);
// Dog wrongDog = (Dog) cat; // ClassCastException!

// Always check before downcasting
if (cat instanceof Dog) {
    Dog d = (Dog) cat;
}
```

---

## PART 13 - OBJECT CLASS

Every class in Java automatically extends Object class. Object class has some useful methods.

```java
class Student {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    // Override toString - called automatically when you print the object
    @Override
    public String toString() {
        return "Student{name=" + name + ", marks=" + marks + "}";
    }

    // Override equals - default checks reference equality (==)
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return this.name.equals(other.name) && this.marks == other.marks;
    }

    // Override hashCode - always override when you override equals
    @Override
    public int hashCode() {
        return java.util.Objects.hash(name, marks);
    }
}

Student s1 = new Student("Arman", 85);
Student s2 = new Student("Arman", 85);

System.out.println(s1);            // Student{name=Arman, marks=85}
System.out.println(s1.equals(s2)); // true (with our equals)
System.out.println(s1 == s2);      // false (different references)
```

---

## PART 14 - INNER CLASSES

A class inside another class.

```java
class Outer {
    private int x = 10;

    // Inner class - has access to outer class fields
    class Inner {
        void show() {
            System.out.println("x from outer: " + x);
        }
    }

    // Static nested class - no access to outer instance fields
    static class StaticNested {
        void show() {
            System.out.println("Static nested class");
        }
    }
}

// Creating inner class object
Outer outer = new Outer();
Outer.Inner inner = outer.new Inner();
inner.show();

// Creating static nested class object
Outer.StaticNested nested = new Outer.StaticNested();
nested.show();
```

### Anonymous class

A class without a name, created and used in one place. Common with interfaces and abstract classes.

```java
interface Greeting {
    void greet(String name);
}

// Anonymous class - implement interface without creating a named class
Greeting g = new Greeting() {
    @Override
    public void greet(String name) {
        System.out.println("Hello, " + name);
    }
};

g.greet("Arman");
```

---

## PART 15 - COMPLETE EXAMPLE (ALL CONCEPTS TOGETHER)

This example uses encapsulation, inheritance, polymorphism, and abstraction together.

```java
import java.util.ArrayList;
import java.util.List;

// Interface
interface Workable {
    void doWork();
    double calculatePay(int hoursWorked);
}

// Abstract class
abstract class Person {
    private String name;
    private int age;
    private String email;

    Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public String getName() { return name; }
    public int getAge() { return age; }
    public String getEmail() { return email; }

    void introduce() {
        System.out.println("Hi, I am " + name + ", age " + age);
    }
}

// Concrete class extending abstract class and implementing interface
class FullTimeEmployee extends Person implements Workable {
    private String department;
    private double monthlySalary;

    FullTimeEmployee(String name, int age, String email, String department, double monthlySalary) {
        super(name, age, email);
        this.department = department;
        this.monthlySalary = monthlySalary;
    }

    @Override
    public void doWork() {
        System.out.println(getName() + " is working full time in " + department);
    }

    @Override
    public double calculatePay(int hoursWorked) {
        return monthlySalary; // fixed salary regardless of hours
    }
}

class FreelanceEmployee extends Person implements Workable {
    private double ratePerHour;

    FreelanceEmployee(String name, int age, String email, double ratePerHour) {
        super(name, age, email);
        this.ratePerHour = ratePerHour;
    }

    @Override
    public void doWork() {
        System.out.println(getName() + " is working as a freelancer");
    }

    @Override
    public double calculatePay(int hoursWorked) {
        return ratePerHour * hoursWorked; // pay based on hours
    }
}

public class Main {
    public static void main(String[] args) {

        List<Person> people = new ArrayList<>();
        people.add(new FullTimeEmployee("Arman", 25, "arman@co.com", "Engineering", 50000));
        people.add(new FreelanceEmployee("Priya", 28, "priya@co.com", 500));

        for (Person p : people) {
            p.introduce();

            // polymorphism with interface reference
            if (p instanceof Workable) {
                Workable w = (Workable) p;
                w.doWork();
                System.out.println("Pay: " + w.calculatePay(160));
            }
        }
    }
}
```
