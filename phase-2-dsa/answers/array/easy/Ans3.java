public class Ans3 {

    public static int findSecondLargest(int[] arr) {

        if (arr.length == 1 || arr.length == 0) {
            return -1;
        }

        int max = Integer.MIN_VALUE, secMax = Integer.MIN_VALUE;

        for (int n : arr) {

            if (n == max) {
                continue;
            }

            if (n > max) {
                secMax = max;
                max = n;
            } else if (n > secMax) {
                secMax = n;
            }
        }

        return max == secMax ? -1 : secMax;
    }

    public static void main(String[] args) {
        int arr[] = { 5, 5, 4 };

        System.out.println(findSecondLargest(arr));
        ;

    }
}

// Time Complexity - O(n)
// Space Complexity - O(1)