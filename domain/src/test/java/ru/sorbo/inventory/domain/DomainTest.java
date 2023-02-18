package ru.sorbo.inventory.domain;

import ru.sorbo.inventory.domain.exception.GenericSpecificationException;
import ru.sorbo.inventory.domain.service.NetworkService;
import ru.sorbo.inventory.domain.service.RouterService;
import ru.sorbo.inventory.domain.service.SwitchService;
import ru.sorbo.inventory.domain.vo.*;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.Router;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInfo;

import static org.junit.jupiter.api.Assertions.*;

class DomainTest {
	
  @Test
  @DisplayName("Add network to switch")
  void addNetworkToSwitch(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var location = createLocation("RU");
    var newNetwork = createTestNetwork("30.0.0.1", 8);
    var networkSwitch = createSwitch("30.0.0.0", 8, location);
    assertTrue(networkSwitch.addNetworkToSwitch(newNetwork), testInfo.getDisplayName());
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Add network to switch - check same network address")
  void addNetworkToSwitch_failBecauseSameNetworkAddress(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var location = createLocation("RU");
    var newNetwork = createTestNetwork("30.0.0.0", 8);
    var networkSwitch = createSwitch("30.0.0.0", 8, location);
    assertThrows(GenericSpecificationException.class, () -> networkSwitch.addNetworkToSwitch(newNetwork));
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Add switch to edge router")
  void addSwitchToEdgeRouter(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var location = createLocation("RU");
    var networkSwitch = createSwitch("30.0.0.0", 8, location);
    var edgeRouter = createEdgeRouter(location,"30.0.0.1");
    edgeRouter.addSwitch(networkSwitch);
    assertEquals(1,edgeRouter.getSwitches().size());
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Add switch to edge router - check different country of equipment")
  void addSwitchToEdgeRouter_failBecauseEquipmentOfDifferentCountries(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var locationRU = createLocation("RU");
    var locationBY = createLocation("BY");
    var networkSwitch = createSwitch("30.0.0.0", 8, locationRU);
    var edgeRouter = createEdgeRouter(locationBY,"30.0.0.1");
    assertThrows(GenericSpecificationException.class, () -> edgeRouter.addSwitch(networkSwitch));
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Add edge router to core router")
  void addEdgeToCoreRouter(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var location = createLocation("RU");
    var edgeRouter = createEdgeRouter(location,"30.0.0.1");
    var coreRouter = createCoreRouter(location, "40.0.0.1");
    coreRouter.addRouter(edgeRouter);
    assertEquals(1,coreRouter.getRouters().size());
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Add edge router to core router - check different country of routers")
  void addEdgeToCoreRouter_failBecauseRoutersOfDifferentCountries(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var locationRU = createLocation("RU");
    var locationBY = createLocation("BY");
    var edgeRouter = createEdgeRouter(locationRU,"30.0.0.1");
    var coreRouter = createCoreRouter(locationBY, "40.0.0.1");
    assertThrows(GenericSpecificationException.class, () -> coreRouter.addRouter(edgeRouter));
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Add core router to core router")
  void addCoreToCoreRouter(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var location = createLocation("RU");
    var coreRouter = createCoreRouter(location, "30.0.0.1");
    var newCoreRouter = createCoreRouter(location, "40.0.0.1");
    coreRouter.addRouter(newCoreRouter);
    assertEquals(1,coreRouter.getRouters().size());
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Add core router to core router - check same ip of router")
  void addCoreToCoreRouter_failBecauseRoutersOfSameIp(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var location = createLocation("RU");
    var coreRouter = createCoreRouter(location, "30.0.0.1");
    var newCoreRouter = createCoreRouter(location, "30.0.0.1");
    assertThrows(GenericSpecificationException.class, () -> coreRouter.addRouter(newCoreRouter));
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Remove router")
  void removeRouter(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var location = createLocation("RU");
    var coreRouter = createCoreRouter(location, "30.0.0.1");
    var edgeRouter = createEdgeRouter(location, "40.0.0.1");
    var expectedId = edgeRouter.getId();
    coreRouter.addRouter(edgeRouter);
    var actualId = coreRouter.removeRouter(edgeRouter).getId();
    assertEquals(expectedId, actualId);
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Remove switch")
  void removeSwitch(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var location = createLocation("RU");
    var network = createTestNetwork("30.0.0.0", 8);
    var networkSwitch = createSwitch("30.0.0.0", 8, location);
    var edgeRouter = createEdgeRouter(location, "40.0.0.1");

    edgeRouter.addSwitch(networkSwitch);
    networkSwitch.removeNetworkFromSwitch(network);
    var expectedId = Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
    var actualId= edgeRouter.removeSwitch(networkSwitch).getId();
    assertEquals(expectedId, actualId, "Tast " + testInfo.getDisplayName() + " fail!");
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Remove network")
  void removeNetwork(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var location = createLocation("RU");
    var network = createTestNetwork("30.0.0.0", 8);
    var networkSwitch = createSwitch("30.0.0.0", 8, location);
    assertEquals(1, networkSwitch.getSwitchNetworks().size());
    assertTrue(networkSwitch.removeNetworkFromSwitch(network));
    assertEquals(0, networkSwitch.getSwitchNetworks().size());
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Filter router by type")
  void filterRouterByType(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    List<Router> routers = new ArrayList<>();
    var location = createLocation("RU");
    var coreRouter = createCoreRouter(location, "30.0.0.1");
    var edgeRouter = createEdgeRouter(location, "40.0.0.1");
    routers.add(coreRouter);
    routers.add(edgeRouter);
    var coreRouters = RouterService.filterAndRetrieveRouter(routers,
        Router.getRouterTypePredicate(RouterType.CORE));
    var actualCoreType = coreRouters.get(0).getRouterType();
    assertEquals(RouterType.CORE, actualCoreType);
    var edgeRouters = RouterService.filterAndRetrieveRouter(routers,
        Router.getRouterTypePredicate(RouterType.EDGE));
    var actualEdgeType = edgeRouters.get(0).getRouterType();
    assertEquals(RouterType.EDGE, actualEdgeType);
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Filter router by vendor")
  void filterRouterByVendor(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    List<Router> routers = new ArrayList<>();
    var location = createLocation("US");
    var coreRouter = createCoreRouter(location, "30.0.0.1");
    var edgeRouter = createEdgeRouter(location, "40.0.0.1");
    routers.add(coreRouter);
    routers.add(edgeRouter);
    var actualVendor = RouterService.filterAndRetrieveRouter(routers,
        Router.getVendorPredicate(Vendor.HP)).get(0).getVendor();
    assertEquals(Vendor.HP, actualVendor);
    actualVendor = RouterService.filterAndRetrieveRouter(routers,
        Router.getVendorPredicate(Vendor.CISCO)).get(0).getVendor();
    assertEquals(Vendor.CISCO, actualVendor);
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Filter router by location")
  void filterRouterByLocation(TestInfo testInfo) {
    List<Router> routers = new ArrayList<>();
    var location = createLocation("RU");
    var coreRouter = createCoreRouter(location, "30.0.0.1");
    routers.add(coreRouter);
    var actualCountry = RouterService.filterAndRetrieveRouter(routers,
        Router.getCountryPredicate(location)).get(0).getLocation().getCountry();
    System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    assertEquals(location.getCountry(), actualCountry);
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Filter router by model")
  void filterRouterByModel(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    List<Router> routers = new ArrayList<>();
    var location = createLocation("RU");
    var coreRouter = createCoreRouter(location, "30.0.0.1");
    var newCoreRouter = createCoreRouter(location, "40.0.0.1");
    coreRouter.addRouter(newCoreRouter);
    routers.add(coreRouter);
    var actualModel= RouterService.filterAndRetrieveRouter(routers,
        Router.getModelPredicate(Model.XYZ0001)).get(0).getModel();
    assertEquals(Model.XYZ0001, actualModel);
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Filter switch by type")
  void filterSwitchByType(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    List<Switch> switches = new ArrayList<>();
    var location = createLocation("RU");
    var networkSwitch = createSwitch("30.0.0.0", 8, location);
    switches.add(networkSwitch);
    var actualSwitchType = SwitchService.filterAndRetrieveSwitch(switches,
        Switch.getSwitchTypePredicate(SwitchType.LAYER3)).get(0).getSwitchType();
    assertEquals(SwitchType.LAYER3, actualSwitchType);
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Filter network by protocol")
  void filterNetworkByProtocol(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    var testNetwork = createTestNetwork("30.0.0.0", 8);
    var networks = createNetworks(testNetwork);
    var expectedProtocol = Protocol.IPV4;
    var actualProtocol = NetworkService.filterAndRetrieveNetworks(networks,
        Switch.getNetworkProtocolPredicate(Protocol.IPV4)).get(0).getNetworkAddress().getProtocol();
    assertEquals(expectedProtocol, actualProtocol);
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Find router by ID")
  void findRouterById(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    Map<Identifier, Router> routersOfCoreRouter = new HashMap<>();
    var location = createLocation("RU");
    var coreRouter = createCoreRouter(location, "30.0.0.1");
    var newCoreRouter = createCoreRouter(location, "40.0.0.1");
    coreRouter.addRouter(newCoreRouter);
    routersOfCoreRouter.put(newCoreRouter.getId(), newCoreRouter);
    var expectedId = newCoreRouter.getId();
    var actualId = RouterService.findById(routersOfCoreRouter, expectedId).getId();
    assertEquals(expectedId, actualId);
    System.out.println(" OK");
  }

  @Test
  @DisplayName("Find switch by ID")
  void findSwitchById(TestInfo testInfo) {
  	System.out.print("Running test: " + testInfo.getDisplayName() + " ...");
    Map<Identifier, Switch> switchesOfEdgeRouter = new HashMap<>();
    var location = createLocation("RU");
    var networkSwitch = createSwitch("30.0.0.0", 8, location);
    switchesOfEdgeRouter.put(networkSwitch.getId(), networkSwitch);
    var expectedId = Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
    var actualId = SwitchService.findById(switchesOfEdgeRouter, expectedId).getId();
    assertEquals(expectedId, actualId);
    System.out.println(" OK");
  }

  private Network createTestNetwork(String address, int CIDR) {
    return Network.builder().
        networkAddress(IPAddress.fromAddress(address)).
        networkName("NewNetwork").
        networkCidr(CIDR).
        build();
  }

  private Location createLocation(String country) {
    return Location.builder().
        address("Test street").
        city("Test City").
        state("Test State").
        country(country).
        zipCode(00000).
        latitude(10F).
        longitude(-10F).
        build();
  }

  private List<Network> createNetworks(Network network) {
    List<Network> networks = new ArrayList<>();
    networks.add(network);
    return networks;
  }

  private Switch createSwitch(String address, int cidr, Location location) {
    var newNetwork = createTestNetwork(address, cidr);
    var networks = createNetworks(newNetwork);
    var networkSwitch = createNetworkSwitch(location, networks);
    return networkSwitch;
  }

  private Switch createNetworkSwitch(Location location, List<Network> networks) {
    return Switch.builder().
        switchId(Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
        routerId(Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
        vendor(Vendor.CISCO).
        model(Model.XYZ0004).
        ip(IPAddress.fromAddress("20.0.0.100")).
        location(location).
        switchType(SwitchType.LAYER3).
        switchNetworks(networks).
        build();
  }

  private EdgeRouter createEdgeRouter(Location location, String address) {
    Map<Identifier, Switch> switchesOfEdgeRouter = new HashMap<>();
    return EdgeRouter.builder().
        id(Identifier.withoutId()).
        vendor(Vendor.CISCO).
        model(Model.XYZ0002).
        ip(IPAddress.fromAddress(address)).
        location(location).
        routerType(RouterType.EDGE).
        switches(switchesOfEdgeRouter).
        build();
  }

  private CoreRouter createCoreRouter(Location location, String address) {
    Map<Identifier, Router> routersOfCoreRouter = new HashMap<>();
    return CoreRouter.builder().
        id(Identifier.withoutId()).
        vendor(Vendor.HP).
        model(Model.XYZ0001).
        ip(IPAddress.fromAddress(address)).
        location(location).
        routerType(RouterType.CORE).
        routers(routersOfCoreRouter).
        build();
  }
}
