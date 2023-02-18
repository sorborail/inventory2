package ru.sorbo.inventory.framework.adapters.input.generic;

import ru.sorbo.inventory.application.ports.input.NetworkManagementInputPort;
import ru.sorbo.inventory.application.ports.input.SwitchManagementInputPort;
import ru.sorbo.inventory.application.usecases.NetworkManagementUseCase;
import ru.sorbo.inventory.application.usecases.SwitchManagementUseCase;
import ru.sorbo.inventory.framework.adapters.output.h2.RouterManagementH2Adapter;
import ru.sorbo.inventory.framework.adapters.output.h2.SwitchManagementH2Adapter;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.domain.vo.Network;

public class NetworkManagementGenericAdapter {
  private SwitchManagementUseCase switchManagementUseCase;
  private NetworkManagementUseCase networkManagementUseCase;

  public NetworkManagementGenericAdapter(){
      setPorts();
  }

  private void setPorts(){
      this.switchManagementUseCase = new SwitchManagementInputPort(SwitchManagementH2Adapter.getInstance());
      this.networkManagementUseCase = new NetworkManagementInputPort(RouterManagementH2Adapter.getInstance());
  }

  /**
   * POST /network/add
   * */
  public Switch addNetworkToSwitch(Network network, Identifier switchId) {
      Switch networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
      return networkManagementUseCase.addNetworkToSwitch(network, networkSwitch);
  }

  /**
   * POST /network/remove
   * */
  public Switch removeNetworkFromSwitch(String networkName, Identifier switchId) {
      Switch networkSwitch = switchManagementUseCase.retrieveSwitch(switchId);
      return networkManagementUseCase.removeNetworkFromSwitch(networkName, networkSwitch);
  }
}
