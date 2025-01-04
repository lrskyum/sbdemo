package lrskyum.sbdemo.infrastructure.commandbus;

import an.awesome.pipelinr.Command;
import reactor.core.publisher.Mono;

public interface CommandBus {
  <R, C extends Command<R>> R send(C command);
}
