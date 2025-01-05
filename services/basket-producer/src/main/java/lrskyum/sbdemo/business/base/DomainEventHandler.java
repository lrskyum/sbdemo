package lrskyum.sbdemo.business.base;

public interface DomainEventHandler<E> {
  void handle(E event);
}
