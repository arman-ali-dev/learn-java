package level1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ans5 {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Priya");
        names.add("Arman");
        names.add("Sneha");
        names.add("Rohan");

        System.out.println("Before Reverse: " + names);

        Collections.reverse(names);

        System.out.println("After Reverse: " + names);

    }
}
