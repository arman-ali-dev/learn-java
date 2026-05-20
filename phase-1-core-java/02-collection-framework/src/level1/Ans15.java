package level1;

import java.util.TreeSet;

public class Ans15 {
    public static void main(String[] args) {
        TreeSet<Integer> s = new TreeSet<>();
        s.add(50);
        s.add(10);
        s.add(40);
        s.add(20);
        s.add(30);

        System.out.println(s);

        System.out.println(s.getFirst());
        System.out.println(s.getLast());
    }
}
