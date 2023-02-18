package ru.sorbo.inventory.domain.spec;

import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.Equipment;
import ru.sorbo.inventory.domain.exception.GenericSpecificationException;
import ru.sorbo.inventory.domain.spec.shared.AbstractSpecification;

public class SameCountrySpec extends AbstractSpecification<Equipment> {
  private Equipment equipment;

  public SameCountrySpec(Equipment equipment){
    this.equipment = equipment;
  }

  @Override
  public boolean isSatisfiedBy(Equipment anyEquipment) {
    if(anyEquipment instanceof CoreRouter) {
      return true;
    } else if (anyEquipment != null && this.equipment != null) {
      return this.equipment.getLocation().getCountry().
          equals(anyEquipment.getLocation().getCountry());
    } else{
      return false;
    }
  }

  @Override
  public void check(Equipment equipment) throws GenericSpecificationException {
    if(!isSatisfiedBy(equipment))
      throw new GenericSpecificationException("The equipments should be in the same country");
  }
}
