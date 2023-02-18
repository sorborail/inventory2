package ru.sorbo.inventory.application;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.Identifier;

import static org.junit.Assert.*;

public class SwitchRemove extends ApplicationTestData {
  Identifier id;
  Switch switchToBeRemoved;

  public SwitchRemove(){
    loadData();
  }

  @Дано("Я знаю какой коммутатор хочу удалить")
  public void i_know_the_switch_i_want_to_remove() {
    id = Identifier.withId("f8c3de3d-1fea-4d7c-a8b0-29f63c4c3490");
    switchToBeRemoved = edgeRouter.getSwitches().get(id);
  }

  @И("Этот коммутатор не имеет подключенных сетей")
  public void the_switch_has_no_networks() {
    switchToBeRemoved.removeNetworkFromSwitch(network);
    assertTrue(switchToBeRemoved.getSwitchNetworks().isEmpty());
  }

  @Тогда("Я удаляю этот коммутатор у внутреннего роутера")
  public void i_remove_the_switch_from_the_edge_router(){
    assertNotNull(edgeRouter);
    edgeRouter = this.switchManagementUseCase.
        removeSwitchFromEdgeRouter(switchToBeRemoved, edgeRouter);
    assertNull(edgeRouter.getSwitches().get(id));
  }
}
