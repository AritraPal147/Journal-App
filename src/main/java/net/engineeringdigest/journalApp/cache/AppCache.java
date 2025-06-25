package net.engineeringdigest.journalApp.cache;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import net.engineeringdigest.journalApp.entity.ConfigJournalAppEntity;
import net.engineeringdigest.journalApp.repository.ConfigJournalAppRepository;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Component
@RequiredArgsConstructor
@Getter
public class AppCache {

    private final ConfigJournalAppRepository configJournalAppRepository;
    private Map<String, String> appCache;

    @PostConstruct
    public void init() {
        appCache = new HashMap<>();
        List<ConfigJournalAppEntity> configList = configJournalAppRepository.findAll();
        for (ConfigJournalAppEntity configJournalAppEntity : configList) {
            appCache.put(configJournalAppEntity.getKey(), configJournalAppEntity.getValue());
        }
    }

    public enum Keys {
        WEATHER_API,
        QUOTE_API
    }
}
