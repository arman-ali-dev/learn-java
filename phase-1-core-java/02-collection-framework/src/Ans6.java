import java.io.FileNotFoundException;
import java.io.FileReader;

interface Greeting {
    void greet(String name);
}

public class Ans6 {
    public static void main(String[] args) throws FileNotFoundException  {
        try {
            System.out.println("Hii");
            int n = 10/0;

            try {
                System.out.println("Helo");
            } catch (Exception e) {
                // TODO: handle exception
            }
        } catch (Exception e) {
System.out.println("Outer catch error");
        }
    }
}
