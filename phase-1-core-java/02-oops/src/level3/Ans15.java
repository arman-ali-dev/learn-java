package level3;

import java.util.Arrays;

class SortUtility {
    public void sort(int[] arr) {
        Arrays.sort(arr);
        System.out.println(Arrays.toString(arr));
    }

    public void sort(int[] arr, boolean ascending) {

        Arrays.sort(arr);

        if (!ascending) {
            int start = 0;
            int end = arr.length - 1;

            while (start < end) {
                int temp = arr[start];
                arr[start] = arr[end];
                arr[end] = temp;

                start++;
                end--;
            }
        }

        System.out.println(Arrays.toString(arr));
    };

    public void sort(String[] arr) {
        Arrays.sort(arr);

        System.out.println(Arrays.toString(arr));
    }

    public void sort(double[] arr) {
        Arrays.sort(arr);

        System.out.println(Arrays.toString(arr));
    }
}

public class Ans15 {
    public static void main(String[] args) {
        SortUtility su = new SortUtility();
        su.sort(new int[] { 5, 2, 8, 1, 9 });
        su.sort(new int[] { 5, 2, 8, 1, 9 }, false);
        su.sort(new String[] { "Banana", "Apple", "Mango" });
        su.sort(new double[] { 3.5, 1.2, 4.8, 2.1 });
    }
}
