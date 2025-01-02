package lrskyum.sbdemo.infrastructure.commandbus;

import an.awesome.pipelinr.Command;
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
    public <R, C extends Command<R>, IC extends IdentifiedCommand<C, R>> Mono<R> send(IC command) {
        var resultMono = requestManager
                .exist(command.getId())
                .flatMap(exists -> {
                    if (!exists) {
                        return requestManager
                                .createRequestForCommand(command.getId(), command.getClass().getSimpleName())
                                .flatMap(cr -> Mono.fromCallable(() -> pipeline.send(command)))
                                .doOnError(e -> log.error("Error executing command: {}", command.getClass().getSimpleName(), e));
                    } else {
                        return Mono.fromCallable(() -> pipeline.send(command))
                                .doOnError(e -> log.error("Error executing command: {}", command.getClass().getSimpleName(), e));
                    }
                });
        return resultMono;
    }
}
