package lrskyum.sbdemo.app.events;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Getter
@Builder
public class IntegrationEvent {
    private final UUID id;
    private final Instant creationDate;

    public IntegrationEvent() {
        this(UUID.randomUUID(), Instant.now());
    }
}
