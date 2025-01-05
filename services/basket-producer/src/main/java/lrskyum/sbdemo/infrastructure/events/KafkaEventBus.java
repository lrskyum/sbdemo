package lrskyum.sbdemo.infrastructure.events;

import lombok.RequiredArgsConstructor;
import lrskyum.sbdemo.infrastructure.outbox.OutboxEntry;
import lrskyum.sbdemo.infrastructure.outbox.OutboxPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class KafkaEventBus implements OutboxPublisher {
    private static final Logger logger = LoggerFactory.getLogger(OutboxPublisher.class);

    private final KafkaTemplate<String, IntegrationEvent> kafkaTemplate;

    @Override
    public void publish(OutboxEntry eventLogEntry) {
        var event = eventLogEntry.getEvent();
        logger.info("Publishing integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
        kafkaTemplate.send(eventLogEntry.getTopic(), event);
    }
}
