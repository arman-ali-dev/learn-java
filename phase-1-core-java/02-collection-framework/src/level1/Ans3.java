package level1;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Ans3 {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();

        nums.add(5);
        nums.add(3);
        nums.add(8);
        nums.add(1);
        nums.add(9);
        nums.add(2);
        nums.add(7);


        
        System.out.println("Before Sorting: " + nums);

        Collections.sort(nums);

        System.out.println("After Sorting: " + nums);
    }
}
