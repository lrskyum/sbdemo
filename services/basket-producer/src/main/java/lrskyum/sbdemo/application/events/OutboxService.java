package lrskyum.sbdemo.application.events;


import lrskyum.sbdemo.infrastructure.outbox.OutboxEntry;

import java.util.List;

public interface OutboxService {
    List<OutboxEntry> retrieveOutboxEntriesPendingToPublish();

    void markEventAsInProgress(OutboxEntry eventLogEntry);

    void markEventAsPublished(OutboxEntry eventLogEntry);

    void markEventAsFailed(OutboxEntry eventLogEntry);

    void saveEvent(IntegrationEvent event, String topic);
}
