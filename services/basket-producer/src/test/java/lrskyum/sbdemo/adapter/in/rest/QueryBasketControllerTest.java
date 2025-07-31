package lrskyum.sbdemo.adapter.in.rest;

import lrskyum.sbdemo.domain.aggregates.basket.Basket;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@ActiveProfiles("tempdb")
public class QueryBasketControllerTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @LocalServerPort
    private int port;

    @Test
    public void shouldGetInitialOrders_withTenOrders() {
        // Act
        ResponseEntity<Basket[]> response = restTemplate.getForEntity("/api/v1/basket", Basket[].class);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());

        Basket[] body = response.getBody();
        assertNotNull(body);

        List<Basket> basketList = Arrays.asList(body);
        assertFalse(basketList.isEmpty(), "Expected at least 10 elements, but found " + basketList.size());

        basketList.forEach(b -> {
            assertNotNull(b.getBasketStatus());
            assertNotNull(b.getBasketDateUtc());
        });
    }
}