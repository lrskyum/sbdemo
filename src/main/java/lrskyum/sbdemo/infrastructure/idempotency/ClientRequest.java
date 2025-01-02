package lrskyum.sbdemo.infrastructure.idempotency;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Builder(toBuilder = true)
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

    public static ClientRequest create(UUID extId, String cmd, Instant time) {
        return ClientRequest.builder()
                .extId(extId)
                .name(cmd)
                .time(time)
                .build();
    }
}
