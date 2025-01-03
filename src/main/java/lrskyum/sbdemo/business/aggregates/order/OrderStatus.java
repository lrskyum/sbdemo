package lrskyum.sbdemo.business.aggregates.order;

public enum OrderStatus {
    SUBMITTED,
    AWAITING_VALIDATION,
    STOCK_CONFIRMED,
    PAID,
    SHIPPED,
    CANCELLED;
}
