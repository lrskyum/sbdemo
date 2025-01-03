package lrskyum.sbdemo.infrastructure.repository;

import lrskyum.sbdemo.business.aggregates.order.CustomerOrder;
import lrskyum.sbdemo.business.aggregates.order.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Transactional("connectionFactoryTransactionManager")
@Component
@Primary
public class OrdersRepositoryImpl implements OrdersRepository {

    @Autowired
    private OrdersRepo delegate;

    public Flux<CustomerOrder> findAll() {
        return delegate.findAll();
    }

    @Override
    public Mono<CustomerOrder> saveAndEmit(CustomerOrder entity) {
        return delegate.save(entity).doOnNext(e -> {
            // entity.domainEvents().forEach(applicationEventPublisher::publishEvent);
            entity.clearDomainEvents();
        });
    }
}

@Transactional("connectionFactoryTransactionManager")
interface OrdersRepo extends ReactiveCrudRepository<CustomerOrder, UUID> {
}