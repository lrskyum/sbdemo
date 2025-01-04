package lrskyum.sbdemo.app.commands.usercheckout;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lombok.experimental.SuperBuilder;
import lrskyum.sbdemo.business.aggregates.basket.BasketStatus;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;
import reactor.core.publisher.Mono;

import java.time.Instant;
import java.util.UUID;


@Getter
@Builder
public class UserCheckoutCommand implements Command<Mono<Boolean>> {
    private Instant orderDateUtc;
    private BasketStatus basketStatus;
    private String buyerName;
    private PaymentMethod paymentMethod;
    private String product;
}
