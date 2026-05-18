# Java Collection Framework - Complete Notes

---

## PART 1 - WHAT IS COLLECTION FRAMEWORK

Before collections, if you wanted to store 100 students, you had to do this:

```java
String student1 = "Arman";
String student2 = "Priya";
String student3 = "Raj";
// ... 97 more variables
```

This is a nightmare. Collections solve this.

Collection Framework is a set of classes and interfaces in Java that gives you ready-made data structures to store, retrieve, and manipulate groups of objects.

It lives in the package: java.util

You need to import things from this package before using them.

```java
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
// or import everything at once:
import java.util.*;
```

---

## PART 2 - THE BIG PICTURE (HIERARCHY)

This is the family tree. Read it once carefully.

```
Iterable  (interface)
    |
Collection  (interface)
    |
    |--- List  (interface)
    |       |--- ArrayList  (class)
    |       |--- LinkedList  (class)
    |       |--- Vector  (class)  -- old, avoid using
    |       |--- Stack  (class)   -- extends Vector
    |
    |--- Set  (interface)
    |       |--- HashSet  (class)
    |       |--- LinkedHashSet  (class)
    |       |--- TreeSet  (class)
    |
    |--- Queue  (interface)
            |--- LinkedList  (class)
            |--- PriorityQueue  (class)
            |--- Deque  (interface)
                    |--- ArrayDeque  (class)


Map  (interface)  -- separate, does NOT extend Collection
    |--- HashMap  (class)
    |--- LinkedHashMap  (class)
    |--- TreeMap  (class)
    |--- Hashtable  (class)  -- old, avoid using
```

The most important thing to remember: Map is NOT a Collection. It is its own thing. That is why Map does not have add() method - it has put().

---

## PART 3 - LIST

### What is a List

List is an ordered collection. Ordered means the elements stay in the order you put them. If you add "Arman" first and "Priya" second, then "Arman" will always be at index 0 and "Priya" at index 1.

List allows duplicates. You can have "Arman" appear 5 times in a List.

Every element has an index starting from 0.

### ArrayList

This is the most used class in the entire Collections Framework. Internally it is a resizable array. When it fills up, it creates a new bigger array and copies everything.

When to use ArrayList:

- You need to access elements by index frequently
- You are mostly adding at the end
- You do not delete from the middle often

```java
import java.util.ArrayList;
import java.util.List;

public class ArrayListDemo {
    public static void main(String[] args) {

        List<String> names = new ArrayList<>();

        // Adding elements
        names.add("Arman");
        names.add("Priya");
        names.add("Raj");
        names.add("Sneha");

        // Adding at a specific index
        names.add(1, "Rohan"); // inserts at index 1, shifts others right
        // now: [Arman, Rohan, Priya, Raj, Sneha]

        // Accessing by index
        System.out.println(names.get(0)); // Arman
        System.out.println(names.get(2)); // Priya

        // Updating an element
        names.set(0, "Arman Khan");
        System.out.println(names.get(0)); // Arman Khan

        // Removing by index
        names.remove(1); // removes Rohan

        // Removing by value
        names.remove("Raj"); // removes first occurrence of Raj

        // Size
        System.out.println(names.size()); // 3

        // Check if element exists
        System.out.println(names.contains("Priya")); // true
        System.out.println(names.contains("Raj"));   // false

        // Loop through - for-each
        for (String name : names) {
            System.out.println(name);
        }

        // Loop through - index based
        for (int i = 0; i < names.size(); i++) {
            System.out.println(i + ": " + names.get(i));
        }

        // Check if list is empty
        System.out.println(names.isEmpty()); // false

        // Clear all elements
        names.clear();
        System.out.println(names.isEmpty()); // true
    }
}
```

### One important confusion - remove(int index) vs remove(Object value)

```java
List<Integer> numbers = new ArrayList<>();
numbers.add(10);
numbers.add(20);
numbers.add(30);

numbers.remove(1);                   // removes element at INDEX 1, which is 20
numbers.remove(Integer.valueOf(10)); // removes the VALUE 10
// result: [30]
```

If you have List<Integer> and call remove(1), Java thinks you mean index 1, not value 1. To remove by value, wrap it: Integer.valueOf(1).

### LinkedList

LinkedList stores elements as nodes. Each node holds the value and a pointer to the next node and previous node. It is doubly linked.

When to use LinkedList:

- You are inserting or deleting from the beginning or middle a lot
- You want to use it as a Queue or Deque also

When NOT to use LinkedList:

- You need to access elements by index often - it is slow because it has to walk from the start

```java
import java.util.LinkedList;

public class LinkedListDemo {
    public static void main(String[] args) {

        LinkedList<String> list = new LinkedList<>();

        list.add("Arman");
        list.add("Priya");
        list.add("Raj");

        // Special LinkedList methods
        list.addFirst("First Person"); // add at beginning
        list.addLast("Last Person");   // add at end

        System.out.println(list.getFirst()); // First Person
        System.out.println(list.getLast());  // Last Person

        list.removeFirst(); // remove from beginning
        list.removeLast();  // remove from end

        System.out.println(list); // [Arman, Priya, Raj]
    }
}
```

### Collections class - utility methods

Note: Collections (with an s) is a separate utility class, not the Collection interface. It has static helper methods.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class CollectionsUtility {
    public static void main(String[] args) {

        List<Integer> marks = new ArrayList<>();
        marks.add(85);
        marks.add(62);
        marks.add(90);
        marks.add(71);
        marks.add(55);

        Collections.sort(marks);
        System.out.println(marks); // [55, 62, 71, 85, 90]

        Collections.reverse(marks);
        System.out.println(marks); // [90, 85, 71, 62, 55]

        System.out.println(Collections.max(marks)); // 90
        System.out.println(Collections.min(marks)); // 55

        Collections.shuffle(marks); // random order

        marks.add(85);
        marks.add(85);
        System.out.println(Collections.frequency(marks, 85)); // 3

        Collections.fill(marks, 0);
        System.out.println(marks); // [0, 0, 0, 0, 0, 0, 0]
    }
}
```

### Sorting a List of Objects - Comparable

When your list contains custom objects like Student, Java does not know how to sort them. You have to tell it. Two ways: Comparable and Comparator.

Comparable - the object itself knows how to compare. You implement it inside your class.

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class Student implements Comparable<Student> {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    // compareTo must return:
    // negative if this object comes BEFORE other
    // positive if this object comes AFTER other
    // zero if they are equal
    public int compareTo(Student other) {
        return this.marks - other.marks; // ascending by marks
        // return other.marks - this.marks; // descending by marks
    }

    public String toString() {
        return name + "(" + marks + ")";
    }
}

public class ComparableDemo {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Arman", 85));
        students.add(new Student("Priya", 92));
        students.add(new Student("Raj", 71));
        students.add(new Student("Sneha", 88));

        Collections.sort(students);
        System.out.println(students);
        // [Raj(71), Arman(85), Sneha(88), Priya(92)]
    }
}
```

### Sorting a List of Objects - Comparator

Comparator - sorting logic is outside the class. Useful when you want multiple sorting options.

```java
import java.util.Comparator;

public class ComparatorDemo {
    public static void main(String[] args) {
        List<Student> students = new ArrayList<>();
        students.add(new Student("Arman", 85));
        students.add(new Student("Priya", 92));
        students.add(new Student("Raj", 71));

        // Sort by marks ascending using lambda
        students.sort((a, b) -> a.marks - b.marks);

        // Sort by name alphabetically
        students.sort((a, b) -> a.name.compareTo(b.name));

        // Sort by marks descending, then by name if marks equal
        students.sort((a, b) -> {
            if (a.marks != b.marks) {
                return b.marks - a.marks;
            }
            return a.name.compareTo(b.name);
        });
    }
}
```

### All List methods in one place

```
add(element)              -- add at end
add(index, element)       -- add at specific position
get(index)                -- get element at index
set(index, element)       -- replace element at index
remove(index)             -- remove by index
remove(Object o)          -- remove by value
size()                    -- number of elements
contains(element)         -- true if present
indexOf(element)          -- first index, -1 if not found
lastIndexOf(element)      -- last index of element
isEmpty()                 -- true if no elements
clear()                   -- remove all elements
subList(from, to)         -- portion of list (from inclusive, to exclusive)
toArray()                 -- convert to array
addAll(collection)        -- add all elements from another collection
removeAll(collection)     -- remove all elements that are in the other collection
retainAll(collection)     -- keep only elements that are in the other collection
Collections.sort(list)    -- sort ascending
Collections.reverse(list) -- reverse
Collections.max(list)     -- maximum element
Collections.min(list)     -- minimum element
Collections.frequency(list, e) -- how many times e appears
Collections.shuffle(list) -- random order
Collections.fill(list, e) -- fill all with same value
```

---

## PART 4 - SET

### What is a Set

Set is a collection that does NOT allow duplicates. If you try to add a value that already exists, nothing happens - no error, no change.

Set does not have an index. You cannot do set.get(0). You have to loop through it.

### HashSet

Most common Set. Internally uses a HashMap. Very fast for add, remove, and contains.

Order is NOT guaranteed. When you print, elements may appear in any order.

```java
import java.util.HashSet;
import java.util.Set;

public class HashSetDemo {
    public static void main(String[] args) {

        Set<String> cities = new HashSet<>();

        cities.add("Jaipur");
        cities.add("Delhi");
        cities.add("Mumbai");
        cities.add("Jaipur"); // duplicate - ignored silently
        cities.add("Delhi");  // duplicate - ignored silently

        System.out.println(cities.size()); // 3, not 5

        System.out.println(cities.contains("Mumbai")); // true
        System.out.println(cities.contains("Pune"));   // false

        cities.remove("Delhi");
        System.out.println(cities.size()); // 2

        for (String city : cities) {
            System.out.println(city); // order is not guaranteed
        }

        // Common use: remove duplicates from a List
        List<String> namesList = new ArrayList<>();
        namesList.add("Arman");
        namesList.add("Priya");
        namesList.add("Arman");
        namesList.add("Raj");

        Set<String> uniqueNames = new HashSet<>(namesList);
        System.out.println(uniqueNames.size()); // 3
    }
}
```

### How does HashSet detect duplicates

For String and Integer, Java already knows how to compare using .equals() and .hashCode() internally.

For your custom objects, you must override equals() and hashCode(). Otherwise HashSet treats two objects with same data as different.

```java
class City {
    String name;

    City(String name) {
        this.name = name;
    }

    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof City)) return false;
        City other = (City) obj;
        return this.name.equals(other.name);
    }

    public int hashCode() {
        return name.hashCode();
    }
}
```

### LinkedHashSet

Same as HashSet but maintains insertion order. Slightly slower than HashSet.

Use when: you want no duplicates AND you want to remember the order elements were added.

```java
Set<String> set = new LinkedHashSet<>();
set.add("Banana");
set.add("Apple");
set.add("Mango");
set.add("Apple"); // ignored

System.out.println(set); // [Banana, Apple, Mango] - insertion order maintained
```

### TreeSet

Stores elements in sorted order (ascending by default). Slightly slower than HashSet.

Use when: you want no duplicates AND you want elements sorted automatically.

```java
import java.util.TreeSet;

public class TreeSetDemo {
    public static void main(String[] args) {

        TreeSet<Integer> numbers = new TreeSet<>();
        numbers.add(50);
        numbers.add(10);
        numbers.add(30);
        numbers.add(20);
        numbers.add(40);

        System.out.println(numbers); // [10, 20, 30, 40, 50] - sorted

        System.out.println(numbers.first()); // 10
        System.out.println(numbers.last());  // 50

        // headSet - all elements less than given value
        System.out.println(numbers.headSet(30)); // [10, 20]

        // tailSet - all elements greater than or equal to given value
        System.out.println(numbers.tailSet(30)); // [30, 40, 50]

        // subSet - from inclusive, to exclusive
        System.out.println(numbers.subSet(20, 40)); // [20, 30]

        // floor - greatest element <= given value
        System.out.println(numbers.floor(25)); // 20

        // ceiling - smallest element >= given value
        System.out.println(numbers.ceiling(25)); // 30

        // pollFirst and pollLast - retrieve and remove
        System.out.println(numbers.pollFirst()); // 10 - removed
        System.out.println(numbers.pollLast());  // 50 - removed
        System.out.println(numbers); // [20, 30, 40]
    }
}
```

### Set Operations

```java
Set<Integer> set1 = new HashSet<>(Arrays.asList(1, 2, 3, 4, 5));
Set<Integer> set2 = new HashSet<>(Arrays.asList(4, 5, 6, 7, 8));

// Union - all elements from both
Set<Integer> union = new HashSet<>(set1);
union.addAll(set2);
System.out.println(union); // [1, 2, 3, 4, 5, 6, 7, 8]

// Intersection - only common elements
Set<Integer> intersection = new HashSet<>(set1);
intersection.retainAll(set2);
System.out.println(intersection); // [4, 5]

// Difference - in set1 but not in set2
Set<Integer> difference = new HashSet<>(set1);
difference.removeAll(set2);
System.out.println(difference); // [1, 2, 3]
```

### All Set methods in one place

```
add(element)              -- add element (ignored if duplicate)
remove(element)           -- remove element
contains(element)         -- true if present
size()                    -- number of elements
isEmpty()                 -- true if empty
clear()                   -- remove all
addAll(collection)        -- union
retainAll(collection)     -- intersection
removeAll(collection)     -- difference
toArray()                 -- convert to array

TreeSet extra methods:
first()                   -- smallest element
last()                    -- largest element
floor(e)                  -- greatest element <= e
ceiling(e)                -- smallest element >= e
headSet(e)                -- elements < e
tailSet(e)                -- elements >= e
subSet(from, to)          -- from inclusive, to exclusive
pollFirst()               -- remove and return smallest
pollLast()                -- remove and return largest
```

---

## PART 5 - MAP

### What is a Map

Map stores data as key-value pairs. Think of it as a dictionary - you look up a word (key) and get its meaning (value).

Rules of Map:

- Keys must be unique
- Values can be duplicated
- Each key maps to exactly one value
- If you put the same key again, the old value is replaced
- Map does NOT extend Collection

### HashMap

Most common Map. No order guaranteed. Very fast for put, get, remove.

```java
import java.util.HashMap;
import java.util.Map;

public class HashMapDemo {
    public static void main(String[] args) {

        Map<String, Integer> marks = new HashMap<>();

        marks.put("Arman", 85);
        marks.put("Priya", 92);
        marks.put("Raj", 71);
        marks.put("Sneha", 88);

        // Same key again - old value replaced
        marks.put("Arman", 95);

        System.out.println(marks.get("Arman")); // 95
        System.out.println(marks.get("Nobody")); // null

        // Safe get - return default if key not found
        System.out.println(marks.getOrDefault("Nobody", 0)); // 0

        System.out.println(marks.containsKey("Priya")); // true
        System.out.println(marks.containsValue(92));    // true

        marks.remove("Raj");
        System.out.println(marks.size()); // 3

        // Loop through all entries
        for (Map.Entry<String, Integer> entry : marks.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Loop through only keys
        for (String key : marks.keySet()) {
            System.out.println(key);
        }

        // Loop through only values
        for (int value : marks.values()) {
            System.out.println(value);
        }

        marks.clear();
    }
}
```

### Counting frequency using HashMap - very common pattern

```java
String[] words = {"apple", "mango", "apple", "banana", "mango", "apple"};

Map<String, Integer> frequency = new HashMap<>();

for (String word : words) {
    frequency.put(word, frequency.getOrDefault(word, 0) + 1);
}

System.out.println(frequency); // {apple=3, mango=2, banana=1}
```

### Grouping using Map - another very common pattern

```java
// Group students by their grade
Map<String, List<String>> gradeGroups = new HashMap<>();

String[] students = {"Arman", "Priya", "Raj", "Sneha"};
int[] marksArr = {95, 82, 67, 91};

for (int i = 0; i < students.length; i++) {
    String grade;
    if (marksArr[i] >= 90)      grade = "A";
    else if (marksArr[i] >= 75) grade = "B";
    else if (marksArr[i] >= 60) grade = "C";
    else                         grade = "F";

    gradeGroups.putIfAbsent(grade, new ArrayList<>());
    gradeGroups.get(grade).add(students[i]);
}

System.out.println(gradeGroups);
// {A=[Arman, Sneha], B=[Priya], C=[Raj]}
```

### LinkedHashMap

Same as HashMap but maintains insertion order.

```java
Map<String, Integer> map = new LinkedHashMap<>();
map.put("Banana", 3);
map.put("Apple", 1);
map.put("Mango", 2);

System.out.println(map); // {Banana=3, Apple=1, Mango=2} - insertion order
```

### TreeMap

Stores entries sorted by key ascending. Keys must be Comparable.

```java
import java.util.TreeMap;

public class TreeMapDemo {
    public static void main(String[] args) {

        TreeMap<String, Integer> scores = new TreeMap<>();
        scores.put("Raj", 70);
        scores.put("Arman", 85);
        scores.put("Priya", 92);
        scores.put("Sneha", 78);

        System.out.println(scores);
        // {Arman=85, Priya=92, Raj=70, Sneha=78} - alphabetical by key

        System.out.println(scores.firstKey()); // Arman
        System.out.println(scores.lastKey());  // Sneha

        // headMap - keys strictly less than given key
        System.out.println(scores.headMap("Raj")); // {Arman=85, Priya=92}

        // tailMap - keys greater than or equal to given key
        System.out.println(scores.tailMap("Raj")); // {Raj=70, Sneha=78}
    }
}
```

### All Map methods in one place

```
put(key, value)              -- add or update
get(key)                     -- get value, null if not found
getOrDefault(key, default)   -- get value, default if not found
remove(key)                  -- remove by key
containsKey(key)             -- true if key exists
containsValue(value)         -- true if value exists
size()                       -- number of entries
isEmpty()                    -- true if empty
clear()                      -- remove all
keySet()                     -- Set of all keys
values()                     -- Collection of all values
entrySet()                   -- Set of key-value pairs to loop
putIfAbsent(key, value)      -- only put if key does not already exist
replace(key, value)          -- replace value only if key exists

TreeMap extra:
firstKey()                   -- smallest key
lastKey()                    -- largest key
headMap(key)                 -- entries with keys < given key
tailMap(key)                 -- entries with keys >= given key
subMap(from, to)             -- from <= key < to
```

---

## PART 6 - QUEUE AND DEQUE

### What is a Queue

Queue works on FIFO - First In, First Out. Like a line at a ticket counter. The person who came first gets served first.

### Queue using LinkedList

```java
import java.util.LinkedList;
import java.util.Queue;

public class QueueDemo {
    public static void main(String[] args) {

        Queue<String> queue = new LinkedList<>();

        queue.offer("Arman");
        queue.offer("Priya");
        queue.offer("Raj");
        queue.offer("Sneha");

        System.out.println(queue); // [Arman, Priya, Raj, Sneha]

        // peek - see the front element without removing
        System.out.println(queue.peek()); // Arman

        // poll - remove and return the front element
        System.out.println(queue.poll()); // Arman
        System.out.println(queue.peek()); // Priya (now at front)

        System.out.println(queue.size()); // 3

        while (!queue.isEmpty()) {
            System.out.println("Serving: " + queue.poll());
        }
    }
}
```

### offer vs add and poll vs remove

```
offer(e)  -- adds element, returns false if queue is full (safe)
add(e)    -- adds element, throws exception if queue is full

poll()    -- removes front, returns null if empty (safe)
remove()  -- removes front, throws exception if empty

peek()    -- returns front without removing, null if empty (safe)
element() -- returns front without removing, throws exception if empty
```

Always prefer offer(), poll(), peek() because they are safe.

### PriorityQueue

PriorityQueue always serves the element with highest priority first. By default, smallest element has highest priority.

```java
import java.util.PriorityQueue;

public class PriorityQueueDemo {
    public static void main(String[] args) {

        PriorityQueue<Integer> pq = new PriorityQueue<>();
        pq.offer(50);
        pq.offer(10);
        pq.offer(30);
        pq.offer(20);

        // poll always gives smallest
        System.out.println(pq.poll()); // 10
        System.out.println(pq.poll()); // 20
        System.out.println(pq.poll()); // 30
        System.out.println(pq.poll()); // 50
    }
}
```

Max heap - largest first:

```java
PriorityQueue<Integer> maxHeap = new PriorityQueue<>((a, b) -> b - a);
maxHeap.offer(50);
maxHeap.offer(10);
maxHeap.offer(30);

System.out.println(maxHeap.poll()); // 50 - largest first
```

PriorityQueue with custom objects:

```java
class Task {
    String name;
    int priority; // lower number = higher priority

    Task(String name, int priority) {
        this.name = name;
        this.priority = priority;
    }
}

PriorityQueue<Task> taskQueue = new PriorityQueue<>((a, b) -> a.priority - b.priority);
taskQueue.offer(new Task("Low task", 3));
taskQueue.offer(new Task("High task", 1));
taskQueue.offer(new Task("Medium task", 2));

while (!taskQueue.isEmpty()) {
    Task t = taskQueue.poll();
    System.out.println(t.name); // High task, Medium task, Low task
}
```

### Deque - Double Ended Queue

Deque allows adding and removing from both ends.

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class DequeDemo {
    public static void main(String[] args) {

        Deque<String> deque = new ArrayDeque<>();

        deque.offerFirst("B");
        deque.offerFirst("A");
        deque.offerLast("C");
        deque.offerLast("D");

        System.out.println(deque); // [A, B, C, D]

        System.out.println(deque.pollFirst()); // A
        System.out.println(deque.pollLast());  // D

        System.out.println(deque); // [B, C]
    }
}
```

### Stack using ArrayDeque

The old Stack class is outdated. Use ArrayDeque for stack behavior. Stack = LIFO. Last In, First Out.

```java
Deque<String> stack = new ArrayDeque<>();

stack.push("First");
stack.push("Second");
stack.push("Third");

System.out.println(stack.peek()); // Third - top of stack
System.out.println(stack.pop());  // Third - removed
System.out.println(stack.pop());  // Second
System.out.println(stack.pop());  // First
```

---

## PART 7 - ITERATOR

Every Collection supports iteration. Three ways:

### Way 1 - for-each loop (most common)

```java
List<String> names = new ArrayList<>(Arrays.asList("Arman", "Priya", "Raj"));
for (String name : names) {
    System.out.println(name);
}
```

### Way 2 - Iterator object

Use this when you need to remove elements while iterating. Removing inside a for-each throws ConcurrentModificationException.

```java
List<Integer> numbers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5, 6));
Iterator<Integer> it = numbers.iterator();

while (it.hasNext()) {
    int num = it.next();
    if (num % 2 == 0) {
        it.remove(); // safe removal during iteration
    }
}
System.out.println(numbers); // [1, 3, 5]
```

### Way 3 - ListIterator (for List only)

ListIterator can go forward and backward and can modify elements.

```java
List<String> names = new ArrayList<>(Arrays.asList("arman", "priya", "raj"));
ListIterator<String> lit = names.listIterator();

while (lit.hasNext()) {
    String name = lit.next();
    lit.set(name.toUpperCase());
}
System.out.println(names); // [ARMAN, PRIYA, RAJ]
```

---

## PART 8 - GENERICS IN COLLECTIONS

When you write List<String>, the <String> is the generic type parameter. It tells the compiler what type of objects this collection holds.

Without generics (avoid):

```java
List list = new ArrayList(); // raw type - no type safety
list.add("hello");
list.add(123);               // no error - dangerous
String s = (String) list.get(0); // manual cast needed
```

With generics (always do this):

```java
List<String> list = new ArrayList<>();
list.add("hello");
// list.add(123);  // COMPILE ERROR - caught early
String s = list.get(0); // no cast needed
```

---

## PART 9 - ARRAYS vs COLLECTIONS

```
Array                                   |  Collection (ArrayList)
----------------------------------------|-----------------------------
Fixed size                              |  Dynamic size
int[] arr = new int[5]                  |  List<Integer> list = new ArrayList<>()
Can hold primitives: int, char, etc     |  Objects only: Integer, String
Access by index: arr[0]                 |  Access by method: list.get(0)
arr.length for size                     |  list.size() for size
No built-in search/sort methods         |  Rich set of methods
Less memory overhead                    |  More memory overhead
```

### Converting between Array and List

```java
// Array to List
String[] arr = {"Arman", "Priya", "Raj"};
List<String> list = new ArrayList<>(Arrays.asList(arr));

// List to Array
String[] backToArray = list.toArray(new String[0]);

// Quick List creation - NOT modifiable
List<String> fixed = Arrays.asList("A", "B", "C");
// fixed.add("D"); // throws UnsupportedOperationException

// Quick List creation - modifiable
List<String> modifiable = new ArrayList<>(Arrays.asList("A", "B", "C"));
modifiable.add("D"); // works fine
```

---

## PART 10 - NULL HANDLING IN COLLECTIONS

This is commonly tested:

```
ArrayList        -> allows null, multiple nulls
LinkedList       -> allows null
HashSet          -> allows ONE null
LinkedHashSet    -> allows ONE null
TreeSet          -> does NOT allow null (NullPointerException)
HashMap          -> allows ONE null key, multiple null values
LinkedHashMap    -> allows ONE null key, multiple null values
TreeMap          -> does NOT allow null key (NullPointerException)
PriorityQueue    -> does NOT allow null
ArrayDeque       -> does NOT allow null
Hashtable        -> does NOT allow null key or value (old class)
```

---

## PART 11 - WHICH ONE TO USE - DECISION GUIDE

```
Do you need key-value pairs?
    YES -> Use Map
        Need sorted keys?          -> TreeMap
        Need insertion order?      -> LinkedHashMap
        Just fast access?          -> HashMap

    NO -> Do you need unique elements only?
        YES -> Use Set
            Need sorted elements?  -> TreeSet
            Need insertion order?  -> LinkedHashSet
            Just fast check?       -> HashSet

        NO -> Do you need FIFO or priority?
            YES -> Use Queue
                Need priority order?   -> PriorityQueue
                Need both ends?        -> ArrayDeque
                Just FIFO?             -> LinkedList as Queue

            NO -> Use List
                Lots of index access?  -> ArrayList
                Lots of insert/delete? -> LinkedList
```

Performance summary:

```
Operation            ArrayList   LinkedList   HashSet/HashMap
get by index         O(1)        O(n)         N/A
get by key/value     O(n)        O(n)         O(1)
add at end           O(1)        O(1)         O(1)
add at middle        O(n)        O(1)         N/A
remove at index      O(n)        O(1)         N/A
contains/search      O(n)        O(n)         O(1)
```

---

## PART 12 - UNMODIFIABLE COLLECTIONS

```java
List<String> original = new ArrayList<>(Arrays.asList("A", "B", "C"));
List<String> unmodifiable = Collections.unmodifiableList(original);
// unmodifiable.add("D"); // throws UnsupportedOperationException

// Java 9+ way
List<String> immutable = List.of("A", "B", "C");
Set<String> immutableSet = Set.of("X", "Y", "Z");
Map<String, Integer> immutableMap = Map.of("key1", 1, "key2", 2);
```

---

## PART 13 - THREAD SAFETY BASICS

Most collections in Java are NOT thread-safe. If multiple threads use the same collection, you can get errors.

Old thread-safe classes to avoid: Vector, Hashtable

Making a regular collection thread-safe:

```java
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());
```

Better options from java.util.concurrent:

```java
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ConcurrentHashMap;

List<String> concurrentList = new CopyOnWriteArrayList<>();
Map<String, Integer> concurrentMap = new ConcurrentHashMap<>();
```

This goes deep into multithreading which is a separate topic. For now just know these exist and when you hear thread-safe, remember ConcurrentHashMap and CopyOnWriteArrayList.
