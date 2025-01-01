package lrskyum.sbdemo;

import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.app.DataInitializationService;
import lrskyum.sbdemo.business.domain.Address;
import lrskyum.sbdemo.business.domain.CustomerOrder;
import lrskyum.sbdemo.infrastructure.OrdersRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.r2dbc.R2dbcTransactionManagerAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.stream.IntStream;


@SpringBootApplication(exclude = {
//        R2dbcAutoConfiguration.class,
//        R2dbcDataAutoConfiguration.class,
//        R2dbcRepositoriesAutoConfiguration.class,
//        R2dbcTransactionManagerAutoConfiguration.class,
//        JpaTransactionManager.class
})
@Configuration
@Slf4j
public class SbdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(SbdemoApplication.class, args);
    }

//    @Bean
//    public CommandLineRunner commandLineRunner(OrdersRepository repository) {
//        return args -> {
//            IntStream.range(0, 3).forEach(i -> {
//                var address = Address.builder()
//                        .city("Aarhus")
//                        .country("Denmark")
//                        .zipCode("8000")
//                        .street("Lars Street " + i)
//                        .build();
//                var order = CustomerOrder.create("Lars Description", address);
//                repository.save(order).block();
//                log.info("Order placed successfully using SLF4J");
//            });
//        };
//    }

//    @Bean
//    public CommandLineRunner commandLineRunner(DataInitializationService  service) {
//        service.initializeData()
//                .doOnError(e -> System.err.println("Error initializing data: " + e.getMessage()))
//                .doOnSuccess(unused -> System.out.println("Data initialized successfully"))
//                .block(); // Block only during startup to ensure data is initialized
//    }
//
//    @Bean
//    public CommandLineRunner addInitialData(JdbcTemplate jdbcTemplate) {
//        return args -> {
//            jdbcTemplate.update("INSERT INTO customer_order (lars) VALUES ('Sample Data')");
//            System.out.println("Data inserted into the database");
//        };
//    }
}
