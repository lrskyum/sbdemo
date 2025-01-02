package lrskyum.sbdemo.infrastructure.repository;

import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.business.domain.Address;
import lrskyum.sbdemo.business.domain.Buyer;
import lrskyum.sbdemo.business.domain.CustomerOrder;
import lrskyum.sbdemo.business.domain.OrdersRepository;
import lrskyum.sbdemo.business.domain.PaymentMethod;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.ReactiveTransaction;
import org.springframework.transaction.reactive.TransactionalOperator;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.stream.IntStream;

@Slf4j
@Component
@Profile("tempdb")
public class DynamicDataInitializerRunner implements CommandLineRunner {

    private final OrdersRepository ordersRepository;
    private final TransactionalOperator transactionalOperator;

    public DynamicDataInitializerRunner(OrdersRepository repo, TransactionalOperator txOp) {
        ordersRepository = repo;
        transactionalOperator = txOp;
    }

    @Override
    public void run(String... args) {
        initializeData()
                .doOnError(e -> log.error("Error initializing data: {}", e.getMessage()))
                .doOnSuccess(unused -> log.info("Data initialized successfully"))
                .block();
    }

    public Mono<Void> initializeData() {
        return initializeOrders();
    }

    private CustomerOrder createOrder(int i) {
        var buyer = Buyer.builder().email("buyer" + i + "@mail.com").name("John Doe " + i).build();
        var address = Address.builder().street("Street " + i).zip("8000").country("Denmark").build();
        return CustomerOrder.create("Order Description " + i, address, buyer, PaymentMethod.CREDIT_CARD, "Product " + i);
    }

    public Mono<Void> initializeOrders() {
        var tempOrders = IntStream.range(0, 10).mapToObj(this::createOrder).toList();
        var ordersFlux = Flux.fromIterable(tempOrders).flatMap(ordersRepository::save).then();
        return ordersFlux;
    }
}