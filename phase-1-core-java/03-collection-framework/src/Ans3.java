import java.util.Iterator;
import java.util.LinkedList;

public class Ans3 {
    public static void main(String[] args) {
        LinkedList<String> names = new LinkedList<>();
        names.add("Arman");
        names.add("Jhon");
        names.add("Elisa");

        names.addFirst("Ali");

        names.addLast("Maan");


        System.out.println("First: " + names.getFirst() + " Last: " + names.getLast());

        System.out.println(names.removeFirst());
        System.out.println(names.removeLast());

        names.add(1, "Jane");

        names.forEach((n) -> System.out.println(n));

        for (int i = 0; i < names.size(); i++) {
            System.out.println(names.get(i));
        }


        Iterator<String> it = names.iterator();

        while (it.hasNext()) {
            System.out.println(it.next());
        }
    }
}
