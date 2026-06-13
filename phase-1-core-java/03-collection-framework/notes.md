# Java Collection Framework - Complete Notes

---

## PART 1 - WHAT IS COLLECTION FRAMEWORK AND WHY IT EXISTS

Before collections existed in Java, storing multiple values was done like this:

```java
String student1 = "Arman";
String student2 = "Priya";
String student3 = "Raj";
// imagine doing this for 100 students
```

Or with arrays:

```java
String[] students = new String[5];
students[0] = "Arman";
```

Arrays have problems. Their size is fixed at creation time. If you need to add more elements than the array size, you are stuck. You have to create a new bigger array, copy everything, then add. This is painful.

Collection Framework solves all of this. It gives you ready-made data structures that handle resizing, searching, sorting, and much more automatically.

Collection Framework is in the package java.util. You import from this package.

```java
import java.util.*;  // imports everything from java.util at once
```

---

## PART 2 - COMPLETE HIERARCHY

This is the full picture. Every class and interface in the framework and how they relate.

```
java.lang.Iterable  (interface)
        |
java.util.Collection  (interface)
        |
        |---------- List  (interface)
        |                |--- ArrayList      (class)
        |                |--- LinkedList     (class)  -- also implements Deque
        |                |--- Vector         (class)  -- legacy, thread-safe
        |                        |--- Stack  (class)  -- legacy, extends Vector
        |
        |---------- Set  (interface)
        |                |--- HashSet            (class)
        |                |       |--- LinkedHashSet  (class)
        |                |--- SortedSet       (interface)
        |                        |--- NavigableSet  (interface)
        |                                |--- TreeSet  (class)
        |
        |---------- Queue  (interface)
                         |--- LinkedList         (class)  -- also implements List
                         |--- PriorityQueue      (class)
                         |--- Deque              (interface)
                                 |--- ArrayDeque     (class)
                                 |--- LinkedList     (class)


java.util.Map  (interface)  -- does NOT extend Collection
        |--- HashMap           (class)
        |       |--- LinkedHashMap  (class)
        |--- SortedMap         (interface)
        |       |--- NavigableMap   (interface)
        |               |--- TreeMap  (class)
        |--- Hashtable         (class)  -- legacy, thread-safe
        |--- WeakHashMap       (class)  -- special use
        |--- IdentityHashMap   (class)  -- special use
        |--- EnumMap           (class)  -- special use
```

Key things to remember from this hierarchy:

- LinkedList appears under both List and Deque. It implements both interfaces.
- Stack extends Vector which is a legacy class.
- Map is completely separate. It does not extend Collection.
- LinkedHashSet extends HashSet, not directly from Set.
- TreeSet implements NavigableSet which extends SortedSet which extends Set.

---

## PART 3 - ITERABLE AND COLLECTION INTERFACES

### Iterable Interface

Iterable is the root. It is in java.lang, not java.util. Any class that implements Iterable can be used in a for-each loop.

It has one method: iterator()

```java
public interface Iterable<T> {
    Iterator<T> iterator();
}
```

When you write:

```java
for (String name : names) {
    System.out.println(name);
}
```

Java internally converts this to:

```java
Iterator<String> it = names.iterator();
while (it.hasNext()) {
    String name = it.next();
    System.out.println(name);
}
```

So for-each is just syntactic sugar over Iterator.

### Collection Interface

Collection extends Iterable. It is the root interface for List, Set, and Queue.

Core methods that every Collection has:

```
add(element)          -- add one element
addAll(collection)    -- add all elements from another collection
remove(element)       -- remove one element
removeAll(collection) -- remove all elements that are in the given collection
retainAll(collection) -- keep only elements that are in the given collection
contains(element)     -- true if element is present
containsAll(collection) -- true if all elements of given collection are present
size()                -- number of elements
isEmpty()             -- true if no elements
clear()               -- remove all elements
toArray()             -- convert to Object array
iterator()            -- get an Iterator
```

---

## PART 4 - ITERATOR AND LISTITERATOR

### Iterator

Iterator is an object that lets you traverse a collection one element at a time.

Methods:

- hasNext() -- returns true if there are more elements
- next() -- returns the next element and moves forward
- remove() -- removes the last element returned by next()

```java
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class IteratorDemo {
    public static void main(String[] args) {

        List<String> names = new ArrayList<>();
        names.add("Arman");
        names.add("Priya");
        names.add("Raj");
        names.add("Sneha");

        Iterator<String> it = names.iterator();

        while (it.hasNext()) {
            String name = it.next();
            System.out.println(name);
        }
    }
}
```

Why Iterator is important - safe removal during iteration:

If you try to remove from a collection while iterating with for-each, you get ConcurrentModificationException. Use Iterator to remove safely.

```java
List<Integer> numbers = new ArrayList<>();
numbers.add(1); numbers.add(2); numbers.add(3);
numbers.add(4); numbers.add(5); numbers.add(6);

// WRONG - throws ConcurrentModificationException
for (int n : numbers) {
    if (n % 2 == 0) {
        numbers.remove(Integer.valueOf(n)); // ERROR
    }
}

// CORRECT - use Iterator
Iterator<Integer> it = numbers.iterator();
while (it.hasNext()) {
    int n = it.next();
    if (n % 2 == 0) {
        it.remove(); // safe
    }
}
System.out.println(numbers); // [1, 3, 5]
```

### Fail-Fast vs Fail-Safe Iterators

This is a very important concept.

Fail-Fast iterators throw ConcurrentModificationException immediately if the collection is modified while iterating (other than through the iterator's own remove() method). They detect modification by tracking a modification count internally.

Most collections in java.util are fail-fast: ArrayList, HashMap, HashSet, LinkedList, etc.

```java
List<String> list = new ArrayList<>();
list.add("A"); list.add("B"); list.add("C");

for (String s : list) {
    list.add("D"); // ConcurrentModificationException thrown here
}
```

Fail-Safe iterators work on a copy of the collection. Modifications to the original do not affect the iterator. They do NOT throw ConcurrentModificationException.

Collections in java.util.concurrent are fail-safe: CopyOnWriteArrayList, ConcurrentHashMap, etc.

```java
import java.util.concurrent.CopyOnWriteArrayList;

List<String> list = new CopyOnWriteArrayList<>();
list.add("A"); list.add("B"); list.add("C");

for (String s : list) {
    list.add("D"); // NO exception - iterator works on a snapshot
}
// list now has extra D's but iteration completes safely
```

### ListIterator

ListIterator is only for List. It extends Iterator and adds the ability to go backwards and to set or add elements.

Methods added over Iterator:

- hasPrevious() -- true if there is a previous element
- previous() -- return previous element
- nextIndex() -- index of next element
- previousIndex() -- index of previous element
- set(e) -- replace last element returned
- add(e) -- insert element before next element

```java
List<String> names = new ArrayList<>();
names.add("arman"); names.add("priya"); names.add("raj");

ListIterator<String> lit = names.listIterator();

// Go forward and uppercase each name
while (lit.hasNext()) {
    String name = lit.next();
    lit.set(name.toUpperCase());
}
System.out.println(names); // [ARMAN, PRIYA, RAJ]

// Go backward
while (lit.hasPrevious()) {
    System.out.println(lit.previous()); // RAJ, PRIYA, ARMAN
}
```

---

## PART 5 - LIST INTERFACE AND ALL IMPLEMENTATIONS

### What is a List

List is an ordered collection. Elements stay in insertion order. Duplicates are allowed. Every element has an index starting from 0.

### ArrayList

Internally a resizable array. When it fills up, a new array of 1.5x size is created and all elements are copied.

Initial capacity is 10 by default. You can set it:

```java
List<String> list = new ArrayList<>(50); // initial capacity 50
```

When to use:

- Frequent access by index (get, set) -- O(1) time
- Mostly adding to end
- Not much insertion or deletion in the middle

When not to use:

- Frequent insertion or deletion in the middle -- O(n) because elements must shift

```java
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ArrayListComplete {
    public static void main(String[] args) {

        List<String> list = new ArrayList<>();

        // add to end
        list.add("Arman");
        list.add("Priya");
        list.add("Raj");
        list.add("Sneha");

        // add at specific index - shifts everything right
        list.add(1, "Rohan");
        // [Arman, Rohan, Priya, Raj, Sneha]

        // get by index
        System.out.println(list.get(0)); // Arman
        System.out.println(list.get(2)); // Priya

        // replace at index
        list.set(0, "Arman Khan");

        // remove by index
        list.remove(1); // removes Rohan

        // remove by value - removes FIRST occurrence
        list.remove("Raj");

        // size
        System.out.println(list.size());

        // contains
        System.out.println(list.contains("Priya")); // true

        // index of element - returns -1 if not found
        System.out.println(list.indexOf("Priya")); // 1

        // last index of (useful when duplicates exist)
        list.add("Priya");
        System.out.println(list.lastIndexOf("Priya")); // 3

        // subList - from index (inclusive) to index (exclusive)
        List<String> sub = list.subList(0, 2);
        System.out.println(sub); // first two elements

        // sort
        Collections.sort(list);

        // reverse
        Collections.reverse(list);

        // max and min
        System.out.println(Collections.max(list));
        System.out.println(Collections.min(list));

        // frequency
        System.out.println(Collections.frequency(list, "Priya")); // 2

        // shuffle
        Collections.shuffle(list);

        // fill all with same value
        Collections.fill(list, "test");

        // swap two elements
        list = new ArrayList<>();
        list.add("A"); list.add("B"); list.add("C");
        Collections.swap(list, 0, 2);
        System.out.println(list); // [C, B, A]

        // disjoint - true if two collections have NO common elements
        List<String> other = new ArrayList<>();
        other.add("X"); other.add("Y");
        System.out.println(Collections.disjoint(list, other)); // true

        // nCopies - create list with n copies of a value
        List<String> copies = Collections.nCopies(5, "hello");
        System.out.println(copies); // [hello, hello, hello, hello, hello]

        // clear
        list.clear();
        System.out.println(list.isEmpty()); // true
    }
}
```

### remove(int index) vs remove(Object value) - important confusion

```java
List<Integer> numbers = new ArrayList<>();
numbers.add(10);
numbers.add(20);
numbers.add(30);

numbers.remove(1);                   // removes at INDEX 1 -- removes 20
numbers.remove(Integer.valueOf(10)); // removes VALUE 10
System.out.println(numbers); // [30]
```

### Binary Search on List

For binary search to work, the list must be sorted first.

```java
List<Integer> marks = new ArrayList<>();
marks.add(55); marks.add(62); marks.add(71);
marks.add(85); marks.add(90);

Collections.sort(marks); // must be sorted

int index = Collections.binarySearch(marks, 71);
System.out.println(index); // 2

// if element not found, returns (-(insertion point) - 1)
int notFound = Collections.binarySearch(marks, 70);
System.out.println(notFound); // negative number
```

### LinkedList

LinkedList is a doubly linked list. Each node has a value, a pointer to the next node, and a pointer to the previous node.

LinkedList implements both List and Deque interfaces. So it can be used as a List, a Queue, or a Stack.

Memory: each node stores the value plus two references. So it uses more memory than ArrayList per element.

When to use:

- Frequent insertion or deletion at beginning, end, or middle -- O(1)
- You want to use it as a Queue or Deque also

When not to use:

- Frequent access by index -- O(n) because it has to walk from the start or end

```java
import java.util.LinkedList;

public class LinkedListComplete {
    public static void main(String[] args) {

        LinkedList<String> list = new LinkedList<>();

        // Standard List operations
        list.add("Arman");
        list.add("Priya");
        list.add("Raj");

        System.out.println(list.get(1)); // Priya -- but O(n)
        list.set(1, "Priya Sharma");
        list.remove(0);

        // LinkedList specific methods
        list.addFirst("First");     // add to beginning
        list.addLast("Last");       // add to end
        list.offerFirst("VeryFirst"); // same as addFirst, returns boolean
        list.offerLast("VeryLast");   // same as addLast

        System.out.println(list.getFirst());  // VeryFirst
        System.out.println(list.getLast());   // VeryLast
        System.out.println(list.peekFirst()); // VeryFirst - null if empty
        System.out.println(list.peekLast());  // VeryLast - null if empty

        list.removeFirst(); // removes first element
        list.removeLast();  // removes last element
        list.pollFirst();   // removes first, null if empty (safe)
        list.pollLast();    // removes last, null if empty (safe)

        // Using LinkedList as Queue (FIFO)
        LinkedList<String> queue = new LinkedList<>();
        queue.offer("Person1");   // add to end
        queue.offer("Person2");
        queue.offer("Person3");
        System.out.println(queue.poll()); // Person1 - removed from front
        System.out.println(queue.peek()); // Person2 - just see front

        // Using LinkedList as Stack (LIFO)
        LinkedList<String> stack = new LinkedList<>();
        stack.push("Bottom"); // push adds to front
        stack.push("Middle");
        stack.push("Top");
        System.out.println(stack.pop());  // Top - removed from front
        System.out.println(stack.peek()); // Middle - just see front
    }
}
```

### ArrayList vs LinkedList - complete comparison

```
Feature                ArrayList           LinkedList
-----------------------------------------------------------------
Internal structure     Resizable array     Doubly linked nodes
get(index)             O(1) - fast         O(n) - slow, must traverse
add at end             O(1) amortized      O(1) always
add at beginning       O(n) - shifts all   O(1)
add at middle          O(n) - shifts half  O(1) if node ref available
remove at end          O(1)                O(1)
remove at beginning    O(n) - shifts all   O(1)
remove at middle       O(n)                O(1) if node ref available
contains(value)        O(n)                O(n)
Memory per element     Less                More (two extra references)
Can be used as Queue   No                  Yes
Can be used as Stack   No                  Yes
```

### Vector

Vector is the old synchronized version of ArrayList. It was created in Java 1.0, before the Collections Framework was introduced in Java 2.

It is thread-safe but slow because every method is synchronized. In modern Java, you almost never use Vector.

Why it still exists: backward compatibility. Old code still uses it.

What to use instead:

- Use ArrayList for non-threaded code
- Use Collections.synchronizedList(new ArrayList<>()) for thread safety
- Use CopyOnWriteArrayList for concurrent read-heavy scenarios

```java
import java.util.Vector;

public class VectorDemo {
    public static void main(String[] args) {

        Vector<String> vector = new Vector<>(); // default initial capacity 10

        vector.add("Arman");
        vector.add("Priya");
        vector.add("Raj");

        // Vector has all List methods plus some old ones
        System.out.println(vector.elementAt(0));    // old way - same as get(0)
        System.out.println(vector.firstElement());  // same as get(0)
        System.out.println(vector.lastElement());   // same as get(size-1)

        vector.addElement("Sneha");                 // same as add()
        vector.removeElement("Priya");              // same as remove()
        vector.removeElementAt(0);                  // same as remove(0)

        System.out.println(vector.capacity());      // internal array size
        System.out.println(vector.size());          // actual elements count

        // Enumeration - old way to iterate (before Iterator)
        java.util.Enumeration<String> e = vector.elements();
        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());
        }
    }
}
```

### Stack

Stack extends Vector. It implements a LIFO (Last In, First Out) data structure.

Problem: Stack extends Vector which is bad design. Vector is a List but Stack should not be a List. push, pop, peek operations are added but the underlying Vector operations are also all exposed which breaks encapsulation.

Modern Java recommendation: Use ArrayDeque instead of Stack.

```java
import java.util.Stack;

public class StackDemo {
    public static void main(String[] args) {

        Stack<String> stack = new Stack<>();

        // push - add to top
        stack.push("First");
        stack.push("Second");
        stack.push("Third");

        System.out.println(stack); // [First, Second, Third]

        // peek - see top without removing
        System.out.println(stack.peek()); // Third

        // pop - remove and return top
        System.out.println(stack.pop()); // Third
        System.out.println(stack.pop()); // Second

        System.out.println(stack); // [First]

        // empty - true if stack is empty
        System.out.println(stack.empty()); // false

        // search - 1-based position from top, -1 if not found
        stack.push("Second");
        stack.push("Third");
        System.out.println(stack.search("Second")); // 2 (from top)
        System.out.println(stack.search("Unknown")); // -1
    }
}
```

### Why ArrayDeque is better than Stack

```java
import java.util.ArrayDeque;
import java.util.Deque;

// Use Deque interface and ArrayDeque implementation
Deque<String> stack = new ArrayDeque<>();

stack.push("First");   // push adds to front (top of stack)
stack.push("Second");
stack.push("Third");

System.out.println(stack.peek()); // Third
System.out.println(stack.pop());  // Third
System.out.println(stack.pop());  // Second
System.out.println(stack.pop());  // First
```

ArrayDeque is faster than Stack because:

- Stack is synchronized (slow)
- ArrayDeque is not synchronized (fast, single thread)
- ArrayDeque has cleaner API (no inherited List methods confusing things)

### Real-world use of Stack - Balanced Brackets

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class BalancedBrackets {
    public static boolean isBalanced(String s) {
        Deque<Character> stack = new ArrayDeque<>();

        for (char c : s.toCharArray()) {
            if (c == '(' || c == '{' || c == '[') {
                stack.push(c);
            } else if (c == ')' || c == '}' || c == ']') {
                if (stack.isEmpty()) return false;

                char top = stack.pop();
                if (c == ')' && top != '(') return false;
                if (c == '}' && top != '{') return false;
                if (c == ']' && top != '[') return false;
            }
        }

        return stack.isEmpty();
    }

    public static void main(String[] args) {
        System.out.println(isBalanced("(())")); // true
        System.out.println(isBalanced("{[()]}")); // true
        System.out.println(isBalanced("(()")); // false
        System.out.println(isBalanced("{[}]")); // false
    }
}
```

---

## PART 6 - SET INTERFACE AND ALL IMPLEMENTATIONS

### What is a Set

Set is a collection that does NOT allow duplicates. If you try to add a value that already exists, nothing happens - no error, no change. The add() method returns false in that case.

Set does not have an index. You cannot do set.get(0).

How Set detects duplicates: it uses equals() and hashCode() methods internally.

### HashSet

Most common Set. Internally uses a HashMap where each element is stored as a key with a dummy value.

Order is NOT guaranteed. Elements can appear in any order when you print.

Time complexity: O(1) for add, remove, contains.

```java
import java.util.HashSet;
import java.util.Set;

public class HashSetComplete {
    public static void main(String[] args) {

        Set<String> cities = new HashSet<>();

        boolean added1 = cities.add("Jaipur");  // returns true
        boolean added2 = cities.add("Delhi");
        boolean added3 = cities.add("Mumbai");
        boolean added4 = cities.add("Jaipur");  // returns FALSE - duplicate
        boolean added5 = cities.add("Delhi");   // returns FALSE - duplicate

        System.out.println(added1); // true
        System.out.println(added4); // false

        System.out.println(cities.size()); // 3

        System.out.println(cities.contains("Mumbai")); // true
        System.out.println(cities.contains("Pune"));   // false

        cities.remove("Delhi");

        // Loop - order not guaranteed
        for (String city : cities) {
            System.out.println(city);
        }

        // Set operations
        Set<Integer> set1 = new HashSet<>();
        set1.add(1); set1.add(2); set1.add(3); set1.add(4);

        Set<Integer> set2 = new HashSet<>();
        set2.add(3); set2.add(4); set2.add(5); set2.add(6);

        // Union
        Set<Integer> union = new HashSet<>(set1);
        union.addAll(set2);
        System.out.println(union); // [1, 2, 3, 4, 5, 6]

        // Intersection
        Set<Integer> intersection = new HashSet<>(set1);
        intersection.retainAll(set2);
        System.out.println(intersection); // [3, 4]

        // Difference (set1 - set2)
        Set<Integer> difference = new HashSet<>(set1);
        difference.removeAll(set2);
        System.out.println(difference); // [1, 2]

        // Check if one set is subset of another
        Set<Integer> small = new HashSet<>();
        small.add(3); small.add(4);
        System.out.println(set1.containsAll(small)); // true - small is subset of set1

        // disjoint - true if no common elements
        Set<Integer> noCommon = new HashSet<>();
        noCommon.add(10); noCommon.add(20);
        System.out.println(java.util.Collections.disjoint(set1, noCommon)); // true
    }
}
```

### HashSet with custom objects - equals and hashCode

For HashSet to correctly identify duplicates in your custom objects, you must override equals() and hashCode().

```java
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

class Student {
    String name;
    int rollNo;

    Student(String name, int rollNo) {
        this.name = name;
        this.rollNo = rollNo;
    }

    // Without these, two Student("Arman", 1) are treated as DIFFERENT objects
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Student)) return false;
        Student other = (Student) obj;
        return this.rollNo == other.rollNo && this.name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(name, rollNo);
    }
}

public class HashSetCustomObjects {
    public static void main(String[] args) {

        Set<Student> students = new HashSet<>();
        students.add(new Student("Arman", 1));
        students.add(new Student("Priya", 2));
        students.add(new Student("Arman", 1)); // duplicate - ignored

        System.out.println(students.size()); // 2, not 3
    }
}
```

### LinkedHashSet

LinkedHashSet extends HashSet. It maintains a doubly linked list running through all entries. This maintains insertion order.

Time complexity: slightly slower than HashSet due to maintaining the linked list.

```java
import java.util.LinkedHashSet;
import java.util.Set;

public class LinkedHashSetDemo {
    public static void main(String[] args) {

        Set<String> set = new LinkedHashSet<>();

        set.add("Banana");
        set.add("Apple");
        set.add("Mango");
        set.add("Apple"); // duplicate - ignored

        System.out.println(set); // [Banana, Apple, Mango] - insertion order maintained

        // Removing duplicates from list while maintaining order
        List<String> list = new ArrayList<>();
        list.add("Raj"); list.add("Amit"); list.add("Raj");
        list.add("Sneha"); list.add("Amit");

        Set<String> unique = new LinkedHashSet<>(list);
        List<String> result = new ArrayList<>(unique);
        System.out.println(result); // [Raj, Amit, Sneha] - order maintained
    }
}
```

### TreeSet

TreeSet implements NavigableSet which implements SortedSet. Elements are stored in sorted order (ascending by default). Internally it uses a Red-Black tree.

No null allowed - it needs to compare elements to sort them, null cannot be compared.

Time complexity: O(log n) for add, remove, contains. Slower than HashSet.

```java
import java.util.TreeSet;

public class TreeSetComplete {
    public static void main(String[] args) {

        TreeSet<Integer> numbers = new TreeSet<>();
        numbers.add(50);
        numbers.add(10);
        numbers.add(30);
        numbers.add(20);
        numbers.add(40);
        numbers.add(10); // duplicate - ignored

        System.out.println(numbers); // [10, 20, 30, 40, 50] - sorted

        // SortedSet methods
        System.out.println(numbers.first()); // 10 - smallest
        System.out.println(numbers.last());  // 50 - largest

        // NavigableSet methods
        System.out.println(numbers.floor(25));   // 20 - greatest <= 25
        System.out.println(numbers.ceiling(25)); // 30 - smallest >= 25
        System.out.println(numbers.lower(30));   // 20 - strictly less than 30
        System.out.println(numbers.higher(30));  // 40 - strictly greater than 30

        // headSet - elements strictly less than given value
        System.out.println(numbers.headSet(30)); // [10, 20]

        // headSet inclusive version
        System.out.println(numbers.headSet(30, true)); // [10, 20, 30]

        // tailSet - elements greater than or equal to given value
        System.out.println(numbers.tailSet(30)); // [30, 40, 50]

        // subSet - from (inclusive) to (exclusive) by default
        System.out.println(numbers.subSet(20, 40)); // [20, 30]

        // subSet with inclusive flags
        System.out.println(numbers.subSet(20, true, 40, true)); // [20, 30, 40]

        // descending set
        System.out.println(numbers.descendingSet()); // [50, 40, 30, 20, 10]

        // pollFirst and pollLast - remove and return
        System.out.println(numbers.pollFirst()); // 10 - removed
        System.out.println(numbers.pollLast());  // 50 - removed
        System.out.println(numbers); // [20, 30, 40]
    }
}
```

TreeSet with custom objects - must be Comparable or provide Comparator:

```java
import java.util.TreeSet;
import java.util.Comparator;

class Employee {
    String name;
    int salary;

    Employee(String name, int salary) {
        this.name = name;
        this.salary = salary;
    }

    public String toString() {
        return name + "(" + salary + ")";
    }
}

public class TreeSetCustom {
    public static void main(String[] args) {

        // Sort employees by salary ascending
        TreeSet<Employee> employees = new TreeSet<>((a, b) -> a.salary - b.salary);

        employees.add(new Employee("Priya", 50000));
        employees.add(new Employee("Arman", 75000));
        employees.add(new Employee("Raj", 60000));

        System.out.println(employees);
        // [Priya(50000), Raj(60000), Arman(75000)]
    }
}
```

---

## PART 7 - QUEUE INTERFACE AND ALL IMPLEMENTATIONS

### What is a Queue

Queue works on FIFO - First In, First Out. Like a line at any counter. The person who arrived first gets served first.

### Queue methods - two sets

Every Queue method has two versions: one that throws exception, one that returns special value (safe version).

```
Operation   Throws exception   Returns special value
------------------------------------------------------
Insert      add(e)             offer(e) -- false if full
Remove      remove()           poll()   -- null if empty
Examine     element()          peek()   -- null if empty
```

Always use the safe versions: offer(), poll(), peek()

### LinkedList as Queue

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

        System.out.println(queue.peek()); // Arman - just look at front
        System.out.println(queue.poll()); // Arman - remove from front
        System.out.println(queue.peek()); // Priya - now at front

        System.out.println(queue.size()); // 3

        while (!queue.isEmpty()) {
            System.out.println("Serving: " + queue.poll());
        }
    }
}
```

### PriorityQueue

PriorityQueue does not follow FIFO. It always serves the element with the highest priority first. By default, the smallest element has the highest priority (min-heap).

Internally it uses a heap data structure (binary heap stored in an array).

Null not allowed.

```java
import java.util.PriorityQueue;

public class PriorityQueueComplete {
    public static void main(String[] args) {

        // Default - min heap (smallest comes first)
        PriorityQueue<Integer> minPQ = new PriorityQueue<>();
        minPQ.offer(50);
        minPQ.offer(10);
        minPQ.offer(30);
        minPQ.offer(20);

        System.out.println(minPQ.peek()); // 10 - smallest
        System.out.println(minPQ.poll()); // 10
        System.out.println(minPQ.poll()); // 20
        System.out.println(minPQ.poll()); // 30
        System.out.println(minPQ.poll()); // 50

        // Max heap - largest comes first
        PriorityQueue<Integer> maxPQ = new PriorityQueue<>((a, b) -> b - a);
        maxPQ.offer(50);
        maxPQ.offer(10);
        maxPQ.offer(30);

        System.out.println(maxPQ.poll()); // 50 - largest first
        System.out.println(maxPQ.poll()); // 30
        System.out.println(maxPQ.poll()); // 10

        // PriorityQueue with custom objects
        PriorityQueue<String> byLength = new PriorityQueue<>((a, b) -> a.length() - b.length());
        byLength.offer("banana");
        byLength.offer("hi");
        byLength.offer("apple");
        byLength.offer("cat");

        while (!byLength.isEmpty()) {
            System.out.println(byLength.poll()); // hi, cat, apple, banana (shortest first)
        }
    }
}
```

### Deque Interface

Deque (Double Ended Queue, pronounced "deck") extends Queue. It allows insertion and removal from both ends. It can work as both a Queue (FIFO) and a Stack (LIFO).

Deque methods:

```
Front operations:
addFirst(e) / offerFirst(e)   -- add at front
removeFirst() / pollFirst()   -- remove from front
getFirst() / peekFirst()      -- look at front

Back operations:
addLast(e) / offerLast(e)     -- add at back
removeLast() / pollLast()     -- remove from back
getLast() / peekLast()        -- look at back

Stack operations (maps to front):
push(e)   -- same as addFirst
pop()     -- same as removeFirst
peek()    -- same as peekFirst
```

### ArrayDeque

ArrayDeque implements Deque using a resizable circular array. It is the preferred implementation over LinkedList when you need a Deque.

Why ArrayDeque is better than LinkedList for Queue/Deque:

- No memory overhead for node references
- Better cache performance
- Faster in practice

Null not allowed.

```java
import java.util.ArrayDeque;
import java.util.Deque;

public class ArrayDequeComplete {
    public static void main(String[] args) {

        Deque<String> deque = new ArrayDeque<>();

        // Add from both ends
        deque.offerFirst("B");
        deque.offerFirst("A"); // front
        deque.offerLast("C");
        deque.offerLast("D");  // back

        System.out.println(deque); // [A, B, C, D]

        // Peek at both ends
        System.out.println(deque.peekFirst()); // A
        System.out.println(deque.peekLast());  // D

        // Remove from both ends
        System.out.println(deque.pollFirst()); // A
        System.out.println(deque.pollLast());  // D
        System.out.println(deque); // [B, C]

        // Using as Queue (FIFO)
        Deque<String> queue = new ArrayDeque<>();
        queue.offerLast("First");
        queue.offerLast("Second");
        queue.offerLast("Third");
        System.out.println(queue.pollFirst()); // First

        // Using as Stack (LIFO)
        Deque<String> stack = new ArrayDeque<>();
        stack.push("Bottom"); // push = addFirst
        stack.push("Middle");
        stack.push("Top");
        System.out.println(stack.pop());  // Top - LIFO
        System.out.println(stack.peek()); // Middle
    }
}
```

---

## PART 8 - MAP INTERFACE AND ALL IMPLEMENTATIONS

### What is a Map

Map stores key-value pairs. Given a key, you can find its value instantly.

Rules:

- Keys must be unique
- Values can be duplicated
- Each key maps to exactly one value
- Same key put again replaces old value
- Map does NOT extend Collection

### HashMap

Most common Map. Uses hashing internally. Order not guaranteed.

How HashMap works internally:

- It has an array of buckets (default size 16)
- For each key, it calls hashCode() to find which bucket
- Multiple keys can go to same bucket (collision) - handled by LinkedList or Tree
- When 75% of buckets are filled (load factor = 0.75), array is resized to 2x

```java
import java.util.HashMap;
import java.util.Map;

public class HashMapComplete {
    public static void main(String[] args) {

        Map<String, Integer> marks = new HashMap<>();

        // put - add or replace
        marks.put("Arman", 85);
        marks.put("Priya", 92);
        marks.put("Raj", 71);
        marks.put("Sneha", 88);
        marks.put("Arman", 95); // replaces 85 with 95

        // get
        System.out.println(marks.get("Arman")); // 95
        System.out.println(marks.get("Nobody")); // null

        // getOrDefault
        System.out.println(marks.getOrDefault("Nobody", 0)); // 0

        // containsKey and containsValue
        System.out.println(marks.containsKey("Priya"));   // true
        System.out.println(marks.containsValue(92));       // true

        // putIfAbsent - only adds if key does not exist
        marks.putIfAbsent("Arman", 50); // will NOT update, Arman already exists
        marks.putIfAbsent("Rohan", 78); // will add, Rohan is new
        System.out.println(marks.get("Arman")); // still 95

        // replace - only updates if key exists
        marks.replace("Arman", 99);
        System.out.println(marks.get("Arman")); // 99

        // remove by key
        marks.remove("Raj");

        // remove by key AND value - only removes if both match
        marks.remove("Priya", 92); // removes only if Priya's value is exactly 92

        // size
        System.out.println(marks.size());

        // Loop options
        // Option 1 - entrySet (most common)
        for (Map.Entry<String, Integer> entry : marks.entrySet()) {
            System.out.println(entry.getKey() + " -> " + entry.getValue());
        }

        // Option 2 - keySet
        for (String key : marks.keySet()) {
            System.out.println(key + " -> " + marks.get(key));
        }

        // Option 3 - values only
        for (int value : marks.values()) {
            System.out.println(value);
        }

        // Option 4 - forEach with lambda
        marks.forEach((key, value) -> System.out.println(key + ": " + value));

        // compute - modify value based on key and existing value
        marks.compute("Arman", (key, oldValue) -> oldValue + 5);
        // if key exists, applies the function
        // if key does not exist, oldValue is null, be careful

        // computeIfAbsent - compute only if key is absent
        marks.computeIfAbsent("NewStudent", key -> 60);

        // computeIfPresent - compute only if key is present
        marks.computeIfPresent("Arman", (key, oldValue) -> oldValue + 10);

        // merge - combine old value and new value
        marks.merge("Arman", 5, Integer::sum); // Arman's marks = old + 5

        marks.clear();
        System.out.println(marks.isEmpty()); // true
    }
}
```

### Frequency counting pattern - very commonly used

```java
String[] words = {"apple", "mango", "apple", "banana", "mango", "apple"};

Map<String, Integer> frequency = new HashMap<>();

for (String word : words) {
    frequency.put(word, frequency.getOrDefault(word, 0) + 1);
    // same as:
    // frequency.merge(word, 1, Integer::sum);
}

System.out.println(frequency); // {apple=3, mango=2, banana=1}
```

### Grouping pattern - also very commonly used

```java
Map<String, List<String>> gradeGroups = new HashMap<>();

String[] students = {"Arman", "Priya", "Raj", "Sneha"};
int[] marksArr = {95, 82, 67, 91};

for (int i = 0; i < students.length; i++) {
    String grade;
    if (marksArr[i] >= 90)       grade = "A";
    else if (marksArr[i] >= 75)  grade = "B";
    else if (marksArr[i] >= 60)  grade = "C";
    else                          grade = "F";

    gradeGroups.putIfAbsent(grade, new ArrayList<>());
    gradeGroups.get(grade).add(students[i]);
}
```

### LinkedHashMap

Maintains insertion order. Slightly slower than HashMap due to maintaining the linked list.

```java
Map<String, Integer> map = new LinkedHashMap<>();
map.put("Banana", 3);
map.put("Apple", 1);
map.put("Mango", 2);

System.out.println(map); // {Banana=3, Apple=1, Mango=2} - insertion order
```

Access-order LinkedHashMap - entries are ordered by access (most recently used last). Used for LRU Cache.

```java
// true = access order (false = insertion order which is default)
Map<String, Integer> accessOrder = new LinkedHashMap<>(16, 0.75f, true);
accessOrder.put("A", 1);
accessOrder.put("B", 2);
accessOrder.put("C", 3);

accessOrder.get("A"); // access A - moves A to end

System.out.println(accessOrder); // {B=2, C=3, A=1} - A moved to end because accessed last
```

LRU Cache using LinkedHashMap:

```java
import java.util.LinkedHashMap;
import java.util.Map;

public class LRUCache<K, V> extends LinkedHashMap<K, V> {
    private int maxSize;

    public LRUCache(int maxSize) {
        super(maxSize, 0.75f, true); // access order = true
        this.maxSize = maxSize;
    }

    @Override
    protected boolean removeEldestEntry(Map.Entry<K, V> eldest) {
        return size() > maxSize; // remove oldest when over capacity
    }
}

public class LRUDemo {
    public static void main(String[] args) {
        LRUCache<Integer, String> cache = new LRUCache<>(3);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");
        cache.get(1);    // access 1 - moves to end
        cache.put(4, "D"); // cache full - removes 2 (least recently used)
        System.out.println(cache); // {3=C, 1=A, 4=D}
    }
}
```

### TreeMap

TreeMap implements NavigableMap. Entries are sorted by key in ascending order. Internally uses a Red-Black tree.

Null key not allowed. Null values are allowed.

Time complexity: O(log n) for put, get, remove.

```java
import java.util.TreeMap;

public class TreeMapComplete {
    public static void main(String[] args) {

        TreeMap<String, Integer> scores = new TreeMap<>();
        scores.put("Raj", 70);
        scores.put("Arman", 85);
        scores.put("Priya", 92);
        scores.put("Sneha", 78);

        System.out.println(scores);
        // {Arman=85, Priya=92, Raj=70, Sneha=78} - sorted by key

        // SortedMap methods
        System.out.println(scores.firstKey()); // Arman
        System.out.println(scores.lastKey());  // Sneha

        // NavigableMap methods
        System.out.println(scores.lowerKey("Raj"));   // Priya - strictly less
        System.out.println(scores.higherKey("Raj"));  // Sneha - strictly greater
        System.out.println(scores.floorKey("Raj"));   // Raj - less or equal
        System.out.println(scores.ceilingKey("Raj")); // Raj - greater or equal

        // headMap - entries with keys < given key
        System.out.println(scores.headMap("Raj")); // {Arman=85, Priya=92}

        // headMap inclusive
        System.out.println(scores.headMap("Raj", true)); // includes Raj

        // tailMap - entries with keys >= given key
        System.out.println(scores.tailMap("Raj")); // {Raj=70, Sneha=78}

        // subMap
        System.out.println(scores.subMap("Arman", "Raj")); // {Arman=85, Priya=92}

        // descending map
        System.out.println(scores.descendingMap());

        // pollFirstEntry and pollLastEntry - remove and return
        Map.Entry<String, Integer> first = scores.pollFirstEntry();
        System.out.println(first.getKey() + " = " + first.getValue()); // Arman = 85
    }
}
```

### Hashtable

Hashtable is the legacy synchronized version of HashMap. Created before Collections Framework.

Problems with Hashtable:

- All methods are synchronized - very slow
- Does not allow null key or null value
- Old API

What to use instead:

- Use HashMap for non-threaded code
- Use ConcurrentHashMap for thread-safe code

```java
import java.util.Hashtable;

Hashtable<String, Integer> table = new Hashtable<>();
table.put("A", 1);
table.put("B", 2);
// table.put(null, 3);    // NullPointerException
// table.put("C", null);  // NullPointerException

System.out.println(table.get("A")); // 1
System.out.println(table.size());   // 2
```

### WeakHashMap

WeakHashMap uses weak references for keys. If a key object has no other strong references, it becomes eligible for garbage collection and the entry is automatically removed from the map.

Use case: caches where entries should be removed when key is no longer used anywhere else.

```java
import java.util.WeakHashMap;

WeakHashMap<String, String> cache = new WeakHashMap<>();
String key = new String("myKey");
cache.put(key, "myValue");

System.out.println(cache.size()); // 1

key = null; // remove strong reference
System.gc(); // suggest garbage collection

// after GC, entry may be removed automatically
// cache.size() might be 0
```

### IdentityHashMap

IdentityHashMap uses == (reference equality) instead of .equals() for comparing keys. Two keys are considered equal only if they are the exact same object in memory.

```java
import java.util.IdentityHashMap;

IdentityHashMap<String, Integer> map = new IdentityHashMap<>();

String s1 = new String("hello");
String s2 = new String("hello"); // same content, different object

map.put(s1, 1);
map.put(s2, 2); // treated as DIFFERENT key because s1 != s2 (different references)

System.out.println(map.size()); // 2 (not 1)
```

### EnumMap

EnumMap is a Map where keys are enum constants. Much faster and more memory-efficient than HashMap when keys are enums.

```java
import java.util.EnumMap;

enum Day { MONDAY, TUESDAY, WEDNESDAY, THURSDAY, FRIDAY, SATURDAY, SUNDAY }

EnumMap<Day, String> schedule = new EnumMap<>(Day.class);
schedule.put(Day.MONDAY, "Java class");
schedule.put(Day.WEDNESDAY, "DSA practice");
schedule.put(Day.FRIDAY, "Project work");

System.out.println(schedule.get(Day.MONDAY)); // Java class
System.out.println(schedule); // keys always in enum declaration order
```

---

## PART 9 - GENERICS IN COLLECTIONS

When you write List<String>, the <String> is the generic type parameter. It tells the compiler what type of objects this collection will hold.

Without generics (old way, avoid):

```java
List list = new ArrayList(); // raw type - dangerous
list.add("hello");
list.add(123);        // no compile error - different types mixed
String s = (String) list.get(1); // ClassCastException at runtime!
```

With generics (always do this):

```java
List<String> list = new ArrayList<>();
list.add("hello");
// list.add(123);  // COMPILE ERROR - caught immediately
String s = list.get(0); // no cast needed
```

Generics with multiple type parameters (as in Map):

```java
Map<String, List<Integer>> studentMarks = new HashMap<>();
// key = student name, value = list of their marks
```

Wildcard types (you will encounter these in method parameters):

```java
// ? means any type
// ? extends Number means any subtype of Number
// ? super Integer means Integer or any supertype of Integer

public static double sum(List<? extends Number> list) {
    double total = 0;
    for (Number n : list) {
        total += n.doubleValue();
    }
    return total;
}
// This method accepts List<Integer>, List<Double>, List<Float> etc.
```

---

## PART 10 - NULL HANDLING IN COLLECTIONS

```
ArrayList        -> allows multiple nulls
LinkedList       -> allows multiple nulls
Vector           -> allows multiple nulls
Stack            -> allows nulls (but logically avoid)
HashSet          -> allows ONE null
LinkedHashSet    -> allows ONE null
TreeSet          -> does NOT allow null (NullPointerException - needs to compare)
HashMap          -> allows ONE null key, multiple null values
LinkedHashMap    -> allows ONE null key, multiple null values
TreeMap          -> does NOT allow null key (NullPointerException - needs to compare)
Hashtable        -> does NOT allow null key or null value
WeakHashMap      -> allows null key and null values
PriorityQueue    -> does NOT allow null
ArrayDeque       -> does NOT allow null
```

---

## PART 11 - ARRAYS vs COLLECTIONS

```
Array                                |  ArrayList
-------------------------------------|------------------------------
Fixed size at creation               |  Dynamic size
int[] arr = new int[5]               |  List<Integer> list = new ArrayList<>()
Primitives: int, char, double etc    |  Objects only: Integer, String, etc
arr[0] to access                     |  list.get(0) to access
arr.length for size                  |  list.size() for size
No built-in methods                  |  Rich methods
Less memory                          |  More memory
Multidimensional: int[][]            |  List<List<Integer>>
```

Converting:

```java
// Array to List
String[] arr = {"Arman", "Priya", "Raj"};
List<String> list = new ArrayList<>(Arrays.asList(arr));

// List to Array
String[] backToArr = list.toArray(new String[0]);

// Arrays.asList result is FIXED SIZE - cannot add or remove
List<String> fixed = Arrays.asList("A", "B", "C");
// fixed.add("D"); // UnsupportedOperationException

// Always wrap in ArrayList if you need to modify
List<String> modifiable = new ArrayList<>(Arrays.asList("A", "B", "C"));
modifiable.add("D"); // fine
```

---

## PART 12 - UNMODIFIABLE AND IMMUTABLE COLLECTIONS

```java
// Unmodifiable wrapper - original can still be changed, wrapper cannot
List<String> original = new ArrayList<>(Arrays.asList("A", "B", "C"));
List<String> unmodifiable = Collections.unmodifiableList(original);
// unmodifiable.add("D"); // UnsupportedOperationException
original.add("D"); // this works - original is still mutable
System.out.println(unmodifiable); // [A, B, C, D] - reflects original changes

// Immutable - Java 9+
List<String> immutable = List.of("A", "B", "C");
Set<String> immutableSet = Set.of("X", "Y", "Z");
Map<String, Integer> immutableMap = Map.of("a", 1, "b", 2);
// None of these can be modified at all

// Immutable copy - Java 10+
List<String> copy = List.copyOf(original); // independent copy, unmodifiable
```

---

## PART 13 - WHICH COLLECTION TO USE - COMPLETE GUIDE

```
Do you need key-value pairs?
    YES -> Use Map
        Need sorted keys?           -> TreeMap   O(log n)
        Need insertion order?       -> LinkedHashMap   O(1)
        Need LRU cache?             -> LinkedHashMap with access order
        Keys are enums?             -> EnumMap (fastest)
        Need thread safety?         -> ConcurrentHashMap
        Need weak references?       -> WeakHashMap
        Just fast access?           -> HashMap   O(1)

    NO -> Do you need unique elements only?
        YES -> Use Set
            Need sorted?            -> TreeSet   O(log n)
            Need insertion order?   -> LinkedHashSet   O(1)
            Just fast?              -> HashSet   O(1)

        NO -> Do you need FIFO or priority order?
            YES -> Use Queue
                Need priority?      -> PriorityQueue   O(log n)
                Need both ends?     -> ArrayDeque   O(1)
                Just FIFO?          -> ArrayDeque or LinkedList

            NO -> Is it LIFO (stack)?
                YES -> ArrayDeque (use push/pop/peek)

                NO -> Use List
                    Lots of index access?   -> ArrayList   O(1)
                    Lots of insert/delete?  -> LinkedList   O(1)
                    Need thread safety?     -> CopyOnWriteArrayList
                    Old code needs sync?    -> Vector or synchronizedList
```

Performance:

```
Operation               ArrayList   LinkedList   HashSet   HashMap   TreeSet   TreeMap
get by index            O(1)        O(n)         N/A       N/A       N/A       N/A
get by key              N/A         N/A          N/A       O(1)      N/A       O(log n)
add at end              O(1)        O(1)         O(1)      O(1)      O(log n)  O(log n)
add at beginning        O(n)        O(1)         N/A       N/A       N/A       N/A
add at middle           O(n)        O(1)*        N/A       N/A       N/A       N/A
contains / search       O(n)        O(n)         O(1)      O(1)      O(log n)  O(log n)
remove by index/key     O(n)        O(1)*        O(1)      O(1)      O(log n)  O(log n)

* O(1) only after finding the position. Finding position itself is O(n).
```

---

## PART 14 - THREAD SAFETY

Most java.util collections are NOT thread-safe. If two threads modify the same collection at the same time, data can get corrupted.

Thread-safe options:

Old synchronized wrappers (avoid in new code):

```java
List<String> syncList = Collections.synchronizedList(new ArrayList<>());
Set<String> syncSet = Collections.synchronizedSet(new HashSet<>());
Map<String, Integer> syncMap = Collections.synchronizedMap(new HashMap<>());

// Still need to synchronize iteration manually:
synchronized (syncList) {
    for (String s : syncList) {
        System.out.println(s);
    }
}
```

Better: java.util.concurrent classes (use these):

```java
import java.util.concurrent.*;

// Thread-safe ArrayList alternative
List<String> list = new CopyOnWriteArrayList<>();
// Every write creates a new copy - good for read-heavy, write-light scenarios

// Thread-safe HashMap alternative
Map<String, Integer> map = new ConcurrentHashMap<>();
// Divides the map into segments, locks only part of it - fast

// Thread-safe queue
Queue<String> queue = new ConcurrentLinkedQueue<>();

// Blocking queue - waits if empty/full
BlockingQueue<String> blockingQueue = new LinkedBlockingQueue<>(10);
```

Legacy thread-safe (avoid, exist for backward compatibility):

- Vector -- synchronized ArrayList
- Hashtable -- synchronized HashMap
- Stack -- synchronized stack (extends Vector)

---

## PART 15 - SORTING COMPLETE GUIDE

### Comparable - natural ordering

Implement in the class. Only one natural order per class.

```java
class Student implements Comparable<Student> {
    String name;
    int marks;

    Student(String name, int marks) {
        this.name = name;
        this.marks = marks;
    }

    public int compareTo(Student other) {
        // return negative: this comes before other
        // return positive: this comes after other
        // return zero: equal

        return this.marks - other.marks; // ascending by marks
    }
}

List<Student> students = new ArrayList<>();
// ... add students
Collections.sort(students); // uses compareTo
```

### Comparator - custom ordering

External to the class. Can have multiple comparators for different orderings.

```java
// Anonymous class
Comparator<Student> byName = new Comparator<Student>() {
    public int compare(Student a, Student b) {
        return a.name.compareTo(b.name);
    }
};

// Lambda - shorter
Comparator<Student> byMarks = (a, b) -> a.marks - b.marks;

// Using Comparator.comparing - cleanest
Comparator<Student> byNameClean = Comparator.comparing(s -> s.name);
Comparator<Student> byMarksClean = Comparator.comparingInt(s -> s.marks);

// Reversed
Comparator<Student> byMarksDesc = Comparator.comparingInt((Student s) -> s.marks).reversed();

// Chained - sort by marks, then by name if marks equal
Comparator<Student> combined = Comparator.comparingInt((Student s) -> s.marks)
                                          .thenComparing(s -> s.name);

students.sort(byName);
students.sort(byMarks);
students.sort(byMarksDesc);
students.sort(combined);
```

### Binary Search

List must be sorted before binary search. Returns index of element if found, negative number if not found.

```java
List<Integer> marks = new ArrayList<>(Arrays.asList(55, 62, 71, 85, 90));
Collections.sort(marks);

int index = Collections.binarySearch(marks, 71);
System.out.println(index); // 2

// Not found - returns (-(insertion point) - 1)
int notFound = Collections.binarySearch(marks, 70);
System.out.println(notFound); // -3 (would be inserted at index 2, so -(2)-1 = -3)

// Binary search with Comparator (for custom objects)
List<Student> students = new ArrayList<>();
// ... add students, must be sorted by same comparator first
Comparator<Student> byMarks = (a, b) -> a.marks - b.marks;
Collections.sort(students, byMarks);
int idx = Collections.binarySearch(students, new Student("", 85), byMarks);
```
