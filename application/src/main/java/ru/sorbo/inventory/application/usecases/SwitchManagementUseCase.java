package ru.sorbo.inventory.application.usecases;

import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.*;

public interface SwitchManagementUseCase {

	Switch createSwitch(Vendor vendor, Model model, IPAddress ip, Location location, SwitchType switchType);

	Switch retrieveSwitch(Identifier id);

	EdgeRouter addSwitchToEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter);

	EdgeRouter removeSwitchFromEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter);
}
