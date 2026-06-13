# OOP Practical Coding Questions

Solve all 20 questions. Each question covers a real scenario.
Do not copy paste. Write code yourself. That is the only way to learn.

---

## Level 1 — Encapsulation (5 Questions)

---

### Q1. Student Class

Create a Student class with these private fields: name, age, marks.

Rules for setters:

- age must be between 5 and 30
- marks must be between 0 and 100
- name cannot be empty

Add a getGrade() method:

- 90 and above = A
- 75 to 89 = B
- 60 to 74 = C
- Below 60 = F

Test:

```
Student s = new Student();
s.setName("Arman");
s.setAge(20);
s.setMarks(85);
System.out.println(s.getGrade()); // B
s.setAge(-5);    // should show error or ignore
s.setMarks(150); // should show error or ignore
```

---

### Q2. Bank Account

Create a BankAccount class with private fields: accountNumber, holderName, balance.

Rules:

- accountNumber should be set only once in constructor, no setter
- deposit() should reject negative amounts
- withdraw() should reject if amount is more than balance, print "Insufficient funds"
- getStatement() should print last 5 transactions

Test:

```
BankAccount acc = new BankAccount("ACC001", "Arman");
acc.deposit(10000);
acc.withdraw(3000);
acc.deposit(5000);
acc.withdraw(20000); // Insufficient funds
acc.getStatement();
```

---

### Q3. Employee Salary

Create an Employee class with private fields: employeeId, name, basicSalary.

Add a getSalarySlip() method that prints:

- Basic Salary
- HRA = 20% of basic
- DA = 10% of basic
- Tax = 10% of (basic + HRA + DA)
- Net Salary = basic + HRA + DA - Tax

Test:

```
Employee emp = new Employee("E001", "Arman", 30000);
emp.getSalarySlip();
```

---

### Q4. Library Book

Create a Book class with private fields: title, author, isbn, isAvailable.

Add these methods:

- checkOut() — if available set isAvailable to false, else print "Book already checked out"
- returnBook() — set isAvailable to true
- getBookInfo() — print all details with availability status

Test:

```
Book book = new Book("Clean Code", "Robert Martin", "ISBN123");
book.checkOut();
book.checkOut(); // Already checked out
book.returnBook();
book.checkOut(); // Works now
```

---

### Q5. Temperature Converter

Create a Temperature class. Store value internally always in Celsius.

Add these methods:

- setFahrenheit(double value)
- setKelvin(double value)
- getCelsius()
- getFahrenheit()
- getKelvin()

All conversions should happen inside the class. User should not do any math.

Test:

```
Temperature temp = new Temperature();
temp.setFahrenheit(98.6);
System.out.println(temp.getCelsius());  // 37.0
System.out.println(temp.getKelvin());   // 310.15
```

---

## Level 2 — Inheritance (5 Questions)

---

### Q6. Vehicle System

Create this hierarchy:

Vehicle (parent class)

- fields: brand, speed, fuelLevel
- refuel(amount) method
- abstract travel(distance) method

Car extends Vehicle

- mileage = 15 km per litre
- travel() should check fuel, print distance covered or "Not enough fuel"

Truck extends Vehicle

- mileage = 8 km per litre
- travel() same as above

Bike extends Vehicle

- mileage = 40 km per litre
- travel() same as above

Test:

```
Car car = new Car("Toyota", 120, 4);
car.travel(300); // Not enough fuel
car.refuel(40);
car.travel(300); // Works
```

---

### Q7. Shape Calculator

Create this hierarchy:

Shape (abstract class)

- field: color
- abstract area() method
- abstract perimeter() method
- printInfo() — print color, area, perimeter

Circle extends Shape

- field: radius

Rectangle extends Shape

- fields: length, width

Triangle extends Shape

- fields: sideA, sideB, sideC
- use Heron's formula for area

Test:

```
Shape[] shapes = {
    new Circle("Red", 7),
    new Rectangle("Blue", 5, 10),
    new Triangle("Green", 3, 4, 5)
};

for (Shape s : shapes) {
    s.printInfo();
}
```

---

### Q8. School System

Create this hierarchy:

Person (parent class)

- fields: name, age, email
- introduce() — prints "Hi, I am [name], [age] years old"

Student extends Person

- fields: studentId, grade
- list of subjects
- addSubject(subject) method
- getReport() method — prints all details

Teacher extends Person

- fields: teacherId, subject, salary
- teach() — prints "[name] is teaching [subject]"

Principal extends Person

- field: schoolName
- makeAnnouncement(message) — prints announcement

Test:

```
Student s = new Student("Arman", 20, "arman@email.com", "S001", "BCA");
s.addSubject("Java");
s.addSubject("DSA");
s.introduce();
s.getReport();

Teacher t = new Teacher("Mr. Sharma", 35, "sharma@email.com", "T001", "Java", 50000);
t.teach();
```

---

### Q9. Animal Sound System

Create this hierarchy:

Animal (abstract class)

- fields: name, age
- abstract makeSound() method
- eat() — "[name] is eating" (same for all animals)
- describe() — prints name, age, and calls makeSound()

Dog extends Animal

- field: breed
- makeSound() — "Woof Woof"

Cat extends Animal

- field: isIndoor
- makeSound() — "Meow"

Parrot extends Animal

- field: canTalk (boolean)
- makeSound() — "Hello!" if canTalk is true, else "Squawk"

Test:

```
Animal[] animals = {
    new Dog("Bruno", 3, "Labrador"),
    new Cat("Whiskers", 2, true),
    new Parrot("Polly", 1, true),
    new Parrot("Rocky", 4, false)
};

for (Animal a : animals) {
    a.describe();
}
```

---

### Q10. Product Warranty System

Create this hierarchy:

Product (abstract class)

- fields: productId, name, price
- abstract getWarranty() — returns months as int
- getProductDetails() — prints all fields and warranty

Laptop extends Product

- getWarranty() — returns 12

Mobile extends Product

- getWarranty() — returns 6

SmartTV extends Product

- getWarranty() — returns 24

Test:

```
Product[] products = {
    new Laptop("L001", "Dell XPS", 80000),
    new Mobile("M001", "iPhone", 90000),
    new SmartTV("T001", "Samsung", 60000)
};

for (Product p : products) {
    p.getProductDetails();
}
```

---

## Level 3 — Polymorphism (5 Questions)

---

### Q11. Payment System

Create this hierarchy:

PaymentMethod (abstract class)

- field: amount
- abstract processPayment() method
- abstract getPaymentType() method
- printReceipt() — prints type and amount

CreditCard extends PaymentMethod

- field: cardNumber (show only last 4 digits in output)
- processPayment() — "Processing credit card payment of Rs.[amount]"

UPI extends PaymentMethod

- field: upiId
- processPayment() — "Sending Rs.[amount] via UPI to [upiId]"

NetBanking extends PaymentMethod

- fields: bankName, accountNumber
- processPayment() — "Processing net banking via [bankName]"

Test:

```
PaymentMethod[] payments = {
    new CreditCard(5000, "1234567890123456"),
    new UPI(1500, "arman@upi"),
    new NetBanking(10000, "SBI", "9876543210")
};

for (PaymentMethod p : payments) {
    p.processPayment();
    p.printReceipt();
}
```

---

### Q12. Print Utility (Method Overloading)

Create a PrintUtility class with only overloaded print() methods:

- print(int number)
- print(double number)
- print(String text)
- print(boolean value)
- print(int[] array) — print all elements
- print(String[] array) — print all elements
- print(String text, int times) — print text that many times
- print(int a, int b, char operation) — do the math and print result (+, -, \*, /)

Test:

```
PrintUtility pu = new PrintUtility();
pu.print(42);
pu.print(3.14);
pu.print("Hello Java");
pu.print(true);
pu.print(new int[]{1, 2, 3, 4, 5});
pu.print("Arman", 3);
pu.print(10, 5, '+');
pu.print(10, 5, '/');
```

---

### Q13. Notification System

Create this hierarchy:

Notification (abstract class)

- fields: recipient, message
- abstract send() method
- logNotification() — prints "[type] sent to [recipient]"

EmailNotification extends Notification

- fields: subject, senderEmail
- send() — "Sending email to [recipient] | Subject: [subject]"

SMSNotification extends Notification

- field: phoneNumber
- send() — "Sending SMS to [phoneNumber]: [message]"

PushNotification extends Notification

- fields: deviceId, appName
- send() — "Push notification to [deviceId] via [appName]"

Also create a NotificationService class:

- sendAll(List notifications) — calls send() on each one

Test:

```
List<Notification> list = new ArrayList<>();
list.add(new EmailNotification("Arman", "Welcome!", "Login Alert", "system@app.com"));
list.add(new SMSNotification("Arman", "OTP: 1234", "9876543210"));
list.add(new PushNotification("Arman", "New message!", "device123", "MyApp"));

NotificationService service = new NotificationService();
service.sendAll(list);
```

---

### Q14. Food Ordering System

Create this hierarchy:

FoodItem (abstract class)

- fields: name, price
- abstract calculatePrice(int quantity) method
- abstract getCategory() method
- printBill(int quantity) — prints item details and total

Pizza extends FoodItem

- field: size (small, medium, large)
- calculatePrice() — small = base price, medium = 1.5x, large = 2x

Burger extends FoodItem

- field: hasExtraCheese (boolean)
- calculatePrice() — extra cheese adds Rs.20 per burger

Drink extends FoodItem

- field: sizeML (250, 500, 750)
- calculatePrice() — 250ml = base, 500ml = 1.8x, 750ml = 2.5x

Test:

```
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
```

---

### Q15. Sorting Utility (Overloading)

Create a SortUtility class with overloaded sort() methods:

- sort(int[] arr) — sort in ascending order
- sort(int[] arr, boolean ascending) — sort based on flag
- sort(String[] arr) — sort alphabetically
- sort(double[] arr) — sort in ascending order

After sorting, print the array.

Test:

```
SortUtility su = new SortUtility();
su.sort(new int[]{5, 2, 8, 1, 9});
su.sort(new int[]{5, 2, 8, 1, 9}, false);
su.sort(new String[]{"Banana", "Apple", "Mango"});
su.sort(new double[]{3.5, 1.2, 4.8, 2.1});
```

---

## Level 4 — Abstraction (5 Questions)

---

### Q16. Coffee Machine

Create this hierarchy:

CoffeeMachine (abstract class)

- private fields: waterLevel, beansLevel
- abstract brew() method
- addWater(int ml) method
- addBeans(int grams) method
- checkIngredients() — prints warning if water or beans are low
- makeCoffee() — first calls checkIngredients(), then brew()

EspressoMachine extends CoffeeMachine

- brew() — "Brewing espresso: 30ml, strong shot"

CappuccinoMachine extends CoffeeMachine

- extra field: milkLevel
- addMilk(int ml) method
- brew() — "Brewing cappuccino: espresso + steamed milk + foam"

Test:

```
CoffeeMachine machine = new EspressoMachine();
machine.makeCoffee(); // shows low ingredients warning
machine.addWater(500);
machine.addBeans(100);
machine.makeCoffee(); // brews successfully
```

---

### Q17. Report System with Multiple Interfaces

Create three interfaces:

Printable

- void print()

Saveable

- void save(String filename)

Exportable

- void exportToPDF()
- void exportToExcel()

Create a Report class that implements all three:

- fields: title, content, author, date
- Implement all methods with simple print statements

Test:

```
Report report = new Report("Sales Report", "Q1 data here", "Arman", "2026-01-01");
report.print();
report.save("sales.txt");
report.exportToPDF();
report.exportToExcel();
```

---

### Q18. ATM Machine

Create three interfaces:

Authenticatable

- boolean authenticate(String pin)

Transactable

- boolean deposit(double amount)
- boolean withdraw(double amount)
- double checkBalance()

Printable

- void printReceipt()

Create an ATM class that implements all three:

- fields: accountNumber, pin, balance
- After 3 wrong PIN attempts, lock the account
- withdraw() should only work after authentication
- printReceipt() shows last transaction details

Test:

```
ATM atm = new ATM("ACC001", "1234", 50000);
atm.authenticate("0000"); // wrong
atm.authenticate("0000"); // wrong
atm.authenticate("0000"); // account locked
atm.authenticate("1234"); // still locked

ATM atm2 = new ATM("ACC002", "5678", 20000);
atm2.authenticate("5678");
atm2.withdraw(5000);
atm2.printReceipt();
```

---

### Q19. Game Character System

Create this hierarchy:

Character (abstract class)

- fields: name, health, attackPower, defense
- abstract specialAbility() method
- attack(Character enemy) — reduce enemy health by (attackPower - enemy.defense)
- isAlive() — returns true if health > 0
- getStatus() — prints all fields

Warrior extends Character

- specialAbility() — "Shield Bash: deals 150% damage"

Mage extends Character

- field: manaPoints
- specialAbility() — "Fireball: deals 200% damage, costs 50 mana"

Archer extends Character

- field: arrowCount
- specialAbility() — "Rain of Arrows: hits 3 times at 80% damage each"

Test:

```
Character warrior = new Warrior("Thor", 200, 50, 30);
Character mage = new Mage("Gandalf", 120, 90, 10, 200);

warrior.getStatus();
mage.getStatus();

warrior.attack(mage);
mage.specialAbility();

warrior.getStatus();
mage.getStatus();
```

---

### Q20. Final Boss — Hospital Management System

This question uses all 4 OOP concepts together.

Create two interfaces:

Schedulable

- scheduleAppointment(String patientName, String date, String time)
- cancelAppointment(String patientName)

Billable

- generateBill()
- applyDiscount(double percent)

Create an abstract class:

Person

- fields: name, age, contactNumber

Create these classes:

Patient extends Person and implements Billable

- fields: patientId, disease, list of prescriptions
- addPrescription(medicine, dosage) method
- generateBill() — Rs.500 consultation + Rs.200 per prescription
- applyDiscount(percent) — reduce the total bill

Doctor extends Person and implements Schedulable

- fields: doctorId, specialization, list of appointments
- scheduleAppointment() — add to list and confirm
- cancelAppointment() — remove from list
- getDoctorInfo() — print all details

Receptionist extends Person

- field: receptionistId
- registerPatient(Patient p) — print patient details
- assignDoctor(Patient p, Doctor d) — print assignment confirmation

Test:

```
Doctor doc = new Doctor("Dr. Mehta", 45, "9999999999", "D001", "Cardiologist");
Patient patient = new Patient("Arman", 25, "8888888888", "P001", "Fever");

Receptionist r = new Receptionist("Sunita", 30, "7777777777", "R001");
r.registerPatient(patient);
r.assignDoctor(patient, doc);

doc.scheduleAppointment("Arman", "2026-05-10", "10:00 AM");
doc.getDoctorInfo();

patient.addPrescription("Paracetamol", "2 times a day");
patient.addPrescription("Vitamin C", "1 time a day");
patient.generateBill();
patient.applyDiscount(10);
patient.generateBill();
```

---

## How to Approach Each Question

1. Read the question fully first
2. Think about which classes and interfaces you need
3. Write the fields first, all private
4. Write the methods one by one
5. Test with the given test code
6. If stuck, re-read the notes.md file for that concept

---

## Progress Tracker

Level 1 — Encapsulation:

- Q1 Student
- Q2 Bank Account
- Q3 Employee Salary
- Q4 Library Book
- Q5 Temperature Converter

Level 2 — Inheritance:

- Q6 Vehicle System
- Q7 Shape Calculator
- Q8 School System
- Q9 Animal Sounds
- Q10 Product Warranty

Level 3 — Polymorphism:

- Q11 Payment System
- Q12 Print Utility
- Q13 Notification System
- Q14 Food Ordering
- Q15 Sorting Utility

Level 4 — Abstraction:

- Q16 Coffee Machine
- Q17 Report System
- Q18 ATM Machine
- Q19 Game Characters
- Q20 Hospital System (Final Boss)

If you finish all 20, no interviewer can confuse you on OOP.
