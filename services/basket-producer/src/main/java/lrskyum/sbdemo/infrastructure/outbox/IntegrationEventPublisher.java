package lrskyum.sbdemo.infrastructure.outbox;

public interface IntegrationEventPublisher {
    void publish(IntegrationEventLogEntry eventLogEntry);
}
