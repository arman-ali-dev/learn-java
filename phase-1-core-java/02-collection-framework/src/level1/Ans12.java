package level1;

import java.util.LinkedList;
import java.util.Queue;

public class Ans12 {
    public static void main(String[] args) {
        Queue<String> names = new LinkedList<>();

        names.offer("Arman");
        names.offer("Priya");
        names.offer("Rohan");
        names.offer("Sneha");

        System.out.println(names.peek());

        names.poll();
        System.out.println(names);
        names.poll();
        System.out.println(names);

    }
}
