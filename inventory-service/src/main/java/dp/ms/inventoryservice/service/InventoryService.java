package dp.ms.inventoryservice.service;

import dp.ms.inventoryservice.exception.InventoryNotFoundException;
import dp.ms.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public void setInventory(String inventoryId, Integer quantity) {
        inventoryRepository.setStock(inventoryId, quantity);
    }

    public Integer getQuantity(String inventoryId) {
        Integer quantity = inventoryRepository.getQuantity(inventoryId);
        if (quantity != null) {
            return quantity;
        }
        throw new InventoryNotFoundException(inventoryId);
    }

    public void incrementInventory(String inventoryId, Integer quantity) {
        inventoryRepository.incrementInventory(inventoryId, quantity);
    }

    public void decrementInventory(String inventoryId, Integer quantity) {
        inventoryRepository.decrementInventory(inventoryId, quantity);
    }

    public Boolean isInStock(Map<String, Integer> productsWithQuantity) {
        for (Map.Entry<String, Integer> entry : productsWithQuantity.entrySet()) {
            String inventoryId = entry.getKey();
            Integer requiredQuantity = entry.getValue();
            Integer totalInventoryLeft = inventoryRepository.getQuantity(inventoryId);

            if (totalInventoryLeft == null || requiredQuantity > totalInventoryLeft) {
                return false; // Immediately return false if a product is not in stock
            }
        }
        return true; // Return true if all products are in stock
    }
}
