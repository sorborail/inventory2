package ru.sorbo.inventory.application;

import io.cucumber.java.ru.И;
import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import org.mockito.Mockito;
import ru.sorbo.inventory.domain.service.NetworkService;
import ru.sorbo.inventory.domain.vo.Network;

import java.util.function.Predicate;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;

public class NetworkRemove extends ApplicationTestData {
  Predicate<Network> predicate;

  public NetworkRemove(){
    loadData();
  }

  @Дано("Я знаю какую сеть я хочу удалить")
  public void i_know_the_network_i_want_to_remove() {
    predicate = Network.getNetworkNamePredicate("TestNetwork");
    network = NetworkService.findNetwork(networks, predicate);
    assertEquals("TestNetwork", network.getNetworkName());
  }

  @И("У меня есть коммутатор для удаления этой сети")
  public void i_have_a_switch_to_remove_a_network(){
    assertNotNull(networkSwitch);
  }

  @Тогда("Я удаляю эту сеть у этого коммутатора")
  public void i_remove_the_network_from_the_switch() {
    Mockito.when(routerManagementOutputPortMock.retrieveRouter(any())).thenReturn(edgeRouter);
    Mockito.when(routerManagementOutputPortMock.persistRouter(any())).thenReturn(edgeRouter);
    this.networkManagementUseCase.removeNetworkFromSwitch(network.getNetworkName(), networkSwitch);
    network = NetworkService.findNetwork(networks, predicate);
    assertNull(network);
  }
}
