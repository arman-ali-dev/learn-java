package level3;

import java.util.ArrayList;
import java.util.List;

abstract class Notification {
    protected String recipient;
    protected String message;

    public Notification(String recipient, String messgae) {
        this.recipient = recipient;
        this.message = messgae;
    }

    abstract void send();

    protected void logNotification() {
        System.out.println(this.getClass().getSimpleName() + " sent to " + this.recipient);
    }
}

class EmailNotification extends Notification {
    private String subject;
    private String senderEmail;

    public EmailNotification(String recipient, String messgae, String subject, String senderEmail) {
        super(recipient, messgae);
        this.subject = subject;
        this.senderEmail = senderEmail;
    }

    public void send() {
        System.out.println("Sending email to " + this.recipient + " Subject: " + this.subject);
    }
}

class SMSNotification extends Notification {
    private String phoneNumber;

    public SMSNotification(String recipient, String messgae, String phoneNumber) {
        super(recipient, messgae);
        this.phoneNumber = phoneNumber;
    }

    public void send() {
        System.out.println("Sending SMS to " + this.phoneNumber + ": " + this.message);
    }
}

class PushNotification extends Notification {
    private String deviceId;
    private String appName;

    public PushNotification(String recipient, String messgae, String deviceId, String appName) {
        super(recipient, messgae);
        this.deviceId = deviceId;
        this.appName = appName;
    }

    public void send() {
        System.out.println("Push notification to " + this.deviceId + " via " + this.appName);
    }
}

class NotificationService {
    public void sendAll(List<Notification> notifications) {
        for (Notification notification : notifications) {
            notification.send();
                notification.logNotification();
        }
    }
}

public class Ans13 {
    public static void main(String[] args) {
        List<Notification> list = new ArrayList<>();
        list.add(new EmailNotification("Arman", "Welcome!", "Login Alert", "system@app.com"));
        list.add(new SMSNotification("Arman", "OTP: 1234", "9876543210"));
        list.add(new PushNotification("Arman", "New message!", "device123", "MyApp"));

        NotificationService service = new NotificationService();
        service.sendAll(list);
    }
}
