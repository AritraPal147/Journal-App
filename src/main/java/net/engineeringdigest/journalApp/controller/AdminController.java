package net.engineeringdigest.journalApp.controller;

import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.cache.AppCache;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;
    private final AppCache appCache;

    @GetMapping("/all-users")
    public ResponseEntity<?> getAllUsers() {
        List<User> allUsers = userService.getAllUsers();
        if (allUsers != null && !allUsers.isEmpty()) {
            return new ResponseEntity<>(allUsers, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping("/add-new-admin")
    public void addNewAdmin(@RequestBody User user) {
        userService.saveNewAdmin(user);
    }

    @GetMapping("/clear-app-cache")
    public void clearAppCache() {
        appCache.init();
    }

}
