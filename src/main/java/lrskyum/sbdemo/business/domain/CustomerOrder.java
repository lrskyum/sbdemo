package lrskyum.sbdemo.business.domain;


import lombok.Builder;
import lombok.Getter;
import lrskyum.sbdemo.business.base.AggregateRoot;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.time.Instant;
import java.util.Objects;
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
        var order = createNoEvent(description, address, buyer, paymentMethod, product);

        return order;
    }

    private static CustomerOrder createNoEvent(@NonNull String description, @NonNull Address address, @NonNull Buyer buyer,
                                               PaymentMethod paymentMethod, String product) {
        Objects.requireNonNull(description, "Description must not be null!");
        Objects.requireNonNull(address, "Address must not be null!");

        var order = CustomerOrder.builder()
                .description(description)
                .orderDateUtc(Instant.now())
                .orderStatus(OrderStatus.AWAITING_VALIDATION)
                .buyerName(buyer.getName())
                .buyerEmail(buyer.getEmail())
                .street(address.getStreet())
                .zip(address.getZip())
                .country(address.getCountry())
                .paymentMethod(paymentMethod)
                .product(product)
                .build();
        order.extId = UUID.randomUUID();
        return order;
    }
}
