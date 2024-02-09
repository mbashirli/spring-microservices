package dp.ms.orderservice.client;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Map;

@Slf4j
@Component
@RequiredArgsConstructor
public class InventoryClient {

    private final WebClient webClient;

    @Autowired
    public InventoryClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://inventory-service")
                .build();
    }

    public Boolean isInStock(Map<String, Integer> productsWithQuantity) {
        return webClient.post()
                .uri("/api/inventory/isInStock")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productsWithQuantity)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }

    public void decrementProductsQuantity(Map<String, Integer> productsWithQuantity){
        webClient.post()
                .uri("/api/inventory/decrement")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productsWithQuantity)
                .retrieve()
                .bodyToMono(Void.class)
                .subscribe();
    }

}
