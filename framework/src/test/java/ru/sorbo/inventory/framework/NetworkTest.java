package ru.sorbo.inventory.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import java.util.function.Predicate;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.service.NetworkService;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.domain.vo.Network;
import ru.sorbo.inventory.framework.adapters.input.generic.NetworkManagementGenericAdapter;
import ru.sorbo.inventory.framework.adapters.input.generic.SwitchManagementGenericAdapter;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class NetworkTest extends FrameworkTestData {
	NetworkManagementGenericAdapter networkManagementGenericAdapter;
  SwitchManagementGenericAdapter switchManagementGenericAdapter;

  public NetworkTest(){
      this.networkManagementGenericAdapter = new NetworkManagementGenericAdapter();
      this.switchManagementGenericAdapter = new SwitchManagementGenericAdapter();
      loadData();
  }
  @Test
  @Order(1)
  public void addNetworkToSwitch(){
      Identifier switchId = Identifier.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46");
      Switch networkSwitch = networkManagementGenericAdapter.addNetworkToSwitch(network, switchId);
      Predicate<Network> predicate = Network.getNetworkNamePredicate("TestNetwork");
      Network actualNetwork = NetworkService.findNetwork(networkSwitch.getSwitchNetworks(), predicate);
      assertEquals(network, actualNetwork);
  }
  @Test
  @Order(2)
  public void removeNetworkFromSwitch(){
      Identifier switchId = Identifier.withId("922dbcd5-d071-41bd-920b-00f83eb4bb46");
      var networkName = "HR";
      Predicate<Network> predicate = Network.getNetworkNamePredicate(networkName);
      Switch networkSwitch = switchManagementGenericAdapter.retrieveSwitch(switchId);
      Network existentNetwork = NetworkService.findNetwork(networkSwitch.getSwitchNetworks(), predicate);
      assertNotNull(existentNetwork);
      networkSwitch = networkManagementGenericAdapter.removeNetworkFromSwitch(networkName, switchId);
      assertNull(networkSwitch);
  }
}
