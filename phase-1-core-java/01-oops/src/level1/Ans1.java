
class Student {
    private String name;
    private int age;
    private int marks;

    public void setAge(int age) {
        if (age < 5 || age > 30) {
            System.out.println("Invalid age. Age should be between 5 and 30.");
        } else {
            this.age = age;
        }
    }

    public void setName(String name) {
        if (name.isEmpty()) {
            System.out.println("Name cannot be empty.");
        } else {
            this.name = name;
        }
    }

    public void setMarks(int marks) {
        if (marks < 0 || marks > 100) {
            System.out.println("Invalid marks. Marks should be between 0 and 100.");
        } else {
            this.marks = marks;
        }
    }

    public char getGrade() {

        if (this.marks >= 90) {
            return 'A';
        } else if (this.marks <= 89 && this.marks >= 75) {
            return 'B';
        } else if (this.marks < 75 && this.marks >= 60) {
            return 'C';
        } else {
            return 'F';
        }
    }

}

public class Ans1 {
    public static void main(String[] args) {
        Student s = new Student();
        s.setName("Arman");
        s.setAge(19);
        s.setMarks(85);
        System.out.println(s.getGrade());
        s.setAge(-5);
        s.setMarks(150);
    }
}