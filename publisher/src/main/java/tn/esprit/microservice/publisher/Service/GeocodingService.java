package tn.esprit.microservice.publisher.Service;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tn.esprit.microservice.publisher.entity.GeoCoordinates;

@Service
public class GeocodingService {

    private final RestTemplate restTemplate = new RestTemplate();

    public GeoCoordinates getCoordinates(String location) {
        String url = UriComponentsBuilder.fromHttpUrl("https://nominatim.openstreetmap.org/search")
                .queryParam("q", location)
                .queryParam("format", "json")
                .queryParam("limit", 1)
                .toUriString();

        String response = restTemplate.getForObject(url, String.class);
        JSONArray jsonArray = new JSONArray(response);

        if (jsonArray.isEmpty()) {
            throw new RuntimeException("Location not found: " + location);
        }

        JSONObject result = jsonArray.getJSONObject(0);
        double lat = result.getDouble("lat");
        double lon = result.getDouble("lon");

        return new GeoCoordinates(lat, lon);
    }
}
