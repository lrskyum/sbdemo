package lrskyum.sbdemo.business.base;

import org.springframework.data.annotation.Transient;
import org.springframework.lang.NonNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class AggregateRoot extends Entity {

    @Transient
    private transient final List<DomainEvent> domainEvents = new ArrayList<>();

    protected <T extends DomainEvent> T addDomainEvent(@NonNull T event) {
        domainEvents.add(event);
        return event;
    }

    public <T extends DomainEvent> void removeDomainEvent(@NonNull T event) {
        domainEvents.remove(event);
    }

    public void clearDomainEvents() {
        domainEvents.clear();
    }

    public Collection<DomainEvent> domainEvents() {
        return Collections.unmodifiableList(domainEvents);
    }
}
