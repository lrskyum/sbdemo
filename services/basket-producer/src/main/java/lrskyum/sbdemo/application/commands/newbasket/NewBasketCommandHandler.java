package lrskyum.sbdemo.application.commands.newbasket;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.application.events.OutboxService;
import lrskyum.sbdemo.application.events.newbasket.NewBasketIntegrationEvent;
import lrskyum.sbdemo.domain.aggregates.basket.Basket;
import lrskyum.sbdemo.domain.aggregates.basket.BasketRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class NewBasketCommandHandler implements Command.Handler<NewBasketCommand, Basket> {

    private final BasketRepository basketRepository;
    private final OutboxService outboxService;

    @Override
    @Transactional
    public Basket handle(NewBasketCommand command) {
        final var id = command.getId() != null ? command.getId() : UUID.randomUUID().toString();
        final var basket = Basket.create(id, command.getBuyerName(), command.getPaymentMethod(), command.getProduct());

        final var integrationEvent = new NewBasketIntegrationEvent(basket.getBasketStatus(), command.getBuyerName(),
                command.getPaymentMethod(), command.getProduct());
        outboxService.saveEvent(integrationEvent, "basket");

        return basketRepository.save(basket);
    }
}
