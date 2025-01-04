package lrskyum.sbdemo.app.commands.usercheckout;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.BasketRepository;
import lrskyum.sbdemo.infrastructure.events.UserCheckoutIntegrationEvent;
import lrskyum.sbdemo.infrastructure.outbox.IntegrationEventLogService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@AllArgsConstructor
@Service
public class UserCheckoutCommandHandler implements Command.Handler<UserCheckoutCommand, Mono<Void>> {

    private final BasketRepository basketRepository;
    private final IntegrationEventLogService integrationEventLogService;

    @Override
    @Transactional("connectionFactoryTransactionManager")
    public Mono<Void> handle(UserCheckoutCommand command) {
        var integrationEvent = new UserCheckoutIntegrationEvent(command.getBasketStatus(), command.getBuyerName(),
                command.getPaymentMethod(), command.getProduct());
        integrationEventLogService.saveEvent(integrationEvent, "basket");

        final var basket = createBasket(command);
        basketRepository.saveAndEmit(basket).subscribe();

        return Mono.empty();
    }

    private Basket createBasket(UserCheckoutCommand command) {
        var id = command instanceof UserCheckoutIdentifiedCommand ic ? ic.getId() : UUID.randomUUID().toString();
        var basket = Basket.create(id, command.getBuyerName(), command.getPaymentMethod(), command.getProduct());
        return basket;
    }
}
