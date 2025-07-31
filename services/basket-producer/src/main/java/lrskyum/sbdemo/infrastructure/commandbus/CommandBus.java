package lrskyum.sbdemo.infrastructure.commandbus;

import lrskyum.sbdemo.app.commands.IdentifiedCommand;

public interface CommandBus {
  <R, C extends IdentifiedCommand<R>> R send(C command);
}
