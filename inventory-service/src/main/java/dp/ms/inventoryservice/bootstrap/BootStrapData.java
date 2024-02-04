package dp.ms.inventoryservice.bootstrap;

import dp.ms.inventoryservice.model.Inventory;
import dp.ms.inventoryservice.repository.InventoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final InventoryRepository inventoryRepository;

    @Override
    public void run(String... args) throws Exception {
//        if (inventoryRepository.count() == 0) {
//            Inventory item1 = Inventory.builder()
//                    .skuCode("iPhone")
//                    .quantity(100)
//                    .build();
//            Inventory item2 = Inventory.builder()
//                    .skuCode("MacBook")
//                    .quantity(50)
//                    .build();
//            Inventory item3 = Inventory.builder()
//                    .skuCode("iPad")
//                    .quantity(70)
//                    .build();
//
//            inventoryRepository.saveAll(Arrays.asList(item1, item2, item3));
//        }
    }
}
