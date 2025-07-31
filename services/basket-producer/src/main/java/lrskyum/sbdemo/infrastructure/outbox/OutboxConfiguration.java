package lrskyum.sbdemo.infrastructure.outbox;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import lrskyum.sbdemo.application.events.OutboxService;
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
    public OutboxService integrationEventLogService(OutboxRepository outboxRepository) {
        return new OutboxServiceImpl(eventLogObjectMapper(), outboxRepository);
    }

    @Bean
    OutboxProcessor integrationEventProcessor(OutboxService outboxService, OutboxPublisher outboxPublisher) {
        return new OutboxProcessor(outboxService, outboxPublisher);
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
