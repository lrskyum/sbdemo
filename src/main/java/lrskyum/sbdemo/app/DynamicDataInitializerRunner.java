package lrskyum.sbdemo.app;

import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.business.domain.Address;
import lrskyum.sbdemo.business.domain.CustomerOrder;
import lrskyum.sbdemo.infrastructure.OrdersRepository;
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

    private CustomerOrder createOrder(int i) {
        var address = Address.builder()
                .city("Aarhus")
                .country("Denmark")
                .zipCode("8000")
                .street("Lars Street " + i)
                .build();
        return CustomerOrder.create("Lars Description", address);
    }

    public Mono<Void> initializeFlux(ReactiveTransaction reactiveTransaction) {
        var tempOrders = IntStream.range(0, 3)
                .mapToObj(this::createOrder)
                .toList();
        var ordersFlux = Flux.fromIterable(tempOrders)
                .flatMap(ordersRepository::save) // Save each entity
                .then(); // Return a Mono<Void> signaling completion
        return ordersFlux; //.as(transactionalOperator::transactional);
    }

    public Mono<Void> initializeData() {
        return transactionalOperator.execute(this::initializeFlux)
                .then();
    }
}