package ru.sorbo.inventory.application.usecases;

import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Network;

public interface NetworkManagementUseCase {

	Network createNetwork(IPAddress networkAddress, String networkName, int networkCidr);

	Switch addNetworkToSwitch(Network network, Switch networkSwitch);

	Switch removeNetworkFromSwitch(String name, Switch networkSwitch);
}
