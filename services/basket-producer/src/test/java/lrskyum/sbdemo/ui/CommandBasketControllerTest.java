package lrskyum.sbdemo.ui;

import lrskyum.sbdemo.app.commands.newbasket.NewBasketCommand;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("tempdb")
public class CommandBasketControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void shouldCreateBasket_andSaveIt() {
        // Arrange
        var command = NewBasketCommand.builder()
                .buyerName("John Doe")
                .product("Product 1")
                .paymentMethod(PaymentMethod.CASH_ON_DELIVERY)
                .build();
        var extId = UUID.randomUUID().toString();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.set("X-RequestId", extId);

        HttpEntity<NewBasketCommand> requestEntity = new HttpEntity<>(command, headers);

        // Act
        ResponseEntity<Basket> response = restTemplate.postForEntity(
                "/api/v1/basket/create",  // assuming this is your full path
                requestEntity,
                Basket.class
        );

        // Assert
        assertEquals(HttpStatus.CREATED, response.getStatusCode());

        Basket basket = response.getBody();
        assertNotNull(basket);
        assertEquals(extId, basket.getExtId());
        assertEquals("John Doe", basket.getBuyerName());
        assertEquals("Product 1", basket.getProduct());
        assertEquals(PaymentMethod.CASH_ON_DELIVERY, basket.getPaymentMethod());
    }
}