package ru.sorbo.inventory.application;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Router;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.sorbo.inventory.domain.vo.RouterType.CORE;
import static ru.sorbo.inventory.domain.vo.RouterType.EDGE;

public class RouterRemove extends ApplicationTestData {
  CoreRouter coreRouterToBeRemoved;

  public RouterRemove(){
    loadData();
  }
  //Removing an edge router from a core router
  @Дано("Основной роутер имеет подключенный один внутренний роутер")
  public void the_core_router_has_at_least_one_edge_router_connected_to_it(){
    var predicate = Router.getRouterTypePredicate(EDGE);
    edgeRouter = (EdgeRouter) this.coreRouter.
        getRouters().
        entrySet().
        stream().
        map(routerMap -> routerMap.getValue()).
        filter(predicate).
        findFirst().
        get();
    assertEquals(EDGE, edgeRouter.getRouterType());
  }

  @И("Коммутатор не подключен к сети")
  public void the_switch_has_no_networks_attached_to_it(){
    var networksSize = networkSwitch.getSwitchNetworks().size();
    assertEquals(1, networksSize);
    networkSwitch.removeNetworkFromSwitch(network);
    networksSize = networkSwitch.getSwitchNetworks().size();
    assertEquals(0, networksSize);
  }

  @И("Этот внутренний роутер не имеет подключенных коммутаторов")
  public void the_edge_router_has_no_switches_attached_to_it(){
    var switchesSize = edgeRouter.getSwitches().size();
    assertEquals(1, switchesSize);
    edgeRouter.removeSwitch(networkSwitch);
    switchesSize = edgeRouter.getSwitches().size();
    assertEquals(0, switchesSize);
  }
  @Тогда("Я удаляю внутренний роутер у основного роутера")
  public void edge_router_is_removed_from_core_router(){
    var actualID = edgeRouter.getId();
    var expectedID = this.routerManagementUseCase.
        removeRouterFromCoreRouter(edgeRouter, coreRouter).
        getId();
    assertEquals(expectedID, actualID);
  }
  //Removing a core router from another core router
  @Дано("Основной роутер имеет подключенный другой основной роутер")
  public void the_core_router_has_at_least_one_core_router_connected_to_it(){
    var predicate = Router.getRouterTypePredicate(CORE);
    coreRouterToBeRemoved = (CoreRouter) this.coreRouter.
        getRouters().
        entrySet().
        stream().
        map(routerMap -> routerMap.getValue()).
        filter(predicate).
        findFirst().
        get();
    assertEquals(CORE, coreRouterToBeRemoved.getRouterType());
  }
  @И("Этот другой основной роутер не имеет подключенных других роутеров")
  public void the_core_router_no_other_routers_connected_to_it(){
    assertTrue(coreRouterToBeRemoved.getRouters().isEmpty());
  }

  @Тогда("Я удаляю этот основной роутер у другого основного роутера")
  public void i_remove_the_core_router_from_another_core_router(){
    var actualId = coreRouterToBeRemoved.getId();
    var expectedId =  this.coreRouter.removeRouter(coreRouterToBeRemoved).getId();
    assertEquals(expectedId, actualId);
  }
}
