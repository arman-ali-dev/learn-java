public class Ans1 {
    public static void main(String[] args) {
        int arr[] = { 3, 5, 1, 8, 2 };
        int n = arr.length, max = Integer.MIN_VALUE, min = Integer.MAX_VALUE;

        for (int elem : arr) {
            if (elem > max) {
                max = elem;
            }

            if (elem < min) {
                min = elem;
            }
        }

        System.out.println("Max: " + max);
        System.out.println("Min: " + min);
    }
}


// TC - O(n)
// SC - O(1)