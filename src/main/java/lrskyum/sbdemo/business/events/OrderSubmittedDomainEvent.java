package lrskyum.sbdemo.business.events;

import lrskyum.sbdemo.business.aggregates.order.CustomerOrder;
import lrskyum.sbdemo.business.base.DomainEvent;

public record OrderSubmittedDomainEvent(CustomerOrder order) implements DomainEvent {
}
