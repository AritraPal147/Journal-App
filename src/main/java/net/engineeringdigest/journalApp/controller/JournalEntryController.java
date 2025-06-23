package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import net.engineeringdigest.journalApp.service.JournalEntryService;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    @Autowired
    private JournalEntryService journalEntryService;

    @GetMapping()
    public List<JournalEntry> getAll() {        //localhost:8080/journal GET
        return journalEntryService.getAllEntries();
    }

    @PostMapping()
    public JournalEntry createEntry(@RequestBody JournalEntry newEntry) {    //localhost:8080/journal POST
        newEntry.setDate(LocalDateTime.now());
        journalEntryService.saveEntry(newEntry);
        return newEntry;
    }

    @GetMapping("/id/{id}")
    public JournalEntry getEntryById(@PathVariable ObjectId id) {
        return journalEntryService.getEntryById(id).orElse(null);
    }

    @DeleteMapping("/id/{id}")
    public boolean deleteEntryById(@PathVariable ObjectId id) {
        journalEntryService.deleteEntryById(id);
        return true;
    }

    @PutMapping("/id/{id}")
    public JournalEntry updateEntryById(@PathVariable ObjectId id, @RequestBody JournalEntry updatedEntry) {
        JournalEntry oldEntry = journalEntryService.getEntryById(id).orElse(null);
        if (oldEntry != null) {
            oldEntry.setTitle((updatedEntry.getTitle() != null && !updatedEntry.getTitle().isEmpty()) ?
                    updatedEntry.getTitle() : oldEntry.getTitle());
            oldEntry.setContent((updatedEntry.getContent() != null && !updatedEntry.getContent().isEmpty())
                    ? updatedEntry.getContent() : oldEntry.getContent());
        }
        journalEntryService.saveEntry(oldEntry);
        return oldEntry;
    }

}
