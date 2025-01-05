package lrskyum.sbdemo.ui;

import lombok.RequiredArgsConstructor;
import lrskyum.sbdemo.app.commands.usercheckout.UserCheckoutCommand;
import lrskyum.sbdemo.app.commands.usercheckout.UserCheckoutIdentifiedCommand;
import lrskyum.sbdemo.business.aggregates.basket.Basket;
import lrskyum.sbdemo.business.aggregates.basket.BasketRepository;
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
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
@Validated
public class BasketController {

    private final CommandBus commandBus;
    private final BasketRepository basketRepository;

    @GetMapping("/basket")
    @ResponseStatus(HttpStatus.OK)
    public Flux<Basket> getAllBaskets() {
        return basketRepository.findAll();
    }

    @PostMapping(value = "/checkout")
    @ResponseStatus(HttpStatus.CREATED)
    public Mono<Void> checkout(@RequestBody UserCheckoutCommand command,
                               @RequestHeader(value = "X-RequestId", required = false) String requestId) {
        if (requestId != null) {
            command = new UserCheckoutIdentifiedCommand(command, requestId);
        }
        return commandBus.send(command);
    }
}
