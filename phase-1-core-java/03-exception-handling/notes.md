# Java Exception Handling - Complete Notes

---

## PART 1 - WHAT IS AN EXCEPTION

When your program runs and something goes wrong, Java creates an object that describes what went wrong. That object is called an Exception.

For example:

- You divide a number by zero
- You try to access index 10 of an array that has only 5 elements
- You try to use an object that is null

Without exception handling, your program just crashes and prints an error. With exception handling, you can catch that error and decide what to do next.

---

## PART 2 - EXCEPTION HIERARCHY

```
java.lang.Throwable
        |
        |--- Error
        |       |--- StackOverflowError
        |       |--- OutOfMemoryError
        |       |--- VirtualMachineError
        |
        |--- Exception
                |--- IOException                   (checked)
                |       |--- FileNotFoundException (checked)
                |       |--- EOFException          (checked)
                |
                |--- SQLException                  (checked)
                |--- CloneNotSupportedException    (checked)
                |
                |--- RuntimeException              (unchecked)
                        |--- NullPointerException
                        |--- ArrayIndexOutOfBoundsException
                        |--- ArithmeticException
                        |--- NumberFormatException
                        |--- ClassCastException
                        |--- IllegalArgumentException
                        |--- StackOverflowError
```

Two important things from this hierarchy:

Throwable is the root. Everything extends from it.

Error is for serious system level problems. You cannot and should not try to handle these. OutOfMemoryError, StackOverflowError - these are JVM level problems.

Exception is what you deal with in your code. It splits into two types - checked and unchecked.

---

## PART 3 - CHECKED VS UNCHECKED EXCEPTIONS

This is one of the most asked theory questions.

### Checked Exceptions

Checked exceptions are exceptions that the compiler forces you to handle. If you call a method that can throw a checked exception and you do not handle it, your code will not compile.

They extend Exception directly (but not RuntimeException).

Examples: IOException, FileNotFoundException, SQLException

The compiler is basically saying: "Hey, this thing can go wrong. You must either catch it or declare that your method throws it."

```java
import java.io.FileReader;
import java.io.IOException;

public class CheckedDemo {
    public static void main(String[] args) {

        // This will NOT compile without handling IOException
        // FileReader fr = new FileReader("file.txt"); // compile error

        // Option 1 - handle it with try-catch
        try {
            FileReader fr = new FileReader("file.txt");
        } catch (IOException e) {
            System.out.println("File not found: " + e.getMessage());
        }
    }

    // Option 2 - declare it with throws
    public static void readFile() throws IOException {
        FileReader fr = new FileReader("file.txt");
        // now the caller of readFile() must handle it
    }
}
```

### Unchecked Exceptions

Unchecked exceptions are exceptions that the compiler does NOT force you to handle. Your code compiles fine even if you do not catch them. But if they happen at runtime, your program crashes.

They extend RuntimeException.

Examples: NullPointerException, ArrayIndexOutOfBoundsException, ArithmeticException

```java
public class UncheckedDemo {
    public static void main(String[] args) {

        // These all compile fine but crash at runtime if they happen

        // NullPointerException
        String s = null;
        // s.length(); // NullPointerException at runtime

        // ArrayIndexOutOfBoundsException
        int[] arr = new int[3];
        // arr[10] = 5; // ArrayIndexOutOfBoundsException at runtime

        // ArithmeticException
        int a = 10, b = 0;
        // int result = a / b; // ArithmeticException: / by zero

        // NumberFormatException
        // int num = Integer.parseInt("abc"); // NumberFormatException
    }
}
```

### Checked vs Unchecked - Summary

```
Checked                          |  Unchecked
---------------------------------|----------------------------------
Extends Exception                |  Extends RuntimeException
Compiler forces you to handle    |  Compiler does not check
Must use try-catch or throws     |  Optional to handle
Happen due to external factors   |  Happen due to bugs in your code
FileNotFoundException            |  NullPointerException
IOException                      |  ArrayIndexOutOfBoundsException
SQLException                     |  ArithmeticException
```

---

## PART 4 - TRY CATCH FINALLY

### Basic try-catch

```java
public class TryCatchDemo {
    public static void main(String[] args) {

        try {
            int result = 10 / 0; // this throws ArithmeticException
            System.out.println(result); // this line never runs
        } catch (ArithmeticException e) {
            System.out.println("Cannot divide by zero");
            System.out.println("Exception message: " + e.getMessage());
        }

        System.out.println("Program continues after catch");
    }
}
```

### Multiple catch blocks

You can have multiple catch blocks for different exceptions.

```java
public class MultipleCatch {
    public static void main(String[] args) {

        String[] names = {"Arman", "Priya", null, "Raj"};

        try {
            System.out.println(names[10].length()); // two problems here
            // names[10] throws ArrayIndexOutOfBoundsException
            // if index was valid but value was null, throws NullPointerException
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println("Index does not exist: " + e.getMessage());
        } catch (NullPointerException e) {
            System.out.println("Value is null");
        } catch (Exception e) {
            // generic catch - catches anything not caught above
            System.out.println("Something went wrong: " + e.getMessage());
        }
    }
}
```

Important rule: more specific exceptions must come before more general ones. If you put Exception first, it catches everything and the specific ones below it will never run. The compiler will give an error.

```java
// WRONG - compile error
try {
    // code
} catch (Exception e) {        // catches everything
} catch (ArithmeticException e) { // unreachable - compiler error
}

// CORRECT - specific first, general last
try {
    // code
} catch (ArithmeticException e) { // specific
} catch (Exception e) {           // general - catches rest
}
```

### Multi-catch (Java 7+)

If you want to handle multiple exceptions the same way, you can combine them.

```java
try {
    // code that can throw multiple exceptions
} catch (IOException | SQLException e) {
    System.out.println("IO or SQL problem: " + e.getMessage());
}
```

### finally block

finally block always runs, no matter what. Whether an exception happened or not. Whether it was caught or not.

Use it for cleanup - closing connections, releasing resources.

```java
public class FinallyDemo {
    public static void main(String[] args) {

        try {
            System.out.println("In try block");
            int result = 10 / 0;
            System.out.println("This will not print");
        } catch (ArithmeticException e) {
            System.out.println("In catch block");
        } finally {
            System.out.println("In finally block - always runs");
        }

        System.out.println("After try-catch-finally");
    }
}

// Output:
// In try block
// In catch block
// In finally block - always runs
// After try-catch-finally
```

finally runs even when there is no exception:

```java
try {
    System.out.println("No exception here");
} catch (Exception e) {
    System.out.println("This will not run");
} finally {
    System.out.println("This will still run");
}

// Output:
// No exception here
// This will still run
```

finally and return - finally still runs even if you return inside try:

```java
public static int getData() {
    try {
        return 1;
    } finally {
        System.out.println("finally runs before the return");
        // if you put return here, it overrides the return in try
    }
}
```

### One case where finally does NOT run

If System.exit() is called, the JVM shuts down and finally does not run.

```java
try {
    System.exit(0); // JVM shuts down immediately
} finally {
    System.out.println("This will NOT print");
}
```

### Nested try-catch

```java
try {
    System.out.println("Outer try");
    try {
        int result = 10 / 0;
    } catch (ArithmeticException e) {
        System.out.println("Inner catch: " + e.getMessage());
    }
} catch (Exception e) {
    System.out.println("Outer catch");
} finally {
    System.out.println("Outer finally");
}
```

---

## PART 5 - THROWS KEYWORD

throws is used in the method signature to declare that this method might throw a checked exception. It passes the responsibility to the caller.

```java
import java.io.IOException;

public class ThrowsDemo {

    // declaring that this method might throw IOException
    public static void readFile(String path) throws IOException {
        // some file reading code
        throw new IOException("File not found: " + path);
    }

    public static void main(String[] args) {
        // since readFile throws IOException, we must handle it here
        try {
            readFile("data.txt");
        } catch (IOException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
```

Chaining throws - if main also does not want to handle it:

```java
public static void main(String[] args) throws IOException {
    readFile("data.txt");
    // now whoever calls main must handle it
    // but since main is the entry point, JVM handles it - program crashes
}
```

---

## PART 6 - THROW KEYWORD

throw is used to manually throw an exception from your code.

```java
public class ThrowDemo {

    public static void checkAge(int age) {
        if (age < 0) {
            throw new IllegalArgumentException("Age cannot be negative: " + age);
        }
        if (age < 18) {
            throw new ArithmeticException("Not eligible - age is " + age);
        }
        System.out.println("Eligible. Age is " + age);
    }

    public static void main(String[] args) {
        try {
            checkAge(-5);
        } catch (IllegalArgumentException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            checkAge(15);
        } catch (ArithmeticException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        checkAge(20); // works fine
    }
}
```

### throw vs throws

```
throw                            |  throws
---------------------------------|----------------------------------
Used inside method body          |  Used in method signature
Throws an actual exception       |  Declares possible exceptions
throw new IOException(...)       |  public void method() throws IOException
Only one exception at a time     |  Can declare multiple: throws A, B, C
```

---

## PART 7 - CUSTOM EXCEPTIONS

You can create your own exception classes. This is useful when you want meaningful, specific error messages for your application.

### Custom Unchecked Exception (extends RuntimeException)

Use this when the exception is due to a programming mistake or invalid data.

```java
// Define custom exception
class InvalidAgeException extends RuntimeException {

    public InvalidAgeException(String message) {
        super(message); // pass message to parent
    }

    public InvalidAgeException(String message, Throwable cause) {
        super(message, cause); // pass message and original cause
    }
}

// Use it
public class CustomExceptionDemo {

    public static void validateAge(int age) {
        if (age < 0 || age > 150) {
            throw new InvalidAgeException("Age must be between 0 and 150. You gave: " + age);
        }
    }

    public static void main(String[] args) {
        try {
            validateAge(-5);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
        }

        try {
            validateAge(200);
        } catch (InvalidAgeException e) {
            System.out.println("Caught: " + e.getMessage());
        }
    }
}
```

### Custom Checked Exception (extends Exception)

Use this when the caller must be forced to handle the situation.

```java
// Define custom checked exception
class InsufficientBalanceException extends Exception {

    private double amount;

    public InsufficientBalanceException(String message, double amount) {
        super(message);
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }
}

// Use it
class BankAccount {
    String owner;
    double balance;

    BankAccount(String owner, double balance) {
        this.owner = owner;
        this.balance = balance;
    }

    // checked exception - caller must handle
    public void withdraw(double amount) throws InsufficientBalanceException {
        if (amount > balance) {
            throw new InsufficientBalanceException(
                "Not enough balance. Available: " + balance + ", Requested: " + amount,
                amount
            );
        }
        balance -= amount;
        System.out.println("Withdrew " + amount + ". Remaining: " + balance);
    }
}

public class BankDemo {
    public static void main(String[] args) {
        BankAccount account = new BankAccount("Arman", 5000);

        try {
            account.withdraw(2000); // works fine
            account.withdraw(4000); // throws InsufficientBalanceException
        } catch (InsufficientBalanceException e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("You tried to withdraw: " + e.getAmount());
        }
    }
}
```

### Exception with error codes - professional style

```java
class AppException extends RuntimeException {
    private int errorCode;

    public AppException(int errorCode, String message) {
        super(message);
        this.errorCode = errorCode;
    }

    public int getErrorCode() {
        return errorCode;
    }
}

// Usage
throw new AppException(404, "Student not found");
throw new AppException(400, "Invalid input data");
throw new AppException(500, "Database connection failed");
```

---

## PART 8 - TRY-WITH-RESOURCES

Before Java 7, when you opened a resource like a file or database connection, you had to close it manually in the finally block.

```java
// OLD WAY - before Java 7
FileReader fr = null;
try {
    fr = new FileReader("file.txt");
    // use fr
} catch (IOException e) {
    e.printStackTrace();
} finally {
    if (fr != null) {
        try {
            fr.close(); // you even need try-catch here
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
```

This is ugly and easy to forget. Java 7 introduced try-with-resources to fix this.

### Try-with-resources

If you open a resource inside try(), it gets automatically closed when the try block finishes - whether normally or with an exception.

The resource must implement the AutoCloseable interface.

```java
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class TryWithResourcesDemo {
    public static void main(String[] args) {

        // resource is declared inside try()
        // it will be automatically closed at the end
        try (FileReader fr = new FileReader("file.txt");
             BufferedReader br = new BufferedReader(fr)) {

            String line;
            while ((line = br.readLine()) != null) {
                System.out.println(line);
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
        // fr and br are automatically closed here - no finally needed
    }
}
```

### Multiple resources in try-with-resources

```java
try (ResourceA a = new ResourceA();
     ResourceB b = new ResourceB()) {
    // use a and b
} catch (Exception e) {
    // handle
}
// both a and b are closed automatically
// they close in REVERSE order - b closes first, then a
```

### Creating your own AutoCloseable class

```java
class DatabaseConnection implements AutoCloseable {
    String name;

    DatabaseConnection(String name) {
        this.name = name;
        System.out.println("Opening connection: " + name);
    }

    public void query(String sql) {
        System.out.println("Running query: " + sql);
    }

    @Override
    public void close() {
        System.out.println("Closing connection: " + name);
    }
}

public class AutoCloseableDemo {
    public static void main(String[] args) {

        try (DatabaseConnection db = new DatabaseConnection("MyDB")) {
            db.query("SELECT * FROM students");
            db.query("SELECT * FROM teachers");
        } // close() is called automatically here

        System.out.println("After try block");
    }
}

// Output:
// Opening connection: MyDB
// Running query: SELECT * FROM students
// Running query: SELECT * FROM teachers
// Closing connection: MyDB
// After try block
```

### Suppressed Exceptions

If an exception happens inside the try block AND another exception happens during close(), the second one does not replace the first. It gets attached as a suppressed exception.

```java
class BrokenResource implements AutoCloseable {
    public void use() throws Exception {
        throw new Exception("Exception from use()");
    }

    public void close() throws Exception {
        throw new Exception("Exception from close()");
    }
}

public class SuppressedDemo {
    public static void main(String[] args) {
        try (BrokenResource r = new BrokenResource()) {
            r.use();
        } catch (Exception e) {
            System.out.println("Main exception: " + e.getMessage());
            // suppressed exception is attached to main exception
            for (Throwable suppressed : e.getSuppressed()) {
                System.out.println("Suppressed: " + suppressed.getMessage());
            }
        }
    }
}

// Output:
// Main exception: Exception from use()
// Suppressed: Exception from close()
```

---

## PART 9 - EXCEPTION METHODS

Every exception object has these methods:

```java
try {
    int result = 10 / 0;
} catch (ArithmeticException e) {

    e.getMessage();     // returns the error message string
    e.toString();       // returns class name + message
    e.printStackTrace();// prints full stack trace to console

    // getting the cause - when one exception causes another
    e.getCause();       // returns the Throwable that caused this exception

    // getting stack trace as array
    StackTraceElement[] trace = e.getStackTrace();
    for (StackTraceElement element : trace) {
        System.out.println(element.getClassName() + "." + element.getMethodName()
            + " line " + element.getLineNumber());
    }
}
```

### Exception chaining - wrapping one exception in another

```java
public class ExceptionChaining {

    public static void readDatabase() throws Exception {
        try {
            // simulate database error
            throw new RuntimeException("Connection timeout");
        } catch (RuntimeException e) {
            // wrap original exception in a more meaningful one
            throw new Exception("Failed to load data from database", e);
        }
    }

    public static void main(String[] args) {
        try {
            readDatabase();
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            System.out.println("Caused by: " + e.getCause().getMessage());
        }
    }
}
```

---

## PART 10 - COMMON EXCEPTIONS AND WHEN THEY HAPPEN

```java
public class CommonExceptions {
    public static void main(String[] args) {

        // NullPointerException - using a null reference
        String s = null;
        // s.length(); // NPE

        // ArrayIndexOutOfBoundsException - invalid array index
        int[] arr = new int[3];
        // arr[5] = 10; // AIOOBE

        // StringIndexOutOfBoundsException - invalid string index
        String str = "hello";
        // str.charAt(10); // SIOOBE

        // ClassCastException - wrong type cast
        Object obj = "hello";
        // Integer num = (Integer) obj; // CCE

        // NumberFormatException - invalid string to number conversion
        // int n = Integer.parseInt("abc"); // NFE

        // ArithmeticException - math error
        // int r = 10 / 0; // AE

        // StackOverflowError - infinite recursion
        // infiniteMethod(); // SOE

        // IllegalArgumentException - invalid argument to method
        // Thread t = new Thread(null, null, ""); // IAE - empty name

        // IllegalStateException - method called at wrong time
        // Iterator called when hasNext() is false, calling next() throws this
    }
}
```

---

## PART 11 - BEST PRACTICES

These are important for interviews and real code.

Catch specific exceptions, not just Exception:

```java
// BAD
try {
    // code
} catch (Exception e) {
    // too broad - catches everything including things you did not expect
}

// GOOD
try {
    // code
} catch (FileNotFoundException e) {
    // handle specifically
} catch (IOException e) {
    // handle specifically
}
```

Never swallow exceptions silently:

```java
// BAD - you will never know something went wrong
try {
    riskyOperation();
} catch (Exception e) {
    // empty catch - worst thing you can do
}

// GOOD
try {
    riskyOperation();
} catch (Exception e) {
    System.out.println("Error: " + e.getMessage()); // at minimum log it
    // or throw it again, or handle properly
}
```

Use finally or try-with-resources to close resources:

```java
// Always close resources
try (Connection conn = getConnection()) {
    // use conn
} // auto closed
```

Do not use exceptions for flow control:

```java
// BAD - using exception to check if string is a number
try {
    int n = Integer.parseInt(input);
    // it's a number
} catch (NumberFormatException e) {
    // it's not a number
}

// GOOD
if (input.matches("\\d+")) {
    int n = Integer.parseInt(input);
}
```

Custom exception names should end with Exception:

```java
// GOOD names
class InvalidAgeException extends RuntimeException {}
class PaymentFailedException extends Exception {}
class UserNotFoundException extends RuntimeException {}
```
