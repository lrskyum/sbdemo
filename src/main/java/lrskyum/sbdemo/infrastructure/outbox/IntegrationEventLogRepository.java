package lrskyum.sbdemo.infrastructure.outbox;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface IntegrationEventLogRepository extends ReactiveCrudRepository<IntegrationEventLogEntry, Long> {
    Optional<IntegrationEventLogEntry> findByEventId(UUID eventId);
}
