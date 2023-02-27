package ru.sorbo.inventory.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sorbo.inventory.domain.spec.EmptyNetworkSpec;
import ru.sorbo.inventory.domain.spec.SameCountrySpec;
import ru.sorbo.inventory.domain.spec.SameIpAddressSpec;
import ru.sorbo.inventory.domain.vo.*;

import java.util.Map;

@Getter
@ToString
public class EdgeRouter extends Router {

	@Setter
	private Map<Identifier, Switch> switches;

  @Builder
  public EdgeRouter(Identifier id, Vendor vendor, Model model,
                    IPAddress ip, Location location, RouterType routerType,
                    Map<Identifier, Switch> switches) {
    super(id, vendor, model, ip, location, routerType);
    this.switches = switches;
  }

  public void addSwitch(Switch anySwitch) {
    var sameCountryRouterSpec = new SameCountrySpec(this);
    var sameIpSpec = new SameIpAddressSpec(this);

    sameCountryRouterSpec.check(anySwitch);
    sameIpSpec.check(anySwitch);

    this.switches.put(anySwitch.id,anySwitch);
  }

  public Switch removeSwitch(Switch anySwitch) {
    var emptyNetworkSpec = new EmptyNetworkSpec();
    emptyNetworkSpec.check(anySwitch);

    return this.switches.remove(anySwitch.id);
  }
}
