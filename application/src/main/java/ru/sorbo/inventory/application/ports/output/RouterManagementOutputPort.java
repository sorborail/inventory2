package ru.sorbo.inventory.application.ports.output;

import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.vo.Identifier;

public interface RouterManagementOutputPort {
	Router retrieveRouter(Identifier id);

  Router removeRouter(Identifier id);

  Router persistRouter(Router router);
}
