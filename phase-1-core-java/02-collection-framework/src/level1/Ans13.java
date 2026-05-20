package level1;

import java.util.LinkedList;

public class Ans13 {
    public static void main(String[] args) {
        LinkedList<Character> letters = new LinkedList<>();
        letters.addFirst('A');
        letters.addLast('Z');
        letters.add(1,'M');
        
        System.out.println("First Element: " + letters.getFirst());
        System.out.println("Last Element: " + letters.getLast());
        System.out.println(letters);
    }
}
