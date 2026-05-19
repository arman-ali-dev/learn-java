package level1;

import java.util.HashSet;
import java.util.Set;

public class ans8 {
    public static void main(String[] args) {
        Set<Integer> nums = new HashSet<>();
        nums.add(10);
        nums.add(20);
        nums.add(30);
        nums.add(40);

        System.out.println(nums.contains(20));

        System.out.println(nums.contains(99));

        nums.remove(30);

        System.out.println(nums);
        System.out.println(nums.size());
    }
}
