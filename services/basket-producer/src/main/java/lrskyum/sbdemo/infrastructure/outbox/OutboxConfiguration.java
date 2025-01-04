package lrskyum.sbdemo.infrastructure.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import net.javacrumbs.shedlock.core.LockProvider;
import net.javacrumbs.shedlock.provider.jdbctemplate.JdbcTemplateLockProvider;
import net.javacrumbs.shedlock.spring.annotation.EnableSchedulerLock;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.sql.DataSource;

@Configuration
@EnableScheduling
@EnableSchedulerLock(defaultLockAtMostFor = "10m")
public class OutboxConfiguration {

    @Bean
    public IntegrationEventLogService integrationEventLogService(IntegrationEventLogRepository integrationEventLogRepository) {
        return new IntegrationEventLogServiceImpl(eventLogObjectMapper(), integrationEventLogRepository);
    }

    @Bean
    IntegrationEventProcessor integrationEventProcessor(IntegrationEventLogService integrationEventLogService, IntegrationEventPublisher integrationEventPublisher) {
        return new IntegrationEventProcessor(integrationEventLogService, integrationEventPublisher);
    }

    @Bean
    public LockProvider lockProvider(DataSource dataSource) {
        return new JdbcTemplateLockProvider(JdbcTemplateLockProvider.Configuration
                .builder()
                .withJdbcTemplate(new JdbcTemplate(dataSource))
                .usingDbTime()
                .build());
    }

    private ObjectMapper eventLogObjectMapper() {
        ObjectMapper objectMapper = new ObjectMapper();
        objectMapper.registerModule(new JavaTimeModule());
        return objectMapper;
    }
}
