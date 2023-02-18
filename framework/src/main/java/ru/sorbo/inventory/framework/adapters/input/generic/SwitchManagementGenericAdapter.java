package ru.sorbo.inventory.framework.adapters.input.generic;

import ru.sorbo.inventory.application.usecases.RouterManagementUseCase;
import ru.sorbo.inventory.application.usecases.SwitchManagementUseCase;
import ru.sorbo.inventory.application.ports.input.RouterManagementInputPort;
import ru.sorbo.inventory.application.ports.input.SwitchManagementInputPort;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.domain.vo.Location;
import ru.sorbo.inventory.domain.vo.Model;
import ru.sorbo.inventory.domain.vo.RouterType;
import ru.sorbo.inventory.domain.vo.SwitchType;
import ru.sorbo.inventory.domain.vo.Vendor;
import ru.sorbo.inventory.framework.adapters.output.h2.RouterManagementH2Adapter;
import ru.sorbo.inventory.framework.adapters.output.h2.SwitchManagementH2Adapter;

public class SwitchManagementGenericAdapter {
	private SwitchManagementUseCase switchManagementUseCase;
  private RouterManagementUseCase routerManagementUseCase;

  public SwitchManagementGenericAdapter(){
      setPorts();
  }

  private void setPorts(){
      this.routerManagementUseCase = new RouterManagementInputPort(
              RouterManagementH2Adapter.getInstance()
      );
      this.switchManagementUseCase = new SwitchManagementInputPort(
              SwitchManagementH2Adapter.getInstance()
      );
  }

  /**
   * GET /switch/retrieve/{id}
   * */
  public Switch retrieveSwitch(Identifier switchId) {
      return switchManagementUseCase.retrieveSwitch(switchId);
  }

  /**
   * POST /switch/create
   * */
  public EdgeRouter createAndAddSwitchToEdgeRouter(
          Vendor vendor,
          Model model,
          IPAddress ip,
          Location location,
          SwitchType switchType,
          Identifier routerId
  ) {
      Switch newSwitch = switchManagementUseCase.createSwitch(vendor, model, ip, location, switchType);
      Router edgeRouter = routerManagementUseCase.retrieveRouter(routerId);
      if(!edgeRouter.getRouterType().equals(RouterType.EDGE))
          throw new UnsupportedOperationException("Please inform the id of an edge router to add a switch");
      Router router = switchManagementUseCase.addSwitchToEdgeRouter(newSwitch, (EdgeRouter) edgeRouter);
      return (EdgeRouter) routerManagementUseCase.persistRouter(router);
  }

  /**
   * POST /switch/remove
   * */
  public EdgeRouter removeSwitchFromEdgeRouter(Identifier switchId, Identifier edgeRouterId) {
      EdgeRouter edgeRouter = (EdgeRouter) routerManagementUseCase
              .retrieveRouter(edgeRouterId);
      Switch networkSwitch = edgeRouter.getSwitches().get(switchId);
      Router router = switchManagementUseCase
              .removeSwitchFromEdgeRouter(networkSwitch, edgeRouter);
      return (EdgeRouter) routerManagementUseCase.persistRouter(router);
  }
}
