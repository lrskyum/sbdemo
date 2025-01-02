package lrskyum.sbdemo.app.commands;

import an.awesome.pipelinr.Command;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.UUID;

@Getter
@RequiredArgsConstructor
public abstract class IdentifiedCommand<C extends Command<R>, R> implements Command<R> {
    private final C command;
    private final UUID id;
}
