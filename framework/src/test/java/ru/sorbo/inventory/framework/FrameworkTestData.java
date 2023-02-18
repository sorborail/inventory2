package ru.sorbo.inventory.framework;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.domain.vo.Location;
import ru.sorbo.inventory.domain.vo.Model;
import ru.sorbo.inventory.domain.vo.Network;
import ru.sorbo.inventory.domain.vo.RouterType;
import ru.sorbo.inventory.domain.vo.SwitchType;
import ru.sorbo.inventory.domain.vo.Vendor;

public class FrameworkTestData {
	protected List<Router> routers = new ArrayList<>();

  protected List<Switch> switches = new ArrayList<>();

  protected List<Network> networks = new ArrayList<>();

  protected Map<Identifier, Router> routersOfCoreRouter = new HashMap<>();

  protected Map<Identifier, Switch> switchesOfEdgeRouter = new HashMap<>();

  protected Network network;

  protected Switch networkSwitch;

  protected CoreRouter coreRouter;

  protected CoreRouter newCoreRouter;

  protected EdgeRouter edgeRouter;

  protected EdgeRouter newEdgeRouter;

  protected Location locationA;

  protected Location locationB;

  public void loadData(){
      this.locationA = Location.builder().
              address("Amos Ln").
              city("Tully").
              state("NY").
              country("United States").
              zipCode(13159).
              latitude(42.797310F).
              longitude(-76.130750F).
              build();
      this.locationB = Location.builder().
              address("Av Republica Argentina 3109").
              city("Curitiba").
              state("PR").
              country("Italy").
              zipCode(80610260).
              latitude(10F).
              longitude(-10F).
              build();
      this.network  = Network.builder().
              networkAddress(IPAddress.fromAddress("20.0.0.0")).
              networkName("TestNetwork").
              networkCidr(8).
              build();
      this.networks.add(network);
      this.networkSwitch = Switch.builder().
              switchId(Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
              vendor(Vendor.CISCO).
              model(Model.XYZ0004).
              ip(IPAddress.fromAddress("20.0.0.100")).
              location(locationA).
              switchType(SwitchType.LAYER3).
              switchNetworks(networks).
              build();
      this.switchesOfEdgeRouter.put(networkSwitch.getId(), networkSwitch);
      this.edgeRouter = EdgeRouter.builder().
              id(Identifier.withoutId()).
              vendor(Vendor.CISCO).
              model(Model.XYZ0002).
              ip(IPAddress.fromAddress("20.0.0.1")).
              location(locationA).
              routerType(RouterType.EDGE).
              switches(switchesOfEdgeRouter).
              build();
      this.routersOfCoreRouter.put(edgeRouter.getId(), edgeRouter);
      this.coreRouter = CoreRouter.builder().
              id(Identifier.withoutId()).
              vendor(Vendor.HP).
              model(Model.XYZ0001).
              ip(IPAddress.fromAddress("10.0.0.1")).
              location(locationA).
              routerType(RouterType.CORE).
              routers(routersOfCoreRouter).
              build();
      this.newCoreRouter = CoreRouter.builder().
              id(Identifier.withId("81579b05-4b4e-4b9b-91f4-75a5a8561296")).
              vendor(Vendor.HP).
              model(Model.XYZ0001).
              ip(IPAddress.fromAddress("10.1.0.1")).
              location(locationA).
              routerType(RouterType.CORE).
              build();
      this.newEdgeRouter = EdgeRouter.builder().
              id(Identifier.withId("ca23800e-9b5a-11eb-a8b3-0242ac130003")).
              vendor(Vendor.CISCO).
              model(Model.XYZ0002).
              ip(IPAddress.fromAddress("20.1.0.1")).
              location(locationA).
              routerType(RouterType.EDGE).
              build();
  }
}
