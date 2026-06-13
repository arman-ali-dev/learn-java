package level2;

abstract class Vehicle {
    public String brand;
    public int speed;
    public int fuelLevel;

    public Vehicle(String brand, int speed, int fuelLevel) {
        this.brand = brand;
        this.speed = speed;
        this.fuelLevel = fuelLevel;
    }

    public void refuel(int amount) {
        this.fuelLevel += amount;
    }

    abstract int getMileage();

    public void travel(int distance) {
        int mileage = this.getMileage();
        int maxDistance = mileage * this.fuelLevel;

        if (maxDistance >= distance) {
            System.out.println("Covered " + distance + "km");
        } else {
            System.out.println("Not enough fuel!");
        }
    }
}

class Car extends Vehicle {

    public Car(String brand, int speed, int fuelLevel) {
        super(brand, speed, fuelLevel);
    }

    public int getMileage() {
        return 15;
    }
}

class Truck extends Vehicle {

    public Truck(String brand, int speed, int fuelLevel) {
        super(brand, speed, fuelLevel);
    }

    public int getMileage() {
        return 8;
    }
}

class Bike extends Vehicle {

    public Bike(String brand, int speed, int fuelLevel) {
        super(brand, speed, fuelLevel);
    }

    public int getMileage() {
        return 40;
    }
}

public class Ans6 {
    public static void main(String[] args) {
        Car car = new Car("Toyota", 120, 4);
        car.travel(300); // Not enough fuel
        car.refuel(40);
        car.travel(300); // Works
    }
}