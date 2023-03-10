package ru.sorbo.inventory.domain.spec;

import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.exception.GenericSpecificationException;
import ru.sorbo.inventory.domain.spec.shared.AbstractSpecification;

public class EmptyNetworkSpec extends AbstractSpecification<Switch> {
  @Override
  public boolean isSatisfiedBy(Switch switchNetwork) {
    return switchNetwork.getSwitchNetworks()== null|| switchNetwork.getSwitchNetworks().isEmpty();
  }

  @Override
  public void check(Switch aSwitch) throws GenericSpecificationException {
    if(!isSatisfiedBy(aSwitch))
      throw new GenericSpecificationException("It's not possible to remove a switch with networks attached to it");
  }
}
