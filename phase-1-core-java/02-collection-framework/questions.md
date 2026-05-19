# Java Collection Framework - Complete Practice Questions

---

## LEVEL 1 - ABSOLUTE BASICS

Do not skip these. These build your base.

---

### Q1

Create an ArrayList of 5 student names. Print all using for-each loop. Print the element at index 2. Print total size of list.

---

### Q2

Create an ArrayList with numbers [10, 20, 30, 40, 50]. Remove element at index 1. Remove element with value 40. Print the final list.

Expected output: [10, 30, 50]

---

### Q3

Create an ArrayList with these numbers: [5, 3, 8, 1, 9, 2, 7]. Sort it using Collections.sort(). Print before and after sorting.

---

### Q4

Create an ArrayList with numbers [3, 7, 2, 9, 4, 6, 1]. Find the max and min using Collections.max() and Collections.min().

---

### Q5

Create an ArrayList with names ["Priya", "Arman", "Sneha", "Rohan"]. Use Collections.reverse() to reverse the order. Print before and after.

---

### Q6

Create an ArrayList with names ["Arman", "Priya", "Raj"]. Use Collections.swap() to swap the first and last element. Print result.

---

### Q7

Create a HashSet of Strings. Try to add these: ["apple", "banana", "apple", "mango", "banana"]. Print the size. It should be 3. Print the set.

---

### Q8

Create a HashSet of integers. Add 10, 20, 30, 40. Check if it contains 20. Check if it contains 99. Remove 30. Print the final set and size.

---

### Q9

Create a HashMap where keys are student names (String) and values are marks (Integer). Add 4 entries. Get value for one specific key. Try to get value for a key that does not exist. Print what you get.

---

### Q10

Create a HashMap. Put key "Arman" with value 85. Then put key "Arman" again with value 95. Get the value of "Arman". What prints and why? Write the reason as a comment.

---

### Q11

Create a HashMap<String, String> where key is a city name and value is the state it is in. Add 5 entries. Loop through all entries using entrySet() and print each city with its state.

---

### Q12

Create a Queue using LinkedList. Add 4 names. Use peek() to see the front without removing. Use poll() to remove front twice. Print the queue after each operation.

---

### Q13

Create a LinkedList. Use addFirst() to add "A". Use addLast() to add "Z". Use add() to add "M" in between. Use getFirst() and getLast(). Print all.

---

### Q14

Create a Stack. Push 5 numbers: 10, 20, 30, 40, 50. Use peek() to see top. Pop twice. Print the remaining stack. Check if stack is empty.

---

### Q15

Create a TreeSet of integers and add: 50, 10, 40, 20, 30. Print the TreeSet. Note the order. Use first() and last() to get smallest and largest.

---

### Q16

Create an ArrayList of integers: [1, 2, 3, 4, 5]. Get an Iterator from it. Use hasNext() and next() to loop and print each element manually.

---

### Q17

Create a Vector of Strings. Add 4 names. Use elementAt() to access by index. Use firstElement() and lastElement(). Print the capacity and size.

---

### Q18

Create a PriorityQueue of integers. Add 5, 1, 3, 2, 4. Poll elements one by one and print. What is the order of output?

---

### Q19

Create an ArrayDeque. Add "B" at front using offerFirst(). Add "D" at back using offerLast(). Add "A" at front again. Add "E" at back again. Print the deque. Remove from front and back once each.

---

### Q20

Create a HashSet of integers. Create another HashSet of integers. Check if they have any common element using Collections.disjoint(). Try with sets that do share elements and sets that do not.

---

## LEVEL 2 - BASIC PROGRAMS

---

### Q21

Create an ArrayList of integers. Print only even numbers from it.

Input: [1, 4, 7, 8, 12, 3, 6, 15, 20, 9]
Expected: 4 8 12 6 20

---

### Q22

Create an ArrayList of integers. Separate it into two lists: one with even numbers, one with odd numbers.

Input: [1, 4, 7, 8, 12, 3, 6, 15, 20, 9]
Expected: evens=[4, 8, 12, 6, 20], odds=[1, 7, 3, 15, 9]

---

### Q23

Find sum of all elements in an ArrayList without using any built-in sum method. Use a loop only.

Input: [10, 20, 30, 40, 50]
Expected: 150

---

### Q24

Find the second largest number in an ArrayList without sorting.

Input: [3, 1, 4, 1, 5, 9, 2, 6]
Expected: 6

---

### Q25

Count how many strings in a list start with the letter 'A' (case insensitive).

Input: ["Arman", "Priya", "Anita", "raj", "abhishek", "Sneha"]
Expected: 3

---

### Q26

Remove all duplicate elements from an ArrayList. The order of remaining elements should be maintained.

Input: ["Raj", "Amit", "Raj", "Sneha", "Amit", "Priya", "Raj"]
Expected: [Raj, Amit, Sneha, Priya]

Hint: Use LinkedHashSet.

---

### Q27

Count the frequency of each character in a String using HashMap.

Input: "programming"
Expected: {p=1, r=2, o=1, g=2, a=1, m=2, i=1, n=1}

---

### Q28

Count the frequency of each word in a String array using HashMap.

Input: ["apple", "mango", "apple", "banana", "mango", "apple"]
Expected: {apple=3, mango=2, banana=1}

---

### Q29

Find the most frequently occurring element in an integer array using HashMap.

Input: [1, 3, 2, 1, 4, 1, 3, 2, 1]
Expected: 1 appears 4 times

---

### Q30

Create a phone directory using HashMap. User can search a name and get the number. If not found, print "Contact not found".

---

### Q31

Check if two ArrayLists have any common elements using HashSet. Do not use nested loops.

list1 = [1, 2, 3, 4, 5]
list2 = [4, 5, 6, 7, 8]
Expected: Common = [4, 5]

---

### Q32

Find elements only in list1 (not in list2), only in list2 (not in list1), and common in both. Use HashSet operations retainAll and removeAll.

---

### Q33

Use a PriorityQueue of Strings where shorter strings come out first. Add words and poll them one by one.

---

### Q34

Use a Stack (ArrayDeque) to reverse a String. Push each character one by one. Pop each character to get reversed string.

Input: "Arman"
Expected: "namrA"

---

### Q35

Simulate a printer queue using LinkedList. Each print job has a name. Jobs are added and served in FIFO order. Print which job is being processed each time.

---

### Q36

Use Collections.binarySearch() to find a number in a sorted list. First sort the list, then search. Also try searching for a number that is not in the list and print what the return value means.

---

### Q37

Use Collections.frequency() to count how many times a specific element appears in a List.

---

### Q38

Use Collections.nCopies() to create a list of 7 copies of the string "hello". Print it. Then try to add another element to it.

---

### Q39

Create two LinkedLists. Use ListIterator to traverse the first one forward and then backward, printing each element.

---

### Q40

Write a program that safely removes all strings shorter than 3 characters from a List while iterating. Use Iterator so it does not throw ConcurrentModificationException.

Input: ["hi", "arman", "ok", "priya", "yo", "raj"]
Expected remaining: ["arman", "priya", "raj"]

---

## LEVEL 3 - INTERMEDIATE PROGRAMS

---

### Q41

Create a Student class with name and marks. Create an ArrayList of Student objects. Sort them in ascending order using Comparable. Print sorted list.

---

### Q42

Use Comparator to sort the same Student list in descending order by marks. If two students have same marks, sort alphabetically by name.

---

### Q43

Create an Employee class with name, department, salary. Use Comparator to sort first by department alphabetically, then within same department by salary descending.

---

### Q44

Library system using HashMap. Book title is key, available copies is value. Implement:

- addBook(String title, int copies)
- issueBook(String title) -- decrease copies, print "Not available" if 0
- returnBook(String title) -- increase copies
- searchBook(String title) -- print how many copies
- showAll() -- print all books with copies

---

### Q45

Voting system using HashMap. Candidate name is key, vote count is value. Implement:

- castVote(String candidate)
- showResults() -- print all with their counts
- getWinner() -- print candidate with most votes

---

### Q46

Group words by their length using HashMap<Integer, List<String>>.

Input: ["hi", "cat", "dog", "go", "elephant", "rat", "is"]
Expected: {2=[hi, go, is], 3=[cat, dog, rat], 8=[elephant]}

---

### Q47

Group students by their grade using HashMap<String, List<String>>. A=90+, B=75-89, C=60-74, F=below 60.

Input students and marks:
Arman=95, Priya=82, Raj=67, Sneha=91, Rohan=55, Anita=78

Expected: {A=[Arman, Sneha], B=[Priya, Anita], C=[Raj], F=[Rohan]}

---

### Q48

Balanced brackets checker using Stack (ArrayDeque). For every opening bracket push, for every closing bracket pop and verify it matches.

Valid: "(())", "{[()]}", "()[]{}"
Invalid: "(()", "{[}]", "(]"

---

### Q49

Find the first non-repeating character in a String using LinkedHashMap (to maintain insertion order).

Input: "aabbcddeff"
Expected: c (first character that appears only once)

---

### Q50

Group anagrams together using HashMap. Two words are anagrams if they have the same characters.

Input: ["eat", "tea", "tan", "ate", "nat", "bat"]
Expected: [[eat, tea, ate], [tan, nat], [bat]]

Hint: Sort each word's characters to form the key.

---

### Q51

Inventory system using HashMap<String, Product>. Product has name, price, quantity. Implement:

- addProduct(String id, Product p)
- removeProduct(String id)
- updateQuantity(String id, int qty)
- searchProduct(String id)
- getLowStock() -- all products with quantity <= 5
- getTotalValue() -- sum of price \* quantity for all products

---

### Q52

Given a HashMap<String, Integer> of student marks, find:

- Student with highest marks
- Student with lowest marks
- Average of all marks
- All students who scored above the average

---

### Q53

Find the top 3 most frequent elements in a list using HashMap.

Input: [1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 4]
Expected output: 3 appears 4 times, 1 appears 3 times, 2 appears 2 times

---

### Q54

Two Sum problem using HashMap.
Given an array and a target, find two numbers that add up to target. Return their indices.

Input: [2, 7, 11, 15], target=9
Expected: [0, 1] because 2+7=9

Do NOT use nested loops. Use HashMap for O(n) solution.
Hint: For each number, check if (target - number) exists in map.

---

### Q55

Hospital queue using PriorityQueue. Patients have urgency level (1=most urgent, 5=least urgent). Lower number means higher priority and should be treated first.

Patient class: name, urgencyLevel
Add patients in any order. Poll and treat them. Most urgent should come first.

---

### Q56

Merge two sorted ArrayLists into one sorted ArrayList WITHOUT using sort() after merging. Use the two-pointer technique.

list1 = [1, 3, 5, 7, 9]
list2 = [2, 4, 6, 8, 10]
Expected: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

---

### Q57

Use HashMap.compute() to increment marks of a student by 5. Use computeIfAbsent() to add a new student only if they do not already exist. Use computeIfPresent() to give bonus marks only to existing students.

---

### Q58

Use HashMap.merge() to count word frequency. merge() is a cleaner alternative to getOrDefault().

Show the difference between:
frequency.put(word, frequency.getOrDefault(word, 0) + 1)
and:
frequency.merge(word, 1, Integer::sum)

Both should produce same result.

---

### Q59

Implement a simple cache using LinkedHashMap with access order. Max size 3. When a 4th entry is added, oldest entry is automatically removed. Test by adding entries and getting entries, observing which gets evicted.

Override removeEldestEntry() method.

---

### Q60

Use TreeMap to maintain a leaderboard of players sorted by name. Use firstKey() and lastKey(). Use subMap() to get players whose names fall in a given alphabetical range.

---

## LEVEL 4 - ADVANCED PROGRAMS

---

### Q61

Find the length of the longest substring without repeating characters using LinkedHashSet as sliding window.

Input: "abcabcbb"
Expected: 3 (abc)

Input: "pwwkew"
Expected: 3 (wke)

---

### Q62

Implement a graph using HashMap<String, List<String>> where key is a city and value is list of directly connected cities. Implement:

- addCity(String city)
- addRoad(String c1, String c2) -- bidirectional
- getNeighbors(String city)
- isDirectlyConnected(String c1, String c2)

---

### Q63

Word location tracking. Given multiple lines of text, track which line each word appears on using HashMap<String, List<Integer>>. Key = word, value = list of line numbers.

Input:
Line 1: "the cat sat"
Line 2: "the cat ate"
Line 3: "the rat sat"

Expected: {the=[1,2,3], cat=[1,2], sat=[1,3], ate=[2], rat=[3]}

---

### Q64

Multi-level grouping. You have students with name, city, and grade. Group first by city, then by grade inside each city.

Result type: HashMap<String, HashMap<String, List<String>>>
Outer key: city, Inner key: grade, Value: list of names

---

### Q65

Implement your own simple HashMap called MyHashMap<K, V>. Use an array of LinkedLists internally (chaining for collision handling). Implement put(), get(), remove(), size(). Use key.hashCode() % capacity to find bucket index.

---

### Q66

Implement a task scheduler using PriorityQueue. Task has name, priority (1-5, lower is higher priority), and deadline (day number). Sort first by deadline, then by priority if deadlines equal.

---

### Q67

Given a list of transactions (sender, receiver, amount), calculate net balance for each person using HashMap. Net balance = total received - total sent. Print who owes money and who should receive money.

---

### Q68

Sliding window maximum. Given an integer array and window size k, find the maximum in every window of size k using ArrayDeque.

Input: [1, 3, -1, -3, 5, 3, 6, 7], k=3
Expected: [3, 3, 5, 5, 6, 7]

---

### Q69

Clone a HashMap two ways:

1. Shallow copy using new HashMap<>(original) or HashMap.clone()
2. Deep copy by manually copying each entry

Show that modifying the clone after shallow copy can affect the original (if values are objects). Show that deep copy prevents this.

---

### Q70

Sort a HashMap by value (not key). HashMap does not sort by value natively. Use entrySet(), convert to List, sort by value using Comparator.

Input: {Arman=95, Priya=82, Raj=71, Sneha=88}
Expected (descending by value): Arman=95, Sneha=88, Priya=82, Raj=71

---

## LEVEL 5 - OOP + COLLECTIONS COMBINED

---

### Q71

School management system.

Classes:

- Student: name, rollNo, List<Integer> marks, calculateAverage()
- Teacher: name, subject, List<Student> students
- School: HashMap<String, Teacher> (key=subject), List<Student> allStudents

Methods in School:

- enrollStudent(Student s)
- assignTeacher(Teacher t, String subject)
- getTopStudents(int n) -- top n by average, use Comparable or Comparator
- getSubjectReport(String subject) -- print all students with averages for that subject

---

### Q72

E-commerce cart system.

Classes:

- Product: id, name, price, int stock
- CartItem: Product product, int quantity
- Cart: HashMap<String, CartItem> items (key = product id)

Methods in Cart:

- addItem(Product p, int qty) -- if product already in cart, increase quantity
- removeItem(String productId)
- updateQuantity(String productId, int qty)
- getTotal() -- sum of price \* quantity
- checkout() -- check stock for each item, if enough: print bill and reduce stock, else print which items are out of stock

---

### Q73

Social network.

Classes:

- User: id, name
- SocialNetwork: HashMap<String, User> users, HashMap<String, Set<String>> friends (userId -> Set of friend ids)

Methods:

- addUser(User u)
- addFriendship(String id1, String id2) -- bidirectional
- getFriends(String userId)
- getMutualFriends(String id1, String id2) -- use Set intersection (retainAll)
- getSuggestedFriends(String userId) -- friends of friends who are not already friends with userId

---

### Q74

Bank system.

Classes:

- Account: accNo, holderName, double balance
- Transaction: fromAcc, toAcc, amount, type (CREDIT or DEBIT), timestamp
- Bank: HashMap<String, Account> accounts, List<Transaction> allTransactions

Methods:

- createAccount(Account a)
- deposit(String accNo, double amount)
- withdraw(String accNo, double amount) -- check balance before
- transfer(String from, String to, double amount)
- getMiniStatement(String accNo) -- last 5 transactions involving this account
- getTopCustomers(int n) -- top n by balance, use sorting

---

### Q75

Hospital management system.

Classes:

- Patient: patientId, name, age, List<String> diagnoses, List<String> medicines
- Doctor: doctorId, name, specialization, PriorityQueue<Patient> waitQueue (older patients first by age)
- Ward: wardNo, type, int capacity, List<Patient> patients
- Hospital: HashMap<String, Doctor> doctors, HashMap<String, Ward> wards, HashMap<String, Patient> allPatients

Methods in Hospital:

- admitPatient(Patient p, String wardNo) -- check capacity
- dischargePatient(String patientId, String wardNo)
- assignDoctor(String patientId, String doctorId) -- add to doctor's priority queue
- getDoctorWorkload() -- each doctor with number of patients waiting
- getWardStatus() -- each ward with current/total capacity
- getPatientRecord(String patientId) -- all diagnoses and medicines

---

## CONCEPT / THEORY QUESTIONS

Write answers as comments in code or plain text.

---

### C1

What is the difference between Iterable, Iterator, and ListIterator? Write one sentence for each.

---

### C2

What is a fail-fast iterator? What is a fail-safe iterator? Which collection gives you fail-fast and which gives you fail-safe? Write a small code to demonstrate ConcurrentModificationException.

---

### C3

Why does removing from a collection inside a for-each loop throw ConcurrentModificationException? How do you fix it? Show two ways to remove elements safely while iterating.

---

### C4

What is the difference between ArrayList and Vector? Why is Vector considered outdated?

---

### C5

Why is using Stack class not recommended in modern Java? What should you use instead and why?

---

### C6

LinkedList implements both List and Deque. What does this mean practically? Write code showing LinkedList being used as a List, as a Queue, and as a Stack.

---

### C7

What is the difference between HashSet, LinkedHashSet, and TreeSet in terms of ordering, speed, and null handling?

---

### C8

For HashSet to correctly detect duplicates in custom objects, which two methods must you override? What happens if you do not override them? Show with code.

---

### C9

What is the difference between HashMap, LinkedHashMap, and TreeMap in terms of ordering and performance?

---

### C10

HashMap does not allow null keys. True or false? What about null values? What about Hashtable? What about TreeMap?

---

### C11

What is the difference between HashMap and Hashtable? Name 3 differences.

---

### C12

When would you use WeakHashMap? What problem does it solve?

---

### C13

When would you use IdentityHashMap? How is it different from HashMap in key comparison?

---

### C14

When would you use EnumMap? Why is it faster than HashMap when keys are enums?

---

### C15

What is the difference between Comparable and Comparator? Give a real use case for each.

---

### C16

What does Collections.binarySearch() return if the element is not found? What does it mean?

---

### C17

What is the difference between poll() and remove() in Queue? Which should you prefer and why?

---

### C18

What is the difference between peek() and element() in Queue?

---

### C19

What is ArrayDeque? Why is it recommended over Stack class and also over LinkedList when used as a queue?

---

### C20

What is PriorityQueue? Does it follow FIFO? How is the order determined? How do you change from min-heap to max-heap?

---

### C21

What is the difference between Map.put() and Map.putIfAbsent()? Give a use case where putIfAbsent is more useful.

---

### C22

What does Map.compute() do? What does Map.merge() do? How are they different?

---

### C23

What is the difference between Collections.synchronizedList() and CopyOnWriteArrayList? When would you choose one over the other?

---

### C24

What is the difference between Arrays.asList() and new ArrayList<>(Arrays.asList())? What operation fails on Arrays.asList() result?

---

### C25

What is a raw type in generics? Why is it dangerous? Show an example that compiles but throws a ClassCastException at runtime when raw types are used.
