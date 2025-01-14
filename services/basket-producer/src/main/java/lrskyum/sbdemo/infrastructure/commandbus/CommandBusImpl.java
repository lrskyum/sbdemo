package lrskyum.sbdemo.infrastructure.commandbus;

import an.awesome.pipelinr.Command;
import an.awesome.pipelinr.Pipeline;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import lrskyum.sbdemo.app.commands.IdentifiedCommand;
import lrskyum.sbdemo.infrastructure.idempotency.RequestManager;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Component
public class CommandBusImpl implements CommandBus {
    private final RequestManager requestManager;
    private final Pipeline pipeline;

    @Override
    public <R, C extends Command<R>> R send(C command) {
        var rnd = UUID.randomUUID().toString();
        var resultMono = requestManager
                .exist(command instanceof IdentifiedCommand ic ? ic.getId() : rnd)
                .flatMap(exists -> {
                    if (exists) {
                        return Mono.fromCallable(() -> pipeline.send(command))
                                .doOnError(e -> log.error("Error executing command: {}", command.getClass().getSimpleName(), e));
                    } else {
                        return requestManager
                                .createRequestForCommand(command instanceof IdentifiedCommand ic ? ic.getId() : rnd,
                                        command.getClass().getSimpleName())
                                .flatMap(cr -> Mono.fromCallable(() -> pipeline.send(command)))
                                .doOnError(e -> log.error("Error executing command: {}", command.getClass().getSimpleName(), e));
                    }
                });
        return resultMono.block();
    }
}
