package tn.esprit.microservice.publisher.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.microservice.publisher.Repositry.PubRepositry;
import tn.esprit.microservice.publisher.entity.GeoCoordinates;
import tn.esprit.microservice.publisher.entity.Publisher;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class PubService {
    @Autowired
    private PubRepositry pubRepositry;
    @Autowired
    private GeocodingService geocodingService;
    public List<Publisher> getAllPublishers() {
        return pubRepositry.findAll();
    }
    public Publisher getPublisherById(Integer id) {
        return pubRepositry.findById(id).get();}
    public Publisher createPublisher(Publisher publisher) {
        return pubRepositry.save(publisher);
    }
    public Publisher updatePublisher(Publisher publisher) {return pubRepositry.save(publisher);
    }

    public void deletePublisher(Integer id) {
        pubRepositry.deleteById(id);
    }

    public List<Publisher> searchPub(String name , String localisation){
        if (name!=null){
            return pubRepositry.findByName(name);
        } else if (localisation!=null) {
            return pubRepositry.findByLocalisation(localisation);

        }
        return pubRepositry.findAll();
    }

    public Map<String, Object> getDemographicStats() {
        List<Publisher> publishers = pubRepositry.findAll();

        Map<String, Long> ageDistribution = publishers.stream()
                .collect(Collectors.groupingBy(
                        p -> {
                            int age = p.getAge();
                            if (age < 20) return "0-19";
                            if (age < 40) return "20-39";
                            if (age < 60) return "40-59";
                            return "60+";
                        },
                        Collectors.counting()
                ));

        Map<String, Long> locationDistribution = publishers.stream()
                .collect(Collectors.groupingBy(
                        Publisher::getLocalisation,
                        Collectors.counting()
                ));

        double averageAge = publishers.stream()
                .mapToInt(Publisher::getAge)
                .average()
                .orElse(0);

        Map<String, Object> stats = new HashMap<>();
        stats.put("totalPublishers", publishers.size());
        stats.put("ageDistribution", ageDistribution);
        stats.put("locationDistribution", locationDistribution);
        stats.put("averageAge", averageAge);

        return stats;
    }



    public List<Publisher> findPublishersNearby(String location, double radiusKm) {


            GeoCoordinates coords = geocodingService.getCoordinates(location);
            return pubRepositry.findAll().stream()
                    .filter(p -> {
                        GeoCoordinates publisherCoords = geocodingService.getCoordinates(p.getLocalisation());
                        return calculateDistance(coords, publisherCoords) <= radiusKm;
                    })
                    .collect(Collectors.toList());
        /*catch (Exception e){
            System.out.println("erreur");
            return new ArrayList<Publisher>();
        }*/



    }

    private double calculateDistance(GeoCoordinates coord1, GeoCoordinates coord2) {
        // Haversine
        double lat1 = Math.toRadians(coord1.getLatitude());
        double lon1 = Math.toRadians(coord1.getLongitude());
        double lat2 = Math.toRadians(coord2.getLatitude());
        double lon2 = Math.toRadians(coord2.getLongitude());

        double dlon = lon2 - lon1;
        double dlat = lat2 - lat1;
        double a = Math.pow(Math.sin(dlat / 2), 2)
                + Math.cos(lat1) * Math.cos(lat2) * Math.pow(Math.sin(dlon / 2), 2);

        return 6371 * 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
    }



}
