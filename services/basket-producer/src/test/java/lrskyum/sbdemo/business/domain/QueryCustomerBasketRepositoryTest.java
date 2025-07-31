package lrskyum.sbdemo.business.domain;

import lrskyum.sbdemo.business.aggregates.basket.BasketRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

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
        // Act
        var orders = basketRepository.findAll(); // returns Iterable<Basket> or List<Basket>

        // Assert
        assertThat(orders).hasSizeGreaterThanOrEqualTo(10);
    }

    @Test
    void shouldInitializeDatabase_withOrdersFromToday() {
        // Act
        var orders = basketRepository.findAll();

        // Assert
        for (var order : orders) {
            assertThat(order.getBasketDateUtc())
                    .isAfter(Instant.now().minus(1, ChronoUnit.DAYS));
        }
    }

}
