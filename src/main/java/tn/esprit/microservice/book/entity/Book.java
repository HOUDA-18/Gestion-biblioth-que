package tn.esprit.microservice.book.entity;

import jakarta.persistence.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer bookId;

    private String title;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private Integer numberAvailableCopies;

    private Integer authorId;
    private Integer publisherId;
    private Integer genreId;


    public Book() {}

    public Book(String title, Integer authorId, Integer publisherId, Integer genreId, Status status, Integer numberAvailableCopies) {
        this.title = title;
        this.authorId = authorId;
        this.publisherId = publisherId;
        this.genreId = genreId;
        this.status = status;
        this.numberAvailableCopies = numberAvailableCopies;
    }
    public Integer getBookId() {
        return bookId;
    }

    public void setBookId(Integer bookId) {
        this.bookId = bookId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Integer authorId) {
        this.authorId = authorId;
    }

    public Integer getPublisherId() {
        return publisherId;
    }

    public void setPublisherId(Integer publisherId) {
        this.publisherId = publisherId;
    }

    public Integer getGenreId() {
        return genreId;
    }

    public void setGenreId(Integer genreId) {
        this.genreId = genreId;
    }

    public Status getStatus() {
        return status;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    public Integer getNumberAvailableCopies() {
        return numberAvailableCopies;
    }

    public void setNumberAvailableCopies(Integer numberAvailableCopies) {
        this.numberAvailableCopies = numberAvailableCopies;
    }
}
