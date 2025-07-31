package lrskyum.sbdemo.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.app.events.OutboxService;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class OutboxProcessor {
    private final OutboxService outboxService;
    private final OutboxPublisher outboxPublisher;

    @Scheduled(fixedDelay = 5000)
    @SchedulerLock(name = "IntegrationEventProcessorLock")
    @Transactional
    public List<?> process() {
        var entries = outboxService.retrieveOutboxEntriesPendingToPublish();
        entries.forEach(this::publish);
        return entries;
    }

    private void publish(OutboxEntry eventLogEntry) {
        outboxService.markEventAsInProgress(eventLogEntry);
        outboxPublisher.publish(eventLogEntry);
        outboxService.markEventAsPublished(eventLogEntry);
    }
}
