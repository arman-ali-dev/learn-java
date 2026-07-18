class Solution {
    public int maxSubArray(int[] nums) {

        if (nums.length == 1)
            return nums[0];

        int maxSum = Integer.MIN_VALUE;
        int currSum = 0;

        for (int n : nums) {
            if (n > currSum + n) {
                currSum = n;
            } else {
                currSum += n;
            }

            maxSum = Math.max(currSum, maxSum);
        }

        return maxSum;
    }
}