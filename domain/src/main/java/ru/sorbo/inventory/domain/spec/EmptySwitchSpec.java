package ru.sorbo.inventory.domain.spec;

import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.exception.GenericSpecificationException;
import ru.sorbo.inventory.domain.spec.shared.AbstractSpecification;

public class EmptySwitchSpec extends AbstractSpecification<EdgeRouter> {
  @Override
  public boolean isSatisfiedBy(EdgeRouter edgeRouter) {
    return edgeRouter.getSwitches()==null ||
        edgeRouter.getSwitches().isEmpty();
  }

  @Override
  public void check(EdgeRouter edgeRouter) {
    if(!isSatisfiedBy(edgeRouter))
      throw new GenericSpecificationException("It isn't allowed to remove an edge router with a switch attached to it");
  }
}
