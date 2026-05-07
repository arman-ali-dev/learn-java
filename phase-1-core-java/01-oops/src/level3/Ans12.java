package level3;

class PrintUtility {

    public void print(int number) {
        System.out.println(number);
    }

    public void print(double number) {
        System.out.println(number);
    }

    public void print(String text) {
        System.out.println(text);
    }

    public void print(boolean value) {
        System.out.println(value);
    }

    public void print(int[] array) {
        for (int n : array) {
            System.out.println(n);
        }
    }

    public void print(String[] array) {
        for (String s : array) {
            System.out.println(s);
        }
    }

    public void print(String text, int times) {
        for (int i = 0; i < times; i++) {
            System.out.println(text);
        }
    }

    public void print(int a, int b, char operation) {
        switch (operation) {
            case '+':
                System.out.println(a + b);
                break;
            case '-':
                System.out.println(a - b);
                break;
            case '*':
                System.out.println(a * b);
                break;
            case '/':
                if (b != 0) {
                    System.out.println(a / b);
                } else {
                    System.out.println("Cannot divide by zero");
                }
                break;
            default:
                break;
        }
    }
}

public class Ans12 {
    public static void main(String[] args) {
        PrintUtility pu = new PrintUtility();
        pu.print(42);
        pu.print(3.14);
        pu.print("Hello Java");
        pu.print(true);
        pu.print(new int[] { 1, 2, 3, 4, 5 });
        pu.print("Arman", 3);
        pu.print(10, 5, '+');
        pu.print(10, 5, '/');
    }
}