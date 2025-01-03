package lrskyum.sbdemo.business.aggregates.order;


import lombok.Builder;
import lombok.Getter;
import lrskyum.sbdemo.business.aggregates.buyer.Buyer;
import lrskyum.sbdemo.business.base.AggregateRoot;
import lrskyum.sbdemo.business.events.OrderSubmittedDomainEvent;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.UUID;

@Getter
@Builder
@Table
public class CustomerOrder extends AggregateRoot {
    private String description;
    private Instant orderDateUtc;
    private OrderStatus orderStatus;
    private String buyerName;
    private String buyerEmail;
    private String street;
    private String zip;
    private String country;
    private PaymentMethod paymentMethod;
    private String product;

    public static CustomerOrder create(@NonNull String description, @NonNull Address address, @NonNull Buyer buyer,
                                       PaymentMethod paymentMethod, String product) {
        var order = CustomerOrder.builder()
                .description(description)
                .orderDateUtc(Instant.now())
                .orderStatus(OrderStatus.SUBMITTED)
                .buyerName(buyer.getName())
                .buyerEmail(buyer.getEmail())
                .street(address.getStreet())
                .zip(address.getZip())
                .country(address.getCountry())
                .paymentMethod(paymentMethod)
                .product(product)
                .build();
        order.extId = UUID.randomUUID();

        order.addOrderStartedDomainEvent(order);

        return order;
    }

    private OrderSubmittedDomainEvent addOrderStartedDomainEvent(CustomerOrder order) {
        var orderStartedDomainEvent = new OrderSubmittedDomainEvent(this);
        return addDomainEvent(orderStartedDomainEvent);
    }
}
