package lrskyum.sbdemo.infrastructure.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.app.events.IntegrationEvent;
import lrskyum.sbdemo.app.events.OutboxService;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

    private final ObjectMapper eventLogObjectMapper;
    private final OutboxRepository outboxRepository;

    @Override
    public Flux<OutboxEntry> retrieveEventLogsPendingToPublish() {
        return outboxRepository.findAll()
                .filter(eventLogEntry -> EventState.NotPublished.equals(eventLogEntry.getEventState()))
                .map(eventLogEntry -> {
                    eventLogEntry.setEvent(deserialize(eventLogEntry));
                    return eventLogEntry;
                });
    }

    @Override
    public void markEventAsInProgress(OutboxEntry eventLogEntry) {
        updateEventStatus(eventLogEntry, EventState.InProgress);
    }

    @Override
    public void markEventAsPublished(OutboxEntry eventLogEntry) {
        updateEventStatus(eventLogEntry, EventState.Published);
    }

    @Override
    public void markEventAsFailed(OutboxEntry eventLogEntry) {
        updateEventStatus(eventLogEntry, EventState.PublishedFailed);
    }

    @Override
    public void saveEvent(IntegrationEvent event, String topic) {
        try {
            var eventLogEntry = new OutboxEntry(event, eventLogObjectMapper.writeValueAsString(event), topic);
            outboxRepository.save(eventLogEntry).subscribe();
        } catch (JsonProcessingException e) {
            log.error("Error while creating IntegrationEventLogEntry for {}: ", event.getClass().getSimpleName(), e);
        }
    }

    private void updateEventStatus(OutboxEntry eventLogEntry, EventState eventState) {
        eventLogEntry.setEventState(eventState);
        if (EventState.InProgress.equals(eventState))
            eventLogEntry.incrementTimesSent();

        outboxRepository.save(eventLogEntry).subscribe();
    }

    @SneakyThrows
    private IntegrationEvent deserialize(OutboxEntry eventLogEntry) {
        var x = eventLogObjectMapper.readValue(eventLogEntry.getContent(), Class.forName(eventLogEntry.getEventTypeName()));
        return (IntegrationEvent) x;
    }
}
