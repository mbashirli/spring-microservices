package dp.ms.productservice.exception;

public class FieldAccessException extends RuntimeException {

    public FieldAccessException(String message) {
        super(message);
    }

    public FieldAccessException(String message, Throwable cause) {
        super(message, cause);
    }
}
