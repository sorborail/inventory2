package ru.sorbo.inventory.application;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import org.mockito.Mockito;
import ru.sorbo.inventory.domain.service.NetworkService;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Network;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.ArgumentMatchers.any;

public class NetworkAdd extends ApplicationTestData {

  public NetworkAdd() {
    loadData();
  }

  @Дано("У меня есть сеть")
  public void i_have_a_network(){
    network = networkManagementUseCase.createNetwork(
        IPAddress.fromAddress("10.0.0.1"),
        "Finance",
        8
    );
    assertNotNull(network);
  }

  @И("У меня есть коммутатор для добавления сети")
  public void i_have_a_switch_to_add_a_network(){
    assertNotNull(networkSwitch);
  }

  @Тогда("Я добавляю эту сеть к этому коммутатору")
  public void i_add_the_network_to_the_switch() {
    Mockito.when(routerManagementOutputPortMock.retrieveRouter(any())).thenReturn(edgeRouter);
    Mockito.when(routerManagementOutputPortMock.persistRouter(any())).thenReturn(edgeRouter);
    var predicate = Network.getNetworkNamePredicate("Finance");
    var networks = this.networkManagementUseCase.addNetworkToSwitch(network, networkSwitch).getSwitchNetworks();
    var network = NetworkService.findNetwork(networks, predicate).getNetworkName();
    assertEquals("Finance", network);
  }
}
