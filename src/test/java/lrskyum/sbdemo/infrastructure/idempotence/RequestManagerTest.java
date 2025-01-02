package lrskyum.sbdemo.infrastructure.idempotence;

import lrskyum.sbdemo.infrastructure.idempotency.RequestManager;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
//@ActiveProfiles("tempdb")
public class RequestManagerTest {
    @Autowired
    private RequestManager requestManager;

    @Test
    void shouldHandleRequest_requestWasNotSubmittedEarlier() {
        // Arrange

        // Act
        var exists = requestManager.exist(UUID.randomUUID());

        // Assert
        StepVerifier.create(exists)
                .expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .expectNextCount(1)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldHandleRequest_submittingNewRequest() {
        // Arrange

        // Act
        var exists = requestManager.createRequestForCommand(UUID.randomUUID(), "test");

        // Assert
        StepVerifier.create(exists)
                .expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .expectNextMatches(cr -> {
                    assertNotNull(cr.getExtId());
                    assertNotNull(cr.getName());
                    assertNotNull(cr.getTime());
                    return true;
                })
                .expectComplete()
                .verify();
    }

}
