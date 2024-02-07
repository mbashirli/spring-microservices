package dp.ms.inventoryservice.controller;

import dp.ms.inventoryservice.service.InventoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Internal api", description = "Inventory service internal controller")
public class InventoryController {

    private final InventoryService inventoryService;

    @GetMapping("/get/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Get inventory", description = "Get inventory for a specific inventoryId.")
    public ResponseEntity<Integer> getInventory(@PathVariable("inventoryId") String inventoryId){
        Integer quantity = inventoryService.getQuantity(inventoryId);
        if (quantity != null) {
            return ResponseEntity.status(HttpStatus.OK).body(quantity);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/set/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Set inventory", description = "Set inventory for a specific inventoryId")
    public ResponseEntity<Void> setInventory(@PathVariable("inventoryId") String inventoryId, @RequestParam Integer quantity) {
        inventoryService.setInventory(inventoryId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/increment/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Increment inventory", description = "Increment inventory by a specific quantity.")
    public ResponseEntity<Void> incrementItemQuantity(@PathVariable("inventoryId") String inventoryId, @RequestParam Integer quantity) {
        inventoryService.incrementInventory(inventoryId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/decrement/{inventoryId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(summary = "Decrement inventory", description = "Decrement inventory by a specific quantity.")
    public ResponseEntity<Void> decrementItemQuantity(@PathVariable("inventoryId") String inventoryId, @RequestParam Integer quantity) {
        inventoryService.decrementInventory(inventoryId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }


}
