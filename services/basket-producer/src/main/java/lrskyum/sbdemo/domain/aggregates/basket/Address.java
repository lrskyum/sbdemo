package lrskyum.sbdemo.domain.aggregates.basket;


import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;
import lrskyum.sbdemo.domain.base.ValueObject;

import java.util.List;

@RequiredArgsConstructor
@Getter
@Builder
@ToString
public class Address extends ValueObject {
    private final String street;
    private final String zip;
    private final String country;

    @Override
    protected List<Object> getEqualityComponents() {
        return List.of(
                street,
                zip,
                country
        );
    }
}
