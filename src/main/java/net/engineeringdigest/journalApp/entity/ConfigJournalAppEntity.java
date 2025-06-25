package net.engineeringdigest.journalApp.entity;

import lombok.Getter;
import lombok.Setter;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Document(collection = "config_journal_app")
public class ConfigJournalAppEntity {
    @Id
    private ObjectId id;
    private String key;
    private String value;
}
