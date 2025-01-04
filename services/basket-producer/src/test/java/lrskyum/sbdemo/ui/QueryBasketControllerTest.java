package lrskyum.sbdemo.ui;

import lrskyum.sbdemo.business.aggregates.basket.Basket;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("tempdb")
public class QueryBasketControllerTest {

    @LocalServerPort
    private int port;

    private WebTestClient testClient;

    @BeforeEach
    public void setup() {
        testClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port + "/api/v1")
                .responseTimeout(Duration.ofDays(1))
                .build();
    }

    @Test
    public void shouldGetInitialOrders_withTenOrders() {
        // Arrange
        testClient.get()
                .uri("/basket")
                // Act
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(Basket.class)
                .consumeWith(response -> {
                    List<Basket> basket = response.getResponseBody();
                    // Assert
                    assertNotNull(basket);
                    assertFalse(basket.isEmpty(), "Expected at least 10 elements, but found " + basket.size());

                    basket.forEach(order -> {
                        assertNotNull(order.getBasketStatus());
                        assertNotNull(order.getBasketDateUtc());
                    });
                });
    }

}