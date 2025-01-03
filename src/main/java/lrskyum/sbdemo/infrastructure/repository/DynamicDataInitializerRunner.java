package lrskyum.sbdemo.infrastructure.repository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.business.aggregates.order.Address;
import lrskyum.sbdemo.business.aggregates.buyer.Buyer;
import lrskyum.sbdemo.business.aggregates.order.CustomerOrder;
import lrskyum.sbdemo.business.aggregates.order.OrdersRepository;
import lrskyum.sbdemo.business.aggregates.order.PaymentMethod;
import lrskyum.sbdemo.infrastructure.idempotency.RequestManager;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.stream.IntStream;

@Slf4j
@Component
@RequiredArgsConstructor
@Profile("tempdb")
public class DynamicDataInitializerRunner implements CommandLineRunner {

    private final OrdersRepository ordersRepository;
    private final RequestManager requestManager;

    @Override
    public void run(String... args) {
        initializeData()
                .doOnError(e -> log.error("Error initializing data: {}", e.getMessage()))
                .doOnSuccess(unused -> log.info("Data initialized successfully"))
                .block();
    }

    public Mono<Void> initializeData() {
        return initializeOrders().then(initializeClientRequests());
    }

    public Mono<Void> initializeOrders() {
        var tempOrders = IntStream.range(0, 10).mapToObj(this::createOrder).toList();
        var ordersFlux = Flux.fromIterable(tempOrders).flatMap(ordersRepository::saveAndEmit).then();
        return ordersFlux;
    }

    private CustomerOrder createOrder(int i) {
        var buyer = Buyer.builder().email("buyer" + i + "@mail.com").name("John Doe " + i).build();
        var address = Address.builder().street("Street " + i).zip("8000").country("Denmark").build();
        return CustomerOrder.create("Order Description " + i, address, buyer, PaymentMethod.CREDIT_CARD, "Product " + i);
    }

    public Mono<Void> initializeClientRequests() {
        var commands = IntStream.range(0, 10).mapToObj(i -> "InitialIdentifiedCommand" + i).toList();
        var clientRequestFlux = Flux.fromIterable(commands).flatMap(c -> requestManager.createRequestForCommand(UUID.randomUUID(), c)).then();
        return clientRequestFlux;
    }
}