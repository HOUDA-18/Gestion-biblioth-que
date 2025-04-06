package tn.esprit.microservice.book.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.microservice.book.entity.Book;

import java.util.List;
import java.util.Map;

@Repository

public interface BookRepositry extends JpaRepository<Book, Integer> {
   public List<Book> findBookByTitleContaining(String title);
    public List<Book> findBookByAuthorId(int authorId);
    public List<Book> findBookByPublisherId(int publisherId);
    public List<Book> findBookByGenreId(int genreId);

}