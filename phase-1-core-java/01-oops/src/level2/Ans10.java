package level2;

abstract class Product {
    public String productId;
    public String name;
    public int price;

    public Product(String productId, String name, int price) {
        this.productId = productId;
        this.name = name;
        this.price = price;
    }

    abstract int getWarranty();

    public void getProductDetails() {
        System.out.println("ProductId: " + this.productId + "\nName: " + this.name + "\nPrice: " + this.price
                + "\nWarranty: " + this.getWarranty());
    }
}

class Laptop extends Product {
    public Laptop(String productId, String name, int price) {
        super(productId, name, price);
    }

    public int getWarranty() {
        return 12;
    }
}

class Mobile extends Product {
    public Mobile(String productId, String name, int price) {
        super(productId, name, price);
    }

    public int getWarranty() {
        return 6;
    }
}

class SmartTV extends Product {
    public SmartTV(String productId, String name, int price) {
        super(productId, name, price);
    }

    public int getWarranty() {
        return 24;
    }
}

public class Ans10 {
    public static void main(String[] args) {
        Product[] products = {
                new Laptop("L001", "Dell XPS", 80000),
                new Mobile("M001", "iPhone", 90000),
                new SmartTV("T001", "Samsung", 60000)
        };

        for (Product p : products) {
            p.getProductDetails();
        }
    }
}
