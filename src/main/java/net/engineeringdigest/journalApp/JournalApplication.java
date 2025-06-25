package net.engineeringdigest.journalApp;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
public class JournalApplication {

    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }

    // Creating an instance of RestTemplate so that it can be injected into QuoteService and WeatherService
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

}