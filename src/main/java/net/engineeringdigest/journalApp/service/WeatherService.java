package net.engineeringdigest.journalApp.service;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.constants.Placeholders;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class WeatherService {

    private final RestTemplate restTemplate;
    private final AppCache appCache;
    @Value("${api.weather.key}")
    private String apiKey;

    public WeatherResponse getWeather(String city) {
        // Gets the Cached APIs from AppCache and gets weather API from that
        String finalApi = appCache
                .getAppCache()
                .get(AppCache.Keys.WEATHER_API.name())
                .replace(Placeholders.CITY, city)
                .replace(Placeholders.API_KEY, apiKey);
        ResponseEntity<WeatherResponse> response = restTemplate.exchange(
                finalApi,
                HttpMethod.GET,
                null,
                WeatherResponse.class
        );
        return response.getBody();
    }
}
