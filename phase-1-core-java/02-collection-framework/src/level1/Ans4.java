package level1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ans4 {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();

        nums.add(5);
        nums.add(2);
        nums.add(8);
        nums.add(1);
        nums.add(9);
        nums.add(3);

        System.out.println("Maximum Number: " + Collections.max(nums));
        System.out.println("Minimum Number: " + Collections.min(nums));
    }
}
