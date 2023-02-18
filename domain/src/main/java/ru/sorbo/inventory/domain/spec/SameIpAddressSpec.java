package ru.sorbo.inventory.domain.spec;

import ru.sorbo.inventory.domain.entity.Equipment;
import ru.sorbo.inventory.domain.exception.GenericSpecificationException;
import ru.sorbo.inventory.domain.spec.shared.AbstractSpecification;

public class SameIpAddressSpec extends AbstractSpecification<Equipment> {
  private Equipment equipment;

  public SameIpAddressSpec(Equipment equipment){
    this.equipment = equipment;
  }

  @Override
  public boolean isSatisfiedBy(Equipment anyEquipment) {
    return !equipment.getIp().equals(anyEquipment.getIp());
  }

  @Override
  public void check(Equipment equipment) {
    if(!isSatisfiedBy(equipment))
      throw new GenericSpecificationException("It's not possible to attach routers with the same IP");
  }
}
