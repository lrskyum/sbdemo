package lrskyum.sbdemo.infrastructure.events;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lrskyum.sbdemo.business.aggregates.basket.BasketStatus;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;

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
