package dsa;

import java.util.ArrayList;
import java.util.List;
import model.Book;
import model.Student;

public class IssueQueue {

   
    private static class Node {
        Book book;
        Student student;
        Node next;

        Node(Book book, Student student) {
            this.book = book;
            this.student = student;
            this.next = null;
        }
    }

    private Node front, rear;

  
    public void enqueue(Book book, Student student) {
        Node newNode = new Node(book, student);
        if (rear == null) {
            front = rear = newNode;
        } else {
            rear.next = newNode;
            rear = newNode;
        }
    }

  
    public static class IssueRequest {
        private Book book;
        private Student student;

        public IssueRequest(Book book, Student student) {
            this.book = book;
            this.student = student;
        }

        public Book getBook() { return book; }
        public Student getStudent() { return student; }
    }


    public List<IssueRequest> getAllRequests() {
        List<IssueRequest> requests = new ArrayList<>();
        Node current = front;
        while (current != null) {
            requests.add(new IssueRequest(current.book, current.student));
            current = current.next;
        }
        return requests;
    }


    public IssueRequest dequeue() {
        if (front == null) return null;

        IssueRequest request = new IssueRequest(front.book, front.student);
        front = front.next;

        if (front == null) {
            rear = null;
        }
        return request;
    }

    
    public boolean isEmpty() {
        return front == null;
    }


    public void displayQueue() {
        Node current = front;
        while (current != null) {
            System.out.println("Student: " + current.student.getName() +
                               " wants to borrow: " + current.book.getTitle());
            current = current.next;
        }
    }
}
