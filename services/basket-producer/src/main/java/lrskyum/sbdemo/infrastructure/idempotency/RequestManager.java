package lrskyum.sbdemo.infrastructure.idempotency;

public interface RequestManager {
    Boolean exist(String id);

    ClientRequest createRequestForCommand(String id, String commandName);
}
