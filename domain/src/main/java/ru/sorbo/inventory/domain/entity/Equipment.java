package ru.sorbo.inventory.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import ru.sorbo.inventory.domain.vo.*;

import java.util.function.Predicate;

@Getter
@AllArgsConstructor
public abstract class Equipment {
  protected Identifier id;
  protected Vendor vendor;
  protected Model model;
  protected IPAddress ip;
  protected Location location;

  public static Predicate<Equipment> getVendorPredicate(Vendor vendor) {
    return r -> r.getVendor().equals(vendor);
  }
}
