package lrskyum.sbdemo.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Component
@Primary
public class BasketRepositoryImpl implements BasketRepository {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private BasketRepo delegate;

    public Flux<Basket> findAll() {
        return delegate.findAll();
    }

    @Override
    public Mono<Basket> saveAndEmit(Basket entity) {
        return delegate.save(entity)
                .doOnNext(e -> {
                    entity.domainEvents().forEach(applicationEventPublisher::publishEvent);
                    entity.clearDomainEvents();
                });
    }
}

interface BasketRepo extends ReactiveCrudRepository<Basket, UUID> {
}