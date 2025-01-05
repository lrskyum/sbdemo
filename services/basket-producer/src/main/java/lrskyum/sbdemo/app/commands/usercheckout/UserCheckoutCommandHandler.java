package lrskyum.sbdemo.app.commands.usercheckout;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.business.aggregates.basket.BasketRepository;
import lrskyum.sbdemo.infrastructure.events.UserCheckoutIntegrationEvent;
import lrskyum.sbdemo.infrastructure.outbox.OutboxService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import reactor.core.publisher.Mono;

@Slf4j
@AllArgsConstructor
@Service
public class UserCheckoutCommandHandler implements Command.Handler<UserCheckoutCommand, Mono<Void>> {

    private final BasketRepository basketRepository;
    private final OutboxService outboxService;

    @Override
    @Transactional("connectionFactoryTransactionManager")
    public Mono<Void> handle(UserCheckoutCommand command) {
        var integrationEvent = new UserCheckoutIntegrationEvent(command.getBasketStatus(), command.getBuyerName(),
                command.getPaymentMethod(), command.getProduct());
        outboxService.saveEvent(integrationEvent, "basket");

        final var basket = command.toBasket(command);
        basketRepository.saveAndEmit(basket).subscribe();

        return Mono.empty();
    }
}
