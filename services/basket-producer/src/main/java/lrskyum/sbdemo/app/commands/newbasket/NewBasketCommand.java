package lrskyum.sbdemo.app.commands.newbasket;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.BasketStatus;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;


@Getter
@Builder
public class NewBasketCommand implements Command<Mono<Basket>> {
    private Instant orderDateUtc;
    private BasketStatus basketStatus;
    private String buyerName;
    private PaymentMethod paymentMethod;
    private String product;

    public Basket toBasket(NewBasketCommand command) {
        var id = command instanceof NewBasketIdentifiedCommand ic ? ic.getId() : UUID.randomUUID().toString();
        var basket = Basket.create(id, command.getBuyerName(), command.getPaymentMethod(), command.getProduct());
        return basket;
    }
}
