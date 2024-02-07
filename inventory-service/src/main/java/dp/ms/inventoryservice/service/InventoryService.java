package dp.ms.inventoryservice.service;

import dp.ms.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;

    public void setInventory(String inventoryId, Integer quantity) {
        inventoryRepository.setStock(inventoryId, quantity);
    }

    public Integer getQuantity(String inventoryId) {
        return inventoryRepository.getQuantity(inventoryId);
    }

    public void incrementInventory(String inventoryId, Integer quantity) {
        inventoryRepository.incrementInventory(inventoryId, quantity);
    }

    public void decrementInventory(String inventoryId, Integer quantity) {
        inventoryRepository.decrementInventory(inventoryId, quantity);
    }
}
