package lrskyum.sbdemo.business.domain;


import lombok.Builder;
import lombok.Getter;
import lrskyum.sbdemo.business.base.AggregateRoot;
import org.springframework.data.relational.core.mapping.Table;
import org.springframework.lang.NonNull;

import java.util.Objects;
import java.util.UUID;

@Getter
@Builder
@Table
public class CustomerOrder extends AggregateRoot {

    public static CustomerOrder create(@NonNull String description, @NonNull Address address) {
        var order = createNoEvent(description, address);

        return order;
    }

    private static CustomerOrder createNoEvent(@NonNull String description, @NonNull Address address) {
        Objects.requireNonNull(description, "Description must not be null!");
        Objects.requireNonNull(address, "Address must not be null!");

        var order = CustomerOrder.builder().build();
        return order;
    }
}
