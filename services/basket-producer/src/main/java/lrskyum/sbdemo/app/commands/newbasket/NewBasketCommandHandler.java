package lrskyum.sbdemo.app.commands.newbasket;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.BasketRepository;
import lrskyum.sbdemo.infrastructure.events.NewBasketIntegrationEvent;
import lrskyum.sbdemo.infrastructure.outbox.OutboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class NewBasketCommandHandler implements Command.Handler<NewBasketCommand, Mono<Basket>> {

    private final BasketRepository basketRepository;
    private final OutboxService outboxService;

    @Override
    @Transactional("connectionFactoryTransactionManager")
    public Mono<Basket> handle(NewBasketCommand command) {
        var integrationEvent = new NewBasketIntegrationEvent(command.getBasketStatus(), command.getBuyerName(),
                command.getPaymentMethod(), command.getProduct());
        outboxService.saveEvent(integrationEvent, "basket");

        var id = command instanceof NewBasketIdentifiedCommand ic ? ic.getId() : UUID.randomUUID().toString();
        final var basket = Basket.create(id, command.getBuyerName(), command.getPaymentMethod(), command.getProduct());
        return basketRepository.saveAndEmit(basket);
    }
}
