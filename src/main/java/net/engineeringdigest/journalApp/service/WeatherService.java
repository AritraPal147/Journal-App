package net.engineeringdigest.journalApp.service;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class WeatherService {

    private static final String apiKey = "30ee0204faf755745e681d8623a1a10d";
    private static final String api =
            "http://api.weatherstack.com/current?access_key=API_KEY&query=CITY";
    private final RestTemplate restTemplate;

    public WeatherResponse getWeather(String city) {
        String finalApi = api.replace("CITY", city).replace("API_KEY", apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                finalApi,
                HttpMethod.GET,
                null,
                WeatherResponse.class
        );
        return response.getBody();
    }
}
