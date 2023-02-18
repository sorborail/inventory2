package ru.sorbo.inventory.domain.exception;

public class GenericSpecificationException extends RuntimeException {
  /**
	 * 
	 */
	private static final long serialVersionUID = 8513031440565811156L;

	public GenericSpecificationException(String message) {
    super(message);
  }
}
