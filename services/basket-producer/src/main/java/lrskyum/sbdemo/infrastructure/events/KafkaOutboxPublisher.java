package lrskyum.sbdemo.infrastructure.events;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.infrastructure.outbox.OutboxEntry;
import lrskyum.sbdemo.infrastructure.outbox.OutboxPublisher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class KafkaOutboxPublisher implements OutboxPublisher {

    private final ObjectMapper eventLogObjectMapper;
    private final KafkaTemplate<String, String> kafkaTemplate;

    @SneakyThrows
    @Override
    public void publish(OutboxEntry eventLogEntry) {
        var event = eventLogEntry.getEvent();
        log.info("Publishing integration event: {} ({})", event.getId(), event.getClass().getSimpleName());
        kafkaTemplate.send(eventLogEntry.getTopic(), eventLogObjectMapper.writeValueAsString(event));
    }
}
