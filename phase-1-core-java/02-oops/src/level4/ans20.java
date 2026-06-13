package level4;

import java.util.ArrayList;
import java.util.List;

interface Schedulable {
    void scheduleAppointment(String patientName, String date, String time);

    void cancelAppointment(String patientName);
}

interface Billable {
    void generateBill();

    void applyDiscount(double percent);
}

abstract class Person {
    protected String name;
    protected int age;
    protected String contactNumber;

    public Person(String name, int age, String contactNumber) {
        this.name = name;
        this.age = age;
        this.contactNumber = contactNumber;
    }
}

class Patient extends Person implements Billable {
    private String patientId;
    private String disease;
    public List<String> prescriptions;

    private double totalBill;

    public Patient(String name, int age, String contactNumber, String patientId, String disease) {
        super(name, age, contactNumber);
        this.patientId = patientId;
        this.disease = disease;
        this.prescriptions = new ArrayList<>();
    }

    public void addPrescription(String medicine, String dosage) {
        this.prescriptions.add(medicine);
        this.prescriptions.add(dosage);
    }

    public void generateBill() {
         totalBill = 500 + (prescriptions.size() / 2) * 200;
        System.out.println(this.totalBill);
    }

    public void applyDiscount(double percent) {
        double discount = (this.totalBill * percent) / 100;
        this.totalBill -= discount;
    }
}

class Doctor extends Person implements Schedulable {
    private String doctorId;
    private String specialization;
    private List<Appointment> appointments;

    public Doctor(String name, int age, String contactNumber, String doctorId, String specialization) {
        super(name, age, contactNumber);
        this.doctorId = doctorId;
        this.specialization = specialization;
        this.appointments = new ArrayList<>();
    }

    public void scheduleAppointment(String patientName, String date, String time) {
        Appointment appointment = new Appointment(patientName, date, time);
        appointments.add(appointment);
    };

    public void cancelAppointment(String patientName) {
        this.appointments.removeIf(a -> a.patientName.equals(patientName));
    };

    public void getDoctorInfo() {
        System.out.println("Name: " + this.name + "\nAge: " + this.age + "\nContact Number: " + this.contactNumber
                + "Specialization: " + this.specialization + "\nDoctorId: " + this.doctorId);
        System.out.println("Appointments: ");
        for (Appointment a : appointments) {
            System.out.println("Patient Name: " + a.patientName + "\tDate: " + a.date + "\tTime: " + a.time);
        }
    }
}

class Appointment {
    String patientName;
    String date;
    String time;

    public Appointment(String patientName, String date, String time) {
        this.patientName = patientName;
        this.date = date;
        this.time = time;
    }
}

class Receptionist extends Person {
    private String receptionistId;

    public Receptionist(String name, int age, String contactNumber, String receptionistId) {
        super(name, age, contactNumber);
        this.receptionistId = receptionistId;
    }

    public void registerPatient(Patient p) {
        System.out.println(
                "Patient Name: " + p.name + "\nPatient Age: " + p.age + "\nPatient Contact Number: " + p.contactNumber);

        System.out.println(
                "Prescriptions of Patient: " + String.join(", ", p.prescriptions));
    }

    public void assignDoctor(Patient p, Doctor d) {
        System.out.println("Patient " + p.name + " has assigned to: " + d.name);
    }
}

public class ans20 {
    public static void main(String[] args) {
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
    }
}
