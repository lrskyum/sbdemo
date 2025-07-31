package lrskyum.sbdemo.infrastructure.idempotence;

import lrskyum.sbdemo.infrastructure.idempotency.RequestManager;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("tempdb")
public class RequestManagerTest {
    @Autowired
    private RequestManager requestManager;

    @Test
    void shouldHandleRequest_requestWasNotSubmittedEarlier() {
        // Act
        boolean exists = requestManager.exist(UUID.randomUUID().toString());

        // Assert
        assertThat(exists).isFalse();
    }

    @Test
    void shouldHandleRequest_submittingNewRequest() {
        // Act
        var cr = requestManager.createRequestForCommand(UUID.randomUUID().toString(), "test");

        // Assert
        assertNotNull(cr.getExtId());
        assertNotNull(cr.getName());
        assertNotNull(cr.getTime());
    }

}
