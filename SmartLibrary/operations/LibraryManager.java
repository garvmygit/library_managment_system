package operations;

import dsa.BookLinkedList;
import dsa.IssueQueue;
import model.Book;
import model.Student;
import java.io.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

public class LibraryManager {
    private BookLinkedList books;
    private List<Student> students;
    private IssueQueue issueQueue;
    private static final String BOOKS_FILE = "data/books.txt";
    private static final String STUDENTS_FILE = "data/students.txt";
    private static final String ISSUED_FILE = "data/issued.txt";

    public LibraryManager() {
        books = new BookLinkedList();
        students = new ArrayList<>();
        issueQueue = new IssueQueue();
        loadData();
    }

  
    public void addBook(int id, String title, String author, String publicationDate) {
        Book book = new Book(id, title, author, publicationDate);
        books.add(book);
        saveBooksToFile();
    }

    public Book findBookById(int id) {
        return books.findById(id);
    }

    public Book findBookByTitle(String title) {
        return books.findByTitle(title);
    }

    public void updateBook(int id, String title, String author, String publicationDate) {
        Book book = books.findById(id);
        if (book != null) {
            book.setTitle(title);
            book.setAuthor(author);
            book.setPublicationDate(publicationDate);
            saveBooksToFile();
        }
    }

    public boolean deleteBook(int id) {
        boolean result = books.remove(id);
        if (result) {
            saveBooksToFile();
        }
        return result;
    }

    public void displayAllBooks() {
        books.displayAll();
    }


    public void addStudent(int id, String name, String contact) {
        Student student = new Student(id, name, contact);
        students.add(student);
        saveStudentsToFile();
    }

    public Student findStudentById(int id) {
        for (Student student : students) {
            if (student.getId() == id) {
                return student;
            }
        }
        return null;
    }

    public void updateStudent(int id, String name, String contact) {
        Student student = findStudentById(id);
        if (student != null) {
            student.setName(name);
            student.setContact(contact);
            saveStudentsToFile();
        }
    }

    public boolean deleteStudent(int id) {
        Student student = findStudentById(id);
        if (student != null) {
            students.remove(student);
            saveStudentsToFile();
            return true;
        }
        return false;
    }

    public void displayAllStudents() {
        for (Student student : students) {
            System.out.println("ID: " + student.getId() + ", Name: " + student.getName() + 
                              ", Contact: " + student.getContact());
        }
    }

   
    public void requestIssue(int bookId, int studentId) {
        Book book = books.findById(bookId);
        Student student = findStudentById(studentId);
        if (book != null && student != null) {
            issueQueue.enqueue(book, student);
            saveIssuedToFile();
        }
    }

    public void processIssue() {
        IssueQueue.IssueRequest request = issueQueue.dequeue();
        if (request != null) {
            Book book = request.getBook();
            Student student = request.getStudent();
            
            if (book.isAvailable()) {
                book.setAvailable(false);
                student.borrowBook(book.getId());
                saveBooksToFile();
                saveStudentsToFile();
                saveIssuedToFile();
                System.out.println("Book issued successfully to " + student.getName());
            } else {
                System.out.println("Book is not available for issuing");
            }
        } else {
            System.out.println("No pending issue requests");
        }
    }

    public void returnBook(int bookId, int studentId) {
        Book book = books.findById(bookId);
        Student student = findStudentById(studentId);
        if (book != null && student != null) {
            book.setAvailable(true);
            student.returnBook(bookId);
            saveBooksToFile();
            saveStudentsToFile();
            System.out.println("Book returned successfully");
        }
    }

   
    public void sortBooksByTitle() {
        List<Book> bookList = books.getAllBooks();
        
       
        for (int i = 0; i < bookList.size() - 1; i++) {
            for (int j = 0; j < bookList.size() - i - 1; j++) {
                if (bookList.get(j).getTitle().compareToIgnoreCase(bookList.get(j+1).getTitle()) > 0) {
                    Book temp = bookList.get(j);
                    bookList.set(j, bookList.get(j+1));
                    bookList.set(j+1, temp);
                }
            }
        }
        
       
        for (Book book : bookList) {
            System.out.println("Title: " + book.getTitle() + ", Author: " + book.getAuthor());
        }
    }

    public void sortBooksByPublicationDate() {
        List<Book> bookList = books.getAllBooks();
        

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (int i = 0; i < bookList.size() - 1; i++) {
            int minIndex = i;
            for (int j = i+1; j < bookList.size(); j++) {
                try {
                    Date date1 = sdf.parse(bookList.get(j).getPublicationDate());
                    Date date2 = sdf.parse(bookList.get(minIndex).getPublicationDate());
                    if (date1.before(date2)) {
                        minIndex = j;
                    }
                } catch (ParseException e) {
                    System.out.println("Error parsing date: " + e.getMessage());
                }
            }
            Book temp = bookList.get(minIndex);
            bookList.set(minIndex, bookList.get(i));
            bookList.set(i, temp);
        }
        
       
        for (Book book : bookList) {
            System.out.println("Date: " + book.getPublicationDate() + ", Title: " + book.getTitle());
        }
    }

   
    private void loadData() {
        loadBooks();
        loadStudents();
        loadIssued();
    }

    private void loadBooks() {
        try (BufferedReader reader = new BufferedReader(new FileReader(BOOKS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (line.trim().isEmpty()) continue; // skip blank lines
    
                String[] parts = line.split(",");
                if (parts.length >= 4 && !parts[0].trim().isEmpty()) {
                    try {
                        int id = Integer.parseInt(parts[0].trim());
                        String title = parts[1].trim();
                        String author = parts[2].trim();
                        String pubDate = parts[3].trim();
                        boolean available = parts.length > 4 ? Boolean.parseBoolean(parts[4].trim()) : true;
    
                        Book book = new Book(id, title, author, pubDate);
                        book.setAvailable(available);
                        books.add(book);
                    } catch (NumberFormatException e) {
                        System.out.println("Skipping malformed line in books file: " + line);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading books: " + e.getMessage());
        }
    }



// Add to LibraryManager.java
public void displayStudentBooks(int studentId) {
    Student student = findStudentById(studentId);
    if (student != null) {
        System.out.println("\n===== Your Borrowed Books =====");
        System.out.println(student.getBorrowedBooksInfo());
    } else {
        System.out.println("Student not found!");
    }
}

public void displayReturnDates(int studentId) {
    Student student = findStudentById(studentId);
    if (student != null) {
        System.out.println("\n===== Your Return Dates =====");
        if (student.getBorrowedBooks().isEmpty()) {
            System.out.println("No books currently borrowed");
        } else {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Calendar cal = Calendar.getInstance();
            
            for (int bookId : student.getBorrowedBooks()) {
                Book book = findBookById(bookId);
                if (book != null) {
                    cal.setTime(new Date()); // today
                    cal.add(Calendar.DAY_OF_MONTH, 14); // 2 weeks from now
                    System.out.println(book.getTitle() + ": Due " + sdf.format(cal.getTime()));
                }
            }
        }
    } else {
        System.out.println("Student not found!");
    }
}
    private void loadStudents() {
        try (BufferedReader reader = new BufferedReader(new FileReader(STUDENTS_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length >= 3) {
                    int id = Integer.parseInt(parts[0]);
                    String name = parts[1];
                    String contact = parts[2];
                    
                    Student student = new Student(id, name, contact);
                    
                    if (parts.length > 3) {
                        String[] borrowed = parts[3].split(";");
                        for (String bookId : borrowed) {
                            if (!bookId.isEmpty()) {
                                student.borrowBook(Integer.parseInt(bookId));
                            }
                        }
                    }
                    
                    students.add(student);
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading students: " + e.getMessage());
        }
    }

    private void loadIssued() {
        try (BufferedReader reader = new BufferedReader(new FileReader(ISSUED_FILE))) {
            String line;
            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 2) {
                    int bookId = Integer.parseInt(parts[0]);
                    int studentId = Integer.parseInt(parts[1]);
                    
                    Book book = books.findById(bookId);
                    Student student = findStudentById(studentId);
                    
                    if (book != null && student != null) {
                        issueQueue.enqueue(book, student);
                    }
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading issued books: " + e.getMessage());
        }
    }

    private void saveBooksToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(BOOKS_FILE))) {
            for (Book book : books.getAllBooks()) {
                writer.println(book.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving books: " + e.getMessage());
        }
    }

    private void saveStudentsToFile() {
        try (PrintWriter writer = new PrintWriter(new FileWriter(STUDENTS_FILE))) {
            for (Student student : students) {
                writer.println(student.toString());
            }
        } catch (IOException e) {
            System.out.println("Error saving students: " + e.getMessage());
        }
    }

  private void saveIssuedToFile() {
    try (PrintWriter writer = new PrintWriter(new FileWriter(ISSUED_FILE))) {
        for (IssueQueue.IssueRequest request : issueQueue.getAllRequests()) {
            writer.println(request.getBook().getId() + "," + request.getStudent().getId());
        }
    } catch (IOException e) {
        System.out.println("Error saving issued books: " + e.getMessage());
    }
}

    
}