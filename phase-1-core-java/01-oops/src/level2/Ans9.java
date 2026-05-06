package level2;

abstract class Animal {
    public String name;
    public int age;

    public Animal(String name, int age) {
        this.name = name;
        this.age = age;
    }

    public abstract void makeSound();

    public void eat() {
        System.out.println(this.name + " is eating");
    }

    public void describe() {
        System.out.println("Name: " + this.name + "\nAge: " + this.age );
        this.makeSound();
    }
}

class Dog extends Animal {
    private String breed;

    public Dog(String name, int age, String breed) {
        super(name, age);
        this.breed = breed;
    }

    public void makeSound() {
        System.out.println("Woof Woof");
    }
}

class Cat extends Animal {
    private boolean isIndoor;

    public Cat(String name, int age, boolean isIndoor) {
        super(name, age);
        this.isIndoor = isIndoor;
    }

    public void makeSound() {
        System.out.println("Meow");
    }
}

class Parrot extends Animal {
    private boolean canTalk;

    public Parrot(String name, int age, boolean canTalk) {
        super(name, age);
        this.canTalk = canTalk;
    }

    public void makeSound() {
        System.out.println(canTalk ? "Hello!" : "Squawk");
    }
}

public class Ans9 {
    public static void main(String[] args) {
        Animal[] animals = {
                new Dog("Bruno", 3, "Labrador"),
                new Cat("Whiskers", 2, true),
                new Parrot("Polly", 1, true),
                new Parrot("Rocky", 4, false)
        };

        for (Animal a : animals) {
            a.describe();
        }
    }
}
