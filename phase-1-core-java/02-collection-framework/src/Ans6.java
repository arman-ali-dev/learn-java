

class Outer {
    int x = 10;


    class Inner {
        void show() {
            System.out.println("x from outer: " + x);
        }
    }
    
    static class StaticNested {
             void show() {
            System.out.println("Static nested class: ");
        }
    }
} 

public class Ans6 {
    public static void main(String[] args) {
        Outer outer = new Outer();

        System.out.println(outer.x);

        Outer.Inner inner = outer.new Inner();

        inner.show();
    }
}
