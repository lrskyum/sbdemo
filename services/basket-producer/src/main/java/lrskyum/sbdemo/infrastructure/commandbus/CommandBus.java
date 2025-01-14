package lrskyum.sbdemo.infrastructure.commandbus;

import an.awesome.pipelinr.Command;
import lrskyum.sbdemo.app.commands.IdentifiedCommand;
import reactor.core.publisher.Mono;

public interface CommandBus {
  <R, C extends IdentifiedCommand<R>> R send(C command);
}
