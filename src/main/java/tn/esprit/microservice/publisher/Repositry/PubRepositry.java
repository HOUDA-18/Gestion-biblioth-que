package tn.esprit.microservice.publisher.Repositry;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.microservice.publisher.entity.Publisher;

import java.util.List;

@Repository
public interface PubRepositry extends JpaRepository<Publisher, Integer> {

    List<Publisher>findByName(String name);
    List<Publisher>findByLocalisation(String localisation);

}
