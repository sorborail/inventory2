package ru.sorbo.inventory.application;

import io.cucumber.java.ru.Дано;
import io.cucumber.java.ru.Тогда;
import ru.sorbo.inventory.domain.vo.IPAddress;
import ru.sorbo.inventory.domain.vo.Model;
import ru.sorbo.inventory.domain.vo.SwitchType;
import ru.sorbo.inventory.domain.vo.Vendor;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class SwitchCreate extends ApplicationTestData {
  public SwitchCreate(){
    loadData();
  }

  @Дано("Я предоставляю все неоюходимые данные для создания нового коммутатора")
  public void i_provide_all_required_data_to_create_a_switch() {
    networkSwitch = this.switchManagementUseCase.createSwitch(
        Vendor.CISCO,
        Model.XYZ0001,
        IPAddress.fromAddress("20.0.0.100"),
        locationA,
        SwitchType.LAYER3
    );
  }

  @Тогда("Новый коммутатор создан")
  public void a_new_switch_is_created() {
    assertNotNull(networkSwitch);
    assertEquals(Vendor.CISCO, networkSwitch.getVendor());
    assertEquals(Model.XYZ0001,networkSwitch.getModel());
    assertEquals(IPAddress.fromAddress("20.0.0.100"), networkSwitch.getIp());
    assertEquals(locationA, networkSwitch.getLocation());
    assertEquals(SwitchType.LAYER3, networkSwitch.getSwitchType());
  }
}
