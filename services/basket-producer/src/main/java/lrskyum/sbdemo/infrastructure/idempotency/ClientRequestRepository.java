package lrskyum.sbdemo.infrastructure.idempotency;

import org.springframework.data.repository.CrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional
public interface ClientRequestRepository extends CrudRepository<ClientRequest, UUID> {
    Boolean existsByExtId(String extId);
}
