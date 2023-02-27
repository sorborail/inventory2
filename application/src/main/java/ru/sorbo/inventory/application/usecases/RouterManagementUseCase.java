package ru.sorbo.inventory.application.usecases;

import ru.sorbo.inventory.domain.entity.CoreRouter;
import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.vo.*;

public interface RouterManagementUseCase {

	Router createRouter(Identifier id, Vendor vendor, Model model,
											IPAddress ip, Location location, RouterType routerType);

	Router removeRouter(Identifier id);

	Router retrieveRouter(Identifier id);

	Router persistRouter(Router router);

	Router addRouterToCoreRouter(Router router, CoreRouter coreRouter);

	Router removeRouterFromCoreRouter(Router router, CoreRouter coreRouter);
}
