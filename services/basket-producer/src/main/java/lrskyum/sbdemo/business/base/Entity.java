package lrskyum.sbdemo.business.base;

import lombok.Getter;
import org.springframework.data.annotation.Id;

@Getter
public class Entity {
    @Id
    protected Long id;
    // External ID for the entity, used for idempotency and external references
    // This field is not automatically generated and should be set explicitly
    protected String extId;
}
