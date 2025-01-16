package lrskyum.sbdemo.business.domain;

import lrskyum.sbdemo.business.aggregates.basket.BasketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("tempdb")
class QueryCustomerBasketRepositoryTest {

    @Autowired
    private BasketRepository basketRepository;

    @Test
    void shouldInitializeDatabase_withTenOrders() {
        // Arrange

        // Act
        var orders = basketRepository.findAll();

        // Assert
        StepVerifier.create(orders.collectList())
                .expectSubscription()
                .assertNext(list -> assertThat(list.size()).isGreaterThanOrEqualTo(10))
                .expectComplete()
                .verify();
    }

    @Test
    void shouldInitializeDatabase_withOrdersFromToday() {
        // Arrange

        // Act
        var orders = basketRepository.findAll();

        // Assert
        StepVerifier.create(orders)
                .expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .thenConsumeWhile(order -> order.getBasketDateUtc().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
                .expectComplete()
                .verify();
    }

}
