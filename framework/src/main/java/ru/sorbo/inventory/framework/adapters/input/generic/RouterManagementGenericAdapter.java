package ru.sorbo.inventory.framework.adapters.input.generic;

import ru.sorbo.inventory.application.usecases.RouterManagementUseCase;
import ru.sorbo.inventory.application.ports.input.RouterManagementInputPort;
import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.domain.vo.Location;
import ru.sorbo.inventory.domain.vo.Model;
import ru.sorbo.inventory.domain.vo.RouterType;
import ru.sorbo.inventory.domain.vo.Vendor;
import ru.sorbo.inventory.framework.adapters.output.h2.RouterManagementH2Adapter;

public class RouterManagementGenericAdapter {
	
	private RouterManagementUseCase routerManagementUseCase;

  public RouterManagementGenericAdapter() {
      setPorts();
  }

  private void setPorts(){
      this.routerManagementUseCase = new RouterManagementInputPort(
              RouterManagementH2Adapter.getInstance()
      );
  }

  /**
   * GET /router/retrieve/{id}
   * */
  public Router retrieveRouter(Identifier id){
      return routerManagementUseCase.retrieveRouter(id);
  }

  /**
   * GET /router/remove/{id}
   * */
  public Router removeRouter(Identifier id){
      return routerManagementUseCase.removeRouter(id);
  }

  /**
   * POST /router/create
   * */
  public Router createRouter(Vendor vendor,
                                 Model model,
                                 IPAddress ip,
                                 Location location,
                                 RouterType routerType) {
      var router = routerManagementUseCase.createRouter(
              null,
              vendor,
              model,
              ip,
              location,
              routerType

      );
      return routerManagementUseCase.persistRouter(router);
  }

  /**
   * POST /router/add
   * */
  public Router addRouterToCoreRouter(Identifier routerId, Identifier coreRouterId){
      Router router = routerManagementUseCase.retrieveRouter(routerId);
      CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
      return routerManagementUseCase.
              addRouterToCoreRouter(router, coreRouter);
  }

  /**
   * POST /router/remove
   * */
  public Router removeRouterFromCoreRouter(Identifier routerId, Identifier coreRouterId){
      Router router = routerManagementUseCase.retrieveRouter(routerId);
      CoreRouter coreRouter = (CoreRouter) routerManagementUseCase.retrieveRouter(coreRouterId);
      return routerManagementUseCase.
              removeRouterFromCoreRouter(router, coreRouter);
  }
}
