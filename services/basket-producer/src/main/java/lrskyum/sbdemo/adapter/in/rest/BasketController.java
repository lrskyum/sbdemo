package lrskyum.sbdemo.adapter.in.rest;

import lombok.RequiredArgsConstructor;
import lrskyum.sbdemo.application.commands.newbasket.NewBasketCommand;
import lrskyum.sbdemo.domain.aggregates.basket.Basket;
import lrskyum.sbdemo.domain.aggregates.basket.BasketRepository;
import lrskyum.sbdemo.infrastructure.commandbus.CommandBus;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class BasketController {

    private final CommandBus commandBus;
    private final BasketRepository basketRepository;

    @GetMapping("/basket")
    @ResponseStatus(HttpStatus.OK)
    public List<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    @PostMapping(value = "/basket/create")
    @ResponseStatus(HttpStatus.CREATED)
    public Basket create(@RequestBody NewBasketCommand command,
                         @RequestHeader(value = "X-RequestId", required = false) String requestId) {
        if (requestId != null) {
            command = NewBasketCommand.builder()
                    .buyerName(command.getBuyerName())
                    .paymentMethod(command.getPaymentMethod())
                    .product(command.getProduct())
                    .id(requestId)
                    .build();
        }
        return commandBus.send(command);
    }
}
