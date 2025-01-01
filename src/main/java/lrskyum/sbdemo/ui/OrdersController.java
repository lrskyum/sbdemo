package lrskyum.sbdemo.ui;

import lombok.RequiredArgsConstructor;
import lrskyum.sbdemo.business.domain.CustomerOrder;
import lrskyum.sbdemo.business.domain.OrdersRepository;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class OrdersController {

    private final OrdersRepository ordersRepository;

    @GetMapping("/orders")
    @ResponseStatus(HttpStatus.OK)
    public Flux<CustomerOrder> getAllOrders() {
        return ordersRepository.findAll();
    }
}
