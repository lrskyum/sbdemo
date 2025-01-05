package lrskyum.sbdemo.infrastructure.outbox;

public interface OutboxPublisher {
    void publish(OutboxEntry eventLogEntry);
}
