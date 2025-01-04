package lrskyum.sbdemo.infrastructure.idempotency;

import reactor.core.publisher.Mono;

import java.util.UUID;

public interface RequestManager {
    Mono<Boolean> exist(String id);

    Mono<ClientRequest> createRequestForCommand(String id, String commandName);
}
