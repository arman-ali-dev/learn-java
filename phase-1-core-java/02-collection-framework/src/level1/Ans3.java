package level1;

import java.util.ArrayList;
import java.util.List;

public class Ans3 {
    public static void main(String[] args) {
          List<Integer> nums = new ArrayList<>();

        nums.add(10);
        nums.add(20);
        nums.add(30);
        nums.add(40);
        nums.add(50);

        System.out.println(nums.contains(7)); // false

        System.out.println(nums.indexOf(30)); // 2

        System.out.println(nums.size()); // 5

    }
}
