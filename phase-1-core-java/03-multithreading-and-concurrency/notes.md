# Java Multithreading and Concurrency - Complete Notes

---

## PART 1 - WHAT IS MULTITHREADING AND WHY IT EXISTS

Normally a Java program runs one task at a time. One line runs, then the next, then the next. This is called single threading.

Problem: Imagine you are downloading a file and the entire program freezes until the download finishes. Nothing else works. This is bad.

Multithreading solves this. It lets your program do multiple things at the same time. Download the file in one thread, keep the UI responsive in another thread, process data in a third thread.

A Thread is the smallest unit of execution. One process (your program) can have many threads running at the same time.

The main() method itself runs in a thread called the main thread. When you create new threads, they run alongside the main thread.

---

## PART 2 - CREATING A THREAD

Two ways to create a thread.

### Way 1 - Extend Thread class

```java
class MyThread extends Thread {

    @Override
    public void run() {
        // this code runs in a new thread
        for (int i = 1; i <= 5; i++) {
            System.out.println("MyThread: " + i);
            try {
                Thread.sleep(500); // pause for 500ms
            } catch (InterruptedException e) {
                System.out.println("Thread interrupted");
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        MyThread t = new MyThread();
        t.start(); // start()   new thread and calls run()
        // DO NOT call t.run() directly - that runs in main thread, no new thread created

        // this runs in main thread at the same time
        for (int i = 1; i <= 5; i++) {
            System.out.println("Main: " + i);
        }
    }
}

// Output will be mixed - main and MyThread run simultaneously
// Main: 1
// MyThread: 1
// Main: 2
// MyThread: 2
// ... order is not guaranteed
```

### Way 2 - Implement Runnable interface (preferred)

```java
class MyRunnable implements Runnable {

    @Override
    public void run() {
        for (int i = 1; i <= 5; i++) {
            System.out.println(Thread.currentThread().getName() + ": " + i);
        }
    }
}

public class Main {
    public static void main(String[] args) {

        MyRunnable task = new MyRunnable();
        Thread t1 = new Thread(task, "Thread-1");
        Thread t2 = new Thread(task, "Thread-2");

        t1.start();
        t2.start();
    }
}
```

### Way 3 - Lambda (shortest way, when using Runnable)

```java
public class Main {
    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread 1: " + i);
            }
        });

        Thread t2 = new Thread(() -> {
            for (int i = 1; i <= 5; i++) {
                System.out.println("Thread 2: " + i);
            }
        });

        t1.start();
        t2.start();
    }
}
```

### Extend Thread vs Implement Runnable

```
Extend Thread                    |  Implement Runnable
---------------------------------|---------------------------------
Your class cannot extend anything else  |  Your class can extend another class
Tightly coupled to Thread        |  Loosely coupled - just a task
Less flexible                    |  More flexible
Not recommended                  |  Recommended
```

Always prefer Runnable. In real code you almost always use Runnable or Callable with ExecutorService.

---

## PART 3 - THREAD LIFECYCLE

A thread goes through different states in its life.

```
NEW -> RUNNABLE -> RUNNING -> (BLOCKED / WAITING / TIMED_WAITING) -> TERMINATED
```

### States explained

NEW - Thread object is created but start() is not called yet.

```java
Thread t = new Thread(() -> System.out.println("hello"));
// t is in NEW state here
```

RUNNABLE - start() has been called. Thread is ready to run. It may or may not be actually running right now - depends on the CPU scheduler.

```java
t.start();
// t is now RUNNABLE
```

RUNNING - Thread is currently executing on the CPU. You cannot directly see this state in Java - it is managed by the OS.

BLOCKED - Thread is waiting to acquire a lock (synchronized block). Another thread is holding that lock.

WAITING - Thread is waiting indefinitely for another thread to do something. Caused by wait(), join() with no timeout.

TIMED_WAITING - Thread is waiting but only for a specific amount of time. Caused by sleep(ms), join(ms), wait(ms).

TERMINATED - Thread has finished executing. Once terminated, it cannot be started again.

```java
public class ThreadStateDemo {
    public static void main(String[] args) throws InterruptedException {

        Thread t = new Thread(() -> {
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {}
        });

        System.out.println(t.getState()); // NEW

        t.start();
        System.out.println(t.getState()); // RUNNABLE

        Thread.sleep(500);
        System.out.println(t.getState()); // TIMED_WAITING (because of sleep inside)

        t.join(); // wait for t to finish
        System.out.println(t.getState()); // TERMINATED
    }
}
```

### Important Thread methods

```java
Thread t = new Thread(() -> {});

t.start();                    // start the thread
t.getName();                  // get thread name
t.setName("WorkerThread");    // set thread name
t.getPriority();              // get priority (1-10, default 5)
t.setPriority(Thread.MAX_PRIORITY); // set priority (10)
t.isAlive();                  // true if thread is running
t.isDaemon();                 // true if it is a daemon thread
t.setDaemon(true);            // set as daemon thread
t.getState();                 // get current state
t.interrupt();                // interrupt the thread

Thread.sleep(1000);           // pause current thread for 1 second
Thread.currentThread();       // get reference to current thread
Thread.currentThread().getName(); // name of current thread

t.join();                     // wait for t to finish before continuing
t.join(2000);                 // wait at most 2 seconds for t to finish
```

### join() example

```java
public class JoinDemo {
    public static void main(String[] args) throws InterruptedException {

        Thread downloader = new Thread(() -> {
            System.out.println("Downloading...");
            try { Thread.sleep(3000); } catch (InterruptedException e) {}
            System.out.println("Download complete");
        });

        downloader.start();
        downloader.join(); // main thread waits here until downloader finishes

        System.out.println("Processing downloaded file..."); // runs after download
    }
}
```

### Daemon threads

Daemon threads are background threads that die automatically when all non-daemon threads finish. The JVM does not wait for them.

Examples: Garbage collector runs as a daemon thread.

```java
Thread daemonThread = new Thread(() -> {
    while (true) {
        System.out.println("Daemon running...");
        try { Thread.sleep(1000); } catch (InterruptedException e) {}
    }
});

daemonThread.setDaemon(true); // must set BEFORE start()
daemonThread.start();

// main thread does its work and ends
// when main ends, daemon thread automatically stops too
Thread.sleep(3000);
System.out.println("Main thread ending");
```

---

## PART 4 - RUNNABLE VS CALLABLE

Both are used to define tasks that run in threads. The difference is that Callable returns a result and can throw checked exceptions.

```
Runnable                         |  Callable
---------------------------------|---------------------------------
run() method                     |  call() method
Returns void                     |  Returns a value (generic type)
Cannot throw checked exceptions  |  Can throw checked exceptions
Used with Thread directly        |  Used with ExecutorService
Result: nothing                  |  Result: Future<T>
```

### Runnable

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

Runnable task = () -> {
    System.out.println("Task running in: " + Thread.currentThread().getName());
};

ExecutorService executor = Executors.newFixedThreadPool(2);
executor.submit(task);
executor.shutdown();
```

### Callable

```java
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

Callable<Integer> task = () -> {
    System.out.println("Calculating...");
    Thread.sleep(2000);
    return 42; // returns a result
};

ExecutorService executor = Executors.newSingleThreadExecutor();
Future<Integer> future = executor.submit(task);

System.out.println("Task submitted, doing other work...");

Integer result = future.get(); // blocks until task completes and returns result
System.out.println("Result: " + result); // 42

executor.shutdown();
```

### Future methods

```java
Future<Integer> future = executor.submit(callableTask);

future.get();              // wait and get result - blocks current thread
future.get(2, TimeUnit.SECONDS); // wait max 2 seconds, throw TimeoutException if not done
future.isDone();           // true if task is done
future.isCancelled();      // true if task was cancelled
future.cancel(true);       // try to cancel the task
```

---

## PART 5 - EXECUTORSERVICE AND THREADPOOL

Creating a new Thread every time you need one is expensive. Thread creation takes time and memory.

ThreadPool solves this. You create a pool of threads upfront. When you submit a task, an available thread from the pool picks it up and runs it. When done, the thread goes back to the pool and waits for the next task.

ExecutorService is the interface. Executors is the utility class that creates different types of thread pools.

### Types of thread pools

```java
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

// Fixed thread pool - always exactly n threads
ExecutorService fixed = Executors.newFixedThreadPool(4);
// 4 threads, if more than 4 tasks submitted, extras wait in queue

// Single thread pool - exactly 1 thread, tasks run one after another
ExecutorService single = Executors.newSingleThreadExecutor();

// Cached thread pool - creates new threads as needed, reuses idle ones
// threads die after 60 seconds of being idle
ExecutorService cached = Executors.newCachedThreadPool();

// Scheduled thread pool - run tasks after a delay or repeatedly
import java.util.concurrent.ScheduledExecutorService;
ScheduledExecutorService scheduled = Executors.newScheduledThreadPool(2);
```

### Submitting tasks

```java
ExecutorService executor = Executors.newFixedThreadPool(3);

// submit Runnable - returns Future<?> but get() returns null
executor.submit(() -> System.out.println("Task 1"));

// submit Callable - returns Future<T> with actual result
Future<String> future = executor.submit(() -> {
    Thread.sleep(1000);
    return "Task 2 result";
});

// execute Runnable - no return value, no Future
executor.execute(() -> System.out.println("Task 3"));
```

### Full example with multiple tasks

```java
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class ThreadPoolDemo {
    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        List<Future<Integer>> futures = new ArrayList<>();

        // Submit 5 tasks - only 3 threads, so some tasks wait
        for (int i = 1; i <= 5; i++) {
            final int taskId = i;
            Future<Integer> future = executor.submit(() -> {
                System.out.println("Task " + taskId + " running in " + Thread.currentThread().getName());
                Thread.sleep(1000);
                return taskId * 10;
            });
            futures.add(future);
        }

        // Collect all results
        for (Future<Integer> future : futures) {
            System.out.println("Result: " + future.get()); // waits for each
        }

        executor.shutdown(); // stop accepting new tasks
        executor.awaitTermination(10, TimeUnit.SECONDS); // wait for current tasks to finish
    }
}
```

### Shutdown properly

```java
executor.shutdown();
// stops accepting new tasks
// waits for already submitted tasks to finish

executor.shutdownNow();
// stops accepting new tasks
// tries to interrupt all running tasks immediately
// returns list of tasks that were waiting but not started

// wait for shutdown to complete
boolean done = executor.awaitTermination(5, TimeUnit.SECONDS);
if (!done) {
    System.out.println("Some tasks did not finish");
}
```

### ScheduledExecutorService

```java
import java.util.concurrent.*;

ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(2);

// Run once after a delay
scheduler.schedule(() -> {
    System.out.println("Runs after 3 seconds");
}, 3, TimeUnit.SECONDS);

// Run repeatedly - first after 1 second, then every 2 seconds
scheduler.scheduleAtFixedRate(() -> {
    System.out.println("Repeating task: " + System.currentTimeMillis());
}, 1, 2, TimeUnit.SECONDS);

// Run repeatedly - wait 2 seconds after previous task ENDS before starting next
scheduler.scheduleWithFixedDelay(() -> {
    System.out.println("Task with fixed delay");
}, 1, 2, TimeUnit.SECONDS);
```

---

## PART 6 - SYNCHRONIZED KEYWORD

When two threads access and modify the same data at the same time, the result becomes unpredictable. This is called a race condition.

Example without synchronization - broken:

```java
class Counter {
    int count = 0;

    void increment() {
        count++; // this looks like one step but it is actually 3 steps:
                 // 1. read count
                 // 2. add 1
                 // 3. write back
                 // another thread can jump in between these steps
    }
}

public class RaceConditionDemo {
    public static void main(String[] args) throws InterruptedException {
        Counter counter = new Counter();

        Thread t1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });

        Thread t2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) counter.increment();
        });

        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Count: " + counter.count);
        // Expected: 2000
        // Actual: some random number less than 2000 because of race condition
    }
}
```

synchronized fixes this. Only one thread can execute a synchronized method or block at a time. Other threads wait.

### Synchronized method

```java
class Counter {
    int count = 0;

    synchronized void increment() {
        count++; // only one thread can be here at a time
    }

    synchronized int getCount() {
        return count;
    }
}

// Now count will always be 2000
```

### Synchronized block - more fine-grained

```java
class Counter {
    int count = 0;
    Object lock = new Object();

    void increment() {
        // non-synchronized code can run here
        System.out.println("About to increment");

        synchronized (lock) {
            count++; // only this part is synchronized
        }

        // non-synchronized code can run here
        System.out.println("Incremented");
    }
}
```

Synchronized block is better than synchronized method when you only need to protect a small part of the method. Less waiting time for other threads.

### Synchronized on static method

```java
class Counter {
    static int count = 0;

    // locks on the CLASS, not the object
    static synchronized void increment() {
        count++;
    }
}
```

### How synchronization works internally

Every object in Java has a monitor (intrinsic lock). When a thread enters a synchronized method or block, it acquires the lock. Other threads trying to enter any synchronized method of the same object will block until the lock is released. Lock is released when the thread exits the synchronized block.

### Deadlock

Deadlock happens when two threads are waiting for each other to release a lock and neither can proceed.

```java
Object lock1 = new Object();
Object lock2 = new Object();

Thread t1 = new Thread(() -> {
    synchronized (lock1) {
        System.out.println("T1 holds lock1, waiting for lock2");
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        synchronized (lock2) { // waiting for T2 to release lock2
            System.out.println("T1 got lock2");
        }
    }
});

Thread t2 = new Thread(() -> {
    synchronized (lock2) {
        System.out.println("T2 holds lock2, waiting for lock1");
        try { Thread.sleep(100); } catch (InterruptedException e) {}
        synchronized (lock1) { // waiting for T1 to release lock1
            System.out.println("T2 got lock1");
        }
    }
});

t1.start();
t2.start();
// Both threads wait forever - deadlock
```

Avoid deadlock by always acquiring locks in the same order.

---

## PART 7 - VOLATILE KEYWORD

Each thread has its own local cache of variables for performance. When a thread reads a variable, it may read from its local cache, not from main memory. This means one thread's changes may not be visible to another thread.

volatile fixes this. A volatile variable is always read from and written to main memory. Changes are immediately visible to all threads.

```java
class SharedFlag {

    // without volatile - thread2 may never see the change made by thread1
    // with volatile - change is immediately visible
    volatile boolean running = true;

    void stop() {
        running = false; // written to main memory immediately
    }
}

public class VolatileDemo {
    public static void main(String[] args) throws InterruptedException {

        SharedFlag flag = new SharedFlag();

        Thread worker = new Thread(() -> {
            while (flag.running) { // reads from main memory every time
                // keep working
            }
            System.out.println("Worker stopped");
        });

        worker.start();
        Thread.sleep(1000);
        flag.stop(); // worker will see this change
    }
}
```

### volatile vs synchronized

```
volatile                         |  synchronized
---------------------------------|---------------------------------
Only visibility guarantee        |  Visibility + atomicity guarantee
Does not prevent race conditions |  Prevents race conditions
No locking - faster              |  Locking - slower
Good for simple flags            |  Good for compound operations
Only for single read/write       |  For multiple operations as unit
```

volatile is enough when you just need one thread to signal another. Not enough for operations like count++ which are multiple steps.

---

## PART 8 - ATOMIC CLASSES

Instead of synchronized or volatile, Java provides atomic classes that handle thread safety automatically for simple operations.

```java
import java.util.concurrent.atomic.*;

AtomicInteger counter = new AtomicInteger(0);

counter.get();              // read value
counter.set(10);            // set value
counter.incrementAndGet();  // increment and return new value (like ++counter)
counter.getAndIncrement();  // return current value then increment (like counter++)
counter.addAndGet(5);       // add 5 and return new value
counter.getAndAdd(5);       // return current value then add 5
counter.compareAndSet(10, 20); // if current value is 10, set to 20, return true/false

AtomicLong longCounter = new AtomicLong(0);
AtomicBoolean flag = new AtomicBoolean(false);
AtomicReference<String> ref = new AtomicReference<>("initial");
```

Counter example with AtomicInteger - no synchronized needed:

```java
import java.util.concurrent.atomic.AtomicInteger;

class SafeCounter {
    AtomicInteger count = new AtomicInteger(0);

    void increment() {
        count.incrementAndGet(); // thread safe, no synchronized needed
    }

    int getCount() {
        return count.get();
    }
}
```

---

## PART 9 - LOCKS (ReentrantLock)

ReentrantLock is a more flexible alternative to synchronized.

Why use it over synchronized:

- You can try to acquire a lock without blocking (tryLock)
- You can set a timeout for lock acquisition
- You can interrupt a thread waiting for a lock
- More control overall

```java
import java.util.concurrent.locks.ReentrantLock;

class SafeCounter {
    private int count = 0;
    private ReentrantLock lock = new ReentrantLock();

    void increment() {
        lock.lock(); // acquire lock
        try {
            count++;
        } finally {
            lock.unlock(); // always unlock in finally - even if exception occurs
        }
    }

    int getCount() {
        lock.lock();
        try {
            return count;
        } finally {
            lock.unlock();
        }
    }
}
```

### tryLock - non-blocking attempt

```java
ReentrantLock lock = new ReentrantLock();

// try to get lock, don't wait if not available
if (lock.tryLock()) {
    try {
        // got the lock, do work
    } finally {
        lock.unlock();
    }
} else {
    System.out.println("Lock not available, doing something else");
}

// try for up to 2 seconds
if (lock.tryLock(2, TimeUnit.SECONDS)) {
    try {
        // got the lock
    } finally {
        lock.unlock();
    }
}
```

### ReadWriteLock - for read-heavy scenarios

Multiple threads can read at the same time. But when one thread writes, all others must wait.

```java
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

class SharedData {
    private String data = "initial";
    private ReadWriteLock rwLock = new ReentrantReadWriteLock();

    String read() {
        rwLock.readLock().lock(); // multiple threads can hold read lock simultaneously
        try {
            return data;
        } finally {
            rwLock.readLock().unlock();
        }
    }

    void write(String newData) {
        rwLock.writeLock().lock(); // only one thread can hold write lock
        try {
            data = newData;
        } finally {
            rwLock.writeLock().unlock();
        }
    }
}
```

---

## PART 10 - THREAD COMMUNICATION (wait, notify, notifyAll)

Sometimes threads need to coordinate. One thread waits for another to do something before continuing.

wait() - current thread releases the lock and waits until another thread calls notify()
notify() - wakes up one waiting thread
notifyAll() - wakes up all waiting threads

These must be called inside a synchronized block on the same object.

```java
class SharedBuffer {
    private int data;
    private boolean hasData = false;

    synchronized void produce(int value) throws InterruptedException {
        while (hasData) {
            wait(); // buffer full, wait for consumer to consume
        }
        data = value;
        hasData = true;
        System.out.println("Produced: " + value);
        notify(); // tell consumer data is ready
    }

    synchronized int consume() throws InterruptedException {
        while (!hasData) {
            wait(); // no data, wait for producer
        }
        hasData = false;
        System.out.println("Consumed: " + data);
        notify(); // tell producer buffer is free
        return data;
    }
}

public class ProducerConsumerDemo {
    public static void main(String[] args) {
        SharedBuffer buffer = new SharedBuffer();

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.produce(i);
                    Thread.sleep(500);
                }
            } catch (InterruptedException e) {}
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 5; i++) {
                    buffer.consume();
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {}
        });

        producer.start();
        consumer.start();
    }
}
```

---

## PART 11 - CONCURRENT COLLECTIONS

Regular collections like ArrayList, HashMap are not thread-safe. Use these instead in multithreaded code.

```java
import java.util.concurrent.*;

// Thread-safe ArrayList alternative
// Every write creates a copy - good for read-heavy, rare-write scenarios
CopyOnWriteArrayList<String> list = new CopyOnWriteArrayList<>();
list.add("Arman");
list.add("Priya");
// Can iterate safely even while another thread is modifying

// Thread-safe HashMap alternative - best general purpose
ConcurrentHashMap<String, Integer> map = new ConcurrentHashMap<>();
map.put("score", 100);
map.putIfAbsent("level", 1);
map.computeIfPresent("score", (k, v) -> v + 10);

// Thread-safe queue - good for producer-consumer
ConcurrentLinkedQueue<String> queue = new ConcurrentLinkedQueue<>();
queue.offer("task1");
queue.poll();

// Blocking queue - blocks when full or empty
BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(10);
blockingQueue.put("item");   // blocks if queue is full
blockingQueue.take();        // blocks if queue is empty
blockingQueue.offer("item", 2, TimeUnit.SECONDS); // wait max 2 seconds
blockingQueue.poll(2, TimeUnit.SECONDS);           // wait max 2 seconds
```

### BlockingQueue for Producer-Consumer pattern

```java
import java.util.concurrent.*;

public class BlockingQueueDemo {
    public static void main(String[] args) {

        BlockingQueue<Integer> queue = new LinkedBlockingQueue<>(5);

        Thread producer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    queue.put(i); // blocks if queue is full (size 5)
                    System.out.println("Produced: " + i + ", Queue size: " + queue.size());
                }
            } catch (InterruptedException e) {}
        });

        Thread consumer = new Thread(() -> {
            try {
                for (int i = 1; i <= 10; i++) {
                    Thread.sleep(500);
                    int item = queue.take(); // blocks if queue is empty
                    System.out.println("Consumed: " + item);
                }
            } catch (InterruptedException e) {}
        });

        producer.start();
        consumer.start();
    }
}
```

---

## PART 12 - COMPLETABLEFUTURE

CompletableFuture is the modern way to do async programming in Java. It was added in Java 8.

Problems with Future:

- future.get() blocks the current thread
- You cannot chain multiple async tasks easily
- You cannot combine results of multiple futures easily
- You cannot handle exceptions nicely

CompletableFuture solves all of this.

### Basic usage

```java
import java.util.concurrent.CompletableFuture;

public class CompletableFutureBasics {
    public static void main(String[] args) throws Exception {

        // Run a task asynchronously - no return value
        CompletableFuture<Void> cf1 = CompletableFuture.runAsync(() -> {
            System.out.println("Running in: " + Thread.currentThread().getName());
            System.out.println("Async task running");
        });

        // Run a task asynchronously - with return value
        CompletableFuture<String> cf2 = CompletableFuture.supplyAsync(() -> {
            System.out.println("Fetching data...");
            return "Arman";
        });

        // get() - wait and get result (still blocks)
        String result = cf2.get();
        System.out.println("Got: " + result);

        cf1.get(); // wait for first one too
    }
}
```

### Chaining with thenApply, thenAccept, thenRun

```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> {
        return "arman"; // step 1 - runs async
    })
    .thenApply(name -> {
        return name.toUpperCase(); // step 2 - transform result
    })
    .thenApply(name -> {
        return "Hello, " + name; // step 3 - transform again
    });

System.out.println(future.get()); // Hello, ARMAN
```

```
thenApply(Function)   -- transform result, returns new CompletableFuture<new type>
thenAccept(Consumer)  -- use result but return nothing (CompletableFuture<Void>)
thenRun(Runnable)     -- run something after but don't use result
```

```java
CompletableFuture.supplyAsync(() -> "Arman")
    .thenApply(name -> name.toUpperCase())    // transforms
    .thenAccept(name -> System.out.println("Name: " + name)) // uses result
    .thenRun(() -> System.out.println("All done")); // runs after
```

### thenCompose - chaining async tasks

Use when the next step is also an async operation that returns a CompletableFuture.

```java
CompletableFuture<String> result = CompletableFuture
    .supplyAsync(() -> "Arman") // get user name
    .thenCompose(name -> CompletableFuture.supplyAsync(() -> {
        // simulate fetching user data from database
        return "User data for: " + name;
    }));

System.out.println(result.get());
```

### thenCombine - combine two independent futures

When two tasks run in parallel and you need both results.

```java
CompletableFuture<String> nameFuture = CompletableFuture.supplyAsync(() -> {
    return "Arman"; // fetch user name
});

CompletableFuture<Integer> ageFuture = CompletableFuture.supplyAsync(() -> {
    return 25; // fetch user age
});

CompletableFuture<String> combined = nameFuture.thenCombine(ageFuture, (name, age) -> {
    return name + " is " + age + " years old";
});

System.out.println(combined.get()); // Arman is 25 years old
```

### allOf - wait for multiple futures

```java
CompletableFuture<String> f1 = CompletableFuture.supplyAsync(() -> "Result 1");
CompletableFuture<String> f2 = CompletableFuture.supplyAsync(() -> "Result 2");
CompletableFuture<String> f3 = CompletableFuture.supplyAsync(() -> "Result 3");

// Wait for all to complete
CompletableFuture<Void> all = CompletableFuture.allOf(f1, f2, f3);
all.get(); // blocks until all three are done

System.out.println(f1.get()); // Result 1
System.out.println(f2.get()); // Result 2
System.out.println(f3.get()); // Result 3
```

### anyOf - return first completed

```java
CompletableFuture<Object> fastest = CompletableFuture.anyOf(f1, f2, f3);
System.out.println(fastest.get()); // whichever finishes first
```

### Exception handling

```java
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> {
        if (true) throw new RuntimeException("Something went wrong");
        return "result";
    })
    .exceptionally(ex -> {
        System.out.println("Caught: " + ex.getMessage());
        return "default value"; // return fallback
    });

System.out.println(future.get()); // default value
```

```java
// handle - runs whether exception or not
CompletableFuture<String> future = CompletableFuture
    .supplyAsync(() -> "result")
    .handle((result, ex) -> {
        if (ex != null) {
            return "Error: " + ex.getMessage();
        }
        return result.toUpperCase();
    });
```

### Custom thread pool with CompletableFuture

By default CompletableFuture uses ForkJoinPool.commonPool(). You can provide your own.

```java
ExecutorService executor = Executors.newFixedThreadPool(4);

CompletableFuture<String> future = CompletableFuture.supplyAsync(() -> {
    return "result from custom pool";
}, executor);

future.thenApplyAsync(result -> result.toUpperCase(), executor);

executor.shutdown();
```

### Real world example - parallel API calls

```java
import java.util.concurrent.*;

public class ParallelCalls {
    public static void main(String[] args) throws Exception {

        ExecutorService executor = Executors.newFixedThreadPool(3);

        // Simulate 3 independent API calls running in parallel
        CompletableFuture<String> userFuture = CompletableFuture.supplyAsync(() -> {
            sleep(1000);
            return "User: Arman";
        }, executor);

        CompletableFuture<String> orderFuture = CompletableFuture.supplyAsync(() -> {
            sleep(1500);
            return "Orders: 5";
        }, executor);

        CompletableFuture<String> paymentFuture = CompletableFuture.supplyAsync(() -> {
            sleep(800);
            return "Balance: Rs.10000";
        }, executor);

        // Wait for all and combine
        CompletableFuture<Void> all = CompletableFuture.allOf(userFuture, orderFuture, paymentFuture);
        all.get(); // total wait time = 1500ms (max), not 3300ms (sum)

        System.out.println(userFuture.get());
        System.out.println(orderFuture.get());
        System.out.println(paymentFuture.get());

        executor.shutdown();
    }

    static void sleep(int ms) {
        try { Thread.sleep(ms); } catch (InterruptedException e) {}
    }
}
```

---

## PART 13 - SEMAPHORE

Semaphore controls how many threads can access a resource at the same time.

```java
import java.util.concurrent.Semaphore;

class DatabasePool {
    private Semaphore semaphore = new Semaphore(3); // only 3 connections allowed

    void getConnection(String threadName) throws InterruptedException {
        semaphore.acquire(); // wait for a permit
        try {
            System.out.println(threadName + " got connection");
            Thread.sleep(2000); // use connection
            System.out.println(threadName + " releasing connection");
        } finally {
            semaphore.release(); // give permit back
        }
    }
}

public class SemaphoreDemo {
    public static void main(String[] args) {
        DatabasePool pool = new DatabasePool();

        // 6 threads competing for 3 connections
        for (int i = 1; i <= 6; i++) {
            String name = "Thread-" + i;
            new Thread(() -> {
                try {
                    pool.getConnection(name);
                } catch (InterruptedException e) {}
            }).start();
        }
    }
}
```

---

## PART 14 - COUNTDOWNLATCH AND CYCLICBARRIER

### CountDownLatch

One or more threads wait until a set of operations in other threads completes.

```java
import java.util.concurrent.CountDownLatch;

public class CountDownLatchDemo {
    public static void main(String[] args) throws InterruptedException {

        int numberOfServices = 3;
        CountDownLatch latch = new CountDownLatch(numberOfServices);

        // Start 3 services
        for (int i = 1; i <= numberOfServices; i++) {
            final int serviceId = i;
            new Thread(() -> {
                try {
                    Thread.sleep(serviceId * 1000);
                    System.out.println("Service " + serviceId + " started");
                } catch (InterruptedException e) {}
                finally {
                    latch.countDown(); // decrement count
                }
            }).start();
        }

        System.out.println("Waiting for all services to start...");
        latch.await(); // main thread waits here until count reaches 0
        System.out.println("All services started. Application ready.");
    }
}
```

### CyclicBarrier

All threads wait at a barrier point until all of them arrive. Then they all proceed together. Can be reused (cyclic).

```java
import java.util.concurrent.CyclicBarrier;

public class CyclicBarrierDemo {
    public static void main(String[] args) {

        int numberOfPlayers = 3;
        CyclicBarrier barrier = new CyclicBarrier(numberOfPlayers, () -> {
            System.out.println("All players ready! Game starting..."); // runs when all arrive
        });

        for (int i = 1; i <= numberOfPlayers; i++) {
            final int playerId = i;
            new Thread(() -> {
                try {
                    System.out.println("Player " + playerId + " loading...");
                    Thread.sleep((long)(Math.random() * 3000));
                    System.out.println("Player " + playerId + " ready");
                    barrier.await(); // wait for all players
                    System.out.println("Player " + playerId + " playing");
                } catch (Exception e) {}
            }).start();
        }
    }
}
```

---

## PART 15 - COMMON PROBLEMS AND HOW TO AVOID THEM

### Race Condition

Problem: Two threads modify shared data at the same time, result is wrong.
Fix: Use synchronized, ReentrantLock, or AtomicInteger.

### Deadlock

Problem: Two threads wait for each other's lock, both stuck forever.
Fix: Always acquire locks in the same order. Use tryLock with timeout.

### Starvation

Problem: A thread never gets CPU time because other higher-priority threads keep running.
Fix: Use fair locks - new ReentrantLock(true) gives lock to longest-waiting thread.

### Livelock

Problem: Two threads keep responding to each other but neither makes progress.
Fix: Add randomness or backoff strategy.

### Memory Visibility

Problem: A thread's change is not seen by another thread due to CPU caching.
Fix: Use volatile, synchronized, or atomic classes.

---

## PART 16 - QUICK REFERENCE

```
Thread creation:
  extends Thread -> override run() -> call start()
  implements Runnable -> override run() -> new Thread(runnable).start()
  Lambda -> new Thread(() -> { code }).start()
  Callable -> executor.submit(callable) -> future.get()

Thread states:
  NEW -> RUNNABLE -> RUNNING -> BLOCKED/WAITING/TIMED_WAITING -> TERMINATED

ExecutorService:
  newFixedThreadPool(n)       -> n threads always
  newSingleThreadExecutor()   -> 1 thread
  newCachedThreadPool()       -> grows and shrinks
  newScheduledThreadPool(n)   -> for delayed/repeated tasks

Thread safety:
  synchronized method/block   -> mutual exclusion, one thread at a time
  volatile                    -> visibility only, not atomicity
  AtomicInteger/Long/Boolean  -> atomic operations without locking
  ReentrantLock               -> flexible locking with tryLock
  ConcurrentHashMap           -> thread-safe map
  CopyOnWriteArrayList        -> thread-safe list

CompletableFuture:
  supplyAsync()               -> run task, return value
  runAsync()                  -> run task, no return value
  thenApply()                 -> transform result
  thenAccept()                -> consume result
  thenCompose()               -> chain async tasks
  thenCombine()               -> combine two parallel tasks
  allOf()                     -> wait for all futures
  anyOf()                     -> wait for first future
  exceptionally()             -> handle exceptions
  handle()                    -> handle result or exception
```
