module application {
	exports ru.sorbo.inventory.application.usecases;
  exports ru.sorbo.inventory.application.ports.output;
  exports ru.sorbo.inventory.application.ports.input;
	requires domain;
  requires static lombok;
}