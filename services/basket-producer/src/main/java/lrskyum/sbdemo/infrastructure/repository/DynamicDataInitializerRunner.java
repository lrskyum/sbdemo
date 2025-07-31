package lrskyum.sbdemo.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.app.commands.newbasket.NewBasketCommand;
import lrskyum.sbdemo.business.aggregates.basket.BasketRepository;
import lrskyum.sbdemo.business.aggregates.basket.PaymentMethod;
import lrskyum.sbdemo.infrastructure.idempotency.RequestManager;
import lrskyum.sbdemo.ui.BasketController;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("tempdb")
public class DynamicDataInitializerRunner implements CommandLineRunner {

    private final BasketRepository basketRepository;
    private final RequestManager requestManager;
    private final BasketController basketController;

    @Override
    public void run(String... args) {
        try {
            initializeData();
            log.info("Data initialized successfully");
        } catch (Exception e) {
            log.error("Error initializing data: {}", e.getMessage());
        }
    }

    public void initializeData() {
        IntStream.range(0, 10)
                .mapToObj(this::createNewBasketCommand)
                .forEach(cmd -> basketController.create(cmd, UUID.randomUUID().toString()));
    }

    public NewBasketCommand createNewBasketCommand(int i) {
        return NewBasketCommand.builder()
                .buyerName("John Doe " + i)
                .paymentMethod(PaymentMethod.CREDIT_CARD)
                .product("Product " + i)
                .build();
    }
}