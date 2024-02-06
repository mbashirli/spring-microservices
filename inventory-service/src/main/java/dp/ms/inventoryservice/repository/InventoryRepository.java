package dp.ms.inventoryservice.repository;

import dp.ms.inventoryservice.dto.InventoryDTO;
import dp.ms.inventoryservice.model.Inventory;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class InventoryRepository {

    private final RedisTemplate<String, Integer> redisTemplate;

    public void save(Inventory inventory) {
        redisTemplate.opsForValue().set(inventory.getId(), inventory.getQuantity());
    }

    public void setStock(String inventoryId, Integer quantity){
        redisTemplate.opsForValue().set(inventoryId, quantity);
    }

    public Integer getQuantity(String inventoryId) {
        return redisTemplate.opsForValue().get(inventoryId);
    }

    public void incrementInventory(String inventoryId, Integer quantity) {
        if (inventoryId != null && quantity != null) {
            redisTemplate.opsForValue().increment(inventoryId, quantity);
        } else {
            // Handle null key or amount appropriately (throw exception, log warning, etc.)
            throw new IllegalArgumentException("Inventory ID and amount must not be null");
        }
    }

    public void decrementInventory(String inventoryId, Integer quantity) {
        if (inventoryId != null && quantity != null) {
            redisTemplate.opsForValue().decrement(inventoryId, quantity);
        } else {
            // Handle null key or amount appropriately (throw exception, log warning, etc.)
            throw new IllegalArgumentException("Inventory ID and amount must not be null");
        }
    }
}
