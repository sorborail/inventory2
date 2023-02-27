package ru.sorbo.inventory.domain.service;

import ru.sorbo.inventory.domain.entity.Equipment;
import ru.sorbo.inventory.domain.entity.Router;
import ru.sorbo.inventory.domain.vo.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class RouterService {
  public static List<Router> filterAndRetrieveRouter(List<Router> routers, Predicate<Equipment> routerPredicate) {
    return routers
        .stream()
        .filter(routerPredicate)
        .collect(Collectors.<Router>toList());
  }

  public static Router findById(Map<Identifier, Router> routers, Identifier id){
    return routers.get(id);
  }
}
