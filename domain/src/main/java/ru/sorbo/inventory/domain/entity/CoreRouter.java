package ru.sorbo.inventory.domain.entity;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.sorbo.inventory.domain.spec.EmptyRouterSpec;
import ru.sorbo.inventory.domain.spec.EmptySwitchSpec;
import ru.sorbo.inventory.domain.spec.SameCountrySpec;
import ru.sorbo.inventory.domain.spec.SameIpAddressSpec;
import ru.sorbo.inventory.domain.vo.*;

import java.util.HashMap;
import java.util.Map;

@Getter
@ToString
public class CoreRouter extends Router {
	
	@Setter
  private Map<Identifier, Router> routers;

  @Builder
  public CoreRouter(Identifier id, Vendor vendor, Model model, IPAddress ip, 
  		Location location, RouterType routerType, Map<Identifier, Router> routers) {
    super(id, vendor, model, ip, location, routerType);
    this.routers = routers == null ? new HashMap<>() : routers;
  }

  public CoreRouter addRouter(Router anyRouter) {
    var sameCountryRouterSpec = new SameCountrySpec(this);
    var sameIpSpec = new SameIpAddressSpec(this);

    sameCountryRouterSpec.check(anyRouter);
    sameIpSpec.check(anyRouter);

    this.routers.put(anyRouter.id, anyRouter);
    
    return this;
  }

  public Router removeRouter(Router anyRouter) {
    var emptyRoutersSpec = new EmptyRouterSpec();
    var emptySwitchSpec = new EmptySwitchSpec();

    if(anyRouter.routerType == RouterType.CORE) {
      var coreRouter = (CoreRouter) anyRouter;
      emptyRoutersSpec.check(coreRouter);
    } else {
      var edgeRouter = (EdgeRouter) anyRouter;
      emptySwitchSpec.check(edgeRouter);
    }
    return this.routers.remove(anyRouter.id);
  }
}
