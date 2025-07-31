package lrskyum.sbdemo.domain.aggregates.basket;


import lombok.Builder;
import lombok.Getter;
import lrskyum.sbdemo.domain.base.AggregateRoot;
import lrskyum.sbdemo.domain.events.newbasket.NewBasketDomainEvent;
import org.springframework.data.relational.core.mapping.Table;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@Table
public class Basket extends AggregateRoot {
    private Instant basketDateUtc;
    private BasketStatus basketStatus;
    private String buyerName;
    private PaymentMethod paymentMethod;
    private String product;

    public static Basket create(String buyer, PaymentMethod paymentMethod, String product) {
        var id = UUID.randomUUID().toString();
        return create(id, buyer, paymentMethod, product);
    }

    public static Basket create(String id, String buyer, PaymentMethod paymentMethod, String product) {
        var order = Basket.builder()
                .basketDateUtc(Instant.now())
                .basketStatus(BasketStatus.NEW)
                .buyerName(buyer)
                .paymentMethod(paymentMethod)
                .product(product)
                .build();
        order.extId = id;

        order.addBasketCheckedOutDomainEvent(order);

        return order;
    }

    private void addBasketCheckedOutDomainEvent(Basket order) {
        var orderStartedDomainEvent = new NewBasketDomainEvent(this);
        addDomainEvent(orderStartedDomainEvent);
    }
}
