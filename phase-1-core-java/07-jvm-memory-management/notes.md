# JVM and Memory Management - Complete Notes

---

## PART 1 - WHAT IS JVM AND WHY IT EXISTS

When you write Java code, you write it in a .java file. But the computer cannot run .java files directly. The computer only understands machine code - binary instructions specific to its processor.

Here is the problem: a Windows computer with Intel processor has completely different machine code than a Mac with Apple M1 chip. If Java compiled directly to machine code, you would have to rewrite your program for every platform.

Java solves this with JVM - Java Virtual Machine.

The process works like this:

```
Your Code (.java)
      |
   javac (Java Compiler)
      |
  Bytecode (.class)        <-- platform independent
      |
   JVM (on your machine)   <-- platform specific
      |
  Machine Code             <-- runs on your CPU
```

You write code once. javac compiles it to bytecode. Bytecode is the same on every platform. JVM on each platform reads that same bytecode and converts it to machine code for that specific platform.

This is what "Write Once, Run Anywhere" means.

JVM is not just a bytecode interpreter. It also:

- Manages memory (allocates and frees memory automatically)
- Handles garbage collection
- Provides security (sandboxes your code)
- Optimizes code at runtime using JIT compiler

---

## PART 2 - JVM ARCHITECTURE

JVM has several components that work together.

```
+--------------------------------------------------+
|                    JVM                           |
|                                                  |
|  +----------------+   +---------------------+   |
|  |  Class Loader  |   |   Execution Engine  |   |
|  |  Subsystem     |   |                     |   |
|  |                |   |  - Interpreter      |   |
|  | - Bootstrap    |   |  - JIT Compiler     |   |
|  | - Extension    |   |  - Garbage Collector|   |
|  | - Application  |   |                     |   |
|  +----------------+   +---------------------+   |
|                                                  |
|  +--------------------------------------------+  |
|  |           Runtime Data Areas               |  |
|  |                                            |  |
|  |  Method Area  |  Heap  |  Java Stacks      |  |
|  |  PC Register  |  Native Method Stack       |  |
|  +--------------------------------------------+  |
|                                                  |
|  +----------------+                             |
|  |  Native Method |                             |
|  |  Interface     |                             |
|  +----------------+                             |
+--------------------------------------------------+
```

### Class Loader Subsystem

Loads .class files into memory. Works in three phases:

- Loading: reads .class file and creates a Class object
- Linking: verifies bytecode, prepares memory, resolves references
- Initialization: runs static initializers and assigns static variables

### Runtime Data Areas (Memory Areas)

This is where your program's data lives while it runs.

**Method Area (Metaspace in Java 8+)**

- Stores class-level data: class name, parent class, methods, static variables, constant pool
- Shared across all threads
- One Method Area per JVM

**Heap**

- Where all objects are created with new keyword
- Shared across all threads
- Garbage collector manages this
- Divided into Young Generation and Old Generation

**Java Stack (one per thread)**

- Each thread has its own stack
- Stores method calls and local variables
- Each method call creates a Stack Frame
- Frame is destroyed when method returns
- StackOverflowError when stack is full (infinite recursion)

**PC Register (Program Counter)**

- One per thread
- Stores address of current instruction being executed
- Moves to next instruction after each execution

**Native Method Stack**

- For native methods (methods written in C/C++ called from Java)
- One per thread

### Execution Engine

**Interpreter**

- Reads bytecode line by line and executes
- Simple but slow - reads same instructions again and again

**JIT Compiler (Just In Time)**

- Identifies frequently executed code (hot code)
- Compiles that bytecode to native machine code
- Next time that code runs, machine code runs directly - much faster
- This is why Java gets faster over time as it runs

**Garbage Collector**

- Automatically finds and removes objects that are no longer referenced
- Frees heap memory

---

## PART 3 - CLASSLOADER MECHANISM

ClassLoader is responsible for loading .class files into memory.

### Three types of ClassLoaders

```
Bootstrap ClassLoader (built in C/C++)
        |
Extension ClassLoader
        |
Application ClassLoader
```

**Bootstrap ClassLoader**

- The parent of all classloaders
- Loads core Java classes from rt.jar
- Classes like java.lang.String, java.util.ArrayList
- Written in native code (C/C++), not Java itself
- Returns null when you call getClassLoader() on classes loaded by it

**Extension ClassLoader**

- Loads classes from jre/lib/ext directory
- Security extensions, XML parsers, etc.

**Application ClassLoader (also called System ClassLoader)**

- Loads classes from your application's classpath
- Your own classes and third-party libraries

```java
public class ClassLoaderDemo {
    public static void main(String[] args) {

        // Application ClassLoader loaded this class
        ClassLoader appLoader = ClassLoaderDemo.class.getClassLoader();
        System.out.println(appLoader);
        // sun.misc.Launcher$AppClassLoader@...

        // Extension ClassLoader loaded this
        ClassLoader extLoader = appLoader.getParent();
        System.out.println(extLoader);
        // sun.misc.Launcher$ExtClassLoader@...

        // Bootstrap ClassLoader loaded String
        // Returns null because Bootstrap is native, not a Java object
        ClassLoader bootstrapLoader = String.class.getClassLoader();
        System.out.println(bootstrapLoader);
        // null

        // How classes are loaded
        System.out.println(ClassLoaderDemo.class.getClassLoader().getClass().getName());
    }
}
```

### Delegation Model (Parent Delegation)

When a ClassLoader is asked to load a class, it does NOT try to load it immediately. It first asks its parent. Parent asks its parent. This goes up to Bootstrap ClassLoader.

If Bootstrap can load it, done. If not, control comes back down. Extension tries. If not found, Application tries. If not found anywhere, ClassNotFoundException.

```
Application ClassLoader receives request to load "com.myapp.Student"
    |
    | delegates to parent
    v
Extension ClassLoader
    |
    | delegates to parent
    v
Bootstrap ClassLoader
    | tries to load - not found in core Java
    | returns control back
    v
Extension ClassLoader
    | tries to load - not found in extensions
    | returns control back
    v
Application ClassLoader
    | finds it in classpath
    | loads it
    v
Class loaded successfully
```

Why this model? Security. If someone writes a malicious class called java.lang.String, the delegation model ensures the real java.lang.String from Bootstrap is always loaded first. Your malicious String never gets used.

### Class Loading in detail - three phases

```java
// When JVM encounters: Student s = new Student();
// It checks if Student class is already loaded
// If not, ClassLoader loads it through these phases:

// Phase 1: LOADING
// - ClassLoader finds Student.class file
// - Reads binary data
// - Creates java.lang.Class object in Method Area

// Phase 2: LINKING
// Sub-phase 2a: VERIFICATION
// - Checks if bytecode is valid and follows JVM spec
// - Prevents malformed/malicious code from running

// Sub-phase 2b: PREPARATION
// - Memory allocated for static variables
// - Static variables set to DEFAULT values (0, null, false)
// - NOT yet the values you wrote in code

// Sub-phase 2c: RESOLUTION
// - Symbolic references replaced with direct references
// - "java.lang.String" text becomes actual memory address of String class

// Phase 3: INITIALIZATION
// - Static variables assigned actual values you wrote
// - Static initializer blocks run
// - Parent class initialized first, then child class
```

### Custom ClassLoader

You can write your own ClassLoader to load classes from unusual places - network, encrypted files, databases.

```java
import java.io.*;

public class CustomClassLoader extends ClassLoader {

    private String classPath;

    public CustomClassLoader(String classPath) {
        this.classPath = classPath;
    }

    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // Read .class file from custom location
            byte[] classData = loadClassData(name);
            // Convert byte array to Class object
            return defineClass(name, classData, 0, classData.length);
        } catch (IOException e) {
            throw new ClassNotFoundException("Could not load class: " + name, e);
        }
    }

    private byte[] loadClassData(String name) throws IOException {
        String fileName = classPath + name.replace('.', '/') + ".class";
        try (FileInputStream fis = new FileInputStream(fileName);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            byte[] buffer = new byte[1024];
            int bytesRead;
            while ((bytesRead = fis.read(buffer)) != -1) {
                bos.write(buffer, 0, bytesRead);
            }
            return bos.toByteArray();
        }
    }
}
```

---

## PART 4 - HEAP VS STACK MEMORY

This is one of the most asked interview questions. Understanding this deeply is very important.

### Stack Memory

- Each thread gets its own stack
- Stores method calls (stack frames) and local primitive variables
- Memory is allocated when method is called, freed when method returns
- LIFO - Last In First Out
- Very fast - just move a pointer
- Fixed size - StackOverflowError if exceeded
- Thread safe - each thread has its own stack

What goes in stack frame:

- Local variables (primitives stored as values, objects stored as references)
- Method parameters
- Return address (where to go after method returns)
- Intermediate calculations

```java
public class StackDemo {

    public static void main(String[] args) {
        int a = 5;          // a stored in main's stack frame
        int b = 10;         // b stored in main's stack frame
        int result = add(a, b); // new stack frame created for add()
        System.out.println(result);
        // add's frame is destroyed after it returns
    }

    public static int add(int x, int y) {
        // x and y stored in add's stack frame
        int sum = x + y;    // sum stored in add's stack frame
        return sum;
        // entire add frame destroyed here
    }
}

// Stack during execution:
// |  add frame: x=5, y=10, sum=15  |  <- top
// |  main frame: a=5, b=10, result |
// +----------------------------------+
```

### Heap Memory

- Shared by all threads
- All objects created with new keyword go here
- Managed by Garbage Collector
- Slower than stack - complex allocation/deallocation
- Can be large - controlled by -Xmx JVM flag
- Not thread safe - need synchronization for shared objects

```java
public class HeapDemo {

    public static void main(String[] args) {

        // "new Student()" creates object in HEAP
        // variable 'student' is a reference stored in STACK
        // the reference points to the object in heap
        Student student = new Student("Arman", 85);

        // 'name' reference is in stack
        // actual string "Hello" is in heap (String pool actually, part of heap)
        String name = "Hello";

        // arr reference is in stack
        // actual array [1,2,3,4,5] is in heap
        int[] arr = {1, 2, 3, 4, 5};
    }
}
```

### Visual representation

```
STACK (per thread)              HEAP (shared)
+-------------------+           +-------------------------+
|  main() frame     |           |                         |
|  student ref -----+---------> | Student object          |
|  name ref    -----+---------> | {name="Arman",marks=85} |
|  arr ref     -----+---------> |                         |
+-------------------+           | int[] {1,2,3,4,5}       |
                                |                         |
+-------------------+           | String "Hello"          |
|  another thread   |           |   (String Pool)         |
|  frame            |           |                         |
+-------------------+           +-------------------------+
```

### Stack vs Heap - complete comparison

```
Feature          Stack                      Heap
-----------------|--------------------------|---------------------------
What is stored   Primitives, references,    Objects, arrays, strings
                 method calls
Shared           No - each thread has own   Yes - all threads share
Size             Small, fixed               Large, dynamic
Speed            Very fast                  Slower
Management       Automatic (LIFO)           Garbage Collector
Error            StackOverflowError         OutOfMemoryError
Lifetime         Method scope               Until no references
```

### Where exactly does each thing go?

```java
public class MemoryDemo {

    // static variable - Method Area (Metaspace)
    static int count = 0;

    // instance variable - Heap (inside the object)
    int id;
    String name; // reference in heap, actual String in String Pool

    public static void main(String[] args) {

        // primitive local variable - Stack
        int x = 10;
        double price = 99.99;

        // object reference in Stack, object in Heap
        MemoryDemo obj = new MemoryDemo();

        // array reference in Stack, array in Heap
        int[] numbers = new int[5];

        // String literal goes to String Pool (part of Heap)
        String s1 = "Hello"; // in String Pool
        String s2 = "Hello"; // same object from String Pool (s1 == s2 is true)

        // new String() creates new object in regular Heap
        String s3 = new String("Hello"); // new object (s1 == s3 is false)

        System.out.println(s1 == s2); // true - same pool object
        System.out.println(s1 == s3); // false - different objects
        System.out.println(s1.equals(s3)); // true - same content
    }
}
```

### StackOverflowError - when stack runs out

```java
public class StackOverflow {

    // This method keeps calling itself forever
    public static void infiniteRecursion(int n) {
        System.out.println("Level: " + n);
        infiniteRecursion(n + 1); // new stack frame every time
        // stack fills up and throws StackOverflowError
    }

    public static void main(String[] args) {
        try {
            infiniteRecursion(1);
        } catch (StackOverflowError e) {
            System.out.println("Stack overflow caught");
        }
    }
}
```

### OutOfMemoryError - when heap runs out

```java
import java.util.ArrayList;
import java.util.List;

public class OutOfMemory {
    public static void main(String[] args) {
        List<byte[]> list = new ArrayList<>();
        try {
            while (true) {
                list.add(new byte[1024 * 1024]); // add 1MB at a time
                System.out.println("Total: " + list.size() + "MB");
            }
        } catch (OutOfMemoryError e) {
            System.out.println("Heap is full: " + e.getMessage());
        }
    }
}
```

---

## PART 5 - HEAP MEMORY STRUCTURE

The heap is not one flat area. It is divided into regions that the Garbage Collector manages.

```
+-------------------------------------------------------+
|                        HEAP                           |
|                                                       |
|  +------------------+  +---------------------------+  |
|  |  Young Generation |  |    Old Generation         |  |
|  |                   |  |    (Tenured Space)        |  |
|  |  +----+  +-----+  |  |                           |  |
|  |  |Eden|  | S0  |  |  |  Long-lived objects       |  |
|  |  |    |  +-----+  |  |  survive multiple GC      |  |
|  |  |    |  | S1  |  |  |  cycles and end up here   |  |
|  |  +----+  +-----+  |  |                           |  |
|  +------------------+  +---------------------------+  |
|                                                       |
+-------------------------------------------------------+
```

### Young Generation

Where all new objects are created first. Divided into:

**Eden Space**: New objects are born here. When Eden fills up, Minor GC runs.

**Survivor Spaces (S0 and S1)**: Objects that survive Minor GC are moved here. Two spaces, only one used at a time. Objects bounce between S0 and S1 across GC cycles.

**Aging**: Each time an object survives a GC, its age counter increases. When age reaches threshold (usually 15), object is promoted to Old Generation.

### Old Generation (Tenured Space)

Long-lived objects live here. Objects promoted from Young Generation. When Old Generation fills up, Major GC (Full GC) runs. Full GC is more expensive and causes longer pauses.

### Metaspace (Java 8+)

In Java 7 and before, there was a PermGen (Permanent Generation) inside heap for class metadata. In Java 8, PermGen was removed and replaced with Metaspace outside heap in native memory.

Metaspace stores: class definitions, method bytecode, static variables.

---

## PART 6 - GARBAGE COLLECTION

### What is Garbage Collection

In languages like C/C++, you manually allocate memory (malloc) and free it (free). If you forget to free, you have a memory leak. If you free something twice, program crashes.

Java handles this automatically. The Garbage Collector (GC) finds objects that are no longer reachable and frees their memory.

An object is eligible for garbage collection when there are no more references pointing to it.

```java
public class GCDemo {
    public static void main(String[] args) {

        // Object created - referenced by 'a'
        Student a = new Student("Arman");

        // 'b' also references the same object
        Student b = a;

        // 'a' no longer references the object - but 'b' still does
        a = null;
        // Object is NOT eligible for GC yet - b still references it

        // 'b' no longer references the object
        b = null;
        // NOW the Student object is eligible for GC - no references remain

        // Suggest GC to run (no guarantee it actually runs)
        System.gc();
    }
}
```

### How GC finds garbage - Mark and Sweep

**Step 1 - Mark phase:**
GC starts from GC Roots and marks everything reachable.

GC Roots are:

- Local variables in active stack frames
- Static variables
- Active threads
- JNI references

GC traverses all objects reachable from these roots and marks them as alive.

**Step 2 - Sweep phase:**
Everything NOT marked is garbage. GC frees that memory.

**Step 3 - Compact phase (optional):**
After sweeping, memory is fragmented. Compaction moves all live objects together, leaving free space as one contiguous block.

### Minor GC vs Major GC

**Minor GC** (Young Generation GC):

- Runs when Eden space fills up
- Very fast - Young Gen is small
- Most objects die young (short-lived local variables, temporary objects)
- Surviving objects move to Survivor spaces
- After enough cycles, long-lived objects promoted to Old Gen

**Major GC / Full GC** (Old Generation GC):

- Runs when Old Gen fills up
- Much slower - Old Gen is large
- Causes longer pause times (Stop the World - all application threads pause)
- This is what causes the infamous "GC pauses" in Java applications

### GC Algorithms - different types

**Serial GC**

- Single threaded - only one thread does GC
- Causes long Stop-The-World pauses
- Good for small applications, single CPU
- Enable: -XX:+UseSerialGC

**Parallel GC (Throughput GC)**

- Multiple threads for GC - faster
- Still has Stop-The-World pauses
- Good for batch processing where throughput matters more than latency
- Default in Java 8
- Enable: -XX:+UseParallelGC

**G1 GC (Garbage First)**

- Divides heap into equal-sized regions
- Collects regions with most garbage first (hence Garbage First)
- Aims for predictable pause times
- Default in Java 9+
- Good for large heaps (4GB+)
- Enable: -XX:+UseG1GC

**ZGC (Z Garbage Collector)**

- Very low pause times (< 10ms even for large heaps)
- Java 11+
- Good for real-time applications
- Enable: -XX:+UseZGC

**Shenandoah GC**

- Similar to ZGC - very low pause
- Available in OpenJDK
- Enable: -XX:+UseShenandoahGC

### finalize() - do not use

Before Java 9, you could override finalize() in a class to run code when object was garbage collected. This was unreliable - no guarantee when or if it would run.

```java
// DON'T DO THIS
class Resource {
    @Override
    protected void finalize() {
        // cleanup - unreliable, may never run
        System.out.println("Cleaning up");
    }
}
```

Instead use try-with-resources or explicit cleanup methods.

### Making objects eligible for GC

```java
public class MakeEligible {
    public static void main(String[] args) {

        // 1. Null the reference
        Student s = new Student("Arman");
        s = null; // eligible

        // 2. Reassign reference
        Student s2 = new Student("Priya");
        s2 = new Student("Raj"); // Priya object eligible

        // 3. Object goes out of scope
        {
            Student s3 = new Student("Sneha");
        } // s3 out of scope - Sneha object eligible

        // 4. Island of isolation - objects reference each other
        // but no external references
        Node n1 = new Node();
        Node n2 = new Node();
        n1.next = n2;
        n2.next = n1; // circular reference
        n1 = null;
        n2 = null;
        // Both are eligible - GC handles circular references
        // (old reference counting could not handle this)
    }
}
```

### Memory leaks in Java

Java has GC but memory leaks are still possible. They happen when objects are still referenced but never used again.

```java
import java.util.*;

// Classic memory leak - static collection grows forever
public class MemoryLeak {

    // Static map - lives as long as the class
    private static Map<Integer, String> cache = new HashMap<>();

    public static void addToCache(int id, String data) {
        cache.put(id, data);
        // Never removes entries - cache grows forever
    }

    // Fix: use WeakHashMap or manually remove entries
    private static WeakHashMap<Integer, String> weakCache = new WeakHashMap<>();
    // WeakHashMap entries are removed when key has no strong references
}
```

Common causes of memory leaks:

- Static collections that grow but never shrink
- Listeners/callbacks not deregistered
- Inner class holding reference to outer class
- Unclosed streams and connections
- ThreadLocal variables not cleaned up

---

## PART 7 - JAVA MEMORY MODEL (JMM)

### What is Java Memory Model

When you have multiple threads, each thread might cache variable values in its own local cache (CPU registers, L1/L2 cache). Changes made by one thread may not be visible to another thread immediately.

Java Memory Model (JMM) defines the rules for when changes made by one thread become visible to other threads.

Without JMM rules, multi-threaded programs would behave differently on different hardware because different CPUs have different caching strategies.

### Main Memory vs Working Memory

JMM defines two concepts:

**Main Memory**: the actual RAM where all variables live. Shared by all threads.

**Working Memory**: each thread has its own working memory (think of it as the CPU cache for that thread). A thread reads variables into its working memory and works with them there.

```
Thread 1                    Main Memory              Thread 2
Working Memory              (Shared RAM)             Working Memory
+----------+                +----------+             +----------+
| count=5  |<--- read ------| count=5  |--- read --->| count=5  |
|          |---- write ---->|          |<-- write ---|          |
+----------+                +----------+             +----------+
```

Problem: Thread 1 updates count to 6 in its working memory. Thread 2 still sees 5 in its working memory. This is the visibility problem.

### Happens-Before Relationship

JMM uses "happens-before" to define when one operation's result is guaranteed to be visible to another.

Key happens-before rules:

**1. Within a thread**: each action happens-before the next action in that thread.

**2. Monitor lock**: releasing a lock happens-before acquiring that same lock.

```java
synchronized (lock) {
    count = 5; // write
} // unlock - this write is now visible

synchronized (lock) { // lock again - any thread that locks sees count=5
    System.out.println(count); // guaranteed to see 5
}
```

**3. volatile write**: a write to a volatile variable happens-before any subsequent read of that variable.

```java
volatile boolean ready = false;
String data = null;

// Thread 1
data = "result";
ready = true; // volatile write

// Thread 2
if (ready) { // volatile read - if this sees true
    System.out.println(data); // guaranteed to see "result"
}
```

**4. Thread start**: Thread.start() happens-before any action in the started thread.

**5. Thread join**: all actions in a thread happen-before Thread.join() returns.

**6. Object construction**: object constructor completes before the object's finalizer starts.

### Visibility, Atomicity, Ordering - the three problems

**Visibility**: one thread's write may not be visible to another thread. Fix: volatile or synchronized.

```java
// Problem
boolean flag = false;
// Thread 1: flag = true;
// Thread 2: while(!flag) {} -- may never see flag=true

// Fix
volatile boolean flag = false;
```

**Atomicity**: compound operations like count++ are not atomic. They are read-modify-write which can be interrupted. Fix: synchronized or AtomicInteger.

```java
// Problem
int count = 0;
// Thread 1 and Thread 2 both do count++ -- race condition

// Fix
AtomicInteger count = new AtomicInteger(0);
count.incrementAndGet();
```

**Ordering**: compiler and CPU can reorder instructions for optimization. This can break multi-threaded code even if individual operations look correct.

```java
// Code you write
int a = 1;
int b = 2;
int c = a + b;

// Compiler might reorder to:
int b = 2;
int a = 1;
int c = a + b;
// Safe here because single thread

// But with multiple threads, reordering can cause bugs
// volatile and synchronized prevent problematic reordering
```

### Safe publication of objects

An object is "safely published" when it is made available to other threads in a way that guarantees they see its fully constructed state.

```java
// UNSAFE - another thread might see half-constructed object
class UnsafePublish {
    static Resource resource;

    // Thread 1
    static void init() {
        resource = new Resource(); // might be seen as non-null before constructor finishes
    }

    // Thread 2
    static void use() {
        if (resource != null) {
            resource.doSomething(); // might fail - object not fully constructed
        }
    }
}

// SAFE - ways to safely publish
class SafePublish {

    // 1. Static initializer - guaranteed to run before any thread uses the class
    static final Resource resource = new Resource();

    // 2. volatile field
    volatile Resource vResource;

    // 3. Final field - safe after constructor returns
    final Resource fResource;
    SafePublish() {
        fResource = new Resource();
    }

    // 4. Synchronized access
    private Resource syncResource;
    synchronized Resource getResource() {
        if (syncResource == null) syncResource = new Resource();
        return syncResource;
    }
}
```

### Double-checked locking - classic example of JMM

```java
// BROKEN - without volatile
class Singleton {
    private static Singleton instance;

    static Singleton getInstance() {
        if (instance == null) {              // check 1
            synchronized (Singleton.class) {
                if (instance == null) {      // check 2
                    instance = new Singleton();
                    // Problem: new Singleton() involves 3 steps:
                    // 1. allocate memory
                    // 2. call constructor
                    // 3. assign to instance
                    // CPU can reorder steps: 1, 3, 2
                    // Another thread sees instance != null at check 1
                    // but constructor has not run yet!
                }
            }
        }
        return instance;
    }
}

// FIXED - with volatile
class SingletonFixed {
    private static volatile SingletonFixed instance; // volatile prevents reordering

    static SingletonFixed getInstance() {
        if (instance == null) {
            synchronized (SingletonFixed.class) {
                if (instance == null) {
                    instance = new SingletonFixed(); // safe with volatile
                }
            }
        }
        return instance;
    }
}
```

---

## PART 8 - JVM TUNING FLAGS

You control JVM memory with flags when starting the program.

```bash
# Heap size
java -Xms512m -Xmx2g MyApp
# -Xms = initial heap size (512 MB)
# -Xmx = maximum heap size (2 GB)
# Good practice: set Xms = Xmx to avoid resize overhead

# Young generation size
java -Xmn256m MyApp
# -Xmn = size of young generation

# Stack size per thread
java -Xss512k MyApp
# -Xss = stack size per thread

# GC selection
java -XX:+UseG1GC MyApp        # Use G1 GC
java -XX:+UseZGC MyApp         # Use ZGC (Java 11+)
java -XX:+UseSerialGC MyApp    # Use Serial GC

# GC logging - see what GC is doing
java -verbose:gc MyApp
java -XX:+PrintGCDetails MyApp

# Heap dump when OutOfMemoryError
java -XX:+HeapDumpOnOutOfMemoryError MyApp
```

---

## PART 9 - QUICK REFERENCE

```
JVM Components:
  ClassLoader          -> loads .class files
  Method Area          -> class metadata, static variables
  Heap                 -> all objects live here
  Stack                -> method calls, local variables (one per thread)
  PC Register          -> current instruction (one per thread)
  Execution Engine     -> runs bytecode (interpreter + JIT)
  Garbage Collector    -> frees heap memory

ClassLoader hierarchy:
  Bootstrap -> Extension -> Application
  Delegation: child asks parent first

Memory areas:
  Heap: Young Gen (Eden + S0 + S1) + Old Gen + Metaspace
  Stack: one per thread, method frames
  Method Area: class data, static vars (Metaspace in Java 8+)

GC Types:
  Minor GC    -> Young Generation, fast
  Major GC    -> Old Generation, slow, Stop-The-World
  Serial      -> single thread, small apps
  Parallel    -> multi-thread, throughput focused, Java 8 default
  G1          -> region based, predictable pauses, Java 9+ default
  ZGC         -> very low latency, Java 11+

JMM Three problems:
  Visibility  -> volatile, synchronized
  Atomicity   -> synchronized, AtomicInteger
  Ordering    -> volatile (prevents reordering), synchronized

Common JVM flags:
  -Xms    -> initial heap size
  -Xmx    -> max heap size
  -Xss    -> stack size per thread
  -Xmn    -> young generation size
```
