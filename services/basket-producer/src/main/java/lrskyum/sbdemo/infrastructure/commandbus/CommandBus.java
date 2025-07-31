package lrskyum.sbdemo.infrastructure.commandbus;

import lrskyum.sbdemo.application.commands.IdentifiedCommand;

public interface CommandBus {
  <R, C extends IdentifiedCommand<R>> R send(C command);
}
