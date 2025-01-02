package lrskyum.sbdemo.ui;

import lrskyum.sbdemo.business.domain.CustomerOrder;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("tempdb")
public class OrdersControllerTest {

    @LocalServerPort
    private int port;

    private WebTestClient testClient;

    @BeforeEach
    public void setup() {
        testClient = WebTestClient
                .bindToServer()
                .baseUrl("http://localhost:" + port + "/api/v1")
                .build();
    }

    @Test
    public void shouldGetInitialOrders_withTenOrders() {
        // Arrange
        testClient.get()
                .uri("/orders")
                // Act
                .exchange()
                .expectStatus().isOk()
                .expectBodyList(CustomerOrder.class)
                .hasSize(10)
                .consumeWith(response -> {
                    List<CustomerOrder> orders = response.getResponseBody();
                    // Assert
                    assertNotNull(orders);
                    orders.forEach(order -> {
                        assertNotNull(order.getOrderStatus());
                        assertNotNull(order.getOrderDateUtc());
                    });
                });
    }
}