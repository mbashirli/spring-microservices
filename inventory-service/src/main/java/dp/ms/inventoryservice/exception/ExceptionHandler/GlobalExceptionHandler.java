package dp.ms.inventoryservice.exception.ExceptionHandler;

import dp.ms.inventoryservice.exception.InventoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.time.LocalDateTime;
import java.util.LinkedHashMap;
import java.util.Map;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(InventoryNotFoundException.class)
    public ResponseEntity<?> handleInventoryNotFoundException(InventoryNotFoundException ex) {
        // Create a custom response body. This could be a simple Map or a custom class.
        Map<String, Object> body = new LinkedHashMap<>();
        body.put("timestamp", LocalDateTime.now());
        body.put("message", "Inventory item not found");
        body.put("details", ex.getMessage());

        // Return the custom response entity with HTTP status 404 (Not Found)
        return new ResponseEntity<>(body, HttpStatus.NOT_FOUND);
    }

}
