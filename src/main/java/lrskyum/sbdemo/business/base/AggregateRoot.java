package lrskyum.sbdemo.business.base;

import lombok.Builder;
import org.springframework.data.annotation.Transient;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public class AggregateRoot extends Entity {

    @Transient
    private transient final List<DomainEvent> domainEvents = new ArrayList<>();

    protected <T extends DomainEvent> T addDomainEvent(@NonNull T event) {
        Objects.requireNonNull(event, "Domain event must not be null!");

        this.domainEvents.add(event);
        return event;
    }

    public <T extends DomainEvent> void removeDomainEvent(@NonNull T event) {
        Objects.requireNonNull(event, "Domain event must not be null!");

        domainEvents.remove(event);
    }

    public void clearDomainEvents() {
        this.domainEvents.clear();
    }

    public Collection<DomainEvent> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}
