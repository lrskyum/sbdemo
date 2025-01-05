package lrskyum.sbdemo.business.events.newbasket;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.business.base.DomainEventHandler;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class NewBasketDomainEventHandler implements DomainEventHandler<NewBasketDomainEvent> {

    @EventListener
    public void handle(NewBasketDomainEvent newBasketDomainEvent) {
        log.info("Basket with Id: {} has been successfully create with status {}", newBasketDomainEvent.basket().getExtId(),
                newBasketDomainEvent.basket().getBasketStatus());
    }
}
