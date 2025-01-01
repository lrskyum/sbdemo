package lrskyum.sbdemo.app;

import lrskyum.sbdemo.infrastructure.OrdersRepository;
import reactor.core.publisher.Mono;

public interface DataInitializationService {
    Mono<Void> initializeData();
}
