package lrskyum.sbdemo;

import lrskyum.sbdemo.business.domain.OrdersRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import reactor.test.StepVerifier;

import java.time.Instant;
import java.time.temporal.ChronoUnit;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@ActiveProfiles("tempdb")
class CustomerOrdersRepositoryTest {

    @Autowired
    private OrdersRepository ordersRepository;

    @Test
    void shouldInitializeDatabase_withTenOrders() {
        // Arrange

        // Act
        var orders = ordersRepository.findAll();

        // Assert
        StepVerifier.create(orders).expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .expectNextCount(10)
                .expectComplete()
                .verify();
    }

    @Test
    void shouldInitializeDatabase_withOrdersFromToday() {
        // Arrange

        // Act
        var orders = ordersRepository.findAll();

        // Assert
        StepVerifier.create(orders).expectSubscription()
                .thenRequest(Long.MAX_VALUE)
                .thenConsumeWhile(order -> order.getOrderDateUtc().isAfter(Instant.now().minus(1, ChronoUnit.DAYS)))
                .expectComplete()
                .verify();
    }

}
