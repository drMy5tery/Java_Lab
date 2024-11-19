import java.util.ArrayList;
import java.util.List;
class Book {
    int bookId;
    String title;
    String author;
    boolean isAvailable;
    int delayedDays;
    public Book(int bookId, String title, String author) {
        if (bookId <= 0){
            System.out.println("Give Positive Book Id");
            //System.exit(0);
        }
        this.bookId = bookId;
        this.title = title;
        this.author = author;
        this.isAvailable = true;
    }
    // Getters and Setters
    public int getBookId() {
        return bookId;
    }

    public String getTitle() {
        return title;
    }

    public String getAuthor() {
        return author;
    }

    public boolean isAvailable() {
        return isAvailable;
    }
    public void set_delayedDays(int delayedDays) {
        if (delayedDays < 0){
            System.out.println("Delayed Days cannot be Negative");
        }
        this.delayedDays = delayedDays;
    }
    public void borrowBook() {
        if (!isAvailable) {
            System.out.println("Book already borrowed: " + title);
        } else {
            isAvailable = false;
            System.out.println("Book borrowed: " + title);
        }
    }

    public void returnBook() {
        if (isAvailable) {
            System.out.println("Book was not borrowed: " + title);
        } else {
            isAvailable = true;
            System.out.println("Book returned: " + title);
        }
    }

    // Fine Calculation
    public double calculateFine() {
        int fine = 5; // Default fine rate
        return delayedDays * fine;
    }
    // Display book details
    public void displayBookDetails() {
        System.out.println("Book ID: " + bookId);
        System.out.println("Title: " + title);
        System.out.println("Author: " + author);
        if(isAvailable){
            System.out.println("Availability: Available");
        }
        else{
            System.out.println("Availability: Not Available");
            System.out.println("Late Days: " + delayedDays);
            System.out.println("Fine Amount: " + calculateFine());
        }
        
    }
    
}

class ReferenceBook extends Book{
    int edition;

    public ReferenceBook(int bookId, String title, String author, int edition) {
        super(bookId, title, author);
        if (edition <= 0){
            System.out.println("Invalid edition");
        }
        this.edition = edition;
    }

    public int getEdition() {
        return edition;
    }
    @Override
    public void displayBookDetails(){
        super.displayBookDetails();
        System.out.println("Edition: " + edition);
    }
}
class FictionBook extends Book{
    String genre;
    public FictionBook(int bookId, String title, String author, String genre) {
        super(bookId, title, author);
        this.genre = genre;
    }

    public String getGenre() {
        return genre;
    }

    @Override
    public void displayBookDetails() {
        super.displayBookDetails();
        System.out.println("Genre: " + genre);
    }
}
// Managememt class
class LibraryManagementSystem {
    int totalBooks = 0;
    int borrowedBooks = 0;
    List<Book> bookList = new ArrayList<>();
    List<Book> borrowedBookList = new ArrayList<>();

    // Add Book
    public void addBook(Book book) {
        bookList.add(book);
        totalBooks++;
        System.out.println("Book added: " + book.getTitle());
    }

    // Borrow Book
    public void borrowBook(Book book) {
        if (book.isAvailable()) {
            book.borrowBook();
            borrowedBooks++;
            borrowedBookList.add(book);
        }
    }

    // Return Book
    public void returnBook(Book book) {
        if (!book.isAvailable()) {
            book.returnBook();
            borrowedBooks--;
            borrowedBookList.remove(book);
        }
    }

    // Display Total Books
    public void displayTotalBooks() {
        System.out.println("Total Books in Library: " + totalBooks);
        System.out.println("Currently Borrowed Books: " + borrowedBooks);
    }

    // Display Borrowed Books
    public void displayBorrowedBooks() {
        System.out.println("\n--- Borrowed Books ---");
        for (Book book : borrowedBookList) {
            System.out.println("- " + book.getTitle() + " by " + book.getAuthor());
        }
    }
}


public class Part_b {
    public static void main(String[] args) {
        LibraryManagementSystem library = new LibraryManagementSystem();

        // Create books
        ReferenceBook refBook1 = new ReferenceBook(1, "Java Programming", "John", 3);
        ReferenceBook refBook2 = new ReferenceBook(4, "Data Structures", "Alice", 2);

        FictionBook ficBook1 = new FictionBook(2, "Harry Potter", "J.K. Rowling", "Fantasy");
        FictionBook ficBook2 = new FictionBook(5, "The Hobbit", "J.R.R. Tolkien", "Adventure");


        // Add books to the library
        library.addBook(refBook1);
        library.addBook(refBook2);
        library.addBook(ficBook1);
        library.addBook(ficBook2);

        // Borrow books
        System.out.println("\n--- Borrowing Books ---");
        library.borrowBook(refBook1);
        library.borrowBook(ficBook1);

        // Set late days for overdue fine calculation
        refBook1.set_delayedDays(3);
        // Display borrowed books
        library.displayBorrowedBooks();

        // Return books
        System.out.println("\n--- Returning Books ---");
        library.returnBook(refBook1);

        // Display total books
        System.out.println("\n--- Library Summary ---");
        library.displayTotalBooks();

        //Display Delayed book
        System.out.println("\n--- Delayed Book Details ---");
        refBook1.displayBookDetails();

    }    
}
