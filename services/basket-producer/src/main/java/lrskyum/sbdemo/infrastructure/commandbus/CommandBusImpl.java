package lrskyum.sbdemo.infrastructure.commandbus;

import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.app.commands.IdentifiedCommand;
import lrskyum.sbdemo.infrastructure.idempotency.RequestManager;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommandBusImpl implements CommandBus {
    private final RequestManager requestManager;
    private final Pipeline pipeline;

    @Override
    public <R, C extends IdentifiedCommand<R>> R send(C command) {
        final var id = command.getId();
        var resultMono = requestManager
                .exist(id)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.fromCallable(() -> pipeline.send(command))
                                .doOnError(e -> log.error("Error executing command: {}", command.getClass().getSimpleName(), e));
                    } else {
                        return requestManager
                                .createRequestForCommand(id, command.getClass().getSimpleName())
                                .map(cr -> pipeline.send(command))
                                .doOnError(e -> log.error("Error executing command: {}", command.getClass().getSimpleName(), e));
                    }
                });
        return resultMono.block();
    }
}
