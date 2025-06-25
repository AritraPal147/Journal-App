package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.api.response.QuoteResponse;
import net.engineeringdigest.journalApp.api.response.WeatherResponse;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.QuoteService;
import net.engineeringdigest.journalApp.service.UserService;
import net.engineeringdigest.journalApp.service.WeatherService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
    private final WeatherService weatherService;
    private final QuoteService quoteService;

    @PutMapping
    public ResponseEntity<?> updateUser(@RequestBody User user) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User userInDB = userService.findByUsername(userName);
        userInDB.setPassword(user.getPassword());
        userInDB.setUserName(user.getUserName());
        userService.saveUser(userInDB);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteUserById() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        userService.deleteUserByUserName(authentication.getName());
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @GetMapping
    @Transactional
    public ResponseEntity<?> getGreetings() {
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String greetingMessage = "Hi " + authentication.getName();

            WeatherResponse weatherResponse = weatherService.getWeather("Hyderabad");
            if (weatherResponse != null) {
                String weatherDescription = String.join(", ",
                        weatherResponse.getCurrent().getWeatherDescriptions());
                greetingMessage += "\n\nThe weather today is: "
                        + weatherDescription
                        + ". The temperature is "
                        + weatherResponse.getCurrent().getTemperature()
                        + "C. It feels like "
                        + weatherResponse.getCurrent().getFeelsLike()
                        + "C.";
            }

            QuoteResponse quoteResponse = quoteService.getQuote();
            if (quoteResponse != null) {
                greetingMessage += "\n\nQuote of the day is:\n"
                        + quoteResponse.getQuoteText()
                        + "\n-By "
                        + quoteResponse.getQuoteAuthor();
            }

            return new ResponseEntity<>(greetingMessage, HttpStatus.OK);
        } catch (Exception e) {
            System.out.println(e);
            throw new RuntimeException("An error occurred while getting greetings: ", e);
        }
    }

}
