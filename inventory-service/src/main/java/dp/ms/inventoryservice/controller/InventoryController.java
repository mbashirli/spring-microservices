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
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Slf4j
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
@Tag(name = "Internal api", description = "Inventory service internal controller")
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/isInStock")
    @Operation(description = "Checks if all the sent products (combined) are in stock.")
    public ResponseEntity<Boolean> isInStock(@RequestBody Map<String, Integer> productsWithQuantity){
        Boolean isStock = inventoryService.isInStock(productsWithQuantity);
        return ResponseEntity.status(HttpStatus.OK).body(isStock);
    }


    @GetMapping("/get/{inventoryId}")
    @Operation(description = "Get inventory for a specific inventoryId.")
    public ResponseEntity<Integer> getInventory(@PathVariable("inventoryId") String inventoryId){
        Integer quantity = inventoryService.getQuantity(inventoryId);
        return ResponseEntity.status(HttpStatus.OK).body(quantity);

    }

    @PostMapping("/set/{inventoryId}")
    @Operation(description = "Set inventory for a specific inventoryId")
    public ResponseEntity<Void> setInventory(@PathVariable("inventoryId") String inventoryId, @RequestParam Integer quantity) {
        inventoryService.setInventory(inventoryId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/increment/{inventoryId}")
    @Operation(description = "Increment inventory by a specific quantity.")
    public ResponseEntity<Void> incrementProductQuantity(@PathVariable("inventoryId") String inventoryId, @RequestParam Integer quantity) {
        inventoryService.incrementProductQuantity(inventoryId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PostMapping("/decrement")
    @Operation(description = "Decrement inventory for productIds")
    public ResponseEntity<Void> decrementMultipleProductsQuantity(@RequestBody Map<String, Integer> productsWithQuantity){
        inventoryService.decrementMultipleProductsQuantity(productsWithQuantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }

    @PatchMapping("/decrement/{inventoryId}")
    @Operation(description = "Decrement inventory by a specific quantity.")
    public ResponseEntity<Void> decrementProductQuantity(@PathVariable("inventoryId") String inventoryId, @RequestParam Integer quantity) {
        inventoryService.decrementProductQuantity(inventoryId, quantity);
        return ResponseEntity.status(HttpStatus.OK).build();
    }
}
