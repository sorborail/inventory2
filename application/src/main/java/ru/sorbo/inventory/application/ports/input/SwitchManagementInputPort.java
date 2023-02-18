package ru.sorbo.inventory.application.ports.input;

import lombok.NoArgsConstructor;
import ru.sorbo.inventory.application.ports.output.SwitchManagementOutputPort;
import ru.sorbo.inventory.application.usecases.SwitchManagementUseCase;
import ru.sorbo.inventory.domain.entity.EdgeRouter;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.*;

public class SwitchManagementInputPort implements SwitchManagementUseCase {
	SwitchManagementOutputPort switchManagementOutputPort;

  public SwitchManagementInputPort(SwitchManagementOutputPort switchManagementOutputPort) {
      this.switchManagementOutputPort = switchManagementOutputPort;
  }

  @Override
  public Switch createSwitch(
          Vendor vendor,
          Model model,
          IPAddress ip,
          Location location,
          SwitchType switchType) {
      return Switch
              .builder()
              .switchId(Identifier.withoutId())
              .vendor(vendor)
              .model(model)
              .ip(ip)
              .location(location)
              .switchType(switchType)
              .build();
  }

  public Switch retrieveSwitch(Identifier id){
      return switchManagementOutputPort.retrieveSwitch(id);
  }

  @Override
  public EdgeRouter addSwitchToEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter) {
      networkSwitch.setRouterId(edgeRouter.getId());
      edgeRouter.addSwitch(networkSwitch);
      return edgeRouter;
  }

  @Override
  public EdgeRouter removeSwitchFromEdgeRouter(Switch networkSwitch, EdgeRouter edgeRouter) {
      edgeRouter.removeSwitch(networkSwitch);
      return edgeRouter;
  }
}
