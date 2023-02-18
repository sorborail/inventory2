package ru.sorbo.inventory.domain.spec;

import ru.sorbo.inventory.domain.entity.Equipment;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.exception.GenericSpecificationException;
import ru.sorbo.inventory.domain.spec.shared.AbstractSpecification;

public class NetworkAmountSpec extends AbstractSpecification<Equipment> {
  public static final int MAXIMUM_ALLOWED_NETWORKS = 6;

  @Override
  public boolean isSatisfiedBy(Equipment switchNetwork) {
    return ((Switch)switchNetwork).getSwitchNetworks().size()
        <=MAXIMUM_ALLOWED_NETWORKS;
  }

  @Override
  public void check(Equipment equipment) throws GenericSpecificationException {
    if(!isSatisfiedBy(equipment))
      throw new GenericSpecificationException("The max number of networks is "+ NetworkAmountSpec.MAXIMUM_ALLOWED_NETWORKS);
  }
}
