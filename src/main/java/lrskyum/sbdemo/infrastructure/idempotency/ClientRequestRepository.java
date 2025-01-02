package lrskyum.sbdemo.infrastructure.idempotency;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Transactional("connectionFactoryTransactionManager")
public interface ClientRequestRepository extends ReactiveCrudRepository<ClientRequest, UUID> {
    Mono<Boolean> existsByExtId(UUID extId);
}
