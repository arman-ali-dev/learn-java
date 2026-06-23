import java.util.Arrays;

public class Main {
    public static void main(String[] args) {
        int[] arr = { 3, 0, 1 };
        int n = arr.length;

        Arrays.sort(arr);

        for (int i = 0; i <= n; i++) {
            if (arr[i] != i) {
                System.out.println(i);
                break;
            }
        }
    }
}