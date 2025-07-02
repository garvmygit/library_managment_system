package dsa;

import java.util.ArrayList;
import java.util.List;

import model.Book;

public class BookLinkedList {
    public Node head;

    public static class Node {
        Book data;
        Node next;

        Node(Book data) {
            this.data = data;
            this.next = null;
        }
    }

    public void add(Book book) {
        Node newNode = new Node(book);
        if (head == null) {
            head = newNode;
        } else {
            Node current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = newNode;
        }
    }

    public List<Book> getAllBooks() {
    List<Book> bookList = new ArrayList<>();
    Node current = head;
    while (current != null) {
        bookList.add(current.data);
        current = current.next;
    }
    return bookList;
}
    public Book findById(int id) {
        Node current = head;
        while (current != null) {
            if (current.data.getId() == id) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public Book findByTitle(String title) {
        Node current = head;
        while (current != null) {
            if (current.data.getTitle().equalsIgnoreCase(title)) {
                return current.data;
            }
            current = current.next;
        }
        return null;
    }

    public boolean remove(int id) {
        if (head == null) return false;

        if (head.data.getId() == id) {
            head = head.next;
            return true;
        }

        Node current = head;
        while (current.next != null) {
            if (current.next.data.getId() == id) {
                current.next = current.next.next;
                return true;
            }
            current = current.next;
        }
        return false;
    }

    public void displayAll() {
        Node current = head;
        while (current != null) {
            Book book = current.data;
            System.out.println("ID: " + book.getId() + ", Title: " + book.getTitle() + 
                               ", Author: " + book.getAuthor() + ", Available: " + book.isAvailable());
            current = current.next;
        }
    }

    public int size() {
        int count = 0;
        Node current = head;
        while (current != null) {
            count++;
            current = current.next;
        }
        return count;
    }
}