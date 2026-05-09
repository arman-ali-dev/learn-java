package level4;

interface Authenticatable {
    boolean authenticate(String pin);
}

interface Transactable {
    boolean deposit(double amount);

    boolean withdraw(double amount);

    double checkBalance();
}

interface Printable {
    void printReceipt();
}

class ATM implements Authenticatable, Transactable, Printable {
    private String accountNumber;
    private String pin;
    private double balance;
    private int pinAttempts;
    private String lastTransaction;
    private boolean isAuthenticated;

    public ATM(String accountNumber, String pin, double balance) {
        this.accountNumber = accountNumber;
        this.pin = pin;
        this.balance = balance;
        this.pinAttempts = 0;
        this.isAuthenticated = false;
    }

    public boolean authenticate(String pin) {
        if (this.pinAttempts == 3) {
            System.out.println("Still Locked!");
            return false;
        } else if (!pin.equals(this.pin)) {
            System.out.println("Wrong Pin!");
            this.pinAttempts++;

            if (this.pinAttempts == 3) {
                System.out.println("Account Locked!");
            }

            return false;
        }

        this.isAuthenticated = true;
        return true;
    }

    public boolean deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount!");
            return false;
        }

        if (!isAuthenticated) {
            System.out.println("Please authenticate first!");
            return false;
        }

        this.balance += amount;
        this.lastTransaction = amount + " deposited!";
        return true;
    }

    public boolean withdraw(double amount) {

        if (!isAuthenticated) {
            System.out.println("Please authenticate first!");
            return false;
        }

        if (amount > this.balance) {
            System.out.println("Insufficient balance!");
            return false;
        }

        this.balance -= amount;
        this.lastTransaction = amount + " withdrawal!";

        return true;
    }

    public double checkBalance() {
        return this.balance;
    }

    public void printReceipt() {
        System.out.println(this.lastTransaction);
    }
}

public class Ans18 {
    public static void main(String[] args) {
        ATM atm = new ATM("ACC001", "1234", 50000);
        atm.authenticate("0000"); // wrong
        atm.authenticate("0000"); // wrong
        atm.authenticate("0000"); // account locked
        atm.authenticate("1234"); // still locked

        ATM atm2 = new ATM("ACC002", "5678", 20000);
        atm2.authenticate("5678");
        atm2.withdraw(5000);
        atm2.printReceipt();
    }
}
