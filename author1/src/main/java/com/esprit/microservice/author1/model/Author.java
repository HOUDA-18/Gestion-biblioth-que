package com.esprit.microservice.author1.model;

import com.esprit.microservice.author1.model.Book;
import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "author")
public class Author {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long authorId;

    private String name;

    @OneToMany(mappedBy = "author", fetch = FetchType.EAGER)
    private List<com.esprit.microservice.author1.model.Book> books;

    public Author(long l, String s) {
    }
    public Author() {
    }

    // Getters and Setters

    public Long getAuthorId() {
        return authorId;
    }

    public void setAuthorId(Long authorId) {
        this.authorId = authorId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<com.esprit.microservice.author1.model.Book> getBooks() {
        return books;
    }

    public void setBooks(List<com.esprit.microservice.author1.model.Book> books) {
        this.books = books;
    }


}
