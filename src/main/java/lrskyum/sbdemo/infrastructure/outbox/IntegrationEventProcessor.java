package lrskyum.sbdemo.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import reactor.core.publisher.Flux;

@RequiredArgsConstructor
@Configuration
public class IntegrationEventProcessor {
    private static final Logger logger = LoggerFactory.getLogger(IntegrationEventProcessor.class);

    private final IntegrationEventLogService integrationEventLogService;
    private final IntegrationEventPublisher integrationEventPublisher;

    @Scheduled(fixedDelay = 2000)
    @SchedulerLock(name = "IntegrationEventProcessorLock")
    public void process() {
        var db = integrationEventLogService.retrieveEventLogsPendingToPublish();
        db.hasElements().flatMapMany(hasElements -> {
            if (hasElements) {
                logger.info("integration events are ready to be published");
                return db.doOnNext(this::publish);
            } else {
                logger.info("No integration events found to publish");
                return Flux.empty();
            }
        }).subscribe();
    }

    private void publish(IntegrationEventLogEntry eventLogEntry) {
        integrationEventLogService.markEventAsInProgress(eventLogEntry);
        integrationEventPublisher.publish(eventLogEntry);
        integrationEventLogService.markEventAsPublished(eventLogEntry);
    }
}
