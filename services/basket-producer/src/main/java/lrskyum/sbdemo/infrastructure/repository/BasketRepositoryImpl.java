package lrskyum.sbdemo.infrastructure.repository;

import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.BasketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Primary;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.UUID;

@Component
@Primary
public class BasketRepositoryImpl implements BasketRepository {

    @Autowired
    private ApplicationEventPublisher applicationEventPublisher;
    @Autowired
    private BasketRepo delegate;

    public List<Basket> findAll() {
        return delegate.findAll();
    }

    @Override
    public Basket save(Basket entity) {
        var e = delegate.save(entity);
        entity.domainEvents().forEach(applicationEventPublisher::publishEvent);
        entity.clearDomainEvents();
        return e;
    }
}

interface BasketRepo extends ListCrudRepository<Basket, UUID> {
}