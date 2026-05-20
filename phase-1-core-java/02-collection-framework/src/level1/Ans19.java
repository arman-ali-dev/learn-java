package level1;

import java.util.ArrayDeque;

public class Ans19 {
    public static void main(String[] args) {
        ArrayDeque<Character> ad = new ArrayDeque<>();

        ad.offerFirst('B');
        ad.offerLast('D');
        ad.offerFirst('A');
        ad.offerLast('E');

        System.out.println(ad);

        ad.pollFirst();
        ad.pollLast();

        System.out.println(ad);
    }
}
