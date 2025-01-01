package lrskyum.sbdemo.app;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.business.domain.Address;
import lrskyum.sbdemo.business.domain.CustomerOrder;
import lrskyum.sbdemo.infrastructure.OrdersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.transaction.reactive.TransactionalOperator;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Slf4j
@Testcontainers
@Configuration
@Service
@Profile("tempdb")
public class DynamicDataSourceConfiguration implements DataInitializationService {

    @Value("${spring.datasource.containerPort:5432}")
    private int containerPort;

    @Value("${spring.datasource.localPort:5442}")
    private int localPort;

    private final OrdersRepository ordersRepository;
    private final TransactionalOperator transactionalOperator;

    public DynamicDataSourceConfiguration(OrdersRepository repo, TransactionalOperator txOp) {
        ordersRepository = repo;
        transactionalOperator = txOp;
    }

    @Bean
    @ServiceConnection
    @LiquibaseDataSource
    public DataSource dataSource() throws SQLException {
        var postgresContainer = postgresContainer();
        var ds = DataSourceBuilder.create()
                .driverClassName(postgresContainer.getDriverClassName())
                .url(postgresContainer.getJdbcUrl())
                .username(postgresContainer.getUsername())
                .password(postgresContainer.getPassword())
                .build();
        return ds;
    }

    private CustomerOrder createOrder(int i) {
        var address = Address.builder()
                .city("Aarhus")
                .country("Denmark")
                .zipCode("8000")
                .street("Lars Street " + i)
                .build();
        return CustomerOrder.create("Lars Description", address);
    }

    public Mono<Void> initializeFlux(ReactiveTransaction reactiveTransaction) {
        var tempOrders = IntStream.range(0, 3)
                .mapToObj(this::createOrder)
                .toList();
        var ordersFlux = Flux.fromIterable(tempOrders)
                .flatMap(ordersRepository::save) // Save each entity
                .then(); // Return a Mono<Void> signaling completion
        return ordersFlux; //.as(transactionalOperator::transactional);
    }

    public Mono<Void> initializeData() {
        return transactionalOperator.execute(this::initializeFlux)
                .then();
    }

    private PostgreSQLContainer<?> postgresContainer() {
        DockerImageName postgres = DockerImageName.parse("postgres");
        var postgresContainer = new PostgreSQLContainer<>(postgres)
                .withDatabaseName("postgres")
                .withUsername("postgres")
                .withPassword("postgres")
                .withReuse(true)
                .withExposedPorts(containerPort)
                .withCreateContainerCmdModifier(cmd -> cmd.withName("dynamic-postgres"))
                .withCreateContainerCmdModifier(cmd -> cmd.withHostConfig(
                        new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(localPort),
                                new ExposedPort(containerPort)))
                ));
        postgresContainer.start();
        return postgresContainer;
    }
}
