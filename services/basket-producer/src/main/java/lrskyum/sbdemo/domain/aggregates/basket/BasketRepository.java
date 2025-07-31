package lrskyum.sbdemo.domain.aggregates.basket;

import lrskyum.sbdemo.domain.base.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BasketRepository extends Repository<Basket>{
    List<Basket> findAll();

    Basket save(Basket entity);
}
