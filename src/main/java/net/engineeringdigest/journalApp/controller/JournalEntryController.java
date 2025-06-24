package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.entity.User;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import net.engineeringdigest.journalApp.service.UserService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

// Controller class for CRUD operations on Journal Entries
@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    // Bean for JournalEntryService
    @Autowired
    private JournalEntryService journalEntryService;

    // Bean for UserService
    @Autowired
    private UserService userService;

    // HTTP Get request to get all the journal entries of the logged-in user -> localhost:8080/journal GET
    @GetMapping
    public ResponseEntity<?> getAllJournalEntriesOfUser() {
        // Using authentication to get the username that is sent along with the GET request
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
        List<JournalEntry> journalEntryList = user.getJournalEntries();
        if (journalEntryList != null && !journalEntryList.isEmpty()) {
            return new ResponseEntity<>(journalEntryList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // HTTP Post request to create new Journal Entry for logged-in user
    @PostMapping
    public ResponseEntity<JournalEntry> createJournalEntry(@RequestBody JournalEntry newEntry) {    //localhost:8080/journal POST
        try {
            Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            String userName = authentication.getName();
            newEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(newEntry, userName);
            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }

    // HTTP Get request to get Journal entry by id for the logged-in user
    @GetMapping("/id/{id}")
    public ResponseEntity<?> getJournalEntryById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
        List<JournalEntry> collect = user.getJournalEntries()
                .stream()
                .filter(
                        x -> x.getId().equals(id)
                ).collect(Collectors.toList()
                );
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
            if (journalEntry.isPresent()) {
                return new ResponseEntity<>(journalEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<JournalEntry> deleteEntryById(@PathVariable ObjectId id) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        boolean deleted = journalEntryService.deleteEntryById(id, userName);
        if (deleted) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateJournalEntryById(
            @PathVariable ObjectId id,
            @RequestBody JournalEntry updatedEntry
    ) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userName = authentication.getName();
        User user = userService.findByUsername(userName);
        List<JournalEntry> collect = user.getJournalEntries()
                .stream()
                .filter(x -> x.getId().equals(id)).
                collect(Collectors.toList()
                );
        if (!collect.isEmpty()) {
            Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
            if (journalEntry.isPresent()) {
                JournalEntry oldEntry = journalEntry.get();
                oldEntry.setTitle(
                        updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()
                                ? updatedEntry.getTitle()
                                : oldEntry.getTitle()
                );
                oldEntry.setContent(
                        updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty()
                                ? updatedEntry.getContent()
                                : oldEntry.getContent()
                );
                journalEntryService.saveEntry(oldEntry);
                return new ResponseEntity<>(oldEntry, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
