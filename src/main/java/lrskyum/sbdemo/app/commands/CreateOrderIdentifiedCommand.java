package lrskyum.sbdemo.app.commands;

import lombok.Getter;

import java.util.UUID;

@Getter
public class CreateOrderIdentifiedCommand extends IdentifiedCommand<CreateOrderCommand, Boolean> {
    public CreateOrderIdentifiedCommand(CreateOrderCommand command, UUID id) {
        super(command, id);
    }
}
