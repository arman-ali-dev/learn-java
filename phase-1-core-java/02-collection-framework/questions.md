# Java Collection Framework - Practice Questions

---

## LEVEL 1 - ABSOLUTE BASICS

These questions test if you understand the very basic operations. Do not skip these even if they look easy.

---

### Q1

Create an ArrayList of Strings. Add 5 student names to it. Print all names using a for-each loop. Then print the name at index 2.

---

### Q2

Create an ArrayList. Add these numbers: 10, 20, 30, 40, 50. Remove the element at index 1. Then remove the element with value 40. Print the final list.

Expected output: [10, 30, 50]

---

### Q3

Create an ArrayList of integers. Add 5 numbers. Check if the list contains the number 7. Print the index of number 30. Print the total size of the list.

---

### Q4

Create an ArrayList with names ["Arman", "Priya", "Raj", "Sneha", "Rohan"]. Sort the list alphabetically using Collections.sort(). Print before and after sorting.

---

### Q5

Create an ArrayList of integers: [5, 2, 8, 1, 9, 3]. Find the maximum and minimum using Collections.max() and Collections.min().

---

### Q6

Create a HashSet of Strings. Add these values: "apple", "banana", "apple", "mango", "banana". Print the size. The answer should be 3, not 5. Explain in a comment why.

---

### Q7

Create a HashSet. Add 10 to it. Add 20. Add 10 again. Check if it contains 10. Check if it contains 50. Print size.

---

### Q8

Create a HashMap<String, Integer> where keys are student names and values are their ages. Add at least 4 entries. Get the age of one specific student. Check if a key exists. Remove one entry.

---

### Q9

Create a HashMap. Put the key "score" with value 85. Then put the same key "score" with value 95. Get the value of "score". What will it print and why?

---

### Q10

Create a HashMap<String, String> where key is roll number and value is student name. Loop through all entries using entrySet() and print each roll number with its name.

---

### Q11

Create a Queue using LinkedList. Add 4 names to it. Use peek() to see the front. Use poll() to remove the front. Print the queue again.

---

### Q12

Create a LinkedList. Use addFirst() to add "A" and addLast() to add "Z". Add "M" normally with add(). Print getFirst() and getLast().

---

### Q13

Create a TreeSet of integers. Add numbers in this order: 50, 10, 40, 20, 30. Print the TreeSet. What order will they be in?

---

### Q14

Create an ArrayList of Strings. Add some names. Convert the entire list to a HashSet. What happens to any duplicates that were in the list?

---

### Q15

What is the difference between ArrayList and LinkedList? Write a program that creates both, adds 5 elements, and uses get(2) on both. Which one is faster at get() and why?

---

## LEVEL 2 - BASIC PROGRAMS

---

### Q16

Create an ArrayList of integers. Loop through it and print only the even numbers.

Input: [1, 4, 7, 8, 12, 3, 6, 15, 20, 9]
Expected output: 4, 8, 12, 6, 20

---

### Q17

Create an ArrayList of integers. Move all even numbers to one list and all odd numbers to another list. Print both lists.

Input: [1, 4, 7, 8, 12, 3, 6, 15, 20, 9]
Expected: evens=[4, 8, 12, 6, 20], odds=[1, 7, 3, 15, 9]

---

### Q18

Given an ArrayList of integers, find the sum of all elements without using any built-in sum method. Use a loop.

Input: [10, 20, 30, 40, 50]
Expected output: 150

---

### Q19

Given an ArrayList of integers, find the second largest number. Do not sort the list.

Input: [3, 1, 4, 1, 5, 9, 2, 6]
Expected output: 6

---

### Q20

Given an ArrayList of Strings, count how many strings start with the letter 'A' (case insensitive).

Input: ["Arman", "Priya", "Anita", "raj", "abhishek", "Sneha"]
Expected output: 3

---

### Q21

Remove all duplicate elements from an ArrayList while maintaining insertion order.

Input: ["Raj", "Amit", "Raj", "Sneha", "Amit", "Priya", "Raj"]
Expected output: [Raj, Amit, Sneha, Priya]

Hint: Use LinkedHashSet.

---

### Q22

Count the frequency of each word in a String array using HashMap.

Input: ["apple", "mango", "apple", "banana", "mango", "apple", "banana", "mango"]
Expected output: apple=3, mango=3, banana=2

---

### Q23

Find the most frequent element in an array using HashMap.

Input: [1, 3, 2, 1, 4, 1, 3, 2, 1]
Expected output: 1 appears 4 times

---

### Q24

Create a phone book using HashMap<String, String>. Keys are names, values are phone numbers. Write a method that searches by name and returns the number. If name not found, print "Contact not found".

---

### Q25

Use a HashMap to count the number of vowels and consonants in a given String. Print the count of each.

Input: "Hello World"
Expected: vowels=3, consonants=7 (ignore spaces)

---

### Q26

Create a Queue. Write a simulation where 5 customers enter the queue one by one, and then they are served one by one (polled). Print a message when each customer enters and when each is served.

---

### Q27

Use a PriorityQueue of integers. Add these numbers: 5, 1, 3, 2, 4. Poll them one by one and print. What order do they come out in?

---

### Q28

Create a TreeSet of Strings. Add 5 city names. Print them. They should appear alphabetically. Then use first() and last() to get the alphabetically first and last city.

---

### Q29

Check if two lists have any common elements using HashSet. Do not use nested loops.

list1 = [1, 2, 3, 4, 5]
list2 = [4, 5, 6, 7, 8]
Expected: Common elements are [4, 5]

---

### Q30

Given two ArrayLists, create three results: union (all elements from both), intersection (common elements), and difference (in list1 but not list2). Use HashSet operations.

---

## LEVEL 3 - INTERMEDIATE PROGRAMS

---

### Q31

Create a Student class with fields name and marks. Create an ArrayList of Student objects. Sort them in ascending order by marks using Comparable. Print all students after sorting.

---

### Q32

Same as Q31, but now sort in descending order by marks. If two students have same marks, sort them alphabetically by name.

---

### Q33

Create an Employee class with fields name, department, and salary. Use Comparator to:

- First sort by department alphabetically
- Within same department, sort by salary descending
  Print the sorted list.

---

### Q34

Library management using HashMap. Create a class with a HashMap<String, Integer> where key is book title and value is number of available copies.

Methods needed:

- addBook(String title, int copies)
- issueBook(String title) -- decrease copies by 1, print "Not available" if 0
- returnBook(String title) -- increase copies by 1
- searchBook(String title) -- print copies available
- showAllBooks() -- print all books and their copies

Test all methods.

---

### Q35

Implement a simple voting system using HashMap<String, Integer>. Keys are candidate names, values are vote counts.

Methods needed:

- castVote(String candidate) -- add 1 to that candidate's count, create entry if not exists
- getResults() -- print all candidates with their vote counts
- getWinner() -- print the candidate with most votes

---

### Q36

You have a List<String> of words. Group them by their length using HashMap<Integer, List<String>>. Key is the word length, value is list of words with that length.

Input: ["hi", "cat", "dog", "go", "elephant", "rat", "is"]
Expected: {2=[hi, go, is], 3=[cat, dog, rat], 8=[elephant]}

---

### Q37

Use a Stack (using ArrayDeque) to check if a string has balanced brackets. For every opening bracket you push, for every closing bracket you pop and check if it matches.

Valid: "(())", "{[()]}", "()[]{}"
Invalid: "(()", "{[}]", "(]"

---

### Q38

Use a Queue to simulate a printer queue. Each print job has a name and number of pages. Jobs are processed in order (FIFO). Print each job as it is processed.

Create a PrintJob class with fields jobName and pages.

---

### Q39

Create an inventory system using HashMap<String, Product> where key is product ID and Product has fields name, price, quantity.

Methods needed:

- addProduct(String id, Product p)
- removeProduct(String id)
- updateQuantity(String id, int newQty)
- searchProduct(String id) -- print product details
- getLowStock() -- print all products where quantity < 5
- getTotalValue() -- return sum of (price \* quantity) for all products

---

### Q40

Given a HashMap<String, Integer> of student marks, find:

- Student with highest marks
- Student with lowest marks
- Average of all marks
- All students who scored above average

---

### Q41

Use two HashMaps: one stores employee name and department, another stores department name and manager name. Given an employee name, find their manager by connecting the two maps.

employees = {Arman: Engineering, Priya: HR, Raj: Engineering}
managers = {Engineering: Dr. Mehta, HR: Ms. Sharma}

Given "Arman", output: "Arman's manager is Dr. Mehta"

---

### Q42

Create a frequency map of characters in a String. Then find the first non-repeating character.

Input: "aabbcddeff"
Expected: first non-repeating is 'c'

Hint: Use LinkedHashMap to maintain insertion order while counting.

---

### Q43

Given a list of Strings, group anagrams together using HashMap<String, List<String>>. Two words are anagrams if they have the same characters.

Input: ["eat", "tea", "tan", "ate", "nat", "bat"]
Expected: [[eat, tea, ate], [tan, nat], [bat]]

Hint: Sort each word's characters to use as the key.

---

### Q44

Implement a simple cache using LinkedHashMap. The cache has a max size. When it is full and a new entry is added, the oldest entry is removed (LRU - Least Recently Used behavior).

Override removeEldestEntry() in LinkedHashMap.

---

### Q45

Use PriorityQueue to implement a hospital queue where patients have urgency levels (1 = most urgent, 5 = least urgent). Higher urgency patients should be treated first regardless of arrival order.

Patient class: name, urgencyLevel
Print the order in which patients are treated.

---

## LEVEL 4 - ADVANCED PROGRAMS

---

### Q46

Two Sum Problem using HashMap.
Given an array of integers and a target sum, find two numbers that add up to the target. Return their indices. Use HashMap for O(n) solution.

Input: [2, 7, 11, 15], target = 9
Expected: indices [0, 1] because 2 + 7 = 9

---

### Q47

Implement a word frequency counter that also tracks which line each word appears on.

Use HashMap<String, List<Integer>> where key is the word and value is list of line numbers where that word appears.

Input:
Line 1: "the cat sat"
Line 2: "the cat ate"
Line 3: "the rat sat"

Expected: {the=[1,2,3], cat=[1,2], sat=[1,3], ate=[2], rat=[3]}

---

### Q48

Graph representation using HashMap<String, List<String>>. Each key is a city, value is list of cities it is directly connected to.

Implement:

- addCity(String city)
- addRoad(String city1, String city2) -- adds both directions
- getNeighbors(String city) -- print all connected cities
- isConnected(String city1, String city2) -- true if directly connected

---

### Q49

Find the top 3 most frequent elements in a list using HashMap and a sorted approach.

Input: [1, 1, 1, 2, 2, 3, 3, 3, 3, 4, 4]
Expected: [(3, 4 times), (1, 3 times), (2, 2 times)]

---

### Q50

Implement a multi-level grouping. You have students with name, city, and grade. Group them first by city, then by grade within each city.

Result type: HashMap<String, HashMap<String, List<String>>>
Key of outer map: city
Key of inner map: grade
Value: list of student names

---

### Q51

Merge two sorted ArrayLists into one sorted ArrayList without using sort() after merging. Use the two-pointer technique.

list1 = [1, 3, 5, 7, 9]
list2 = [2, 4, 6, 8, 10]
Expected: [1, 2, 3, 4, 5, 6, 7, 8, 9, 10]

---

### Q52

Implement your own version of a simple HashMap. Create a class MyHashMap<K, V> with:

- An array of LinkedLists as the internal structure (for chaining)
- put(K key, V value)
- get(K key)
- remove(K key)
- size()

Use key.hashCode() % capacity to find the bucket index.

---

### Q53

Given a list of transactions where each transaction has sender, receiver, and amount, use HashMap to:

- Calculate net balance for each person (received - sent)
- Find who owes money (negative balance)
- Find who should receive money (positive balance)

---

### Q54

Implement a task scheduler using PriorityQueue. Each task has a name, priority (1-5), and deadline (integer day number). Tasks with earlier deadlines should be processed first. If deadlines are equal, process by higher priority.

---

### Q55

Given a String, find the length of the longest substring without repeating characters. Use LinkedHashSet to track current window of characters.

Input: "abcabcbb"
Expected: 3 (substring "abc")

Input: "bbbbb"
Expected: 1

---

## LEVEL 5 - OOP + COLLECTIONS COMBINED

These are the hardest. They combine everything you know.

---

### Q56

Create a School management system:

Classes needed:

- Student: name, rollNo, marks (ArrayList<Integer>), calculateAverage()
- Teacher: name, subject, List<Student> students they teach
- School: HashMap<String, Teacher> (key = subject), List<Student> allStudents

Methods in School:

- enrollStudent(Student s)
- assignTeacher(Teacher t, String subject)
- getTopStudents(int n) -- top n students by average marks
- getSubjectReport(String subject) -- print all students of that subject with their averages

---

### Q57

Create an e-commerce cart system:

Classes:

- Product: id, name, price, stock
- CartItem: product, quantity
- Cart: HashMap<String, CartItem> (key = product id)

Methods in Cart:

- addItem(Product p, int qty) -- if product already in cart, increase quantity
- removeItem(String productId)
- updateQuantity(String productId, int newQty)
- getTotal() -- sum of (price \* quantity) for all items
- checkout() -- verify stock for each item, print bill, deduct from stock

---

### Q58

Create a social network:

Classes:

- User: name, id
- SocialNetwork: HashMap<String, User> allUsers, HashMap<String, Set<String>> friendships (userId -> Set of friend ids)

Methods:

- addUser(User u)
- addFriendship(String userId1, String userId2) -- both directions
- getFriends(String userId) -- print all friends
- getMutualFriends(String userId1, String userId2) -- friends of both (use Set intersection)
- getSuggestedFriends(String userId) -- friends of friends who are not already friends

---

### Q59

Create a bank system:

Classes:

- Account: accountNo, holderName, balance
- Transaction: fromAcc, toAcc, amount, type (CREDIT/DEBIT)
- Bank: HashMap<String, Account> accounts, List<Transaction> transactionHistory

Methods:

- createAccount(Account a)
- deposit(String accNo, double amount)
- withdraw(String accNo, double amount) -- check balance first
- transfer(String fromAcc, String toAcc, double amount)
- getMiniStatement(String accNo) -- last 5 transactions for this account
- getRichestCustomers(int n) -- top n by balance using sorting

---

### Q60

Create a Hospital management system combining everything:

Classes:

- Patient: patientId, name, age, List<String> diagnoses, List<String> medicines
- Doctor: doctorId, name, specialization, PriorityQueue<Patient> waitingQueue (by age, older patients first)
- Ward: wardNo, wardType, List<Patient> currentPatients, int capacity
- Hospital: HashMap<String, Doctor> doctors, HashMap<String, Ward> wards, HashMap<String, Patient> patients

Methods in Hospital:

- admitPatient(Patient p, String wardNo) -- check capacity first
- dischargePatient(String patientId, String wardNo)
- assignDoctor(String patientId, String doctorId) -- add to doctor's queue
- getDoctorWorkload() -- print each doctor with number of patients waiting
- getWardStatus() -- print each ward with occupied/total capacity
- getPatientHistory(String patientId) -- print all diagnoses and medicines

---

## CONCEPT QUESTIONS - WRITE ANSWERS IN COMMENTS

These test your understanding, not just your coding ability.

---

### C1

Explain the difference between ArrayList and LinkedList. In what situation would you choose LinkedList over ArrayList?

---

### C2

What happens when you add a duplicate to a HashSet? Write a small code snippet to prove your answer.

---

### C3

What is the difference between HashMap and Hashtable? Why is Hashtable considered outdated?

---

### C4

Why does TreeSet throw NullPointerException when you try to add null? What would happen in HashSet if you add null twice?

---

### C5

What is the difference between poll() and remove() in a Queue? Which one should you prefer and why?

---

### C6

Why is Map not a part of the Collection interface? Think about how Map works differently from List, Set, and Queue.

---

### C7

What is the difference between Comparable and Comparator? When would you use one over the other?

---

### C8

You have a List and you want to remove elements inside a for-each loop. What happens? How do you fix it?

---

### C9

What does Collections (with s) provide that Collection (without s) does not? Name at least 5 methods from Collections class.

---

### C10

If you use a custom class as a key in HashMap, what two methods must you override and why? What happens if you don't override them?

---

### C11

What is the difference between HashMap, LinkedHashMap, and TreeMap? Give a real world example of when you would use each.

---

### C12

What is Iterator and when should you use it instead of for-each loop? What exception does for-each throw if you modify the collection during iteration?

---

### C13

Explain the difference between peek(), poll(), and element() methods in Queue.

---

### C14

What is the difference between List.of() and new ArrayList<>()? What happens if you try to add to a List.of() list?

---

### C15

Compare HashSet, LinkedHashSet, and TreeSet on three things: order, performance, and null handling.
