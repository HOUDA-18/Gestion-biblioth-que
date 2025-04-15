package com.esprit.microservice.author1.service;

import com.esprit.microservice.author1.model.Author;
import com.esprit.microservice.author1.repository.AuthorRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class AuthorServiceImpl implements AuthorService {

    @Autowired
    private AuthorRepository authorRepository;

    @Override
    @Transactional
    public Page<Author> getAllAuthors(Pageable pageable) {
        try {
            return authorRepository.findAll(pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la récupération paginée des auteurs", e);
        }
    }

    @Override
    @Transactional
    public Optional<Author> getAuthorById(Long id) {
        try {
            return authorRepository.findById(id);
        } catch (Exception e) {
            e.printStackTrace();
            return Optional.empty();
        }
    }

    @Override
    @Transactional
    public Author createAuthor(Author author) {
        try {
            return authorRepository.save(author);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la création de l'auteur", e);
        }
    }

    @Override
    @Transactional
    public Author updateAuthor(Long id, Author author) {
        try {
            author.setAuthorId(id);
            return authorRepository.save(author);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la mise à jour de l'auteur", e);
        }
    }

    @Override
    @Transactional
    public void deleteAuthor(Long id) {
        try {
            authorRepository.deleteById(id);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la suppression de l'auteur", e);
        }
    }

    @Override
    @Transactional
    public Page<Author> searchAuthors(String keyword, Pageable pageable) {
        try {
            return authorRepository.findByNameContainingIgnoreCase(keyword, pageable);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Erreur lors de la recherche d'auteurs", e);
        }
    }
}