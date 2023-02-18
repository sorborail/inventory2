package ru.sorbo.inventory.application.ports.output;

import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.Identifier;

public interface SwitchManagementOutputPort {
	Switch retrieveSwitch(Identifier id);
}
