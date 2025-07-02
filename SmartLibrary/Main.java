import java.util.Scanner;
import model.Book;
import model.Student;
import operations.LibraryManager;

public class Main {
    public static void main(String[] args) {
        LibraryManager library = new LibraryManager();
        Scanner scanner = new Scanner(System.in);
        
        System.out.println("===== Library Management System =====");
        System.out.println("1. Student Login");
        System.out.println("2. Librarian Login");
        System.out.println("3. Exit");
        System.out.print("Enter your choice: ");
        
        int userType = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        switch (userType) {
            case 1:
                studentMenu(library, scanner);
                break;
            case 2:
                librarianMenu(library, scanner);
                break;
            case 3:
                System.out.println("Exiting system...");
                scanner.close();
                System.exit(0);
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void studentMenu(LibraryManager library, Scanner scanner) {
        System.out.print("Enter your student ID: ");
        int studentId = scanner.nextInt();
        scanner.nextLine(); // consume newline
        
        Student student = library.findStudentById(studentId);
        if (student == null) {
            System.out.println("Student not found!");
            return;
        }

        while (true) {
            System.out.println("\n===== Student Menu =====");
            System.out.println("1. View my borrowed books");
            System.out.println("2. View book return dates");
            System.out.println("3. Search for a book");
            System.out.println("4. Request to borrow a book");
            System.out.println("5. Return a book");
            System.out.println("6. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    library.displayStudentBooks(studentId);
                    break;
                case 2:
                    library.displayReturnDates(studentId);
                    break;
                case 3:
                    System.out.print("Enter book title to search: ");
                    String title = scanner.nextLine();
                    Book book = library.findBookByTitle(title);
                    if (book != null) {
                        System.out.println("Book found: " + book.getTitle() + 
                                         " by " + book.getAuthor() + 
                                         ", Available: " + book.isAvailable());
                    } else {
                        System.out.println("Book not found.");
                    }
                    break;
                case 4:
                    System.out.print("Enter book ID to borrow: ");
                    int bookId = scanner.nextInt();
                    library.requestIssue(bookId, studentId);
                    break;
                case 5:
                    System.out.print("Enter book ID to return: ");
                    int returnBookId = scanner.nextInt();
                    library.returnBook(returnBookId, studentId);
                    break;
                case 6:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }

    private static void librarianMenu(LibraryManager library, Scanner scanner) {
        System.out.print("Enter librarian password: ");
        String password = scanner.nextLine();
        
        // Simple password check (in real app, use proper authentication)
        if (!password.equals("admin123")) {
            System.out.println("Invalid password!");
            return;
        }

        while (true) {
            System.out.println("\n===== Librarian Menu =====");
            System.out.println("1. Book Operations");
            System.out.println("2. Student Operations");
            System.out.println("3. Borrow/Return Operations");
            System.out.println("4. Sorting Operations");
            System.out.println("5. View All Books");
            System.out.println("6. View All Students");
            System.out.println("7. Process Pending Requests");
            System.out.println("8. Logout");
            System.out.print("Enter your choice: ");
            
            int choice = scanner.nextInt();
            scanner.nextLine(); // consume newline
            
            switch (choice) {
                case 1:
                    bookOperations(library, scanner);
                    break;
                case 2:
                    studentOperations(library, scanner);
                    break;
                case 3:
                    borrowOperations(library, scanner);
                    break;
                case 4:
                    sortingOperations(library, scanner);
                    break;
                case 5:
                    library.displayAllBooks();
                    break;
                case 6:
                    library.displayAllStudents();
                    break;
                case 7:
                    library.processIssue();
                    break;
                case 8:
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }
    

    private static void bookOperations(LibraryManager library, Scanner scanner) {
        System.out.println("\n===== Book Operations =====");
        System.out.println("1. Add Book");
        System.out.println("2. Search Book by ID");
        System.out.println("3. Search Book by Title");
        System.out.println("4. Update Book");
        System.out.println("5. Delete Book");
        System.out.println("6. View All Books");
        System.out.println("7. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        switch (choice) {
            case 1:
                System.out.print("Enter book ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter title: ");
                String title = scanner.nextLine();
                System.out.print("Enter author: ");
                String author = scanner.nextLine();
                System.out.print("Enter publication date (YYYY-MM-DD): ");
                String pubDate = scanner.nextLine();
                library.addBook(id, title, author, pubDate);
                System.out.println("Book added successfully.");
                break;
                
            case 2:
                System.out.print("Enter book ID to search: ");
                int searchId = scanner.nextInt();
                scanner.nextLine();
                Book book = library.findBookById(searchId);
                if (book != null) {
                    System.out.println("Book found: " + book.getTitle() + " by " + book.getAuthor());
                } else {
                    System.out.println("Book not found.");
                }
                break;
                
            case 3:
                System.out.print("Enter book title to search: ");
                String searchTitle = scanner.nextLine();
                Book bookByTitle = library.findBookByTitle(searchTitle);
                if (bookByTitle != null) {
                    System.out.println("Book found: ID " + bookByTitle.getId() + ", " + 
                                      bookByTitle.getTitle() + " by " + bookByTitle.getAuthor());
                } else {
                    System.out.println("Book not found.");
                }
                break;
                
            case 4:
                System.out.print("Enter book ID to update: ");
                int updateId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new title: ");
                String newTitle = scanner.nextLine();
                System.out.print("Enter new author: ");
                String newAuthor = scanner.nextLine();
                System.out.print("Enter new publication date (YYYY-MM-DD): ");
                String newPubDate = scanner.nextLine();
                library.updateBook(updateId, newTitle, newAuthor, newPubDate);
                System.out.println("Book updated successfully.");
                break;
                
            case 5:
                System.out.print("Enter book ID to delete: ");
                int deleteId = scanner.nextInt();
                scanner.nextLine();
                if (library.deleteBook(deleteId)) {
                    System.out.println("Book deleted successfully.");
                } else {
                    System.out.println("Book not found.");
                }
                break;
                
            case 6:
                System.out.println("\n===== All Books =====");
                library.displayAllBooks();
                break;
                
            case 7:
                return;
                
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void studentOperations(LibraryManager library, Scanner scanner) {
        System.out.println("\n===== Student Operations =====");
        System.out.println("1. Add Student");
        System.out.println("2. Search Student by ID");
        System.out.println("3. Update Student");
        System.out.println("4. Delete Student");
        System.out.println("5. View All Students");
        System.out.println("6. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        switch (choice) {
            case 1:
                System.out.print("Enter student ID: ");
                int id = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter name: ");
                String name = scanner.nextLine();
                System.out.print("Enter contact: ");
                String contact = scanner.nextLine();
                library.addStudent(id, name, contact);
                System.out.println("Student added successfully.");
                break;
                
            case 2:
                System.out.print("Enter student ID to search: ");
                int searchId = scanner.nextInt();
                scanner.nextLine();
                Student student = library.findStudentById(searchId);
                if (student != null) {
                    System.out.println("Student found: " + student.getName() + 
                                      ", Contact: " + student.getContact());
                } else {
                    System.out.println("Student not found.");
                }
                break;
                
            case 3:
                System.out.print("Enter student ID to update: ");
                int updateId = scanner.nextInt();
                scanner.nextLine();
                System.out.print("Enter new name: ");
                String newName = scanner.nextLine();
                System.out.print("Enter new contact: ");
                String newContact = scanner.nextLine();
                library.updateStudent(updateId, newName, newContact);
                System.out.println("Student updated successfully.");
                break;
                
            case 4:
                System.out.print("Enter student ID to delete: ");
                int deleteId = scanner.nextInt();
                scanner.nextLine();
                if (library.deleteStudent(deleteId)) {
                    System.out.println("Student deleted successfully.");
                } else {
                    System.out.println("Student not found.");
                }
                break;
                
            case 5:
                System.out.println("\n===== All Students =====");
                library.displayAllStudents();
                break;
                
            case 6:
                return;
                
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void borrowOperations(LibraryManager library, Scanner scanner) {
        System.out.println("\n===== Borrow/Return Operations =====");
        System.out.println("1. Request to Borrow a Book");
        System.out.println("2. Process Borrow Request");
        System.out.println("3. Return a Book");
        System.out.println("4. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        switch (choice) {
            case 1:
                System.out.print("Enter student ID: ");
                int studentId = scanner.nextInt();
                System.out.print("Enter book ID to borrow: ");
                int bookId = scanner.nextInt();
                library.requestIssue(bookId, studentId);
                System.out.println("Borrow request submitted.");
                break;
                
            case 2:
                library.processIssue();
                break;
                
            case 3:
                System.out.print("Enter student ID: ");
                int returnStudentId = scanner.nextInt();
                System.out.print("Enter book ID to return: ");
                int returnBookId = scanner.nextInt();
                library.returnBook(returnBookId, returnStudentId);
                break;
                
            case 4:
                return;
                
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private static void sortingOperations(LibraryManager library, Scanner scanner) {
        System.out.println("\n===== Sorting Operations =====");
        System.out.println("1. Sort Books by Title");
        System.out.println("2. Sort Books by Publication Date");
        System.out.println("3. Back to Main Menu");
        System.out.print("Enter your choice: ");
        
        int choice = scanner.nextInt();
        scanner.nextLine(); 
        
        switch (choice) {
            case 1:
                System.out.println("\n===== Books Sorted by Title =====");
                library.sortBooksByTitle();
                break;
                
            case 2:
                System.out.println("\n===== Books Sorted by Publication Date =====");
                library.sortBooksByPublicationDate();
                break;
                
            case 3:
                return;
                
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }
}