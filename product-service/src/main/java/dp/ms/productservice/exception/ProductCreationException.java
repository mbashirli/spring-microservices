package dp.ms.productservice.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class ProductCreationException extends RuntimeException {
    public ProductCreationException(String message) {
        super(message);
    }
}
