package tn.esprit.microservice.loan.proxy;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import tn.esprit.microservice.loan.dto.Book;

import java.util.List;

@FeignClient(name = "BookService",url = "http://localhost:8091") // le nom enregistré dans Eureka
public interface BookClient {

    @RequestMapping("books/available")
    public List<Book> getAvailableBooks();
}