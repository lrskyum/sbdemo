package lrskyum.sbdemo.app.commands;

import java.util.UUID;

public class CreateOrderIdentifiedCommand extends IdentifiedCommand<CreateOrderCommand, Boolean> {
    public CreateOrderIdentifiedCommand(CreateOrderCommand command, UUID id) {
        super(command, id);
    }
}
