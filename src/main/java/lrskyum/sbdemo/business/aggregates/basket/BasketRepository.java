package lrskyum.sbdemo.business.aggregates.basket;

import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional("connectionFactoryTransactionManager")
public interface BasketRepository {
    Flux<Basket> findAll();

    Mono<Basket> saveAndEmit(Basket entity);
}
