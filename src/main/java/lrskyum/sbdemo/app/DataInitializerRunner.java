package lrskyum.sbdemo.app;

import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class DataInitializerRunner implements CommandLineRunner {

    private final DataInitializationService dataInitializationService;

    public DataInitializerRunner(DataInitializationService dataInitializationService) {
        this.dataInitializationService = dataInitializationService;
    }

    @Override
    public void run(String... args) {
        dataInitializationService.initializeData()
                .doOnError(e -> System.err.println("Error initializing data: " + e.getMessage()))
                .doOnSuccess(unused -> System.out.println("Data initialized successfully"))
                .block(); // Block only during startup to ensure data is initialized
    }
}