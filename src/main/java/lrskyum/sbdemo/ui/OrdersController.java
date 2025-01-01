package lrskyum.sbdemo.ui;

import lombok.RequiredArgsConstructor;
import lrskyum.sbdemo.business.domain.CustomerOrder;
import lrskyum.sbdemo.business.domain.OrdersRepository;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

@RequestMapping("/api/v1/orders")
@RestController
@RequiredArgsConstructor
@Validated
public class OrdersController {

    private final OrdersRepository ordersRepository;

    @GetMapping()
    public Flux<CustomerOrder> getAllOrders() {
        return ordersRepository.findAll();
    }
}
