package ru.sorbo.inventory.domain.spec.shared;

import ru.sorbo.inventory.domain.exception.GenericSpecificationException;

public abstract class AbstractSpecification<T> implements Specification<T> {

  public abstract boolean isSatisfiedBy(T t);

  public abstract void check(T t) throws GenericSpecificationException;

  public Specification<T> and(final Specification<T> specification) {
    return new AndSpecification<T>(this, specification);
  }
}
