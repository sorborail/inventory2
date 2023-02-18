package ru.sorbo.inventory.framework;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.domain.vo.Model;
import ru.sorbo.inventory.domain.vo.RouterType;
import ru.sorbo.inventory.domain.vo.Vendor;
import ru.sorbo.inventory.framework.adapters.input.generic.RouterManagementGenericAdapter;

public class RouterTest extends FrameworkTestData {
	RouterManagementGenericAdapter routerManagementGenericAdapter;

  public RouterTest() {
      this.routerManagementGenericAdapter = new RouterManagementGenericAdapter();
      loadData();
  }

  @Test
  public void retrieveRouter() {
      var id = Identifier.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
      var actualId = routerManagementGenericAdapter.
              retrieveRouter(id).getId();
      assertEquals(id, actualId);
  }

  @Test
  public void createRouter() {
      var ipAddress = "40.0.0.1";
      var routerId  = this.
              routerManagementGenericAdapter.createRouter(
              Vendor.DLINK,
              Model.XYZ0001,
              IPAddress.fromAddress(ipAddress),
              locationA,
              RouterType.EDGE).getId();
      var router = this.routerManagementGenericAdapter.retrieveRouter(routerId);
      assertEquals(routerId, router.getId());
      assertEquals(Vendor.DLINK, router.getVendor());
      assertEquals(Model.XYZ0001, router.getModel());
      assertEquals(ipAddress, router.getIp().getIpAddress());
      assertEquals(locationA, router.getLocation());
      assertEquals(RouterType.EDGE, router.getRouterType());
  }

  @Test
  public void addRouterToCoreRouter() {
      var routerId = Identifier.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0b");
      var coreRouterId = Identifier.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
      var actualRouter = (CoreRouter)this.routerManagementGenericAdapter.
             addRouterToCoreRouter(routerId,coreRouterId);
      assertEquals(routerId, actualRouter.getRouters().get(routerId).getId());
  }

  @Test
  public void removeRouterFromCoreRouter(){
      var routerId = Identifier.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0a");
      var coreRouterId = Identifier.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0c");
      var removedRouter = this.routerManagementGenericAdapter.
              removeRouterFromCoreRouter(routerId, coreRouterId);
      var coreRouter = (CoreRouter)this.routerManagementGenericAdapter
              .retrieveRouter(coreRouterId);
      assertEquals(routerId, removedRouter.getId());
      assertFalse(coreRouter.getRouters().containsKey(routerId));
  }

  @Test
  public void removeRouter() {
      var routerId = Identifier.withId("b832ef4f-f894-4194-8feb-a99c2cd4be0b");
      var router = this.routerManagementGenericAdapter.removeRouter(routerId);
      assertEquals(null, router);
  }
}
