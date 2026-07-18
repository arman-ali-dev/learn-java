class Solution {
    public List<List<Integer>> threeSum(int[] nums) {
        Arrays.sort(nums);

        List<List<Integer>> ans = new ArrayList<>();
        int n = nums.length;

        for (int i = 0; i < n; i++) {

            if (i > 0 && nums[i] == nums[i-1]) {
                continue;
            }

            int left = i + 1;
            int right = n - 1;

            while (left < right) {
                int result = nums[i] + nums[left] + nums[right];

                if (result == 0) {
                    ans.add(new ArrayList<>(List.of(nums[i], nums[left], nums[right])));
                    left++;
                    right--;

                    while (left < right && nums[left] == nums[left-1]) {
                        left++;
                    } 

                } else if (result > 0) {
                    right--;
                } else {
                    left++;
                }
            }
        }


        return ans;
    }
}