package lrskyum.sbdemo.business.events.newbasket;

import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.base.DomainEvent;

public record NewBasketDomainEvent(Basket basket) implements DomainEvent {
}
