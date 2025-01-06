package lrskyum.sbdemo.app.commands.newbasket;

import an.awesome.pipelinr.Command;
import lombok.Getter;
import lrskyum.sbdemo.app.commands.IdentifiedCommand;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import reactor.core.publisher.Mono;

@Getter
public class NewBasketIdentifiedCommand extends NewBasketCommand implements IdentifiedCommand, Command<Mono<Basket>> {
    private final String id;

    public NewBasketIdentifiedCommand(NewBasketCommand command, String id) {
        super(command.getOrderDateUtc(), command.getBasketStatus(), command.getBuyerName(), command.getPaymentMethod(), command.getProduct());
        this.id = id;
    }
}
