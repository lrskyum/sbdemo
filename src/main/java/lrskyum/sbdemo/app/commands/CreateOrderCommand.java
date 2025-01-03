package lrskyum.sbdemo.app.commands;

import an.awesome.pipelinr.Command;
import lombok.Builder;
import lombok.Getter;
import lrskyum.sbdemo.business.aggregates.order.OrderStatus;
import lrskyum.sbdemo.business.aggregates.order.PaymentMethod;

import java.time.Instant;


@Getter
@Builder
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
}
