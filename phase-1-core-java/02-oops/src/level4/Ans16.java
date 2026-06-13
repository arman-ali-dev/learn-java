package level4;

abstract class CoffeeMachine {
    private int waterLevel;
    private int beansLevel;

    abstract void brew();

    public void addWater(int ml) {
        waterLevel += ml;
    }

    public void addBeans(int grams) {
        beansLevel += grams;
    }

    public boolean checkIngredients() {
        if (waterLevel < 50 || beansLevel < 50) {
            System.out.println("Warning! low ingredients!");
            return false;
        }

        return true;
    }

    public void makeCoffee() {

        if (this.checkIngredients()) {

            this.brew();
        }
    }
}

class EspressoMachine extends CoffeeMachine {
    public void brew() {
        System.out.println("Brewing espresso: 30ml, strong shot");
    };
}

class CappuccinoMachine extends CoffeeMachine {
    private int milkLevel;

    public void addMilk(int ml) {
        this.milkLevel += ml;
    }

    public void brew() {
        System.out.println("Brewing cappuccino: espresso + steamed milk + foam");
    };
}

public class Ans16 {
    public static void main(String[] args) {
        CoffeeMachine machine = new EspressoMachine();
        machine.makeCoffee(); // shows low ingredients warning
        machine.addWater(500);
        machine.addBeans(100);
        machine.makeCoffee(); // brews successfully
    }
}
