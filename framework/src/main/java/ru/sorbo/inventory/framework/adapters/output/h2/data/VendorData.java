package ru.sorbo.inventory.framework.adapters.output.h2.data;

import jakarta.persistence.Embeddable;

@Embeddable
public enum VendorData {
	CISCO,
  NETGEAR,
  HP,
  TPLINK,
  DLINK,
  JUNIPER
}
