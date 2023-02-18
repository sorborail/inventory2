package ru.sorbo.inventory.application;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import ru.sorbo.inventory.domain.vo.IPAddress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class NetworkCreate extends ApplicationTestData {
  public NetworkCreate(){
    loadData();
  }

  @Дано("Я предоставляю всю необходимую информацию для создания сети")
  public void i_provide_all_required_data_to_create_a_network(){
    network = networkManagementUseCase.createNetwork(
        IPAddress.fromAddress("10.0.0.1"),
        "Finance",
        8
    );
  }

  @Тогда("Новая сеть создана")
  public void a_new_network_is_created(){
    assertNotNull(network);
    assertEquals(IPAddress.fromAddress("10.0.0.1"), network.getNetworkAddress());
    assertEquals("Finance", network.getNetworkName());
    assertEquals(8, network.getNetworkCidr());
  }
}
