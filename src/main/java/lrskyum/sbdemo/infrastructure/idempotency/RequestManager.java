package lrskyum.sbdemo.infrastructure.idempotency;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RequestManager {
    Mono<Boolean> exist(UUID id);

    Mono<ClientRequest> createRequestForCommand(UUID id, String commandName);
}
