package ru.sorbo.inventory.application;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.*;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SwitchAdd extends ApplicationTestData {

  public SwitchAdd(){
    loadData();
  }

  @Дано("У меня есть коммутатор")
  public void i_provide_a_switch() {
    networkSwitch = Switch.builder().
        switchId(Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
        routerId(Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
        vendor(Vendor.CISCO).
        model(Model.XYZ0004).
        ip(IPAddress.fromAddress("20.0.0.100")).
        location(locationA).
        switchType(SwitchType.LAYER3).
        build();
    assertNotNull(networkSwitch);
  }

  @Тогда("Я добавялю этот коммутатор к внутреннему роутеру")
  public void i_add_the_switch_to_the_edge_router() {
    assertNotNull(edgeRouter);
    edgeRouter = this.switchManagementUseCase.
        addSwitchToEdgeRouter(networkSwitch, edgeRouter);
    var actualId = networkSwitch.getId();
    var expectedId = edgeRouter.
        getSwitches().
        get(Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490")).
        getId();
    assertEquals(expectedId, actualId);
  }
}
