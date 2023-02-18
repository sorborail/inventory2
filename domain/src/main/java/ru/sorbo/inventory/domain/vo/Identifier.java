package ru.sorbo.inventory.domain.vo;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode
public class Identifier {
	
  private final UUID uuid;

  private Identifier(UUID id) {
    this.uuid = id;
  }

  public static Identifier withId(String id) {
    return new Identifier(UUID.fromString(id));
  }

  public static Identifier withoutId() {
    return new Identifier(UUID.randomUUID());
  }
}
