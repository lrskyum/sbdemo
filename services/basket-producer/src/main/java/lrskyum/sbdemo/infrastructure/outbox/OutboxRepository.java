package lrskyum.sbdemo.infrastructure.outbox;

import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;
import java.util.UUID;

public interface OutboxRepository extends ListCrudRepository<OutboxEntry, Long> {
    Optional<OutboxEntry> findByEventId(UUID eventId);
}
