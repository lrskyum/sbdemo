package lrskyum.sbdemo.business.exceptions;

public class BasketDomainException extends RuntimeException {
  public BasketDomainException(String message) {
    super(message);
  }
}
