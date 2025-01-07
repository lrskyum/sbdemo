package lrskyum.sbdemo.business.aggregates.basket;

import lrskyum.sbdemo.business.base.Repository;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Transactional("connectionFactoryTransactionManager")
public interface BasketRepository extends Repository<Basket>{
    Flux<Basket> findAll();

    Mono<Basket> save(Basket entity);
}
