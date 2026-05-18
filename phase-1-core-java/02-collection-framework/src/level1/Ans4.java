package level1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ans4 {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();

        names.add("Arman");
        names.add("Priya");
        names.add("Raj");
        names.add("Sneha");
        names.add("Rohan");

        System.out.println("Before Sorting: " + names);

        Collections.sort(names);

        System.out.println("After Sorting: " + names);
    }
}
