package ru.sorbo.inventory.application;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import org.mockito.Mockito;
import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Model;
import ru.sorbo.inventory.domain.vo.Vendor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static ru.sorbo.inventory.domain.vo.RouterType.CORE;
import static ru.sorbo.inventory.domain.vo.RouterType.EDGE;


public class RouterAdd extends ApplicationTestData {
  CoreRouter anotherCoreRouter;

  public RouterAdd(){
    loadData();
  }
  //Adding an edge router to a core router
  @Дано("У меня есть внутренний роутер")
  public void assert_edge_router_exists(){
    edgeRouter = (EdgeRouter) this.routerManagementUseCase.createRouter(
        null,
    		Vendor.HP,
        Model.XYZ0004,
        IPAddress.fromAddress("20.0.0.1"),
        locationA,
        EDGE
    );
    assertNotNull(edgeRouter);
  }
  
  @И("У меня есть основной роутер")
  public void assert_core_router_exists(){
    coreRouter = (CoreRouter) this.routerManagementUseCase.createRouter(
        null,
    		Vendor.CISCO,
        Model.XYZ0001,
        IPAddress.fromAddress("30.0.0.1"),
        locationA,
        CORE
    );
    assertNotNull(coreRouter);
  }
  @Тогда("Я добавляю внутренний роутер к основному роутеру")
  public void add_edge_to_core_router() {
    var actualEdgeId = edgeRouter.getId();
    Mockito.when(routerManagementOutputPortMock.persistRouter(any())).thenReturn(coreRouter);
    var routerWithEdge = (CoreRouter) this.routerManagementUseCase.addRouterToCoreRouter(edgeRouter, coreRouter);
    var expectedEdgeId = routerWithEdge.getRouters().get(actualEdgeId).getId();
    assertEquals(actualEdgeId, expectedEdgeId);
  }
  //Adding a core router to another core router
  @Дано("У меня есть этот основной роутер")
  public void assert_this_core_router_exists(){
    coreRouter = (CoreRouter) this.routerManagementUseCase.createRouter(
        null,
    		Vendor.CISCO,
        Model.XYZ0001,
        IPAddress.fromAddress("30.0.0.1"),
        locationA,
        CORE
    );
    assertNotNull(coreRouter);
  }
  @И("У меня есть другой основной роутер")
  public void assert_another_core_router_exists(){
    anotherCoreRouter = (CoreRouter) this.routerManagementUseCase.createRouter(
        null,
    		Vendor.CISCO,
        Model.XYZ0001,
        IPAddress.fromAddress("40.0.0.1"),
        locationA,
        CORE
    );
    assertNotNull(anotherCoreRouter);
  }
  @Тогда("Я добавляю этот основной роутер к другому основному роутеру")
  public void add_core_to_core_router(){
    var coreRouterId = coreRouter.getId();
    Mockito.when(routerManagementOutputPortMock.persistRouter(any())).thenReturn(anotherCoreRouter);
    var routerWithCore = (CoreRouter) this.routerManagementUseCase.addRouterToCoreRouter(coreRouter, anotherCoreRouter);
    var expectedCoreId = routerWithCore.getRouters().get(coreRouterId).getId();
    assertEquals(coreRouterId, expectedCoreId);
  }
}
