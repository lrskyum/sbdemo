package lrskyum.sbdemo.ui;

import lrskyum.sbdemo.app.commands.usercheckout.NewBasketCommand;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;
import org.junit.jupiter.api.Assertions;
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
import static org.junit.Assert.assertEquals;

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
    public void shouldCreateBasket_andSaveIt() {
        // Arrange
        var command = NewBasketCommand.builder()
                .buyerName("John Doe")
                .product("Product 1")
                .paymentMethod(PaymentMethod.CASH_ON_DELIVERY)
                .build();
        var extId = UUID.randomUUID().toString();

        // Act
        testClient.post()
                .uri("/create")
                .contentType(MediaType.APPLICATION_JSON)
                .header("X-RequestId", extId)
                .bodyValue(command)
                .exchange()
                .expectStatus().isCreated()
                .expectBody(Basket.class)
                .consumeWith(response -> {
                    // Assert
                    Basket basket = response.getResponseBody();
                    Assertions.assertEquals(extId, basket.getExtId());
                    Assertions.assertEquals("John Doe", basket.getBuyerName());
                    Assertions.assertEquals("Product 1", basket.getProduct());
                    Assertions.assertEquals(PaymentMethod.CASH_ON_DELIVERY, basket.getPaymentMethod());
                });
    }
}