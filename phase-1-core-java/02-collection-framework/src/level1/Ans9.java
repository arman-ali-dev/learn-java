package level1;

import java.util.HashMap;
import java.util.Map;

public class Ans9 {
    public static void main(String[] args) {
        Map<String, Integer> students = new HashMap<>();

        students.put("Arman", 32);
        students.put("Priya", 95);
        students.put("Rohan", 65);
        students.put("Sneha", 98);

        System.out.println(students.get("Ali"));
    }
}
