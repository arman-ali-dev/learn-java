package level3;

abstract class PaymentMethod {
    protected int amount;

    public PaymentMethod(int amount) {
        this.amount = amount;
    }

    abstract void processPayment();

    abstract String getPaymentType();

    public void printReceipt() {
        System.out.println("Payment Type: " + this.getPaymentType());
        System.out.println("Amount: " + this.amount);
    }
}

class CreditCard extends PaymentMethod {
    private String cardNumber;

    public CreditCard(int amount, String cardNumber) {
        super(amount);
        this.cardNumber = cardNumber;
    }

    public String getPaymentType() {
        return "Credit Card";
    }

    public void processPayment() {
        System.out.println("Processing credit card payment of Rs." + this.amount);
    }

    public void printReceipt() {
        super.printReceipt();

        String lastDigit = this.cardNumber.substring(this.cardNumber.length() - 4);
        System.out.println("Card: XXXX-XXXX-XXXX-" + lastDigit);
    }
}

class UPI extends PaymentMethod {
    private String upiId;

    public UPI(int amount, String upiId) {
        super(amount);
        this.upiId = upiId;
    }

    public String getPaymentType() {
        return "UPI";
    }

    public void processPayment() {
        System.out.println("Sending Rs." + this.amount + " via UPI to " + this.upiId);
    }
}

class NetBanking extends PaymentMethod {
    private String bankName;
    private String accountNumber;

    public NetBanking(int amount, String bankName, String accountNumber) {
        super(amount);
        this.bankName = bankName;
        this.accountNumber = accountNumber;
    }

    public String getPaymentType() {
        return "Net Banking";
    }

    public void processPayment() {
        System.out.println("Processing net banking via " + this.bankName);
    }

}

public class Ans11 {
    public static void main(String[] args) {
        PaymentMethod[] payments = {
                new CreditCard(5000, "1234567890123456"),
                new UPI(1500, "arman@upi"),
                new NetBanking(10000, "SBI", "9876543210")
        };

        for (PaymentMethod p : payments) {
            p.processPayment();
            p.printReceipt();
        }

        PaymentMethod upi = new UPI(1000, "arman@upi");
        upi.printReceipt();

        PaymentMethod creditCard = new CreditCard(2000, "1234567890123456");
        creditCard.printReceipt();
    }
}
