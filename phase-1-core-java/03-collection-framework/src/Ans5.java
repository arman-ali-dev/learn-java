import java.util.Iterator;
import java.util.Stack;

public class Ans5 {
    public static void main(String[] args) {
        Stack<Integer> stack = new Stack<>();

        stack.add(10);
        stack.add(20);
        stack.add(30);
        stack.add(40);
        stack.add(50);


        System.out.println("Top Element: " + stack.peek());

        System.out.println(stack.pop());
        System.out.println(stack.pop());

        System.out.println("Top Element: " + stack.peek());


        System.out.println(stack.isEmpty());

        System.out.println(stack);
        System.out.println("Position of 10: " + stack.search(30));
    
        System.out.println("Remaining Elements: ");
        stack.forEach((x) -> System.out.println(x));

        int size = stack.size();
        int j = 0;
        while (j < size) {
            stack.pop();
            j++;
        }

        System.out.println(stack.isEmpty());

       
    }
}
