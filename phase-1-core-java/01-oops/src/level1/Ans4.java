class Book {
    private String title;
    private String author;
    private String isbn;
    private boolean isAvailable;

    public Book(String title, String author, String isbn) {
        this.title = title;
        this.author = author;
        this.isbn = isbn;
        this.isAvailable = true;
    }

    public void checkOut() {
        if (isAvailable) {
            isAvailable = false;
        } else {
            System.out.println("Book already checked out");
        }
    }

    public void returnBook() {
        isAvailable = true;
    }

    public void getBookInfo() {
        System.out.println("Title: " + this.title + "\nAuthor: " + this.author + "\nisbn: " + this.isbn + "\nAvalable: "
                + isAvailable);
    }
}

public class Ans4 {
    public static void main(String[] args) {
        Book book = new Book("Clean Code", "Robert Martin", "ISBN123");
        book.checkOut();
        book.checkOut(); // Already checked out
        book.returnBook();
        book.checkOut(); // Works now
        book.returnBook();
        book.getBookInfo();
    }
}
