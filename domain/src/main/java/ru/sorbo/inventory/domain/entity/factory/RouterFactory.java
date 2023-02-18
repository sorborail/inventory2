package ru.sorbo.inventory.domain.entity.factory;

import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.vo.*;

public class RouterFactory {
  public static Router getRouter(Identifier id, Vendor vendor, Model model, IPAddress ip, Location location, RouterType routerType) {

    return switch (routerType) {
      case CORE -> CoreRouter.builder().
          id(id == null ? Identifier.withoutId() : id).
          vendor(vendor).
          model(model).
          ip(ip).
          location(location).
          routerType(routerType).
          build();
      case EDGE -> EdgeRouter.builder().
          id(id == null ? Identifier.withoutId() : id).
          vendor(vendor).
          model(model).
          ip(ip).
          location(location).
          routerType(routerType).
          build();
      default -> null;
    };
  }
}
