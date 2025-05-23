package com.esprit.microservice.author1.repository;

import com.esprit.microservice.author1.model.Author;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AuthorRepository extends JpaRepository<Author, Long> {
    // Version corrigée avec le bon import Page
    Page<Author> findAll(Pageable pageable);

    Page<Author> findByNameContainingIgnoreCase(String name, Pageable pageable);
}