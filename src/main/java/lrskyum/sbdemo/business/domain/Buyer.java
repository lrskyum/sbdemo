package lrskyum.sbdemo.business.domain;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class Buyer {
    private String name;
    private String email;
}
