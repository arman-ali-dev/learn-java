package level1;

import java.util.ArrayList;
import java.util.List;

public class Ans2 {
    public static void main(String[] args) {
        List<Integer> nums = new ArrayList<>();

        nums.add(10);
        nums.add(20);
        nums.add(30);
        nums.add(40);
        nums.add(50);


        nums.remove(1);

        nums.remove(Integer.valueOf(40));

        System.out.println(nums);
    }
}
