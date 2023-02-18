module framework {
  requires domain;
  requires application;
  
  requires static lombok;
  requires org.eclipse.persistence.core;
  requires java.sql;
  requires jakarta.persistence;
  requires com.fasterxml.jackson.databind;
  requires com.fasterxml.jackson.core;

  exports ru.sorbo.inventory.framework.adapters.output.h2.data;
  opens ru.sorbo.inventory.framework.adapters.output.h2.data;
}