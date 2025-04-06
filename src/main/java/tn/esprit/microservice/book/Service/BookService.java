package tn.esprit.microservice.book.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microservice.book.Repositry.BookRepositry;
import tn.esprit.microservice.book.entity.Book;
import tn.esprit.microservice.book.entity.Status;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class BookService {
    @Autowired
    private BookRepositry bookRepositry;

    public List<Book> getAllBooks() {
        return bookRepositry.findAll();

    }

    public Optional<Book> getBookById(Integer id) {
        return bookRepositry.findById(id);
    }

    public Book createBook(Book book) {
        return bookRepositry.save(book);
    }

    public void deleteBook(Integer id) {
        bookRepositry.deleteById(id);
    }


    public  Book updateBook(Integer id, Book bookDetails) {
        Book book = bookRepositry.findById(id).get();
        book.setTitle(bookDetails.getTitle());
        book.setAuthorId(bookDetails.getAuthorId());
        book.setPublisherId(bookDetails.getPublisherId());
        book.setGenreId(bookDetails.getGenreId());
        return bookRepositry.save(book);
    }



    public List<Book> getBooksByFilters(String title, Integer author, Integer publisher, Integer genre) {
        // You can start with an empty list
        List<Book> filteredBooks = new ArrayList<>();

        if(title == null && author == null && publisher == null && genre == null){
            return getAllBooks();
        }
        // Apply filters conditionally based on the parameters
        if (title != null && !title.isEmpty()) {
            filteredBooks = bookRepositry.findBookByTitleContaining(title); // Replace with your repository method
        }

        if (author != null && author != 0) {
            filteredBooks = (filteredBooks.isEmpty()) ?
                    bookRepositry.findBookByAuthorId(author) :
                    filteredBooks.stream()
                            .filter(book -> book.getAuthorId().equals(author))
                            .collect(Collectors.toList());
        }

        if (publisher != null && publisher != 0) {
            filteredBooks = (filteredBooks.isEmpty()) ?
                    bookRepositry.findBookByPublisherId(publisher) :
                    filteredBooks.stream()
                            .filter(book -> book.getPublisherId().equals(publisher))
                            .collect(Collectors.toList());
        }

        if (genre != null && genre != 0) {
            filteredBooks = (filteredBooks.isEmpty()) ?
                    bookRepositry.findBookByGenreId(genre) :
                    filteredBooks.stream()
                            .filter(book -> book.getGenreId().equals(genre))
                            .collect(Collectors.toList());
        }



        return filteredBooks;
    }

    public Map<Integer, List<Book>> getBooksByAuthors(){
        List<Book> allBooks = bookRepositry.findAll();

        return allBooks.stream()
                .collect(Collectors.groupingBy(Book::getAuthorId));
    }

    public Map<Integer, Long> getBooksByPublisherStats(){
        return getAllBooks().stream()
                .collect(Collectors.groupingBy(
                        Book::getPublisherId,
                        Collectors.counting()
                ));
    }

    public Map<Status, Long> getBooksByStatusStats(){
     return getAllBooks().stream()
                .collect(Collectors.groupingBy(
                        Book::getStatus,
                        Collectors.counting()
                ));
    }

}



