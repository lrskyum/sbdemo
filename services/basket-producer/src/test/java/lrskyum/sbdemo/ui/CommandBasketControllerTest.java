package lrskyum.sbdemo.ui;

import lrskyum.sbdemo.app.commands.usercheckout.UserCheckoutCommand;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.reactive.server.WebTestClient;

import java.time.Duration;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("tempdb")
public class CommandBasketControllerTest {

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
    public void shouldCheckoutBasket_andSaveIt() {
        // Arrange
        var command = UserCheckoutCommand.builder()
                .buyerName("John Doe")
                .product("Product 1")
                .paymentMethod(PaymentMethod.CASH_ON_DELIVERY)
                .build();

        // Act
        testClient.post()
                .uri("/checkout")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-RequestId", UUID.randomUUID().toString())
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated()
                .expectBody()
                .consumeWith(response -> {
                    // Assert
                    assertThat(response.getResponseBody()).isNull();
                });
    }
}