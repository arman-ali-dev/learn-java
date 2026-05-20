package level1;

import java.util.Vector;

public class Ans17 {
    public static void main(String[] args) {
        Vector<String> v = new Vector<>();
        v.add("Arman");
        v.add("Priya");
        v.add("Sneha");
        v.add("Rohan");

        System.out.println(v.elementAt(1));
        System.out.println(v.firstElement());
        System.out.println(v.lastElement());
        System.out.println(v.capacity());
        System.out.println(v.size());
    }
}
