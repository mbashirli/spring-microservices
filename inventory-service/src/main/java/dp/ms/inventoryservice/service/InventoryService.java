package dp.ms.inventoryservice.service;

import dp.ms.inventoryservice.dto.InventoryDTO;
import dp.ms.inventoryservice.mapper.InventoryMapper;
import dp.ms.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class InventoryService {

    private final InventoryRepository inventoryRepository;
    private final InventoryMapper inventoryMapper;

    public void addInventory(InventoryDTO inventoryDTO) {
        inventoryRepository.save(inventoryMapper.inventoryDTOToInventory(inventoryDTO));
    }

    public void setInventory(String inventoryId, Integer quantity) {
        inventoryRepository.setStock(inventoryId, quantity);
    }

    public Integer getQuantity(String inventoryId) {
        return inventoryRepository.getQuantity(inventoryId);
    }
}
