package lrskyum.sbdemo.infrastructure.idempotency;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Builder
@Table
public class ClientRequest {
    @Id
    @Getter(AccessLevel.PRIVATE)
    private Long id;

    @Getter
    private UUID extId;

    @Getter
    private String name;

    @Getter
    private Instant time;
}
