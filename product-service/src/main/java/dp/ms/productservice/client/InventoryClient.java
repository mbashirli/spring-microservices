package dp.ms.productservice.client;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

@Component
@Slf4j
public class InventoryClient {

    private final WebClient webClient;

    @Autowired
    public InventoryClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://inventory-service")
                .build();
    }

    public void setInventoryStock(String inventoryId, Integer quantity) {
        webClient.post()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/inventory/set/{inventoryId}")
                        .queryParam("quantity", quantity)
                        .build(inventoryId))
                .retrieve()
                .bodyToMono(Integer.class)
                .block();
    }

}
