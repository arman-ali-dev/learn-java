# Java Exception Handling - Complete Notes

---

## PART 1 - WHAT IS AN EXCEPTION AND WHY IT EXISTS

Imagine you are driving a car. Normally everything works fine. But suddenly a tyre punctures. The car does not just explode randomly - it gives you a signal, you pull over, you handle the situation, and then you continue your journey.

That is exactly what exceptions are in Java.

When something goes wrong while your program is running, Java does not just crash silently. It creates an object that describes exactly what went wrong - where it happened, what the error was, what caused it. That object is called an Exception.

Without exception handling, your program crashes the moment anything goes wrong and the user sees a horrible error screen. With exception handling, you catch the problem, deal with it gracefully, maybe show a proper message to the user, and continue running.

Examples of things that can go wrong:
- You try to divide a number by zero
- You try to access index 10 of an array that only has 5 elements
- You try to call a method on an object that is null
- You try to open a file that does not exist
- The database server is down when you try to connect

All of these are situations where Java throws an exception and your code needs to handle it.

---

## PART 2 - EXCEPTION HIERARCHY

Everything in Java's exception system extends from one root class: Throwable.

```
java.lang.Throwable
        |
        |--- Error
        |       |--- StackOverflowError
        |       |--- OutOfMemoryError
        |       |--- VirtualMachineError
        |
        |--- Exception
                |--- IOException                    (checked)
                |       |--- FileNotFoundException  (checked)
                |       |--- EOFException           (checked)
                |
                |--- SQLException                   (checked)
                |--- CloneNotSupportedException     (checked)
                |
                |--- RuntimeException               (unchecked)
                        |--- NullPointerException
                        |--- ArrayIndexOutOfBoundsException
                        |--- ArithmeticException
                        |--- NumberFormatException
                        |--- ClassCastException
                        |--- IllegalArgumentException
                        |--- IllegalStateException
```

### Throwable

The root of everything. Both Error and Exception extend from it. You can technically catch Throwable but you should never do that in practice.

### Error

Errors are serious JVM level problems. These are NOT meant to be caught or handled by your code.

StackOverflowError happens when you have infinite recursion - a method keeps calling itself forever until the call stack runs out of memory.

OutOfMemoryError happens when the JVM runs out of heap memory. You cannot allocate any more objects.

These are problems you cannot recover from in normal code. If you get an OutOfMemoryError, your application is in a broken state. The right thing to do is let the program crash and investigate the root cause.

```java
// This causes StackOverflowError
public static void infiniteRecursion() {
    infiniteRecursion(); // keeps calling itself
}

// You should NOT do this
try {
    infiniteRecursion();
} catch (StackOverflowError e) {
    // do not catch Errors - you cannot recover from them
}
```

### Exception

This is what you work with in your code. It splits into two important branches - checked and unchecked.

---

## PART 3 - CHECKED VS UNCHECKED EXCEPTIONS

This is one of the most asked theory questions in Java interviews. Understand it deeply.

### What is the difference

Checked exceptions are exceptions that the compiler forces you to handle. If you call a method that can throw a checked exception and you do not either catch it or declare it, your code will NOT compile. The compiler stops you.

Unchecked exceptions are exceptions that the compiler does NOT check. Your code compiles fine even if you do not handle them. But if they happen at runtime, your program crashes.

### Why does this design exist

Checked exceptions are used for situations that are outside your control but are predictable. A file might not exist. A network connection might fail. A database might be down. These are things that can reasonably happen and you should have a plan for them. So the compiler forces you to think about them.

Unchecked exceptions are used for programming mistakes. NullPointerException means you forgot to check if something is null. ArrayIndexOutOfBoundsException means your index calculation is wrong. These are bugs. You should fix the code, not just catch the exception.

### Checked Exceptions

They extend Exception but NOT RuntimeException.

The compiler is saying: this operation might fail for external reasons. You must handle it or tell the caller to handle it.

```java
import java.io.FileReader;
import java.io.IOException;

public class CheckedDemo {
    public static void main(String[] args) {

        // DOES NOT COMPILE - FileReader constructor throws IOException
        // which is checked, so you must handle it
        // FileReader fr = new FileReader("file.txt"); // compile error!

        // Option 1: Handle it yourself with try-catch
        try {
            FileReader fr = new FileReader("file.txt");
            System.out.println("File opened successfully");
        } catch (IOException e) {
            // file does not exist, no permission, etc.
            System.out.println("Could not open file: " + e.getMessage());
        }
    }

    // Option 2: Declare that YOU also throw it, passing responsibility to caller
    public static void readUserData() throws IOException {
        FileReader fr = new FileReader("users.txt");
        // caller of readUserData() must now handle IOException
    }
}
```

### Unchecked Exceptions

They extend RuntimeException.

The compiler does not check these. They represent bugs - situations that should not happen if your code is correct.

```java
public class UncheckedDemo {
    public static void main(String[] args) {

        // All of these COMPILE fine but crash at runtime

        // NullPointerException - you forgot to check null
        String name = null;
        // System.out.println(name.length()); // NPE at runtime
        // Fix: check null before using
        if (name != null) {
            System.out.println(name.length());
        }

        // ArrayIndexOutOfBoundsException - wrong index
        int[] scores = new int[3];
        // scores[10] = 95; // AIOOBE at runtime
        // Fix: check array bounds

        // ArithmeticException - divide by zero
        int marks = 100;
        int students = 0;
        // int avg = marks / students; // ArithmeticException at runtime
        // Fix: check for zero before dividing

        // NumberFormatException - invalid string to number
        String input = "abc";
        // int num = Integer.parseInt(input); // NFE at runtime
        // Fix: validate input before parsing

        // ClassCastException - wrong type cast
        Object obj = "hello";
        // Integer num = (Integer) obj; // CCE at runtime
        // Fix: use instanceof before casting
    }
}
```

### Checked vs Unchecked - Complete Summary

```
Feature               Checked                      Unchecked
----------------------|-----------------------------|--------------------------
Extends               Exception (not Runtime)      RuntimeException
Compiler checks       YES - will not compile        NO - compiles fine
When they happen      External failures             Programming bugs
Examples              IOException, SQLException     NullPointerException,
                      FileNotFoundException         ArithmeticException,
                                                   ArrayIndexOutOfBounds
How to handle         Must use try-catch OR throws  Optional - fix the bug
Design intention      Predictable external issues   Should not happen at all
```

---

## PART 4 - TRY CATCH FINALLY

### Basic try-catch

The try block contains code that might throw an exception. The catch block runs only when that specific exception is thrown.

```java
public class TryCatchBasics {
    public static void main(String[] args) {

        System.out.println("Before try block");

        try {
            System.out.println("Inside try - before exception");
            int result = 10 / 0; // throws ArithmeticException here
            System.out.println("Inside try - after exception"); // NEVER runs
        } catch (ArithmeticException e) {
            // runs only when ArithmeticException happens
            System.out.println("Caught exception: " + e.getMessage());
            // e.getMessage() returns: "/ by zero"
        }

        System.out.println("After try-catch - program continues normally");
    }
}

// Output:
// Before try block
// Inside try - before exception
// Caught exception: / by zero
// After try-catch - program continues normally
```

Important: once an exception is thrown, the remaining lines inside the try block are skipped. Execution jumps directly to the catch block.

### Multiple catch blocks

One try block can have multiple catch blocks, each handling a different exception type.

```java
public class MultipleCatch {
    public static void main(String[] args) {

        String[] names = {"Arman", "Priya", null, "Raj"};

        for (int i = 0; i <= 5; i++) { // going beyond array length
            try {
                String name = names[i]; // might throw ArrayIndexOutOfBoundsException
                int len = name.length(); // might throw NullPointerException
                System.out.println("Name: " + name + ", Length: " + len);
            } catch (ArrayIndexOutOfBoundsException e) {
                System.out.println("Index " + i + " does not exist in array");
            } catch (NullPointerException e) {
                System.out.println("Name at index " + i + " is null");
            } catch (Exception e) {
                // catches anything else not handled above
                System.out.println("Unexpected error: " + e.getMessage());
            }
        }
    }
}
```

Critical rule: put specific exceptions BEFORE general ones. Compiler will give an error if you put Exception before a specific one because specific catch would be unreachable.

```java
// WRONG - compile error
try {
    int x = 10 / 0;
} catch (Exception e) {          // this catches everything
    System.out.println("error");
} catch (ArithmeticException e) { // COMPILE ERROR - unreachable code
    System.out.println("math error");
}

// CORRECT
try {
    int x = 10 / 0;
} catch (ArithmeticException e) { // specific first
    System.out.println("math error");
} catch (Exception e) {           // general last - catches anything else
    System.out.println("other error");
}
```

### Multi-catch - Java 7+

When you want to handle multiple exceptions the exact same way, you can combine them in one catch block using pipe symbol.

```java
try {
    // code that can throw multiple types
    String input = getUserInput();
    int num = Integer.parseInt(input);  // NumberFormatException
    int result = 100 / num;             // ArithmeticException
    writeToFile(result);                // IOException
} catch (NumberFormatException | ArithmeticException e) {
    // handle both the same way
    System.out.println("Input problem: " + e.getMessage());
} catch (IOException e) {
    System.out.println("File problem: " + e.getMessage());
}
```

### finally block - the cleanup block

finally always runs. No matter what happens. Exception thrown or not. Exception caught or not. Return statement hit or not.

The only exception (pun intended) is System.exit() which kills the JVM.

Use finally to close resources, release locks, or do cleanup that must always happen.

```java
public class FinallyDemo {
    public static void main(String[] args) {

        // Case 1: No exception happens
        try {
            System.out.println("Try - no exception");
        } catch (Exception e) {
            System.out.println("Catch - not reached");
        } finally {
            System.out.println("Finally - always runs");
        }
        // Output: Try - no exception
        //         Finally - always runs

        System.out.println("---");

        // Case 2: Exception is caught
        try {
            System.out.println("Try - before exception");
            int x = 10 / 0;
            System.out.println("Try - after exception (never runs)");
        } catch (ArithmeticException e) {
            System.out.println("Catch - handled exception");
        } finally {
            System.out.println("Finally - still runs");
        }
        // Output: Try - before exception
        //         Catch - handled exception
        //         Finally - still runs

        System.out.println("---");

        // Case 3: Exception is NOT caught
        try {
            try {
                System.out.println("Inner try");
                int x = 10 / 0;
            } finally {
                System.out.println("Inner finally - runs even though exception not caught");
            }
        } catch (ArithmeticException e) {
            System.out.println("Outer catch - caught it here");
        }
    }
}
```

### finally with return - important edge case

If you have a return inside try and code in finally, the finally runs BEFORE the method actually returns.

```java
public static String getStatus() {
    try {
        System.out.println("In try");
        return "success"; // finally runs before this return actually happens
    } finally {
        System.out.println("In finally"); // this runs first
        // if you put return here, it OVERRIDES the return in try
        // return "overridden"; // this would return "overridden" instead of "success"
    }
}

// Output when called:
// In try
// In finally
// Then the method returns "success"
```

Avoid putting a return inside finally. It causes confusing behavior and hides exceptions.

### finally does NOT run when System.exit() is called

System.exit() immediately terminates the JVM. Nothing runs after it including finally.

```java
try {
    System.out.println("In try");
    System.exit(0); // JVM dies here
} finally {
    System.out.println("This NEVER prints"); // finally skipped
}
```

### Nested try-catch

You can have try-catch inside another try-catch. Inner exceptions can be handled locally, outer exceptions propagate up.

```java
public class NestedTryCatch {
    public static void main(String[] args) {

        try {
            System.out.println("Outer try start");

            try {
                System.out.println("Inner try");
                int result = 10 / 0; // throws ArithmeticException
            } catch (ArithmeticException e) {
                System.out.println("Inner catch: " + e.getMessage());
                // handle it here - does not propagate to outer catch
            }

            System.out.println("Outer try continues after inner try-catch");

        } catch (Exception e) {
            System.out.println("Outer catch - would catch unhandled inner exceptions");
        } finally {
            System.out.println("Outer finally - always runs");
        }
    }
}

// Output:
// Outer try start
// Inner try
// Inner catch: / by zero
// Outer try continues after inner try-catch
// Outer finally - always runs
```

---

## PART 5 - THROWS KEYWORD

throws is used in a method signature. It says "this method might throw this exception, and I am not handling it - the caller must handle it."

It is a way of passing responsibility. You are saying: I know this can fail, but it is not my job to decide what to do about it. The code that calls me should decide.

```java
import java.io.IOException;

public class ThrowsDemo {

    // This method declares it might throw IOException
    // It does NOT handle it - caller must handle
    public static String readFile(String path) throws IOException {
        // FileReader throws checked IOException
        // We are not catching it - we declare throws and let it propagate
        java.io.FileReader fr = new java.io.FileReader(path);
        return "file content";
    }

    // This method also does not want to handle it - passes up further
    public static void loadUserData() throws IOException {
        String data = readFile("users.txt"); // might throw IOException
        System.out.println("Loaded: " + data);
    }

    public static void main(String[] args) {
        // Main is the final stop - someone must catch it
        try {
            loadUserData();
        } catch (IOException e) {
            System.out.println("Could not load user data: " + e.getMessage());
            // handle gracefully - show error message, use defaults, etc.
        }
    }
}
```

### throws with multiple exceptions

A method can declare multiple exceptions it might throw.

```java
public static void processData(String filename, int userId)
        throws IOException, SQLException, IllegalArgumentException {

    if (userId <= 0) {
        throw new IllegalArgumentException("User ID must be positive");
    }
    // file operations - might throw IOException
    // database operations - might throw SQLException
}
```

### throws on unchecked exceptions

You CAN add throws for unchecked exceptions but you do not have to. It serves as documentation.

```java
// Legal but not required - NullPointerException is unchecked
public static void process(String data) throws NullPointerException {
    System.out.println(data.length()); // might throw NPE if data is null
}
```

---

## PART 6 - THROW KEYWORD

throw is used inside method body to actually throw an exception. You are creating the exception and firing it.

You use throw when:
- Input validation fails and you want to signal the caller
- A condition that should never happen actually happens
- You want to convert a low-level exception to a more meaningful one

```java
public class ThrowDemo {

    public static void validateAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative. Given: " + age);
        }
        if (age > 150) {
            throw new IllegalArgumentException("Age cannot be more than 150. Given: " + age);
        }
        if (age < 18) {
            throw new IllegalStateException("User must be at least 18. Given: " + age);
        }
        System.out.println("Age is valid: " + age);
    }

    public static double divide(double a, double b) {
        if (b == 0) {
            throw new ArithmeticException("Cannot divide by zero");
        }
        return a / b;
    }

    public static String findUser(int id) {
        String[] users = {"Arman", "Priya", "Raj"};
        if (id < 0 || id >= users.length) {
            throw new ArrayIndexOutOfBoundsException("No user with id: " + id);
        }
        return users[id];
    }

    public static void main(String[] args) {

        try {
            validateAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            validateAge(15);
        } catch (IllegalStateException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        validateAge(25); // works fine

        try {
            System.out.println(divide(10, 0));
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
```

### throw vs throws - clear comparison

```
throw                                |  throws
-------------------------------------|--------------------------------------
Used INSIDE method body              |  Used in method SIGNATURE
Actually throws an exception NOW     |  Declares what might be thrown later
throw new IOException("message")     |  public void method() throws IOException
Throws exactly ONE exception         |  Can declare multiple: throws A, B, C
Followed by an exception object      |  Followed by exception class names
```

```java
// throw - inside body, actual exception object
public void withdraw(double amount) throws InsufficientFundsException {
    if (amount > balance) {
        throw new InsufficientFundsException("Need " + amount + " but have " + balance);
        //  ^^^^^ inside body, creates and throws object
    }
    balance -= amount;
}
// throws ^^^^^ in signature, declares what this method might throw
```

---

## PART 7 - CUSTOM EXCEPTIONS

Java gives you many built-in exceptions but sometimes none of them perfectly describe what went wrong in YOUR application. That is when you create your own.

Custom exceptions make your code more readable. UserNotFoundException is much clearer than RuntimeException. InsufficientBalanceException tells you exactly what failed.

### Custom Unchecked Exception - extends RuntimeException

Use when the error is due to a programming mistake or invalid data that should have been validated earlier. The caller does not HAVE to catch it.

```java
// Define the exception
class InsufficientBalanceException extends RuntimeException {

    private double requested;
    private double available;

    // Constructor with just message
    public InsufficientBalanceException(String message) {
        super(message);
    }

    // Constructor with message and extra data
    public InsufficientBalanceException(String message, double requested, double available) {
        super(message);
        this.requested = requested;
        this.available = available;
    }

    // Constructor with message and cause (original exception)
    public InsufficientBalanceException(String message, Throwable cause) {
        super(message, cause);
    }

    // Getter for extra data
    public double getRequested() { return requested; }
    public double getAvailable() { return available; }
}

// Use the exception
class BankAccount {
    private String owner;
    private double balance;

    BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive: " + amount);
        }
        if (amount > balance) {
            throw new InsufficientBalanceException(
                "Cannot withdraw Rs." + amount + " from account of " + owner,
                amount,
                balance
            );
        }
        balance -= amount;
        System.out.println("Withdrew Rs." + amount + ". Remaining: Rs." + balance);
    }

    public double getBalance() { return balance; }
}

public class BankDemo {
    public static void main(String[] args) {

        BankAccount account = new BankAccount("Arman", 5000);

        try {
            account.withdraw(2000); // works
            account.withdraw(4000); // throws InsufficientBalanceException
        } catch (InsufficientBalanceException e) {
            System.out.println("Transaction failed: " + e.getMessage());
            System.out.println("You tried to withdraw: Rs." + e.getRequested());
            System.out.println("Your balance is: Rs." + e.getAvailable());
        }

        try {
            account.withdraw(-100); // throws IllegalArgumentException
        } catch (IllegalArgumentException e) {
            System.out.println("Invalid amount: " + e.getMessage());
        }
    }
}
```

### Custom Checked Exception - extends Exception

Use when the caller MUST be forced to handle this situation. They cannot ignore it.

```java
// Define checked exception
class UserNotFoundException extends Exception {

    private int userId;

    public UserNotFoundException(String message, int userId) {
        super(message);
        this.userId = userId;
    }

    public int getUserId() {
        return userId;
    }
}

class UserService {
    private static String[] users = {"", "Arman", "Priya", "Raj"}; // index = userId

    // Caller MUST handle UserNotFoundException - it is checked
    public static String getUser(int userId) throws UserNotFoundException {
        if (userId <= 0 || userId >= users.length) {
            throw new UserNotFoundException(
                "No user found with ID: " + userId,
                userId
            );
        }
        return users[userId];
    }
}

public class UserDemo {
    public static void main(String[] args) {

        // Compiler forces us to handle UserNotFoundException
        try {
            String user = UserService.getUser(2);
            System.out.println("Found: " + user); // Priya

            String missing = UserService.getUser(99); // throws exception
        } catch (UserNotFoundException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Looked for userId: " + e.getUserId());
        }
    }
}
```

### Exception with error codes - professional pattern

In real applications you often have error codes for logging and API responses.

```java
class AppException extends RuntimeException {

    private final int errorCode;
    private final String errorType;

    public AppException(int errorCode, String errorType, String message) {
        super(message);
        this.errorCode = errorCode;
        this.errorType = errorType;
    }

    public int getErrorCode() { return errorCode; }
    public String getErrorType() { return errorType; }

    @Override
    public String toString() {
        return "[" + errorCode + "] " + errorType + ": " + getMessage();
    }
}

// Define specific exceptions using the base class
class StudentNotFoundException extends AppException {
    public StudentNotFoundException(int studentId) {
        super(404, "NOT_FOUND", "Student with ID " + studentId + " does not exist");
    }
}

class InvalidInputException extends AppException {
    public InvalidInputException(String field, String reason) {
        super(400, "INVALID_INPUT", "Invalid value for '" + field + "': " + reason);
    }
}

class DatabaseException extends AppException {
    public DatabaseException(String operation, Throwable cause) {
        super(500, "DATABASE_ERROR", "Database failed during: " + operation);
        // cause is stored via initCause or super constructor with cause
    }
}

// Usage
public class AppExceptionDemo {
    public static void main(String[] args) {

        try {
            throw new StudentNotFoundException(42);
        } catch (AppException e) {
            System.out.println(e);                  // [404] NOT_FOUND: Student with ID 42 does not exist
            System.out.println("Code: " + e.getErrorCode()); // 404
            System.out.println("Type: " + e.getErrorType()); // NOT_FOUND
        }

        try {
            throw new InvalidInputException("age", "must be between 5 and 100");
        } catch (AppException e) {
            System.out.println(e);
        }
    }
}
```

---

## PART 8 - TRY-WITH-RESOURCES

### The problem before Java 7

Before Java 7, you had to manually close resources like files, database connections, network sockets. You had to do this in finally to ensure they close even when an exception occurs.

The code was ugly and error-prone:

```java
import java.io.*;

public class OldWay {
    public static void main(String[] args) {

        FileReader fr = null;
        BufferedReader br = null;

        try {
            fr = new FileReader("data.txt");
            br = new BufferedReader(fr);

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        } finally {
            // Must close in reverse order of opening
            // Must check for null
            // Must catch exceptions from close() itself
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    System.out.println("Error closing br: " + e.getMessage());
                }
            }
            if (fr != null) {
                try {
                    fr.close();
                } catch (IOException e) {
                    System.out.println("Error closing fr: " + e.getMessage());
                }
            }
        }
    }
}
```

This is messy. Easy to forget. Easy to mess up the order.

### Try-with-resources - Java 7+

You declare resources inside try(). Java automatically calls close() on them when the try block ends - whether normally or with exception.

The resource class must implement AutoCloseable interface (which has one method: close()).

```java
import java.io.*;

public class NewWay {
    public static void main(String[] args) {

        // Resources declared here are auto-closed when try block ends
        try (FileReader fr = new FileReader("data.txt");
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // br and fr are automatically closed here
        // even if an exception was thrown
        // no finally needed
        // no null checks needed
        // clean and simple
    }
}
```

### How resources close when multiple are declared

Resources close in REVERSE order of how they were declared. This prevents issues where closing one resource depends on another still being open.

```java
try (ResourceA a = new ResourceA();  // opened first
     ResourceB b = new ResourceB();  // opened second
     ResourceC c = new ResourceC()) { // opened third
    // use a, b, c
}
// c closes first
// then b closes
// then a closes last
// reverse of opening order
```

### Creating your own AutoCloseable class

Any class that implements AutoCloseable can be used in try-with-resources.

```java
class DatabaseConnection implements AutoCloseable {

    private String url;
    private boolean connected;

    public DatabaseConnection(String url) {
        this.url = url;
        this.connected = true;
        System.out.println("Connected to: " + url);
    }

    public void executeQuery(String sql) {
        if (!connected) {
            throw new IllegalStateException("Connection is closed");
        }
        System.out.println("Executing: " + sql);
        // actual query execution
    }

    @Override
    public void close() {
        // This is called automatically by try-with-resources
        if (connected) {
            connected = false;
            System.out.println("Connection to " + url + " closed");
        }
    }
}

public class AutoCloseableDemo {
    public static void main(String[] args) {

        // db.close() is called automatically at end of try block
        try (DatabaseConnection db = new DatabaseConnection("jdbc:mysql://localhost/mydb")) {

            db.executeQuery("SELECT * FROM students");
            db.executeQuery("SELECT * FROM teachers");

            // Simulate an exception
            if (true) throw new RuntimeException("Query failed");

            db.executeQuery("This never runs");

        } catch (RuntimeException e) {
            System.out.println("Caught: " + e.getMessage());
        }
        // close() was already called automatically before catch ran

        System.out.println("After try-with-resources");
    }
}

// Output:
// Connected to: jdbc:mysql://localhost/mydb
// Executing: SELECT * FROM students
// Executing: SELECT * FROM teachers
// Connection to jdbc:mysql://localhost/mydb closed   <- auto closed before catch
// Caught: Query failed
// After try-with-resources
```

### Try-with-resources with finally

You can still have finally with try-with-resources. Resources close first, then finally runs.

```java
try (DatabaseConnection db = new DatabaseConnection("mydb")) {
    db.executeQuery("SELECT 1");
} catch (Exception e) {
    System.out.println("Error: " + e.getMessage());
} finally {
    System.out.println("Finally runs after resource is closed");
}

// Order: resource closes -> catch (if exception) -> finally
```

---

## PART 9 - EXCEPTION CHAINING

Exception chaining means wrapping one exception inside another. This is useful when you catch a low-level exception and want to throw a higher-level, more meaningful exception to the caller - but you also want to preserve the original exception so nothing is lost.

### Why chain exceptions

Imagine a database layer throws a SQLException. Your service layer should not expose that to the controller. The controller should not know you are using SQL. So you catch SQLException and throw a more meaningful ServiceException. But you also attach the original SQLException as the cause so developers can debug the full chain.

```java
class DataAccessException extends RuntimeException {
    public DataAccessException(String message, Throwable cause) {
        super(message, cause); // cause = original exception
    }
}

class UserRepository {
    public static String findUser(int id) {
        try {
            // Simulate a database operation that fails
            if (id <= 0) {
                throw new java.sql.SQLException("Invalid user ID in query: " + id);
            }
            return "User " + id;
        } catch (java.sql.SQLException e) {
            // Wrap the SQL exception in a more meaningful one
            // Original exception (e) is preserved as cause
            throw new DataAccessException("Failed to fetch user with id: " + id, e);
        }
    }
}

class UserService {
    public static String getUser(int id) {
        try {
            return UserRepository.findUser(id); // might throw DataAccessException
        } catch (DataAccessException e) {
            // Wrap again for service layer
            throw new RuntimeException("User service failed", e);
        }
    }
}

public class ExceptionChainingDemo {
    public static void main(String[] args) {

        try {
            UserService.getUser(-1);
        } catch (RuntimeException e) {
            System.out.println("Top level: " + e.getMessage());
            // Unwrap chain to see root cause
            System.out.println("Caused by: " + e.getCause().getMessage());
            System.out.println("Root cause: " + e.getCause().getCause().getMessage());
        }
    }
}

// Output:
// Top level: User service failed
// Caused by: Failed to fetch user with id: -1
// Root cause: Invalid user ID in query: -1
```

### getCause() and getMessage() - key methods

```java
try {
    riskyOperation();
} catch (Exception e) {
    e.getMessage();      // error message of THIS exception
    e.getCause();        // the exception that caused this one (null if no chain)
    e.toString();        // class name + message
    e.printStackTrace(); // prints full chain to console - use for debugging
}
```

---

## PART 10 - BEST PRACTICES

These are important for interviews. Knowing what NOT to do is as important as knowing what to do.

### Always catch specific exceptions, not just Exception

```java
// BAD - too broad, hides bugs
try {
    processPayment();
} catch (Exception e) {
    System.out.println("Something failed");
    // What failed? Payment? Network? Database? You have no idea.
    // Also catches unexpected bugs you should fix, not hide
}

// GOOD - specific and meaningful
try {
    processPayment();
} catch (PaymentGatewayException e) {
    // payment service is down
    System.out.println("Payment service unavailable. Try again later.");
} catch (InsufficientFundsException e) {
    // not enough money
    System.out.println("Insufficient balance: " + e.getAvailable());
} catch (IOException e) {
    // network issue
    System.out.println("Network error. Check your connection.");
}
```

### Never swallow exceptions silently

An empty catch block is one of the worst things you can do. Problems happen silently and you have no idea why things are broken.

```java
// TERRIBLE - do not ever do this
try {
    loadConfiguration();
} catch (IOException e) {
    // empty - swallowed completely
    // if config fails to load, program runs with wrong settings
    // you will never know why things are behaving strangely
}

// MINIMUM - at least log it
try {
    loadConfiguration();
} catch (IOException e) {
    System.out.println("Config load failed: " + e.getMessage());
    // use default config
}

// BETTER - log and rethrow if you cannot handle it
try {
    loadConfiguration();
} catch (IOException e) {
    System.out.println("Config load failed: " + e.getMessage());
    throw new RuntimeException("Application startup failed", e); // tell the caller
}
```

### Use try-with-resources for everything that needs closing

```java
// BAD - might forget to close, or exception might prevent close()
FileWriter fw = new FileWriter("output.txt");
fw.write("data");
fw.close(); // if write() throws exception, this never runs - resource leak!

// GOOD - always closed no matter what
try (FileWriter fw = new FileWriter("output.txt")) {
    fw.write("data");
} // close() called automatically
```

### Do not use exceptions for normal flow control

Exceptions are for exceptional situations - things that should not happen normally. Using them for normal logic is slow and confusing.

```java
// BAD - using exception to check if string is a number
public static boolean isNumber(String s) {
    try {
        Integer.parseInt(s);
        return true;
    } catch (NumberFormatException e) {
        return false; // using exception for normal logic - bad!
    }
}

// GOOD - use proper check
public static boolean isNumber(String s) {
    if (s == null || s.isEmpty()) return false;
    for (char c : s.toCharArray()) {
        if (!Character.isDigit(c)) return false;
    }
    return true;
}
```

### Custom exception names must end with Exception

```java
// GOOD names
class UserNotFoundException extends RuntimeException {}
class PaymentFailedException extends Exception {}
class InvalidTokenException extends RuntimeException {}
class ConfigurationLoadException extends Exception {}

// BAD names
class UserNotFound extends RuntimeException {}    // missing Exception suffix
class BadPayment extends Exception {}             // not clear it is an exception
```

### Throw early, catch late

Throw the exception as soon as you detect the problem. Catch it at the level where you have enough context to handle it meaningfully.

```java
// GOOD - throw early in validation
public void createUser(String name, int age) {
    // validate immediately when data arrives
    if (name == null || name.trim().isEmpty()) {
        throw new InvalidInputException("name", "cannot be null or empty");
    }
    if (age < 0 || age > 150) {
        throw new InvalidInputException("age", "must be between 0 and 150");
    }
    // proceed with valid data
}

// GOOD - catch late where you can handle it properly
public void handleRequest(HttpRequest request) {
    // catch at the boundary where you can send proper response
    try {
        String name = request.getParam("name");
        int age = Integer.parseInt(request.getParam("age"));
        createUser(name, age);
        sendResponse(200, "User created");
    } catch (InvalidInputException e) {
        sendResponse(400, "Bad request: " + e.getMessage());
    } catch (Exception e) {
        sendResponse(500, "Internal server error");
    }
}
```

### Chain exceptions to preserve context

When you catch and rethrow, always pass the original as cause.

```java
// BAD - loses original exception and its context
try {
    connectToDatabase();
} catch (SQLException e) {
    throw new ServiceException("Database error"); // original e is lost!
}

// GOOD - original exception preserved in chain
try {
    connectToDatabase();
} catch (SQLException e) {
    throw new ServiceException("Database error", e); // e is passed as cause
}
```
