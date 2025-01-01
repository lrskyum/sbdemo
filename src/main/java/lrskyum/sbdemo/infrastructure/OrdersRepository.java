package lrskyum.sbdemo.infrastructure;

import lrskyum.sbdemo.business.domain.CustomerOrder;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Transactional("connectionFactoryTransactionManager")
public interface OrdersRepository extends ReactiveCrudRepository<CustomerOrder, UUID> {
}
