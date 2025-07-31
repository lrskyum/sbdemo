package lrskyum.sbdemo.infrastructure.idempotency;

import lombok.RequiredArgsConstructor;
import lrskyum.sbdemo.business.exceptions.BasketDomainException;
import org.springframework.stereotype.Service;

import java.time.Instant;

@RequiredArgsConstructor
@Service
public class RequestManagerImpl implements RequestManager {

    private final ClientRequestRepository clientRequestRepository;

    @Override
    public Boolean exist(String id) {
        return clientRequestRepository.existsByExtId(id);
    }

    @Override
    public ClientRequest createRequestForCommand(String id, String commandName) {
        if (exist(id)) {
            throw new BasketDomainException("Request with id: %s already exists".formatted(id));
        } else {
            var cr = ClientRequest.builder()
                    .extId(id)
                    .name(commandName)
                    .time(Instant.now())
                    .build();
            return clientRequestRepository.save(cr);
        }
    }
}
