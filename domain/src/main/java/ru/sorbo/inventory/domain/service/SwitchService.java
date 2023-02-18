package ru.sorbo.inventory.domain.service;

import ru.sorbo.inventory.domain.entity.Switch;
import ru.sorbo.inventory.domain.vo.Identifier;

import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class SwitchService {

  public static List<Switch> filterAndRetrieveSwitch(List<Switch> switches, Predicate<Switch> switchPredicate) {
    return switches
        .stream()
        .filter(switchPredicate)
        .collect(Collectors.<Switch>toList());
  }

  public static Switch findById(Map<Identifier, Switch> switches, Identifier id) {
    return switches.get(id);
  }
}
