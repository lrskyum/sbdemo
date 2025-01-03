package lrskyum.sbdemo.business.aggregates.order;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional("connectionFactoryTransactionManager")
public interface OrdersRepository {
    Flux<CustomerOrder> findAll();

    Mono<CustomerOrder> saveAndEmit(CustomerOrder entity);
}
