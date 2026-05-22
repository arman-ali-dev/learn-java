
interface Greeting {
    void greet(String name);
}

public class Ans6 {
    public static void main(String[] args) {
        Greeting g = new Greeting() {
            @Override
            public void greet(String name) {
                System.out.println("Hello " + name);
            }
        };

        g.greet("Armaan");
    }
}
