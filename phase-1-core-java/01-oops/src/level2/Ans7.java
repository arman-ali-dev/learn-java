package level2;

abstract class Shape {
    public String color;

    abstract double area();

    abstract double perimeter();

    public void printInfo() {
        System.out.println(
                "Color: " + this.color + "\nParameter: " + this.perimeter() + "\nArea: " + this.area() + "\n\n");
    }
}

class Circle extends Shape {
    private int radius;

    public Circle(String color, int radius) {
        this.color = color;
        this.radius = radius;
    }

    public double area() {
        return 3.14 * this.radius * this.radius;
    }

    public double perimeter() {
        return 2 * 3.14 * this.radius;
    }
}

class Rectangle extends Shape {
    private int len;
    private int wid;

    public Rectangle(String color, int len, int wid) {
        this.color = color;
        this.len = len;
        this.wid = wid;
    }

    public double area() {
        return this.len * this.wid;
    }

    public double perimeter() {
        return 2 * (this.len + this.wid);
    }
}

class Triangle extends Shape {
    private int sideA;
    private int sideB;
    private int sideC;

    public Triangle(String color, int sideA, int sideB, int sideC) {
        this.color = color;
        this.sideA = sideA;
        this.sideB = sideB;
        this.sideC = sideC;
    }

    public double perimeter() {
        return this.sideA + this.sideB + this.sideC;
    }

    public double area() {
        double s = (sideA + sideB + sideC) / 2;
        double area = Math.sqrt(s * (s - sideA) * (s - sideB) * (s - sideC));

        return area;
    }

}

public class Ans7 {
    public static void main(String[] args) {
        Shape[] shapes = {
                new Circle("Red", 7),
                new Rectangle("Blue", 5, 10),
                new Triangle("Green", 3, 4, 5)
        };

        for (Shape s : shapes) {
            s.printInfo();
        }
    }
}
