package level3;

abstract class FoodItem {
    protected String name;
    protected int price;

    public FoodItem(String name, int price) {
        this.name = name;
        this.price = price;
    }

    abstract double calculatePrice(int quantity);

    abstract String getCategory();

    protected void printBill(int quantity) {
        System.out.println(
                "Food Name: " + this.name + "\nFood Price: " + this.price + "\nFood Category: " + this.getCategory()
                        + "\nTotal: "
                        + this.calculatePrice(quantity) + "\n\n");

    }
}

class Pizza extends FoodItem {
    private String size;

    public Pizza(String name, int price, String size) {
        super(name, price);
        this.size = size;
    }

    public double calculatePrice(int quantity) {
        double price = this.size.equals("small") ? this.price
                : this.size.equals("medium") ? this.price * 1.5 : this.price * 2;

        return quantity * price;
    }

    public String getCategory() {
        return "Pizza";
    }

}

class Burger extends FoodItem {
    private boolean hasExtraCheese;

    public Burger(String name, int price, boolean hasExtraCheese) {
        super(name, price);
        this.hasExtraCheese = hasExtraCheese;
    }

    public double calculatePrice(int quantity) {
        double price = this.hasExtraCheese ? this.price + 20 : this.price;
        return quantity * price;
    }

    public String getCategory() {
        return "Burger";
    }
}

class Drink extends FoodItem {
    private int sizeML;

    public Drink(String name, int price, int sizeML) {
        super(name, price);
        this.sizeML = sizeML;
    }

    public double calculatePrice(int quantity) {
        double price = this.sizeML == 250 ? this.price
                : this.sizeML == 500 ? this.price * 1.8 : this.price * 2.5;
        return quantity * price;
    }

    public String getCategory() {
        return "Drink";
    }
}

public class Ans14 {
    public static void main(String[] args) {
        FoodItem[] order = {
                new Pizza("Margherita", 200, "large"),
                new Burger("Veg Burger", 150, true),
                new Drink("Cola", 60, 500)
        };

        double total = 0;
        for (FoodItem item : order) {
            item.printBill(2);
            total += item.calculatePrice(2);
        }

        System.out.println("Total Bill: Rs." + total);
    }
}
