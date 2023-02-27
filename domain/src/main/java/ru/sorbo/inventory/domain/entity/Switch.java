package ru.sorbo.inventory.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import ru.sorbo.inventory.domain.spec.CIDRSpec;
import ru.sorbo.inventory.domain.spec.NetworkAmountSpec;
import ru.sorbo.inventory.domain.spec.NetworkAvailabilitySpec;
import ru.sorbo.inventory.domain.vo.*;

import java.util.List;
import java.util.function.Predicate;

@Getter
public class Switch extends Equipment {
	private SwitchType switchType;
  private List<Network> switchNetworks;

  @Setter
  private Identifier routerId;

  @Builder
  public Switch(Identifier switchId, Identifier routerId, Vendor vendor, Model model, IPAddress ip, 
  		Location location, SwitchType switchType, List<Network> switchNetworks) {
      super(switchId, vendor, model, ip, location);
      this.switchType = switchType;
      this.switchNetworks = switchNetworks;
      this.routerId = routerId;
  }

  public static Predicate<Network> getNetworkProtocolPredicate(Protocol protocol) {
      return s -> s.getNetworkAddress().getProtocol().equals(protocol);
  }

  public static Predicate<Switch> getSwitchTypePredicate(SwitchType switchType) {

    return s -> s.switchType .equals(switchType);
  }

  public boolean addNetworkToSwitch(Network network) {
      var availabilitySpec = new NetworkAvailabilitySpec(network);
      var cidrSpec = new CIDRSpec();
      var amountSpec = new NetworkAmountSpec();

      cidrSpec.check(network.getNetworkCidr());
      availabilitySpec.check(this);
      amountSpec.check(this);

      return this.switchNetworks.add(network);
  }

  public boolean removeNetworkFromSwitch(Network network) {
    // здесь нужно добавить правило
    /* var availabilitySpec = new NetworkAvailabilitySpec(network);
    availabilitySpec.check(this); */

    return this.switchNetworks.remove(network);
  }
}
