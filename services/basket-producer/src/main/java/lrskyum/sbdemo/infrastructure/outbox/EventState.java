package lrskyum.sbdemo.infrastructure.outbox;

public enum EventState {
    NotPublished,
    InProgress,
    Published,
    PublishedFailed
}
