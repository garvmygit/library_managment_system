package model;

public class Book {
    private int id;
    private String title;
    private String author;
    private String publicationDate;
    private boolean isAvailable;

    public Book(int id, String title, String author, String publicationDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationDate = publicationDate;
        this.isAvailable = true;
    }

   
    public int getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public String getPublicationDate() { return publicationDate; }
    public boolean isAvailable() { return isAvailable; }

    public void setTitle(String title) { this.title = title; }
    public void setAuthor(String author) { this.author = author; }
    public void setPublicationDate(String publicationDate) { this.publicationDate = publicationDate; }
    public void setAvailable(boolean available) { isAvailable = available; }

    @Override
    public String toString() {
        return id + "," + title + "," + author + "," + publicationDate + "," + isAvailable;
    }
}