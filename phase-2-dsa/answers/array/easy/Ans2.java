public class Ans2 {

    public static void reverseArray(int[] arr) {
        int n = arr.length;

        int j = n - 1;
        for (int i = 0; i < n / 2; i++) {
            int temp = arr[i];
            arr[i] = arr[j];
            arr[j] = temp;

            j--;
        }
    }
    public static void main(String[] args) {
        int arr[] = { 1, 2, 3, 4, 5 };
        
        reverseArray(arr);

        System.out.println("Reversed Array: ");
        
        for (int i : arr) {
            System.out.print(i + " ");
        }
    }
}


// Time Complexity - O(n)
// Space Complexity - O(1)