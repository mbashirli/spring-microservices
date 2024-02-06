package dp.ms.inventoryservice.mapper;

import dp.ms.inventoryservice.dto.InventoryDTO;
import dp.ms.inventoryservice.model.Inventory;
import org.mapstruct.Mapper;

@Mapper
public interface InventoryMapper {
    Inventory inventoryDTOToInventory(InventoryDTO inventoryDTO);
}
