package dp.ms.orderservice.client;

import dp.ms.orderservice.dto.OrderLineItemDTO;
import dp.ms.orderservice.model.OrderLineItem;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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

    public Boolean isInStock(Map<String, Integer> productIds) {
        return webClient.post()
                .uri("/api/inventory/isInStock")
                .contentType(MediaType.APPLICATION_JSON)
                .bodyValue(productIds)
                .retrieve()
                .bodyToMono(Boolean.class)
                .block();
    }



}
