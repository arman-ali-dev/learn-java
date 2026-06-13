import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class Ans2 {
    public static void main(String[] args) {
        List<Integer> marks = new ArrayList<>(Arrays.asList(85, 62, 90, 71, 55, 88, 45, 78));

        // System.out.println("Element at index 3: " + marks.get(3));

        // marks.remove(Integer.valueOf(55));

        // System.out.println(marks);

        // marks.remove(0);

        // System.out.println(marks);

        // Collections.sort(marks);

        // System.out.println(marks);

        // System.out.println(Collections.binarySearch(marks, 85));

        // System.out.println("Max: " + Collections.max(marks));
        // System.out.println("Min: " + Collections.min(marks));

        // System.out.println(Collections.frequency(marks, 85));

        // List<Integer> newArr = new ArrayList<>();

        // newArr.addAll(marks);

        // System.out.println(newArr);

        marks.clear();
    
        System.out.println(marks.isEmpty());
    }
}
