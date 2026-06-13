# Multithreading — Natural Interview Q&A

### Exactly Waise Bolo Jaise Interview Mein Bolte Hain

> **Rule:** Padho → Samjho → Band karo → Apne words mein bolo!

---

**Q1. What is Multithreading in Java?**

_"Sir, Multithreading means running multiple threads simultaneously within a single program. The main benefit is better CPU utilization and faster execution. A simple real life example — think of cooking at home. While rice is boiling on one burner, we are chopping vegetables at the same time. We are not waiting for one task to finish before starting another. Multithreading works the same way in a program."_

---

**Q2. What is a Thread in Java?**

_"Sir, a Thread is the smallest unit of execution in a Java program. Every Java program has at least one thread — the main thread. We can create additional threads to run tasks simultaneously. A real life analogy — think of workers in a factory. Each worker is doing a different job at the same time. Each worker is like a thread."_

---

**Q3. What is the difference between Process and Thread?**

_"Sir, a Process is a completely independent running program with its own separate memory space. A Thread is a unit of execution inside a process, and multiple threads share the same memory of that process. Creating a new process is expensive and heavy. Creating a new thread is much lighter and faster. That is why for concurrent tasks within the same application, we prefer threads over processes."_

---

**Q4. How many ways can we create a Thread in Java?**

_"Sir, we can create a thread in two main ways. First is by extending the Thread class and overriding the run() method. Second is by implementing the Runnable interface and passing it to a Thread object. In practice, implementing Runnable is always preferred because Java does not support multiple inheritance — if our class extends Thread, it cannot extend any other class. With Runnable, our class is still free to extend another class if needed."_

---

**Q5. What is the difference between Runnable and Callable?**

_"Sir, both are used to define tasks for threads, but they have a key difference. Runnable's run() method does not return any value and cannot throw checked exceptions. Callable's call() method can return a value and can also throw checked exceptions. Callable is used with ExecutorService and returns a Future object from which we can get the result later. So when we need a result back from our thread task, we use Callable."_

---

**Q6. What is Thread lifecycle in Java?**

_"Sir, a Thread goes through five states in its lifetime. First is New — when thread object is created but start() is not called yet. Second is Runnable — when start() is called and thread is ready to run. Third is Running — when the thread is actually executing. Fourth is Waiting or Blocked — when thread is waiting for a resource or another thread. Fifth is Terminated — when the thread has finished its execution."_

---

**Q7. What is the difference between start() and run() method?**

_"Sir, this is a very common mistake. If we call run() directly, it does not create a new thread — it just executes the run() method in the current thread like a normal method call. If we call start(), Java creates a new thread and then that new thread calls run() internally. So to actually run code in a separate thread, we must always call start() and never run() directly."_

---

**Q8. What is synchronized keyword in Java?**

_"Sir, synchronized keyword ensures that only one thread can access a particular block of code or method at a time. When multiple threads work with shared data simultaneously, they can corrupt the data. Synchronized prevents this by allowing only one thread in at a time. A real life analogy — it is like a single key to a restroom. Only one person can be inside at a time. Others have to wait outside until the key is available."_

---

**Q9. What is a race condition?**

_"Sir, race condition happens when two or more threads access shared data at the same time and try to modify it, leading to incorrect and unpredictable results. The final result depends on which thread ran first — which we cannot control. A simple example — if two threads both read a balance of 1000 and both try to add 500, the final balance might be 1500 instead of 2000 because they overwrote each other. We solve this using synchronized keyword or locks."_

---

**Q10. What is deadlock in Java?**

_"Sir, deadlock is a situation where two or more threads are permanently waiting for each other to release locks, and none of them can ever proceed. For example — Thread A has acquired Lock 1 and is waiting for Lock 2. Thread B has acquired Lock 2 and is waiting for Lock 1. Both are waiting forever — neither can move forward. To prevent deadlock, we should always acquire locks in a fixed consistent order across all threads."_

---

**Q11. What is the volatile keyword in Java?**

_"Sir, when a variable is declared volatile, it tells Java that this variable will be accessed by multiple threads. Normally each thread has its own local cache and may not see updates made by other threads immediately. volatile ensures that every read and write to that variable goes directly to main memory, so all threads always see the latest value. It is useful for simple flag variables that are shared between threads."_

---

**Q12. What is the difference between wait() and sleep()?**

_"Sir, both pause thread execution but in very different ways. sleep() pauses the thread for a specific time but it keeps holding whatever locks it has — other threads cannot enter the synchronized block during this time. wait() on the other hand releases the lock and the thread goes into a waiting state — other threads can now enter. wait() must always be called inside a synchronized block and the thread wakes up only when notify() or notifyAll() is called."_

---

**Q13. What is notify() and notifyAll()?**

_"Sir, notify() and notifyAll() are used to wake up threads that are in waiting state due to wait(). notify() wakes up only one randomly chosen waiting thread. notifyAll() wakes up all waiting threads — they all compete for the lock and only one gets it at a time. We use notifyAll() when multiple threads might be waiting and we want all of them to have a chance to proceed."_

---

**Q14. What is ExecutorService in Java?**

_"Sir, ExecutorService is a framework that manages a pool of threads for us. Instead of manually creating and managing threads, we just submit tasks to ExecutorService and it handles everything — thread creation, reuse, and shutdown. The main benefit is performance — creating new threads every time is expensive. ExecutorService reuses existing threads from the pool. We create it using Executors factory methods like Executors.newFixedThreadPool()."_

---

**Q15. What is ThreadPool?**

_"Sir, ThreadPool is a collection of pre-created worker threads that are ready to execute tasks. When we submit a task, an available thread picks it up and executes it. After finishing, the thread goes back to the pool and waits for the next task instead of dying. This avoids the overhead of creating and destroying threads repeatedly. A real life analogy — like a pool of taxi drivers waiting at a stand. When a customer comes, one driver takes the job. After dropping, they come back to the stand."_

---

**Q16. What is the difference between synchronized method and synchronized block?**

_"Sir, a synchronized method locks the entire method — the whole object is locked for the duration of that method. A synchronized block locks only a specific section of code — we have more granular control over exactly what we want to protect. Synchronized block is generally preferred for better performance because we lock only the minimum necessary code rather than the entire method."_

---

**Q17. What is a daemon thread?**

_"Sir, a daemon thread is a background thread that runs to support other threads. The most important thing about daemon threads is that when all non-daemon threads finish, the JVM automatically terminates all daemon threads and exits — it does not wait for daemon threads to finish. Garbage collector is a classic example of a daemon thread. We can make a thread daemon by calling setDaemon(true) before starting it."_

---

**Q18. What is CompletableFuture in Java?**

_"Sir, CompletableFuture is used for asynchronous programming in Java 8 and above. It allows us to run a task asynchronously — meaning in the background — and then do something with the result when it is ready. The powerful thing is we can chain multiple async operations together, combine results of multiple futures, and handle exceptions in a clean way. It is much more flexible than the older Future interface."_

---

**Q19. What is the difference between Future and CompletableFuture?**

_"Sir, Future is the older way to get result from asynchronous tasks. But it has limitations — we cannot chain operations, we cannot combine multiple futures, and get() is a blocking call — it waits until result is available. CompletableFuture solves all these problems. We can chain operations using thenApply() and thenCompose(), combine futures, handle exceptions, and most importantly the whole thing can be non-blocking. So CompletableFuture is strictly more powerful than Future."_

---

**Q20. What is the difference between Concurrent and Parallel execution?**

_"Sir, Concurrent means multiple tasks are in progress at the same time, but not necessarily running at the exact same instant — they take turns on the CPU. Parallel means tasks are literally running at the exact same time on different CPU cores. A simple analogy — one person switching between two tasks quickly is concurrent. Two people each doing their own task simultaneously is parallel. Java multithreading is concurrent on single core and parallel on multi-core systems."_

---

**Q21. What is ConcurrentHashMap?**

_"Sir, ConcurrentHashMap is a thread-safe version of HashMap that is designed for concurrent use. Regular HashMap is not thread-safe — if multiple threads modify it simultaneously, it can get corrupted. ConcurrentHashMap allows multiple threads to read and write simultaneously without corrupting data. It achieves this by dividing the map into segments and locking only the relevant segment instead of the whole map — which makes it much more efficient than a fully synchronized HashMap."_

---

**Q22. What is ReentrantLock?**

_"Sir, ReentrantLock is an alternative to synchronized keyword for thread synchronization. It gives us more control — we can try to acquire a lock without blocking using tryLock(), we can specify timeout for acquiring lock, and we can interrupt a thread waiting for a lock. It is called reentrant because the same thread can acquire the same lock multiple times without deadlocking itself. We must always release it in a finally block to avoid lock leaks."_

---

**Q23. What is Semaphore in Java?**

_"Sir, Semaphore is a thread synchronization tool that controls how many threads can access a resource simultaneously. Unlike synchronized which allows only one thread at a time, Semaphore allows a fixed number of threads. A real life example — a parking lot with 10 spots. Only 10 cars can park at once. When one leaves, another can enter. Semaphore works exactly like this — we initialize it with a count and threads acquire and release permits."_

---

**Q24. What is CountDownLatch?**

_"Sir, CountDownLatch is a synchronization tool that allows one or more threads to wait until a set of operations in other threads complete. We initialize it with a count. Each time a task completes, it calls countDown() which decrements the count. The waiting thread calls await() and blocks until the count reaches zero. A real life example — a race where the starter waits for all runners to reach the starting line before firing the gun."_

---

**Q25. What is ThreadLocal in Java?**

_"Sir, ThreadLocal is a class that allows us to create variables where each thread has its own separate copy of that variable. So even if multiple threads are using the same ThreadLocal variable, they are actually working with their own isolated copy — no sharing, no synchronization needed. A common use case is storing user session information in a web application — each request thread has its own user context stored in ThreadLocal."_

---

## 📅 Practice Schedule

| Din   | Questions                     |
| ----- | ----------------------------- |
| Day 1 | Q1 to Q5                      |
| Day 2 | Q6 to Q10                     |
| Day 3 | Q11 to Q15                    |
| Day 4 | Q16 to Q20                    |
| Day 5 | Q21 to Q25                    |
| Day 6 | Revision — Saare 25 questions |

---

## 💡 Yaad Rakho

1. Answer padho — samjho — file BAND karo — apne words mein bolo
2. Real life examples se connect karo — tab kabhi nahi bhoologe
3. Mirror ke saamne practice karo — confidence badhta hai
4. Galat bola — koi baat nahi — dobara bolo

---

_Arman — multithreading concepts clear karo aur bolne ki practice karo. 6 din mein yeh section strong ho jaayega!_ 💪
