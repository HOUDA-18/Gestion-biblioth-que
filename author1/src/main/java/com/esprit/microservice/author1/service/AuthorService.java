package com.esprit.microservice.author1.service;

import com.esprit.microservice.author1.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface AuthorService {
    // Ancienne version (à conserver pour compatibilité si nécessaire)
    // List<Author> getAllAuthors();

    // Nouvelle version avec pagination
    Page<Author> getAllAuthors(Pageable pageable);

    Optional<Author> getAuthorById(Long id);
    Author createAuthor(Author author);
    Author updateAuthor(Long id, Author author);
    void deleteAuthor(Long id);

    // Optionnel : Méthode pour recherche filtrée
    Page<Author> searchAuthors(String keyword, Pageable pageable);
}