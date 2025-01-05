package lrskyum.sbdemo.app.commands.usercheckout;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.BasketStatus;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;


@Getter
@Builder
public class UserCheckoutCommand implements Command<Mono<Void>> {
    private Instant orderDateUtc;
    private BasketStatus basketStatus;
    private String buyerName;
    private PaymentMethod paymentMethod;
    private String product;

    public Basket toBasket(UserCheckoutCommand command) {
        var id = command instanceof UserCheckoutIdentifiedCommand ic ? ic.getId() : UUID.randomUUID().toString();
        var basket = Basket.create(id, command.getBuyerName(), command.getPaymentMethod(), command.getProduct());
        return basket;
    }

}
