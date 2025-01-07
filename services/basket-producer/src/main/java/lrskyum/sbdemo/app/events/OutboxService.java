package lrskyum.sbdemo.app.events;


import lrskyum.sbdemo.infrastructure.outbox.OutboxEntry;
import reactor.core.publisher.Flux;

public interface OutboxService {
    Flux<OutboxEntry> retrieveEventLogsPendingToPublish();

    void markEventAsInProgress(OutboxEntry eventLogEntry);

    void markEventAsPublished(OutboxEntry eventLogEntry);

    void markEventAsFailed(OutboxEntry eventLogEntry);

    void saveEvent(IntegrationEvent event, String topic);
}
