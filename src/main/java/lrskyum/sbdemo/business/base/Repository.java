package lrskyum.sbdemo.business.base;

import org.springframework.lang.NonNull;

public interface Repository<T extends AggregateRoot> {
    T save(@NonNull T aggregateRoot);
}
