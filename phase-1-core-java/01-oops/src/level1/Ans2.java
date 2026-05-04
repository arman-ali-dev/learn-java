import java.util.ArrayList;
import java.util.List;

class BankAccount {
    private String accountNumber;
    private String holderName;
    private int balance;
    private List<String> statement;

    public BankAccount(String accountNumber, String holderName) {
        this.accountNumber = accountNumber;
        this.holderName = holderName;
        this.statement = new ArrayList<>();
    }

    public void deposit(int amount) {
        if (amount < 0) {
            System.out.println("Invalid Amount!");
        } else {
            this.balance += amount;
            statement.add(amount + " deposited");
        }
    }

    public void withdraw(int amount) {
        if (amount > this.balance) {
            System.out.println("Insufficient Funds!");
        } else {
            this.balance -= amount;
            statement.add(amount + " withdrawal");
        }
    }

    public void getStatement() {

        int size = statement.size() > 5 ? statement.size() - 5 : 0;

        for (int i = size; i < statement.size(); i++) {
            System.out.println(statement.get(i));
        }
    }
}

public class Ans2 {
    public static void main(String[] args) {
        BankAccount acc = new BankAccount("ACC001", "Arman");
        acc.deposit(10000);
        acc.withdraw(30000);
        acc.deposit(5000);
        acc.getStatement();
    }
}
