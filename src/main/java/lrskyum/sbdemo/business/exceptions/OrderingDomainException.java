package lrskyum.sbdemo.business.exceptions;

public class OrderingDomainException extends RuntimeException {
  public OrderingDomainException(String message) {
    super(message);
  }
}
