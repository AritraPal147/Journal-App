package net.engineeringdigest.journalApp.service;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.api.response.QuoteResponse;
import net.engineeringdigest.journalApp.cache.AppCache;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class QuoteService {

    private final AppCache appCache;
    private final RestTemplate restTemplate;

    public QuoteResponse getQuote() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0"); // Mimic a browser

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<QuoteResponse> response = restTemplate.exchange(
                appCache.getAppCache().get(AppCache.Keys.QUOTE_API.name()),
                HttpMethod.GET,
                entity,
                QuoteResponse.class
        );
        return response.getBody();
    }

}
