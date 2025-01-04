package lrskyum.sbdemo.business.events;

import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.base.DomainEvent;

public record BasketCheckedOutDomainEvent(Basket basket) implements DomainEvent {
}
