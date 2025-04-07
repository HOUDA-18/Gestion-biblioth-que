package tn.esprit.microservice.loan.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

@Service
public class HolidayCheckService {
    private static final String HOLIDAY_API_URL = "https://date.nager.at/api/v3/PublicHolidays/{year}/{countryCode}";

    @Autowired
    private RestTemplate restTemplate;

    public boolean isPublicHoliday(Date date, String countryCode) {
        try {
            // Extraire l'année de la date
            Calendar cal = Calendar.getInstance();
            cal.setTime(date);
            int year = cal.get(Calendar.YEAR);

            // Appeler l'API
            ResponseEntity<List<Map>> response = restTemplate.exchange(
                    HOLIDAY_API_URL
                            .replace("{year}", String.valueOf(year))
                            .replace("{countryCode}", countryCode),
                    HttpMethod.GET,
                    null,
                    new ParameterizedTypeReference<List<Map>>() {}
            );

            // Vérifier si la date existe dans la liste
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            String targetDate = sdf.format(date);

            return response.getBody().stream()
                    .anyMatch(holiday -> holiday.get("date").equals(targetDate));

        } catch (Exception e) {
            // Fallback en cas d'erreur
            return false;
        }
    }
}
