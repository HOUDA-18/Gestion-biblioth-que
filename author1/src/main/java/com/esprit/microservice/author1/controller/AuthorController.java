package com.esprit.microservice.author1.controller;

import com.esprit.microservice.author1.model.Author;
import com.esprit.microservice.author1.service.AuthorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/author")
public class AuthorController {

    @Autowired
    private AuthorService authorService;

    // Version paginée
    @GetMapping("/all")
    public ResponseEntity<Page<Author>> getAllAuthors(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "authorId,asc") String[] sort) {

        try {
            Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

            Page<Author> authors = authorService.getAllAuthors(pageable);
            return ResponseEntity.ok(authors);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Version non-paginée (optionnelle - à supprimer si non nécessaire)
    @GetMapping("/all-list")
    public ResponseEntity<List<Author>> getAllAuthorsList() {
        try {
            // Implémentation alternative si vous gardez l'ancienne méthode dans le service
            Page<Author> page = authorService.getAllAuthors(Pageable.unpaged());
            return ResponseEntity.ok(page.getContent());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthorById(@PathVariable Long id) {
        try {
            Optional<Author> author = authorService.getAuthorById(id);
            return author.map(ResponseEntity::ok)
                    .orElseGet(() -> ResponseEntity.notFound().build());
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Endpoint de recherche
    @GetMapping("/search")
    public ResponseEntity<Page<Author>> searchAuthors(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name,asc") String[] sort) {


            Sort.Direction direction = sort[1].equalsIgnoreCase("desc") ? Sort.Direction.DESC : Sort.Direction.ASC;
            Pageable pageable = PageRequest.of(page, size, Sort.by(direction, sort[0]));

            Page<Author> authors = authorService.searchAuthors(keyword, pageable);
            return ResponseEntity.ok(authors);


    }

    @PostMapping("/add")
    public ResponseEntity<Author> createAuthor(@RequestBody Author author) {

            Author createdAuthor = authorService.createAuthor(author);
            return ResponseEntity.status(HttpStatus.CREATED).body(createdAuthor);

    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Author> updateAuthor(@PathVariable Long id, @RequestBody Author author) {
        try {
            Author updatedAuthor = authorService.updateAuthor(id, author);
            return ResponseEntity.ok(updatedAuthor);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Void> deleteAuthor(@PathVariable Long id) {
        try {
            authorService.deleteAuthor(id);
            return ResponseEntity.noContent().build();
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    // Injection de la propriété welcome.message depuis AuthorService.properties
    @Value("${welcome.message}")
    private String welcomeMessage;

    // Injection de la propriété some.config.value
    @Value("${some.config.value}")
    private String someConfigValue;

    @GetMapping("/welcome")
    public String getWelcomeMessage() {
        return welcomeMessage;
    }

    @GetMapping("/config")
    public String getSomeConfigValue() {
        return someConfigValue;
    }


}