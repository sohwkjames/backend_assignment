package backend_assignment.demo.models;

import java.time.LocalDate;

import jakarta.validation.constraints.NotBlank;

public class Book {

    private String id;
    private String title;
    private String author;
    private String genre;
    private LocalDate publishedDate;
    
    public Book() {}

    public Book(String id, String title, String author, String genre, LocalDate publishedDate) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.genre = genre;
        this.publishedDate = publishedDate;
    }

    // Getters and setters
    public String getId() { return id; }
    public void setId(String id) { this.id = id; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getAuthor() { return author; }
    public void setAuthor(String author) { this.author = author; }

    public String getGenre() { return genre; }
    public void setGenre(String genre) { this.genre = genre; }
    
    public LocalDate getPublishedDate() { return publishedDate; }
    public void setPublishedDate(LocalDate publishedDate) { this.publishedDate = publishedDate; }

}
