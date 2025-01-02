package lrskyum.sbdemo.app.commands;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lrskyum.sbdemo.business.domain.OrderStatus;
import lrskyum.sbdemo.business.domain.PaymentMethod;

import java.time.Instant;


@Getter
public class CreateOrderCommand implements Command<Boolean> {
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

    @Builder
    private CreateOrderCommand(
            String description,
            Instant orderDateUtc,
            OrderStatus orderStatus,
            String buyerName,
            String buyerEmail,
            String street,
            String zip,
            String country,
            PaymentMethod paymentMethod,
            String product) {
        this.description = description;
        this.orderDateUtc = orderDateUtc;
        this.orderStatus = orderStatus;
        this.buyerName = buyerName;
        this.buyerEmail = buyerEmail;
        this.street = street;
        this.zip = zip;
        this.country = country;
        this.paymentMethod = paymentMethod;
        this.product = product;
    }
}
