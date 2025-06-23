package net.engineeringdigest.journalApp.controller;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/journal")
public class JournalEntryController {

    private Map<Long, JournalEntry> journalEntries = new HashMap<Long, JournalEntry>();

    @GetMapping()
    public List<JournalEntry> getAll() {        //localhost:8080/journal GET
        return new ArrayList<JournalEntry>(journalEntries.values());
    }

    @PostMapping()
    public boolean createEntry(@RequestBody JournalEntry newEntry) {    //localhost:8080/journal POST
        journalEntries.put(newEntry.getId(), newEntry);
        return true;
    }

    @GetMapping("/id/{inputId}")
    public JournalEntry getEntryById(@PathVariable Long inputId) {
        return journalEntries.get(inputId);
    }

    @DeleteMapping("/id/{inputId}")
    public JournalEntry deleteEntryById(@PathVariable Long inputId) {
        return journalEntries.remove(inputId);
    }

    @PutMapping("/id/{inputId}")
    public JournalEntry updateEntryById(@PathVariable Long inputId, @RequestBody JournalEntry updatedEntry) {
        return journalEntries.put(inputId, updatedEntry);
    }

}
