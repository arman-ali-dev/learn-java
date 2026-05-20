package level1;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public class Ans20 {
    public static void main(String[] args) {

        // Sets with common elements
        Set<Integer> s1 = new HashSet<>();
        s1.add(1);
        s1.add(2);
        s1.add(3);

        Set<Integer> s2 = new HashSet<>();
        s2.add(3);
        s2.add(4);
        s2.add(5);

        System.out.println(Collections.disjoint(s1, s2));

        // Sets without common elements
        Set<Integer> s3 = new HashSet<>();
        s3.add(10);
        s3.add(20);

        Set<Integer> s4 = new HashSet<>();
        s4.add(30);
        s4.add(40);

        System.out.println(Collections.disjoint(s3, s4));
    }
}