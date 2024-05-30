import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Book {
    int id;
    String title;
    String author;
    String description;
    String categories;
    int qty;
    boolean booked;

    public Book(int id, String title, String author, String description, String categories, int qty) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.description = description;
        this.categories = categories;
        this.qty = qty;
        this.booked = false;
    }

    public String toString() {
        return "ID: " + id + ", Title: " + title + ", Author: " + author + ", Available: " + (qty > 0);
    }
}

class Member {
    int id;
    String name;
    String phone;
    String email;
    String address;

    public Member(int id, String name, String phone, String email, String address) {
        this.id = id;
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public String toString() {
        return "ID: " + id + ", Name: " + name;
    }
}

class Transaction {
    int id;
    String date;
    int idMember;
    int idBook;
    int status; // 1=booked, 2=finished

    public Transaction(int id, String date, int idMember, int idBook, int status) {
        this.id = id;
        this.date = date;
        this.idMember = idMember;
        this.idBook = idBook;
        this.status = status;
    }
}

public class LibraryManagementSystem {
    List<Book> books = new ArrayList<>();
    List<Member> members = new ArrayList<>();
    List<Transaction> transactions = new ArrayList<>();
    int bookId = 1;
    int memberId = 1;
    int transactionId = 1;

    public void addBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adding a new book...");
        System.out.print("Enter title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter description: ");
        String description = scanner.nextLine();
        System.out.print("Enter categories: ");
        String categories = scanner.nextLine();
        System.out.print("Enter quantity: ");
        int qty = scanner.nextInt();
        Book book = new Book(bookId++, title, author, description, categories, qty);
        books.add(book);
        System.out.println("Book added successfully!");
    }

    public void addMember() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Adding a new member...");
        System.out.print("Enter name: ");
        String name = scanner.nextLine();
        System.out.print("Enter phone: ");
        String phone = scanner.nextLine();
        System.out.print("Enter email: ");
        String email = scanner.nextLine();
        System.out.print("Enter address: ");
        String address = scanner.nextLine();
        Member member = new Member(memberId++, name, phone, email, address);
        members.add(member);
        System.out.println("Member added successfully!");
    }

    public void borrowBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Borrowing a book...");
        System.out.print("Enter member ID: ");
        int idMember = scanner.nextInt();
        System.out.print("Enter book ID: ");
        int idBook = scanner.nextInt();

        Book book = findBookById(idBook);
        if (book != null && book.qty > 0) {
            Transaction transaction = new Transaction(transactionId++, "Today", idMember, idBook, 1);
            transactions.add(transaction);
            book.qty--;
            book.booked = true;
            System.out.println("Book borrowed successfully!");
        } else {
            System.out.println("Book is not available for borrowing.");
        }
    }

    public Book findBookById(int id) {
        for (Book book : books) {
            if (book.id == id) {
                return book;
            }
        }
        return null;
    }

    public Member findMemberById(int id) {
        for (Member member : members) {
            if (member.id == id) {
                return member;
            }
        }
        return null;
    }

    public void returnBook() {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Returning a book...");
        System.out.print("Enter member ID: ");
        int idMember = scanner.nextInt();
        System.out.print("Enter book ID: ");
        int idBook = scanner.nextInt();

        Book book = findBookById(idBook);
        if (book != null) {
            Transaction transaction = findTransactionByBookAndMember(idBook, idMember, 1);
            if (transaction != null) {
                transaction.status = 2;
                book.qty++;
                book.booked = false;
                System.out.println("Book returned successfully!");
            } else {
                System.out.println("Transaction not found or the book was not borrowed.");
            }
        } else {
            System.out.println("Book not found.");
        }
    }

    public Transaction findTransactionByBookAndMember(int idBook, int idMember, int status) {
        for (Transaction transaction : transactions) {
            if (transaction.idBook == idBook && transaction.idMember == idMember && transaction.status == status) {
                return transaction;
            }
        }
        return null;
    }

    public void listAvailableBooks() {
        System.out.println("Available books:");
        for (Book book : books) {
            if (book.qty > 0) {
                System.out.println(book);
            }
        }
    }

    public void listMembers() {
        System.out.println("Members:");
        for (Member member : members) {
            System.out.println(member);
        }
    }

    public static void main(String[] args) {
        LibraryManagementSystem system = new LibraryManagementSystem();
        Scanner scanner = new Scanner(System.in);
        int choice;
        do {
            System.out.println("\nLibrary Management System");
            System.out.println("1. Add Book");
            System.out.println("2. Add Member");
            System.out.println("3. Borrow Book");
            System.out.println("4. Return Book");
            System.out.println("5. List Available Books");
            System.out.println("6. List Members");
            System.out.println("0. Exit");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline
            switch (choice) {
                case 1:
                    system.addBook();
                    break;
                case 2:
                    system.addMember();
                    break;
                case 3:
                    system.borrowBook();
                    break;
                case 4:
                    system.returnBook();
                    break;
                case 5:
                    system.listAvailableBooks();
                    break;
                case 6:
                    system.listMembers();
                    break;
                case 0:
                    System.out.println("Exiting the system. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 0);
    }
}
