package lrskyum.sbdemo.infrastructure.outbox;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface OutboxRepository extends ReactiveCrudRepository<OutboxEntry, Long> {
    Optional<OutboxEntry> findByEventId(UUID eventId);
}
