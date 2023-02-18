package ru.sorbo.inventory.application.ports.input;

import ru.sorbo.inventory.application.ports.output.RouterManagementOutputPort;
import ru.sorbo.inventory.application.usecases.RouterManagementUseCase;
import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.entity.factory.RouterFactory;
import ru.sorbo.inventory.domain.vo.*;

//@AllArgsConstructor
public class RouterManagementInputPort implements RouterManagementUseCase {
	RouterManagementOutputPort routerManagementOutputPort;

  public RouterManagementInputPort(RouterManagementOutputPort routerManagementOutputPort) {
      this.routerManagementOutputPort = routerManagementOutputPort;
  }

  @Override
  public Router createRouter(Identifier id,
                             Vendor vendor,
                             Model model,
                             IPAddress ip,
                             Location location,
                             RouterType routerType) {
      return RouterFactory.getRouter(null,
              vendor,
              model,
              ip,
              location,
              routerType
      );
  }

  @Override
  public Router removeRouter(Identifier id) {
      return routerManagementOutputPort.removeRouter(id);
  }

  @Override
  public Router retrieveRouter(Identifier id) {
      return routerManagementOutputPort.retrieveRouter(id);
  }

  @Override
  public Router persistRouter(Router router) {
      return routerManagementOutputPort.persistRouter(router);
  }

  @Override
  public CoreRouter addRouterToCoreRouter(Router router, CoreRouter coreRouter) {
      var addedRouter = coreRouter.addRouter(router);
      return (CoreRouter) persistRouter(addedRouter);
  }

  @Override
  public Router removeRouterFromCoreRouter(Router router, CoreRouter coreRouter) {
      var removedRouter = coreRouter.removeRouter(router);
      persistRouter(coreRouter);
      return removedRouter;
  }
}
