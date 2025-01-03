package lrskyum.sbdemo.ui;

import lrskyum.sbdemo.app.commands.CreateOrderCommand;
import lrskyum.sbdemo.app.commands.CreateOrderIdentifiedCommand;
import lrskyum.sbdemo.business.aggregates.order.CustomerOrder;
import lrskyum.sbdemo.business.aggregates.order.OrderStatus;
import lrskyum.sbdemo.business.aggregates.order.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

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

    @Test
    public void shouldPostOrder_andSaveIt() {
        // Arrange
        var command = CreateOrderCommand.builder()
                .zip("8000")
                .country("Denmark")
                .buyerEmail("johndoe@mail.com")
                .product("Product 1")
                .buyerName("John Doe")
                .street("Main Street 1")
                .description("Description 1")
                .paymentMethod(PaymentMethod.CASH_ON_DELIVERY)
                .build();
        var idCommand = new CreateOrderIdentifiedCommand(command, UUID.randomUUID());

        // Act
        testClient.put()
                .uri("/orders")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(idCommand)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Boolean.class)
                .consumeWith(response -> {
                    // Assert
                    assertEquals(Boolean.TRUE, response.getResponseBody());
                });
    }
}