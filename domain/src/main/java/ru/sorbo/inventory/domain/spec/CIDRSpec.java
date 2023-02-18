package ru.sorbo.inventory.domain.spec;

import ru.sorbo.inventory.domain.exception.GenericSpecificationException;
import ru.sorbo.inventory.domain.spec.shared.AbstractSpecification;

public class CIDRSpec extends AbstractSpecification<Integer> {
  public static final int MINIMUM_ALLOWED_CIDR = 8;

  @Override
  public boolean isSatisfiedBy(Integer cidr) {
    return cidr >= MINIMUM_ALLOWED_CIDR;
  }

  @Override
  public void check(Integer cidr) throws GenericSpecificationException {
    if(!isSatisfiedBy(cidr))
      throw new GenericSpecificationException("CIDR is below "+CIDRSpec.MINIMUM_ALLOWED_CIDR);
  }
}
