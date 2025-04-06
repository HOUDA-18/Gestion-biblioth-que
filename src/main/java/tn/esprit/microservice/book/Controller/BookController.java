package tn.esprit.microservice.book.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.book.Service.BookService;
import tn.esprit.microservice.book.entity.Book;
import tn.esprit.microservice.book.entity.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/books")
public class BookController {
    @Autowired
    private BookService service;

    @GetMapping("/all")
    public List<Book> getAllBooks(@RequestParam(value = "title", required = false) String title,
                                  @RequestParam(value = "author", required = false) Integer author,
                                  @RequestParam(value = "publisher", required = false) Integer publisher,
                                  @RequestParam(value = "genre", required = false) Integer genre) {

        return service.getBooksByFilters(title, author, publisher, genre);
    }

    @GetMapping("/{id}")
    public Optional<Book> getBookById(@PathVariable Integer id) {

        return   service.getBookById(id);
    }

    @PostMapping
    public Book createBook(@RequestBody Book book) {
        return service.createBook(book);
    }

    @PutMapping("/{id}")
    public Book updateBook(@PathVariable Integer id, @RequestBody Book bookDetails) {
        return service.updateBook(id, bookDetails);
    }

    @DeleteMapping("/{id}")
    public void deleteBook(@PathVariable Integer id) {
        service.deleteBook(id);

    }

    @GetMapping("/getBooksByAuthors")
    public Map<Integer, List<Book>> getBooksByAuthors(){
        return service.getBooksByAuthors();
    }

    @GetMapping("/getBooksCountByPublisher")
    public Map<Integer, Long> getBooksCountByPublisher(){
        return service.getBooksByPublisherStats();
    }

    @GetMapping("/getBooksCountByStatus")
    public Map<Status, Long> getBooksCountByStatus(){
        return service.getBooksByStatusStats();
    }

}
