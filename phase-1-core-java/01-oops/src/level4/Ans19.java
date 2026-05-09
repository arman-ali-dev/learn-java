package level4;

abstract class Character {
    protected String name;
    protected int health;
    protected int attackPower;
    protected int defense;

    public Character(String name, int health, int attackPower, int defense) {
        this.name = name;
        this.health = health;
        this.attackPower = attackPower;
        this.defense = defense;
    }

    abstract void specialAbility();

    public void attack(Character enemy) {
        int damage = this.attackPower - enemy.defense;
        
        if (damage < 0) {
            damage = 0;
        }

        enemy.health -= damage;
    }

    public boolean isAlive() {
        return this.health > 0;
    }

    public void getStatus() {
        System.out.println(
                "Name: " + this.name + "\nHealth: " + this.health + "\nAttack Power: " + this.attackPower
                        + "\nDefense: " + this.defense + "\n");
    }
}

class Warrior extends Character {

    public Warrior(String name, int health, int attackPower, int defense) {
        super(name, health, attackPower, defense);
    }

    public void specialAbility() {
        System.out.println("Shield Bash: deals 150% damage");
    }
}

class Mage extends Character {
    private int manaPoints;

    public Mage(String name, int health, int attackPower, int defense, int manaPoints) {
        super(name, health, attackPower, defense);
        this.manaPoints = manaPoints;
    }

    public void specialAbility() {
        System.out.println("Fireball: deals 200% damage, costs 50 mana");
    }
}

class Archer extends Character {
    private int arrowCount;

    public Archer(String name, int health, int attackPower, int defense, int arrowCount) {
        super(name, health, attackPower, defense);
        this.arrowCount = arrowCount;
    }

    public void specialAbility() {
        System.out.println("Rain of Arrows: hits 3 times at 80% damage each");
    }
}

public class Ans19 {
    public static void main(String[] args) {
        Character warrior = new Warrior("Thor", 200, 50, 30);
        Character mage = new Mage("Gandalf", 120, 90, 10, 200);

        warrior.getStatus();
        mage.getStatus();

        warrior.attack(mage);
        mage.specialAbility();

        warrior.getStatus();
        mage.getStatus();
    }
}
