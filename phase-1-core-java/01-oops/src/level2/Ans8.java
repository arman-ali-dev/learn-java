package level2;

import java.util.ArrayList;

class Person {
    public String name;
    public int age;
    public String email;

    public Person(String name, int age, String email) {
        this.name = name;
        this.age = age;
        this.email = email;
    }

    public void introduce() {
        System.out.println("Hi, I am " + this.name + " " + this.age + " years old");
    }
}

class Student extends Person {
    private String studentId;
    private String grade;
    private ArrayList<String> subjects;

    public Student(String name, int age, String email, String studentId, String grade) {
        super(name, age, email);
        this.studentId = studentId;
        this.grade = grade;
        this.subjects = new ArrayList<>();
    }

    public void addSubject(String subject) {
        this.subjects.add(subject);
    }

    public void getReport() {
        System.out.println("Name: " + this.name + "\nAge: " + this.age + "\nEmail: " + this.email + "\nStudentId: "
                + this.studentId + "\nGrade: " + this.grade);

        System.out.print("\nSubjects: " + String.join(", ", subjects));

    }
}

class Teacher extends Person {
    private String teacherId;
    private String subject;
    private int salary;

    public Teacher(String name, int age, String email, String teacherId, String subject, int salary) {
        super(name, age, email);
        this.teacherId = teacherId;
        this.subject = subject;
        this.salary = salary;
    }

    public void teach() {
        System.out.println(this.name + " is teaching " + this.subject);
    }
}

class Principal extends Person {
    private String schoolName;

    public Principal(String name, int age, String email, String schoolName) {
        super(name, age, email);
        this.schoolName = schoolName;
    }

    public void makeAnnouncement(String message) {
        System.out.println(message);
    }
}

public class Ans8 {
    public static void main(String[] args) {
        Student s = new Student("Arman", 20, "arman@email.com", "S001", "BCA");
        s.addSubject("Java");
        s.addSubject("DSA");
        s.introduce();
        s.getReport();

        Teacher t = new Teacher("Mr. Sharma", 35, "sharma@email.com", "T001", "Java", 50000);
        t.teach();
    }
}
