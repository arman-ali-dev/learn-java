package level1;

import java.util.HashSet;
import java.util.Set;

public class Ans7 {
    public static void main(String[] args) {
        Set<String> fruits = new HashSet<>();
        fruits.add("apple");
        fruits.add("banana");
        fruits.add("apple");
        fruits.add("mango");
        fruits.add("banana");

        System.out.println(fruits.size());

        System.out.println(fruits);
    }
}
