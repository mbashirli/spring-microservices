package dp.ms.inventoryservice.controller;

import dp.ms.inventoryservice.dto.InventoryDTO;
import dp.ms.inventoryservice.mapper.InventoryMapper;
import dp.ms.inventoryservice.repository.InventoryRepository;
import dp.ms.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/{inventoryId}")
    public ResponseEntity<Integer> getInventory(@PathVariable("inventoryId") String inventoryId){
        Integer quantity = inventoryService.getQuantity(inventoryId);
        if (quantity != null) {
            return ResponseEntity.status(HttpStatus.OK).body(quantity);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping
    public ResponseEntity<Void> addInventory(@RequestBody InventoryDTO inventoryDTO) {
        inventoryService.addInventory(inventoryDTO);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }
    @PatchMapping("/{inventoryId}")
    public ResponseEntity<Void> setInventory(@PathVariable("inventoryId") String inventoryId, @RequestParam Integer quantity) {
        inventoryService.setInventory(inventoryId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

}
