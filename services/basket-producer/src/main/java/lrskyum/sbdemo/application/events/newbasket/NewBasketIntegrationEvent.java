package lrskyum.sbdemo.application.events.newbasket;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lrskyum.sbdemo.application.events.IntegrationEvent;
import lrskyum.sbdemo.domain.aggregates.basket.BasketStatus;
import lrskyum.sbdemo.domain.aggregates.basket.PaymentMethod;

@NoArgsConstructor
@Getter
public class NewBasketIntegrationEvent extends IntegrationEvent {
    private BasketStatus basketStatus;
    private String buyerName;
    private PaymentMethod paymentMethod;
    private String product;

    public NewBasketIntegrationEvent(BasketStatus basketStatus, String buyerName, PaymentMethod paymentMethod, String product) {
        super();
        this.basketStatus = basketStatus;
        this.buyerName = buyerName;
        this.paymentMethod = paymentMethod;
        this.product = product;
    }
}
