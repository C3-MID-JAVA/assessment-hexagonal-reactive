package ec.com.sofka.exception;

/** Exception for handling validation errors. */
public class ValidationException extends RuntimeException {
  public ValidationException(String message) {
    super(message);
  }
}
