package ru.sorbo.inventory.framework.adapters.output.h2;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import jakarta.persistence.PersistenceContext;
import ru.sorbo.inventory.application.ports.output.SwitchManagementOutputPort;
import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.Identifier;
import ru.sorbo.inventory.framework.adapters.output.h2.data.SwitchData;
import ru.sorbo.inventory.framework.adapters.output.h2.mappers.RouterH2Mapper;

public class SwitchManagementH2Adapter implements SwitchManagementOutputPort {
	private static SwitchManagementH2Adapter instance;

  @PersistenceContext
  private EntityManager em;

  private SwitchManagementH2Adapter() {
      setUpH2Database();
  }

  @Override
  public Switch retrieveSwitch(Identifier id) {
      var switchData = em.getReference(SwitchData.class, id.getUuid());
      return RouterH2Mapper.switchDataToDomain(switchData);
  }

  private void setUpH2Database() {
    EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("inventory");
    this.em = entityManagerFactory.createEntityManager();
  }

  public static SwitchManagementH2Adapter getInstance() {
      if (instance == null) {
          instance = new SwitchManagementH2Adapter();
      }
      return instance;
  }
}
