package lrskyum.sbdemo.app.commands.usercheckout;

import an.awesome.pipelinr.Command;
import lombok.Getter;
import lrskyum.sbdemo.app.commands.IdentifiedCommand;
import reactor.core.publisher.Mono;

@Getter
public class UserCheckoutIdentifiedCommand extends UserCheckoutCommand implements IdentifiedCommand, Command<Mono<Boolean>> {
    private final String id;

    public UserCheckoutIdentifiedCommand(UserCheckoutCommand command, String id) {
        super(command.getOrderDateUtc(), command.getBasketStatus(), command.getBuyerName(), command.getPaymentMethod(), command.getProduct());
        this.id = id;
    }
}
