package lrskyum.sbdemo.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RequiredArgsConstructor
@Configuration
public class OutboxProcessor {
    private static final Logger logger = LoggerFactory.getLogger(OutboxProcessor.class);

    private final OutboxService outboxService;
    private final OutboxPublisher outboxPublisher;

    @Scheduled(fixedDelay = 2000)
    @SchedulerLock(name = "IntegrationEventProcessorLock")
    @Transactional("connectionFactoryTransactionManager")
    public Flux<?> process() {
        var db = outboxService.retrieveEventLogsPendingToPublish();
        return db.hasElements().flatMapMany(hasElements -> {
            if (hasElements) {
                logger.info("integration events are ready to be published");
                return db.doOnNext(this::publish);
            } else {
                logger.info("No integration events found to publish");
                return Flux.empty();
            }
        });
    }

    private void publish(OutboxEntry eventLogEntry) {
        outboxService.markEventAsInProgress(eventLogEntry);
        outboxPublisher.publish(eventLogEntry);
        outboxService.markEventAsPublished(eventLogEntry);
    }
}
