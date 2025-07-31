package lrskyum.sbdemo.domain.base;

public interface DomainEventHandler<E> {
  void handle(E event);
}
