package model;

import java.util.ArrayList;
import java.util.List;

public class Student {
    private int id;
    private String name;
    private String contact;
    private List<Integer> borrowedBooks;

    public Student(int id, String name, String contact) {
        this.id = id;
        this.name = name;
        this.contact = contact;
        this.borrowedBooks = new ArrayList<>();
    }

 
    public int getId() { return id; }
    public String getName() { return name; }
    public String getContact() { return contact; }
    public List<Integer> getBorrowedBooks() { return borrowedBooks; }

    public void setName(String name) { this.name = name; }
    public void setContact(String contact) { this.contact = contact; }

    public void borrowBook(int bookId) {
        borrowedBooks.add(bookId);
    }

    public void returnBook(int bookId) {
        borrowedBooks.remove(Integer.valueOf(bookId));
    }
    public String getBorrowedBooksInfo() {
        if (borrowedBooks.isEmpty()) {
            return "No books currently borrowed";
        }
        StringBuilder sb = new StringBuilder();
        sb.append("Total books borrowed: ").append(borrowedBooks.size()).append("\n");
        for (int bookId : borrowedBooks) {
            sb.append("- Book ID: ").append(bookId).append("\n");
        }
        return sb.toString();
    }

    @Override
    public String toString() {
        return id + "," + name + "," + contact + "," + String.join(";", borrowedBooks.stream().map(String::valueOf).toArray(String[]::new));
    }
}