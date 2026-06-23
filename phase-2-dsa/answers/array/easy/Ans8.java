class Solution {
    public int missingNumber(int[] arr) {
        int n = arr.length;

        Arrays.sort(arr);

        int missingNum = n;

        for (int i = 0; i < n; i++) {
            if (arr[i] != i) {
                missingNum = i;
                break;
            }
        }

        return missingNum;
    }
}

class Solution {
    public int missingNumber(int[] nums) {
        int n = nums.length;
        int actualSum = 0;
        int expectedSum = n * (n + 1) / 2;

        for (int num : nums) {
            actualSum += num;
        }

        return expectedSum - actualSum;
    }
}