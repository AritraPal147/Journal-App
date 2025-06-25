package net.engineeringdigest.journalApp.service;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.api.response.QuoteResponse;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Component
@RequiredArgsConstructor
public class QuoteService {

    private static final String api =
            "http://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en";
    private final RestTemplate restTemplate;

    public QuoteResponse getQuote() {
        HttpHeaders headers = new HttpHeaders();
        headers.add("User-Agent", "Mozilla/5.0"); // Mimic a browser

        HttpEntity<String> entity = new HttpEntity<>(headers);

        ResponseEntity<QuoteResponse> response = restTemplate.exchange(
                api,
                HttpMethod.GET,
                entity,
                QuoteResponse.class
        );
        System.out.println("Hello world");
        return response.getBody();
    }

}
