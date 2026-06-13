class Employee {
    private String employeeId;
    private String name;
    private int basicSalary;

    public Employee(String employeeId, String name, int basicSalary) {
        this.employeeId = employeeId;
        this.name = name;
        this.basicSalary = basicSalary;
    }

    public void getSalarySlip() {
        int hra = this.basicSalary * 20 / 100;
        int da = this.basicSalary * 10 / 100;
        int tax = (this.basicSalary + hra + da) * 10 / 100;
        int netSalary = (this.basicSalary + hra + da) - tax;

        System.out.println("Basic Salary: " + this.basicSalary);
        System.out.println("HRA: " + hra);
        System.out.println("DA: " + da);
        System.out.println("Tax: " + tax);
        System.out.println("Net Salary: " + netSalary);
    }
}

public class Ans3 {
    public static void main(String[] args) {
        Employee emp = new Employee("E001", "Arman", 30000);
        emp.getSalarySlip();
    }
}
