package lrskyum.sbdemo.infrastructure.commandbus;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.app.commands.IdentifiedCommand;
import lrskyum.sbdemo.infrastructure.idempotency.RequestManager;
import org.springframework.stereotype.Component;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommandBusImpl implements CommandBus {
    private final RequestManager requestManager;
    private final Pipeline pipeline;

    @Override
    public <R, C extends IdentifiedCommand<R>> R send(C command) {
        try {
            final var id = command.getId();
            if (requestManager.exist(id)) {
                return pipeline.send(command);
            } else {
                requestManager.createRequestForCommand(id, command.getClass().getSimpleName());
                return pipeline.send(command);
            }
        } catch (Exception e) {
            log.error("Error executing command: {}", command.getClass().getSimpleName(), e);
            throw e; // Re-throw the exception to indicate failure
        }
    }
}