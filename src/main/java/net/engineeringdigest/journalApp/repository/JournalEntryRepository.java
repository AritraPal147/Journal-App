package net.engineeringdigest.journalApp.repository;

import net.engineeringdigest.journalApp.entity.JournalEntry;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

// This is just the interface that has all the methods from MongoRepository.
// The types for MongoRepository are: <model_class, datatype_of_primary_key_of_model_class>
public interface JournalEntryRepository extends MongoRepository<JournalEntry, ObjectId> {}
