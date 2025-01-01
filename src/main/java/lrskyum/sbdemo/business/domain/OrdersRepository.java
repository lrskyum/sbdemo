package lrskyum.sbdemo.business.domain;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional("connectionFactoryTransactionManager")
public interface OrdersRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {
}
