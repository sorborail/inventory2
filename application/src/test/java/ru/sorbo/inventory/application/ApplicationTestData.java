package ru.sorbo.inventory.application;

import org.mockito.Mockito;
import ru.sorbo.inventory.application.ports.input.NetworkManagementInputPort;
import ru.sorbo.inventory.application.ports.input.RouterManagementInputPort;
import ru.sorbo.inventory.application.ports.input.SwitchManagementInputPort;
import ru.sorbo.inventory.application.ports.output.RouterManagementOutputPort;
import ru.sorbo.inventory.application.ports.output.SwitchManagementOutputPort;
import ru.sorbo.inventory.application.usecases.NetworkManagementUseCase;
import ru.sorbo.inventory.application.usecases.RouterManagementUseCase;
import ru.sorbo.inventory.application.usecases.SwitchManagementUseCase;
import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ApplicationTestData {
  protected RouterManagementUseCase routerManagementUseCase;

  protected SwitchManagementUseCase switchManagementUseCase;

  protected NetworkManagementUseCase networkManagementUseCase;

  protected RouterManagementOutputPort routerManagementOutputPortMock;
  protected SwitchManagementOutputPort switchManagementOutputPortMock;
  protected Router router;

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

  public void loadData() {
    this.routerManagementOutputPortMock = Mockito.mock(RouterManagementOutputPort.class);
    this.switchManagementOutputPortMock = Mockito.mock(SwitchManagementOutputPort.class);
    this.routerManagementUseCase = new RouterManagementInputPort(routerManagementOutputPortMock);
    this.switchManagementUseCase = new SwitchManagementInputPort(switchManagementOutputPortMock);
    this.networkManagementUseCase = new NetworkManagementInputPort(routerManagementOutputPortMock);
    this.locationA = Location.builder().
        address("Большая ордынка, 44/4").
        city("Москва").
        state("Москва").
        country("Россия").
        zipCode(111111).
        latitude(55.735682).
        longitude(37.622863).
        build();
    this.locationB = Location.builder().
        address("Октябрьская улица, 15к15").
        city("Минск").
        state("Минск").
        country("Беларусь").
        zipCode(222222).
        latitude(53.893429). 
        longitude(27.568533).
        build();
    this.network  = Network.builder().
        networkAddress(IPAddress.fromAddress("20.0.0.0")).
        networkName("TestNetwork").
        networkCidr(8).
        build();
    this.networks.add(network);
    this.networkSwitch = Switch.builder().
        switchId(Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
        routerId(Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
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
    this.coreRouter.addRouter(newCoreRouter);
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
