package lrskyum.sbdemo.business.aggregates.basket;

import lrskyum.sbdemo.business.base.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface BasketRepository extends Repository<Basket>{
    List<Basket> findAll();

    Basket save(Basket entity);
}
