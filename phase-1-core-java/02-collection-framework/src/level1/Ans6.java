package level1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ans6 {
    public static void main(String[] args) {
        List<String> names = new ArrayList<>();
        names.add("Arman");
        names.add("Priya");
        names.add("Raj");

        Collections.swap(names, 0, 2);

        System.out.println(names);
    }
}
