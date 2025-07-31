package lrskyum.sbdemo.domain.events.newbasket;

import lrskyum.sbdemo.domain.aggregates.basket.Basket;
import lrskyum.sbdemo.domain.base.DomainEvent;

public record NewBasketDomainEvent(Basket basket) implements DomainEvent {
}
