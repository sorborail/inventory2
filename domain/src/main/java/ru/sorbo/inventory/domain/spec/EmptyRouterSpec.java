package ru.sorbo.inventory.domain.spec;

import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.exception.GenericSpecificationException;
import ru.sorbo.inventory.domain.spec.shared.AbstractSpecification;

public class EmptyRouterSpec extends AbstractSpecification<CoreRouter> {
  @Override
  public boolean isSatisfiedBy(CoreRouter coreRouter) {
    return coreRouter.getRouters() == null|| coreRouter.getRouters().isEmpty();
  }

  @Override
  public void check(CoreRouter coreRouter) {
    if(!isSatisfiedBy(coreRouter))
      throw new GenericSpecificationException("It isn't allowed to remove a core router with other routers attached to it");
  }
}
