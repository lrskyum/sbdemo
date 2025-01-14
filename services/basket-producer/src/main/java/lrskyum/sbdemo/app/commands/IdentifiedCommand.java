package lrskyum.sbdemo.app.commands;

import an.awesome.pipelinr.Command;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class IdentifiedCommand<R> implements Command<R> {
    protected final String id;
}
