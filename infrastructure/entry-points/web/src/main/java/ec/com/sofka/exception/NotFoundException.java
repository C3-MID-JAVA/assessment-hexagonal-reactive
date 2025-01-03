package ec.com.sofka.exception;

/** Exception for handling didn't find scenarios. */
public class NotFoundException extends RuntimeException {
  public NotFoundException(String message) {
    super(message);
  }
}
