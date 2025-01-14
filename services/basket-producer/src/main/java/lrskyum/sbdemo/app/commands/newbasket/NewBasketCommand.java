package lrskyum.sbdemo.app.commands.newbasket;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lrskyum.sbdemo.app.commands.IdentifiedCommand;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.BasketStatus;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;


@Getter
public class NewBasketCommand extends IdentifiedCommand<Mono<Basket>> {
    private final String buyerName;
    private final PaymentMethod paymentMethod;
    private final String product;

    @Builder
    public NewBasketCommand(String id, String buyerName, PaymentMethod paymentMethod, String product) {
        super(id);
        this.buyerName = buyerName;
        this.paymentMethod = paymentMethod;
        this.product = product;
    }
}
