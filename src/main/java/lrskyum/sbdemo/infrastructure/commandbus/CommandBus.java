package lrskyum.sbdemo.infrastructure.commandbus;

import an.awesome.pipelinr.Command;
import lrskyum.sbdemo.app.commands.IdentifiedCommand;
import reactor.core.publisher.Mono;

public interface CommandBus {
  <R, C extends Command<R>, IC extends IdentifiedCommand<C, R>> Mono<R> send(IC command);
}
