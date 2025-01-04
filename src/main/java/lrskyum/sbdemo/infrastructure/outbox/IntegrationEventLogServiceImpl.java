package lrskyum.sbdemo.infrastructure.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.infrastructure.events.IntegrationEvent;
import reactor.core.publisher.Flux;

@Slf4j
@RequiredArgsConstructor
public class IntegrationEventLogServiceImpl implements IntegrationEventLogService {

    private final ObjectMapper eventLogObjectMapper;
    private final IntegrationEventLogRepository integrationEventLogRepository;

    @Override
    public Flux<IntegrationEventLogEntry> retrieveEventLogsPendingToPublish() {
        return integrationEventLogRepository.findAll()
                .filter(eventLogEntry -> EventState.NotPublished.equals(eventLogEntry.getEventState()))
                .map(eventLogEntry -> {
                    eventLogEntry.setEvent(deserialize(eventLogEntry));
                    return eventLogEntry;
                });
    }

    @Override
    public void markEventAsInProgress(IntegrationEventLogEntry eventLogEntry) {
        updateEventStatus(eventLogEntry, EventState.InProgress);
    }

    @Override
    public void markEventAsPublished(IntegrationEventLogEntry eventLogEntry) {
        updateEventStatus(eventLogEntry, EventState.Published);
    }

    @Override
    public void markEventAsFailed(IntegrationEventLogEntry eventLogEntry) {
        updateEventStatus(eventLogEntry, EventState.PublishedFailed);
    }

    @Override
    public void saveEvent(IntegrationEvent event, String topic) {
        try {
            var eventLogEntry = new IntegrationEventLogEntry(event, eventLogObjectMapper.writeValueAsString(event), topic);
            integrationEventLogRepository.save(eventLogEntry).subscribe();
        } catch (JsonProcessingException e) {
            log.error("Error while creating IntegrationEventLogEntry for {}: ", event.getClass().getSimpleName(), e);
        }
    }

    private void updateEventStatus(IntegrationEventLogEntry eventLogEntry, EventState eventState) {
        eventLogEntry.setEventState(eventState);
        if (EventState.InProgress.equals(eventState))
            eventLogEntry.incrementTimesSent();

        integrationEventLogRepository.save(eventLogEntry).subscribe();
    }

    @SneakyThrows
    private IntegrationEvent deserialize(IntegrationEventLogEntry eventLogEntry) {
        var x = eventLogObjectMapper.readValue(eventLogEntry.getContent(), Class.forName(eventLogEntry.getEventTypeName()));
        return (IntegrationEvent) x;
    }
}
