package lrskyum.sbdemo.infrastructure.outbox;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import net.javacrumbs.shedlock.spring.annotation.SchedulerLock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Configuration
public class OutboxProcessor {
    private final OutboxService outboxService;
    private final OutboxPublisher outboxPublisher;

    @Scheduled(fixedDelay = 5000)
    @SchedulerLock(name = "IntegrationEventProcessorLock")
    @Transactional("connectionFactoryTransactionManager")
    public Flux<?> process() {
        var db = outboxService.retrieveEventLogsPendingToPublish();
        return db.hasElements().flatMapMany(hasElements -> {
            if (hasElements) {
                log.info("integration events are ready to be published");
                return db.doOnNext(this::publish);
            } else {
                log.info("No integration events found to publish");
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
