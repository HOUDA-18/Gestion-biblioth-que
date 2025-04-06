package tn.esprit.microservice.publisher.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.microservice.publisher.Service.PubService;
import tn.esprit.microservice.publisher.entity.Publisher;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/publishers")
public class PubController {

    @Autowired  // Add this annotation
    private PubService service;  // Make field private

    @GetMapping("/all")
    public List<Publisher> getAllPublishers() {
        return service.getAllPublishers();
    }

    @GetMapping("getPub/{id}")
    public Publisher getPublisherById(@PathVariable Integer id) {
        return service.getPublisherById(id);
    }

    @PostMapping
    public Publisher createPublisher(@RequestBody Publisher publisher) {
        return service.createPublisher(publisher);
    }

    @PutMapping("/update")
    public Publisher updatePublisher(@RequestBody Publisher publisher) {
        return service.updatePublisher(publisher);
    }

    @DeleteMapping("delete/{id}")
    public void deletePublisher(@PathVariable Integer id) {
        service.deletePublisher(id);
    }



    @GetMapping("/search")
    public List<Publisher> searchPub(@RequestParam(required = false,value = "name") String name, @RequestParam(required = false,value = "localisation") String localisation) {
        return service.searchPub(name, localisation);
    }


    @GetMapping("/stats-demographic")
    public Map<String, Object> getPublisherDemographics() {

        return service.getDemographicStats();
    }


    @GetMapping("/nearby")
    public List<Publisher> getNearbyPublishers(
            @RequestParam String location,
            @RequestParam double radiusKm) {

        return service.findPublishersNearby(location, radiusKm);
    }

}