package net.engineeringdigest.journalApp.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@EnableTransactionManagement
public class TransactionConfig {

    // Implementation of PlatformTransactionManager - needed to enable transactions in Spring
    // The name of the method can be anything, no need to have specific name.
    @Bean
    public PlatformTransactionManager createTransactionManagerBean(MongoDatabaseFactory dbFactory) {
        return new MongoTransactionManager(dbFactory);
    }

}
