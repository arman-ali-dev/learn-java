package level1;

import java.util.Stack;

public class Ans14 {
    public static void main(String[] args) {
        Stack<Integer> s = new Stack<>();
        s.add(10);
        s.add(20);
        s.add(30);
        s.add(40);
        s.add(50);

        System.out.println(s.peek());

        s.pop();
        s.pop();


        System.out.println(s.isEmpty());
    }
}