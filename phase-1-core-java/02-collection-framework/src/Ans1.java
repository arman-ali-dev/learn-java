import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;

public class Ans1 {
    public static void main(String[] args) {
        ArrayList<String> students = new ArrayList<>();

        students.add("Arman");
        students.add("Raj");
        students.add("Priya");
        students.add("Sneha");
        students.add("Rohan");
        

        System.out.println("Student at index 2: " + students.get(2));

        // Remove "Raj"
        students.remove("Raj");

        // Add element at index 1
        students.add(1, "Jhon");

        System.out.println("Size of students list: " + students.size());

        System.out.println("Is Priya present: " + students.contains("Priya"));

        Collections.sort(students);
        
        System.out.println("Sorted student list " + students);

        Collections.reverse(students);
        System.out.println("Reversed student list " + students);

        System.out.println("Last Name: " + Collections.max(students));
        
        // print all elements using foreach loop
        System.out.println("Using foreach: ");
        students.forEach((s) -> System.out.println(s));

        // Loop using index based for loop and print all
        System.out.println("Using for of: ");
        for (int i = 0; i < students.size(); i++) {
            System.out.println(students.get(i));
        }

        // print all elements using iterator
        Iterator<String> it = students.iterator();
        System.out.println("Using iterator: ");
        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
