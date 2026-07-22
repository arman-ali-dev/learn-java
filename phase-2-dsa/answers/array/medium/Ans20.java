class Solution {
    static ArrayList<Integer> subarraySum(int[] arr, int target) {
        ArrayList<Integer> ans = new ArrayList<>();
        
        int start = 0;
        int end = 0;
        int sum = 0;
        
        
        while (end < arr.length) {
            sum += arr[end];
            
            while (sum > target) {
                sum -= arr[start];
                start++;
            }
            
            if (sum == target) {
                ans.add(start + 1);
                ans.add(end + 1);
                return ans;
            }
            
            end++;
        }
        
        ans.add(-1);
        return ans;
    }
}
