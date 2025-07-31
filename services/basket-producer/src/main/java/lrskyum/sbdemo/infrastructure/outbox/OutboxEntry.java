package lrskyum.sbdemo.infrastructure.outbox;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lrskyum.sbdemo.application.events.IntegrationEvent;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Table("outbox")
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OutboxEntry {
    @Id
    @Getter(AccessLevel.PRIVATE)
    private Long id;

    private UUID eventId;
    private Instant creationTime;
    private String eventTypeName;
    private String content;
    private EventState eventState;
    private Integer timesSent;
    private String topic;

    @Transient
    @Setter(AccessLevel.PUBLIC)
    private IntegrationEvent event;

    OutboxEntry(IntegrationEvent event, String content, String topic) {
        eventId = event.getId();
        creationTime = Instant.now();
        eventTypeName = event.getClass().getName();
        this.content = content;
        eventState = EventState.NotPublished;
        timesSent = 0;
        this.topic = topic;
    }

    void incrementTimesSent() {
        timesSent++;
    }
}
