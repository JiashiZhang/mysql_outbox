package org.example.mysql_outbox.initializer;

import org.example.mysql_outbox.service.DatabaseService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DatabaseInitializer implements CommandLineRunner {

    private final DatabaseService databaseService;

    public DatabaseInitializer(DatabaseService databaseService) {
        this.databaseService = databaseService;
    }

    @Override
    public void run(String... args) {
        System.out.println("Running DatabaseInitializer...");
        databaseService.createTable();
        System.out.println("Database initialization completed.");

        System.out.println("Inserting one million records...");
        databaseService.insertOneMillionRecords();
        System.out.println("Data insertion completed.");
    }

}