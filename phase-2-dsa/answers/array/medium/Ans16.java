class Solution {
    public int majorityElement(int[] nums) {
        int majorElem = nums[0];
        int freq = 0;

        for (int n : nums) {
            if (majorElem == n) {
                freq++;
            } else {
                freq--;
            }

            if (freq == 0) {
                majorElem = n;
                freq++;
            }
        }

        return majorElem;
    }
}