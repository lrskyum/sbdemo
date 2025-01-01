package lrskyum.sbdemo.app;

import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import io.r2dbc.spi.ConnectionFactory;
import io.r2dbc.spi.ConnectionFactoryOptions;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.business.domain.Address;
import lrskyum.sbdemo.business.domain.CustomerOrder;
import lrskyum.sbdemo.infrastructure.OrdersRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseDataSource;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.boot.r2dbc.ConnectionFactoryBuilder;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
import java.util.stream.IntStream;

@Slf4j
@Testcontainers
@Configuration
@Service
@Profile("tempdb")
public class DynamicDataSourceConfiguration {

    private static PostgreSQLContainer<?> sharedPostgresContainer;

    @Value("${spring.datasource.containerPort:5432}")
    private int containerPort;

    @Value("${spring.datasource.localPort:5442}")
    private int localPort;

    @Bean
    @Primary
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

    @Bean
    @Primary
    public ConnectionFactory connectionFactory() {
        var postgresContainer = postgresContainer();
        var options = ConnectionFactoryOptions.builder()
                .option(ConnectionFactoryOptions.DRIVER, "postgresql")
                .option(ConnectionFactoryOptions.HOST, postgresContainer.getHost())
                .option(ConnectionFactoryOptions.PORT, postgresContainer.getFirstMappedPort())
                .option(ConnectionFactoryOptions.USER, postgresContainer.getUsername())
                .option(ConnectionFactoryOptions.PASSWORD, postgresContainer.getPassword())
                .option(ConnectionFactoryOptions.DATABASE, postgresContainer.getDatabaseName());
        return ConnectionFactoryBuilder
                .withOptions(options)
                .build();
    }

    private PostgreSQLContainer<?> postgresContainer() {
        if (sharedPostgresContainer != null) {
            return sharedPostgresContainer;
        }

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
        sharedPostgresContainer = postgresContainer;
        return postgresContainer;
    }
}
