package ru.sorbo.inventory.application;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Model;
import ru.sorbo.inventory.domain.vo.Vendor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static ru.sorbo.inventory.domain.vo.RouterType.CORE;
import static ru.sorbo.inventory.domain.vo.RouterType.EDGE;

public class RouterCreate extends ApplicationTestData {
  public RouterCreate(){
    loadData();
  }
  //Creating a new core router
  @Дано("Я предоставляю все необходимые данные для создания основного роутера")
  public void create_core_router(){
    router = this.routerManagementUseCase.createRouter(
        null,
    		Vendor.CISCO,
        Model.XYZ0001,
        IPAddress.fromAddress("20.0.0.1"),
        locationA,
        CORE
    );
  }
  @Тогда("Новый основной роутер создан")
  public void a_new_core_router_is_created(){
    assertNotNull(router);
    assertEquals(CORE, router.getRouterType());
  }
  //Creating a new edge router
  @Дано("Я предоставляю все необходимые данные для создания внутреннего роутера")
  public void create_edge_router(){
    router = this.routerManagementUseCase.createRouter(
        null,
    		Vendor.HP,
        Model.XYZ0004,
        IPAddress.fromAddress("30.0.0.1"),
        locationA,
        EDGE
    );
  }

  @Тогда("Новый внутренний роутер создан")
  public void a_new_edge_router_is_created(){
    assertNotNull(router);
    assertEquals(EDGE, router.getRouterType());
  }
}
