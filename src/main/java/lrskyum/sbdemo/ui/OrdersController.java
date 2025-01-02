package lrskyum.sbdemo.ui;

import lombok.RequiredArgsConstructor;
import lrskyum.sbdemo.app.commands.CreateOrderIdentifiedCommand;
import lrskyum.sbdemo.business.domain.CustomerOrder;
import lrskyum.sbdemo.business.domain.OrdersRepository;
import lrskyum.sbdemo.infrastructure.commandbus.CommandBus;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class OrdersController {

    private final CommandBus commandBus;
    private final OrdersRepository ordersRepository;

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerOrder> getAllOrders() {
        return ordersRepository.findAll();
    }

    @PostMapping(value = "/orders")
    @ResponseStatus(HttpStatus.OK)
    public Mono<Boolean> createOrderDraft(@RequestBody CreateOrderIdentifiedCommand command) {
        return commandBus.send(command);
    }
}
