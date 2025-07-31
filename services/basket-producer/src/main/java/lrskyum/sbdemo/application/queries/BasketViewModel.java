package lrskyum.sbdemo.application.queries;

import lrskyum.sbdemo.domain.aggregates.basket.BasketStatus;
import lrskyum.sbdemo.domain.aggregates.basket.PaymentMethod;

import java.time.Instant;

public class BasketViewModel {
    public record BasketDto(
            String externalId,
            Instant basketDateUtc,
            BasketStatus basketStatus,
            String buyerName,
            PaymentMethod paymentMethod,
            String product,
            Integer request_count) {
    }
}
