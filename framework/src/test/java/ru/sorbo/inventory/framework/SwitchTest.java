package ru.sorbo.inventory.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.domain.vo.Model;
import ru.sorbo.inventory.domain.vo.SwitchType;
import ru.sorbo.inventory.domain.vo.Vendor;
import ru.sorbo.inventory.framework.adapters.input.generic.SwitchManagementGenericAdapter;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class SwitchTest extends FrameworkTestData {
	SwitchManagementGenericAdapter switchManagementGenericAdapter;

  public SwitchTest(){
      this.switchManagementGenericAdapter = new SwitchManagementGenericAdapter();
      loadData();
  }

  @Test
  @Order(1)
  public void retrieveSwitch(){
      Identifier switchId = Identifier.withId("922dbcd5-d071-41bd-920b-00f83eb4bb47");
      Switch networkSwitch = switchManagementGenericAdapter.retrieveSwitch(switchId);
      assertNotNull(networkSwitch);
  }

  @Test
  @Order(2)
  public void createAndAddSwitchToEdgeRouter(){
      var expectedSwitchIP = "15.0.0.1";
      var id = Identifier.withId("b07f5187-2d82-4975-a14b-bdbad9a8ad46");
      EdgeRouter edgeRouter = switchManagementGenericAdapter.createAndAddSwitchToEdgeRouter(
              Vendor.HP,
              Model.XYZ0004,
              IPAddress.fromAddress(expectedSwitchIP),
              locationA,
              SwitchType.LAYER3,
              id);
      String actualSwitchIP = edgeRouter.getSwitches()
              .entrySet()
              .stream()
              .map(entry -> entry.getValue())
              .map(aSwitch -> aSwitch.getIp().getIpAddress())
              .filter(ipAddress -> ipAddress.equals(expectedSwitchIP))
              .findFirst()
              .get();
      assertEquals(expectedSwitchIP,actualSwitchIP);
  }

  @Test
  @Order(3)
  public void removeSwitchFromEdgeRouter(){
      Identifier switchId = Identifier.withId("922dbcd5-d071-41bd-920b-00f83eb4bb47");
      Identifier edgerRouterId = Identifier.withId("b07f5187-2d82-4975-a14b-bdbad9a8ad46");
      EdgeRouter edgeRouter = switchManagementGenericAdapter
              .removeSwitchFromEdgeRouter(switchId, edgerRouterId);
      assertNull(edgeRouter.getSwitches().get(switchId));
  }
}
