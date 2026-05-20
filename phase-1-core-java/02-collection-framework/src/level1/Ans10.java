package level1;

import java.util.HashMap;
import java.util.Map;

public class Ans10 {
    public static void main(String[] args) {
        Map<String, Integer> students = new HashMap<>();
        students.put("Arman", 85);
        students.put("Arman", 95);

        // HashMap does not allow duplicate keys
        // The second put() replaces the old value (85) with 95.

        System.out.println(students.get("Arman"));
    }
}
