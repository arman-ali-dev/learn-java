# Java Multithreading - Exercises

---

## Creating Threads

**Exercise 1.**
Create a thread by extending Thread class. In run() print "Hello from Thread" 3 times. Start the thread.

**Exercise 2.**
Create a thread by implementing Runnable. In run() print the current thread name. Pass it to a Thread object and start it.

**Exercise 3.**
Create a thread using lambda. Print "Lambda thread running" inside it. Start it.

**Exercise 4.**
Create 2 threads. Both print numbers 1 to 5. Start both at the same time. Notice the output is mixed.

**Exercise 5.**
Create a thread. Call run() directly instead of start(). Write a comment: what is the difference between calling run() and start()?

**Exercise 6.**
Create a thread. Set its name to "WorkerThread". Print the name using getName() before and after setting it.

**Exercise 7.**
Create a thread. Set its priority to MAX_PRIORITY. Print the priority using getPriority().

---

## Thread Lifecycle

**Exercise 8.**
Create a thread that sleeps for 2 seconds in run(). Print its state before start(), right after start(), and after join().

**Exercise 9.**
Create a thread. Print its state after it has finished running. Try to call start() again on it. What happens?

**Exercise 10.**
Create 3 threads. Use join() so that thread 2 starts only after thread 1 finishes, and thread 3 starts only after thread 2 finishes.

**Exercise 11.**
Create a daemon thread that prints "daemon running" every 500ms in an infinite loop. Start it. Let the main thread finish. Notice the daemon thread also stops.

**Exercise 12.**
Create a thread. Call interrupt() on it while it is sleeping. Catch the InterruptedException inside run() and print "Thread was interrupted".

---

## Runnable vs Callable

**Exercise 13.**
Create a Runnable that prints "Runnable task done". Submit it to a single thread ExecutorService. Shutdown the executor.

**Exercise 14.**
Create a Callable that returns the string "Hello from Callable". Submit it to an executor. Print the result using future.get().

**Exercise 15.**
Create a Callable that sleeps for 3 seconds and then returns 42. Right after submitting it, print "doing other work" to show main thread is not blocked. Then call future.get() and print the result.

**Exercise 16.**
Create a Callable that throws a RuntimeException. Submit it. Call future.get() and catch ExecutionException. Print the cause message.

**Exercise 17.**
Submit a Callable that takes 5 seconds to complete. Call future.get() with a timeout of 2 seconds. Catch and print the TimeoutException.

**Exercise 18.**
Submit a Callable. Before calling get(), call isDone() and print it. Then call get(). Call isDone() again and print it.

**Exercise 19.**
Submit a Callable. Call cancel(true) on the future immediately. Then try to call get(). What exception is thrown?

---

## ExecutorService and ThreadPool

**Exercise 20.**
Create a fixed thread pool with 3 threads. Submit 6 tasks. Each task prints its task number and the thread name running it. Notice only 3 tasks run at a time.

**Exercise 21.**
Create a single thread executor. Submit 4 tasks each printing their number. Notice they run one after another in order.

**Exercise 22.**
Create a cached thread pool. Submit 10 tasks at the same time. Notice it creates as many threads as needed.

**Exercise 23.**
Submit 5 Callable tasks to a fixed pool. Collect all Future objects in a list. Loop through and print all results.

**Exercise 24.**
Create an executor. Submit some tasks. Call shutdown(). Try to submit one more task after shutdown. What happens?

**Exercise 25.**
Create an executor. Submit a task that takes 10 seconds. Call shutdownNow() immediately. Print the list of tasks that were waiting but never started.

**Exercise 26.**
After calling shutdown(), use awaitTermination(5, TimeUnit.SECONDS) to wait for tasks to complete. Print whether all tasks finished or not.

**Exercise 27.**
Create a scheduled executor. Schedule a task to run once after 2 seconds. Print a message when it runs.

**Exercise 28.**
Create a scheduled executor. Schedule a task to repeat every 1 second using scheduleAtFixedRate(). Let it run 4 times then cancel it.

**Exercise 29.**
Schedule the same repeating task using scheduleWithFixedDelay() instead. Write a comment explaining the difference between scheduleAtFixedRate and scheduleWithFixedDelay.

---

## Synchronized

**Exercise 30.**
Create a Counter class with int count and an increment() method that does count++. Create 2 threads each calling increment() 10000 times on the same counter. Print the result. Run it 3 times and notice it is different every time.

**Exercise 31.**
Add synchronized to the increment() method from Exercise 30. Run again. Count should always be exactly 20000.

**Exercise 32.**
Create a synchronized method. Create 2 different Counter objects. Run the synchronized method on both from different threads at the same time. Write a comment: do they block each other or not, and why?

**Exercise 33.**
Protect only the count++ line using a synchronized block instead of synchronized method. Add a print statement before and after the synchronized block that runs without the lock.

**Exercise 34.**
Create a static int count and a static synchronized method that increments it. Run from 2 threads. Write a comment: what does static synchronized lock on?

**Exercise 35.**
Create a deadlock. Thread 1 takes lock1 then tries lock2. Thread 2 takes lock2 then tries lock1. Run the program and see it freeze.

**Exercise 36.**
Fix the deadlock from Exercise 35 by always acquiring locks in the same order.

---

## Volatile

**Exercise 37.**
Create a boolean field running = true without volatile. Worker thread loops while running is true. Main thread sets running = false after 1 second. The worker may never stop. Write a comment explaining why.

**Exercise 38.**
Add volatile to the running field from Exercise 37. Run again. Worker stops as soon as running is set to false.

**Exercise 39.**
Create a volatile int status. One thread sets it to 1, waits 1 second, sets it to 2, waits 1 second, sets it to 3. Another thread reads status every 200ms and prints a different message for each value.

**Exercise 40.**
Create a volatile int count. Two threads each do count++ 5000 times. Print the result. Write a comment: is volatile enough here and why not?

---

## Atomic Classes

**Exercise 41.**
Create an AtomicInteger starting at 0. Three threads each call incrementAndGet() 5000 times. Print the final result. It should always be 15000.

**Exercise 42.**
Use getAndIncrement() on an AtomicInteger. Print the value it returns and the value after. Write a comment explaining the difference.

**Exercise 43.**
Use addAndGet(10) on an AtomicInteger starting at 5. Print the result.

**Exercise 44.**
Use compareAndSet(10, 100) on an AtomicInteger. Try it when the value is 10. Try it again when the value is not 10. Print the boolean it returns each time.

**Exercise 45.**
Create an AtomicBoolean flag starting at false. One thread loops until flag is true. Another thread sets it to true after 2 seconds. Show the first thread waking up.

**Exercise 46.**
Create an AtomicReference holding a String "initial". Use compareAndSet to change it to "updated". Try to change it again to "final" using compareAndSet with expected value "initial". Print each result.

---

## ReentrantLock

**Exercise 47.**
Create a ReentrantLock. Use lock() and unlock() in finally to protect a count++ operation. Run 2 threads each incrementing 10000 times. Result should always be 20000.

**Exercise 48.**
Use tryLock() without waiting. One thread holds the lock for 3 seconds. Another thread tries tryLock() - it should return false immediately. Print a message in each case.

**Exercise 49.**
Use tryLock(2, TimeUnit.SECONDS). One thread holds lock for 1 second - other thread should get lock successfully. One thread holds lock for 5 seconds - other thread should give up after 2 seconds.

**Exercise 50.**
Create a fair ReentrantLock with new ReentrantLock(true). Write a comment: what does fair mean and when would you use it?

**Exercise 51.**
Create a ReadWriteLock. Create a shared HashMap. Multiple reader threads read from the map using readLock simultaneously. Show they run at the same time by printing timestamps.

**Exercise 52.**
Use the same ReadWriteLock from Exercise 51. One writer thread writes to the map using writeLock. Show that it blocks all readers while writing.

---

## CompletableFuture

**Exercise 53.**
Create a CompletableFuture using runAsync that prints "async task running". Call get() to wait for it.

**Exercise 54.**
Create a CompletableFuture using supplyAsync that returns "hello". Chain thenApply to convert it to uppercase. Print the result.

**Exercise 55.**
Chain 3 thenApply calls. First returns "arman". Second adds " is a". Third adds " developer". Print final result.

**Exercise 56.**
Use thenAccept to print the result of a supplyAsync. Write a comment: what is the difference between thenApply and thenAccept?

**Exercise 57.**
Use thenRun after a supplyAsync. Write a comment: what is the difference between thenAccept and thenRun?

**Exercise 58.**
Create two independent supplyAsync tasks running in parallel. One returns a name after 1 second. Another returns an age after 1.5 seconds. Use thenCombine to combine them into "Arman is 25". Measure total time - should be ~1.5 seconds not 2.5.

**Exercise 59.**
Create a supplyAsync that returns a userId. Use thenCompose to pass that userId to another supplyAsync that fetches user details. Print the final result.

**Exercise 60.**
Create 3 supplyAsync tasks. Use allOf to wait for all. After allOf.get() completes, print results of all 3.

**Exercise 61.**
Create 3 supplyAsync tasks with delays of 3s, 1s, and 2s. Use anyOf. Print which one finished first.

**Exercise 62.**
Create a supplyAsync that throws a RuntimeException. Use exceptionally to catch it and return "default value". Print the result.

**Exercise 63.**
Create a supplyAsync that may or may not throw. Use handle to deal with both cases - if exception print error, if success print result in uppercase.

**Exercise 64.**
Create a CompletableFuture with a custom ExecutorService instead of the default ForkJoinPool. Print the thread name to confirm it is using your pool.

---

## Concurrent Collections

**Exercise 65.**
Create a ConcurrentHashMap. Start 5 threads each adding 2 key-value pairs. Print the final map size - should be 10 with no exception.

**Exercise 66.**
Use putIfAbsent on a ConcurrentHashMap from 3 threads using the same key. Only one should succeed. Print the final value.

**Exercise 67.**
Use compute on a ConcurrentHashMap to increment a counter value from 3 threads each incrementing 1000 times. Final value should always be 3000.

**Exercise 68.**
Use merge to add scores. If key exists add the new score to old score. If key does not exist start with new score. Run from multiple threads.

**Exercise 69.**
Create a CopyOnWriteArrayList. Start a thread that iterates it slowly with a 500ms sleep between each element. While it iterates, add new elements from another thread. Show no ConcurrentModificationException is thrown.

**Exercise 70.**
Create a LinkedBlockingQueue with capacity 3. Producer adds 7 items one by one. Consumer takes one item every 1 second. Show producer blocking when queue is full and unblocking when consumer takes an item.

**Exercise 71.**
Use offer() with a 2 second timeout on a full BlockingQueue. Show it returning false when queue stays full. Show it returning true when consumer makes space.

**Exercise 72.**
Use poll() with a 2 second timeout on an empty BlockingQueue. Show it returning null when nothing is added. Show it returning the item when producer adds something.
