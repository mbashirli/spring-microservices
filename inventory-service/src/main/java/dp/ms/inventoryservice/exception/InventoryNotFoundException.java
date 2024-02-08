package dp.ms.inventoryservice.exception;

public class InventoryNotFoundException extends RuntimeException {
    public InventoryNotFoundException(String productId) {
        super("Product with ID " + productId + " not found.");
    }
}
