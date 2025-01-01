package lrskyum.sbdemo.business.exceptions;


import lrskyum.sbdemo.business.base.BusinessRule;

public class BusinessRuleBrokenException extends RuntimeException {
  public BusinessRuleBrokenException(BusinessRule rule) {
    super(rule.message());
  }
}
