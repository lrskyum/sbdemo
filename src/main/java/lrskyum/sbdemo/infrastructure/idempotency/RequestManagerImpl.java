package lrskyum.sbdemo.infrastructure.idempotency;

import lombok.RequiredArgsConstructor;
import lrskyum.sbdemo.business.exceptions.OrderingDomainException;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;

@RequiredArgsConstructor
@Service
public class RequestManagerImpl implements RequestManager {

    private final ClientRequestRepository clientRequestRepository;

    @Override
    public Mono<Boolean> exist(UUID id) {
        return clientRequestRepository.existsByExtId(id);
    }

    @Override
    public Mono<ClientRequest> createRequestForCommand(UUID id, String commandName) {
        return exist(id)
                .flatMap(exists -> {
                    if (exists)
                        return Mono.error(new OrderingDomainException("Request with id: %s already exists".formatted(id)));
                    else {
                        var cr = new ClientRequest.ClientRequestBuilder()
                                .extId(id)
                                .name(commandName)
                                .time(Instant.now())
                                .build();
                        return clientRequestRepository.save(cr);
                    }
                });
    }
}
