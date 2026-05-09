package level4;

interface Printable {
    void print();
}

interface Saveable {
    void save(String fileName);
}

interface Exportable {
    void exportToPDF();

    void exportToExcel();
}

class Report implements Printable, Saveable, Exportable {
    private String title;
    private String content;
    private String author;
    private String date;

    public Report(String title, String content, String author, String date) {
        this.title = title;
        this.content = content;
        this.author = author;
        this.date = date;
    }

    public void print() {
        System.out.println("Hello...");
    }

    public void save(String fileName) {
        System.out.println(fileName + " saved.");
    }

    public void exportToPDF() {
        System.out.println("exporting to pdf...");
    }

    public void exportToExcel() {
        System.out.println("exporting to excel...");
    }
}

public class Ans17 {
    public static void main(String[] args) {
        Report report = new Report("Sales Report", "Q1 data here", "Arman", "2026-01-01");
        report.print();
        report.save("sales.txt");
        report.exportToPDF();
        report.exportToExcel();
    }
}
