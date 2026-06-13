import java.util.Collections;
import java.util.Enumeration;
import java.util.Vector;

public class ans4 {
    public static void main(String[] args) {
        Vector<String> products = new Vector<>();
        products.add("Mobile");
        products.add("Laptop");
        products.add("Smart Watch");
        products.add("Tablet");

        System.out.println("First Element: " + products.elementAt(0));

        System.out.println(products.getFirst());
        System.out.println(products.getLast());

        products.addElement("TV");

        products.remove("Tablet");

        System.out.println(products.capacity());
        System.out.println(products.size());

        System.out.println(products.contains("hello"));

        Collections.sort(products);

        System.out.println(products);

        Enumeration<String> e = products.elements();

        while (e.hasMoreElements()) {
            System.out.println(e.nextElement());
        }
    }
}
