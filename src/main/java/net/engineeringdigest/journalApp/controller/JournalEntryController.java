package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping()
    public ResponseEntity<List<JournalEntry>> getAll() {     //localhost:8080/journal GET
        List<JournalEntry> journalEntryList = journalEntryService.getAllEntries();
        if (!journalEntryList.isEmpty()) {
            return new ResponseEntity<>(journalEntryList, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @PostMapping()
    public ResponseEntity<JournalEntry> createEntry(@RequestBody JournalEntry newEntry) {    //localhost:8080/journal POST
        try {
            newEntry.setDate(LocalDateTime.now());
            journalEntryService.saveEntry(newEntry);
            return new ResponseEntity<>(newEntry, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

    }

    @GetMapping("/id/{id}")
    public ResponseEntity<JournalEntry> getEntryById(@PathVariable ObjectId id) {
        Optional<JournalEntry> journalEntry = journalEntryService.getEntryById(id);
        return journalEntry.map(entry ->
                new ResponseEntity<>(entry, HttpStatus.OK)).orElseGet(() ->
                    new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @DeleteMapping("/id/{id}")
    public ResponseEntity<JournalEntry> deleteEntryById(@PathVariable ObjectId id) {
        journalEntryService.deleteEntryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping("/id/{id}")
    public ResponseEntity<JournalEntry> updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry) {
        JournalEntry oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle((updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()) ?
                    updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent((updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty())
                    ? updatedEntry.getContent() : oldEntry.getContent());
            journalEntryService.saveEntry(oldEntry);
            return new ResponseEntity<>(oldEntry, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

}
