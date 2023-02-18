package ru.sorbo.inventory.application.ports.input;

import java.util.function.Predicate;

import lombok.NoArgsConstructor;
import ru.sorbo.inventory.application.ports.output.RouterManagementOutputPort;
import ru.sorbo.inventory.application.usecases.NetworkManagementUseCase;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.service.NetworkService;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.domain.vo.Network;

public class NetworkManagementInputPort implements NetworkManagementUseCase {
	RouterManagementOutputPort routerManagementOutputPort;

  public NetworkManagementInputPort(RouterManagementOutputPort routerManagementOutputPort) {
    this.routerManagementOutputPort = routerManagementOutputPort;
  }

  @Override
  public Network createNetwork(
          IPAddress networkAddress, String networkName, int networkCidr) {
      return Network
              .builder()
              .networkAddress(networkAddress)
              .networkName(networkName)
              .networkCidr(networkCidr)
              .build();
  }

  @Override
  public Switch addNetworkToSwitch(Network network, Switch networkSwitch) {
      Identifier routerId = networkSwitch.getRouterId();
      Identifier switchId = networkSwitch.getId();
      EdgeRouter edgeRouter = (EdgeRouter) routerManagementOutputPort
              .retrieveRouter(routerId);
      Switch switchToAddNetwork = edgeRouter
              .getSwitches()
              .get(switchId);
      switchToAddNetwork.addNetworkToSwitch(network);
      routerManagementOutputPort.persistRouter(edgeRouter);
      return switchToAddNetwork;
  }

  @Override
  public Switch removeNetworkFromSwitch(String networkName, Switch networkSwitch) {
      Identifier routerId = networkSwitch.getRouterId();
      Identifier switchId = networkSwitch.getId();
      EdgeRouter edgeRouter = (EdgeRouter) routerManagementOutputPort
              .retrieveRouter(routerId);
      Switch switchToRemoveNetwork = edgeRouter
              .getSwitches()
              .get(switchId);
      Predicate<Network> networkPredicate = Network.getNetworkNamePredicate(networkName);
      var network = NetworkService.
              findNetwork(switchToRemoveNetwork.getSwitchNetworks(), networkPredicate);
      switchToRemoveNetwork.removeNetworkFromSwitch(network);
      routerManagementOutputPort.persistRouter(edgeRouter);
      return switchToRemoveNetwork.removeNetworkFromSwitch(network)
              ? switchToRemoveNetwork
              : null;
  }
}
