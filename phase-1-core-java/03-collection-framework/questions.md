# Java Collection Framework - Practice Questions

One question per topic. Each question covers all operations of that topic.
No logic building. Just practice using the collection.

---

## Q1 - ArrayList

Create an ArrayList of student names.

- Add 5 names
- Print the name at index 2
- Remove "Raj" from the list
- Insert a new name at index 1
- Print the size
- Check if "Priya" is in the list
- Sort the list and print
- Reverse the list and print
- Print the alphabetically last name using Collections.max()
- Loop using for-each and print all
- Loop using index based for loop and print all
- Get an Iterator and print all elements using it

---

## Q2 - ArrayList with Numbers

Create an ArrayList with these marks: [85, 62, 90, 71, 55, 88, 45, 78]

- Print element at index 3
- Remove value 55
- Remove element at index 0
- Sort the list
- Search for 78 using Collections.binarySearch() and print the index
- Print max and min
- Print how many times 85 appears using Collections.frequency()
- Copy all elements to a new ArrayList using addAll()
- Loop through the new list using for-each and print all
- Loop through using Iterator and print all
- Clear the original list and check if it is empty

---

## Q3 - LinkedList

Create a LinkedList of names.

- Add 3 names normally
- Add one name at the beginning using addFirst()
- Add one name at the end using addLast()
- Print the first and last element
- Remove the first element and print who got removed
- Remove the last element and print who got removed
- Add a new name at index 1
- Loop using for-each and print all
- Loop using index based for loop and print all
- Get an Iterator and print all using it

---

## Q4 - Vector

Create a Vector of product names.

- Add 4 products
- Print first element using elementAt(0)
- Print first and last using firstElement() and lastElement()
- Add one more product using addElement()
- Remove one product using removeElement()
- Print capacity() and size()
- Check if a product exists using contains()
- Sort the vector using Collections.sort()
- Loop using for-each and print all
- Loop using Enumeration and print all
- Loop using Iterator and print all

---

## Q5 - Stack

Create a Stack of numbers.

- Push 5 numbers: 10, 20, 30, 40, 50
- Print top element using peek() without removing
- Pop twice and print what got removed each time
- Check if stack is empty using empty()
- Find position of 10 using search()
- Loop using for-each and print remaining elements
- Loop using Iterator and print remaining elements
- Pop all remaining elements one by one in a while loop
- Check empty() again at the end

---

## Q6 - ArrayDeque as Queue

Create an ArrayDeque and use it as a Queue (FIFO - first in first out).

- Add 4 names using offerLast()
- See who is at front using peekFirst() without removing
- Remove 2 people using pollFirst() and print who got served
- Add 2 more people using offerLast()
- Print size
- Loop using for-each and print current queue
- Loop using Iterator and print current queue
- Keep polling everyone in a while loop until queue is empty
- Check isEmpty() at the end

---

## Q7 - ArrayDeque as Stack

Create an ArrayDeque and use it as a Stack (LIFO - last in first out).

- Push 4 items using push()
- See top item using peek()
- Pop 2 items and print what got removed
- Push 2 new items
- Print size
- Loop using for-each and print current stack
- Loop using Iterator and print current stack
- Pop everything in a while loop
- Check isEmpty() at the end

---

## Q8 - HashSet

Create a HashSet of city names.

- Add 5 cities but include 2 duplicates
- Print size - duplicates should not be counted
- Check if a city exists using contains()
- Remove one city
- Loop using for-each and print all
- Loop using Iterator and print all
- Create a second HashSet with some cities
- Merge both sets using addAll() and print (union)
- Find common cities using retainAll() and print (intersection)
- Find cities only in first set using removeAll() and print (difference)
- Check if two sets share no common element using Collections.disjoint()

---

## Q9 - LinkedHashSet

Create a LinkedHashSet of website names.

- Add 5 websites
- Try adding a duplicate - it will be ignored
- Print the set - insertion order should be maintained
- Check if a website exists using contains()
- Remove one website
- Loop using for-each and print all
- Loop using Iterator and print all
- Create an ArrayList that has duplicate names in it
- Convert that ArrayList to a LinkedHashSet to remove duplicates
- Convert it back to an ArrayList and print

---

## Q10 - TreeSet

Create a TreeSet with these scores: [85, 62, 90, 71, 55, 88, 45, 78]

- Print the set - it should be sorted automatically
- Print first() and last()
- Print floor(75) - largest value less than or equal to 75
- Print ceiling(75) - smallest value greater than or equal to 75
- Print headSet(71) - all values less than 71
- Print tailSet(71) - 71 and all values greater than it
- Print subSet(62, 88) - values from 62 up to but not including 88
- Loop using for-each and print all
- Loop using Iterator and print all
- Remove and print the smallest using pollFirst()
- Remove and print the largest using pollLast()
- Print the final set

---

## Q11 - HashMap

Create a HashMap of student names and their marks.

- Add 5 students
- Get marks of one student
- Get marks of a student who does not exist - print what you get
- Use getOrDefault() for a missing student with default value 0
- Check if a key exists using containsKey()
- Check if a value exists using containsValue()
- Update one student's marks using put() with the same key
- Try to update an existing student using putIfAbsent() - it should not change
- Add a new student using putIfAbsent() - it should work
- Remove one student
- Loop using entrySet() and print key and value
- Loop using keySet() and print only keys
- Loop using values() and print only values
- Loop using forEach with lambda and print all
- Print size and check isEmpty()

---

## Q12 - HashMap Frequency Count

You have this array: ["java", "python", "java", "c++", "python", "java", "javascript", "c++"]

- Count frequency of each language using HashMap and getOrDefault()
- Do the same using merge() method
- Print both results - they should match
- Loop using entrySet() and print each language with its count
- Find the most frequent language by looping through the map
- Put the data into a TreeMap and loop to print languages in alphabetical order

---

## Q13 - HashMap Grouping

You have these students and marks:
Arman=95, Priya=82, Raj=67, Sneha=91, Rohan=55, Anita=78, Vikram=88, Deepa=61

- Create a HashMap where key is grade and value is list of student names
- Grading: A = 90 and above, B = 75 to 89, C = 60 to 74, F = below 60
- Use putIfAbsent() to create a new list for a grade if it does not exist yet
- Add each student to their grade's list
- Loop using entrySet() and print each grade with its students
- Loop using keySet() and for each grade print the student list
- Print how many students got grade A

---

## Q14 - LinkedHashMap

Create a LinkedHashMap of products and prices.

- Add 5 products in a specific order
- Update price of one product
- Remove one product
- Check if a product exists using containsKey()
- Loop using entrySet() and print all - order should match insertion
- Loop using keySet() and print all keys
- Loop using forEach lambda and print all
- Now create a regular HashMap with the same data
- Loop through HashMap and print - notice order may differ

---

## Q15 - TreeMap

Create a TreeMap of employee names and salaries.

- Add 5 employees
- Print firstKey() and lastKey()
- Use lowerKey() to find the name just before "Raj" alphabetically
- Use higherKey() to find the name just after "Raj"
- Print headMap("Raj") - all entries before Raj
- Print tailMap("Raj") - Raj and all entries after
- Update one employee's salary using put()
- Remove one employee
- Loop using entrySet() and print all - should be alphabetically sorted
- Loop using keySet() and print all keys
- Loop using forEach lambda and print all

---

## Q16 - PriorityQueue

Create a PriorityQueue of integers.

- Add these numbers: 5, 1, 3, 2, 4
- Print peek() - should show smallest
- Loop using for-each and print all - note: this does NOT print in sorted order
- Loop using Iterator and print all
- Poll all elements one by one in a while loop and print each - this WILL be in sorted order
- Now create a new PriorityQueue with comparator (a, b) -> b - a
- Add same numbers again
- Poll all in a while loop and print - should come in descending order now

---

## Q17 - Iterator

Create an ArrayList: ["hi", "arman", "ok", "priya", "yo", "raj"]

- Get an Iterator and print all elements using hasNext() and next()
- Get a new Iterator and use it to remove all strings whose length is less than 3 using it.remove()
- Print the final list
- Now try the same removal using a regular for-each loop
- It will throw ConcurrentModificationException
- Write a comment explaining why Iterator is needed for safe removal
- Create a LinkedList with same data and use ListIterator to print forward
- Then use the same ListIterator to go backward and print

---

## Q18 - Sorting with Comparator

Create a class Student with name and marks fields.
Create an ArrayList of Student objects with 5 students.

- Sort by marks ascending using a lambda Comparator and print using for-each
- Sort by marks descending and print using for-each
- Sort alphabetically by name and print using for-each
- Sort by marks descending, and if marks are equal then sort by name alphabetically
- Print the final sorted list using for-each
- Loop using Iterator also once to print

---

## Q19 - Nested Collections

Create a HashMap where key is a student name and value is an ArrayList of their marks.

- Add 3 students, each with 3 marks
- Loop using entrySet() - for each student, loop their marks list and print all
- Loop using keySet() - get each student's list and print
- Write a method that takes a student name and returns their average marks
- Call that method for each student and print their averages
- Find and print the name of the student with the highest average

---

## Q20 - Mixed Practice

You are building a simple classroom system. Use whatever collections make sense.

- Store 5 student names in a collection where order matters and duplicates are allowed
- Store 5 unique subject names where order does not matter
- Store student name and their grade as key-value pairs
- Store student name and list of their subject marks as key-value pairs
- Print all students using for-each
- Print all students using Iterator
- Print all subjects using for-each
- Print each student with their grade using entrySet() loop
- Print each student with their average marks using entrySet() loop
