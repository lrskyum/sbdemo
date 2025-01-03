package lrskyum.sbdemo.infrastructure.outbox;


import lrskyum.sbdemo.infrastructure.events.IntegrationEvent;
import reactor.core.publisher.Flux;

import java.util.List;

public interface IntegrationEventLogService {
    Flux<IntegrationEventLogEntry> retrieveEventLogsPendingToPublish();

    void markEventAsInProgress(IntegrationEventLogEntry eventLogEntry);

    void markEventAsPublished(IntegrationEventLogEntry eventLogEntry);

    void markEventAsFailed(IntegrationEventLogEntry eventLogEntry);

    void saveEvent(IntegrationEvent event, String topic);
}
