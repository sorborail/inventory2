package ru.sorbo.inventory.domain.spec;

import ru.sorbo.inventory.domain.entity.Equipment;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.exception.GenericSpecificationException;
import ru.sorbo.inventory.domain.spec.shared.AbstractSpecification;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Network;

public class NetworkAvailabilitySpec extends AbstractSpecification<Equipment> {
  private IPAddress address;
  private String name;
  private int cidr;

  public NetworkAvailabilitySpec(Network network){
    this.address = network.getNetworkAddress();
    this.name = network.getNetworkName();
    this.cidr = network.getNetworkCidr();
  }

  @Override
  public boolean isSatisfiedBy(Equipment switchNetworks){
    return switchNetworks!=null && isNetworkAvailable(switchNetworks);
  }

  @Override
  public void check(Equipment equipment) throws GenericSpecificationException {
    if(!isSatisfiedBy(equipment))
      throw new GenericSpecificationException("This network already exists");
  }

  private boolean isNetworkAvailable(Equipment switchNetworks){
    var availability = true;
    for (Network network : ((Switch)switchNetworks).getSwitchNetworks()) {
      if(network.getNetworkAddress().equals(address) &&
          network.getNetworkName().equals(name) &&
          network.getNetworkCidr() == cidr)
        availability = false;
      break;
    }
    return availability;
  }
}
