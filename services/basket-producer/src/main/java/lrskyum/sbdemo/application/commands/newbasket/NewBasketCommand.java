package lrskyum.sbdemo.application.commands.newbasket;

import lombok.Builder;
import lombok.Getter;
import lrskyum.sbdemo.application.commands.IdentifiedCommand;
import lrskyum.sbdemo.domain.aggregates.basket.Basket;
import lrskyum.sbdemo.domain.aggregates.basket.PaymentMethod;


@Getter
public class NewBasketCommand extends IdentifiedCommand<Basket> {
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
