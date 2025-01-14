package lrskyum.sbdemo.app.queries;

import lrskyum.sbdemo.business.aggregates.basket.BasketStatus;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;

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
