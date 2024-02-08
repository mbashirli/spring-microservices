package dp.ms.orderservice.client;

import com.fasterxml.jackson.databind.JsonNode;
import dp.ms.orderservice.dto.ProductPriceDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;

import java.math.BigDecimal;

@Slf4j
@Component
public class ProductClient {

    private final WebClient webClient;
    @Autowired
    public ProductClient(WebClient.Builder webClientBuilder) {
        this.webClient = webClientBuilder
                .baseUrl("http://product-service")
                .build();
    }

    public BigDecimal getProductPrice(String productId){
        ProductPriceDTO productPrice = webClient.get()
                .uri(uriBuilder -> uriBuilder
                        .path("/api/product/{productId}/select-fields")
                        .queryParam("fields", "price")
                        .build(productId))
                .retrieve()
                .bodyToMono(ProductPriceDTO.class)
                .block();
        return productPrice != null ? productPrice.getPrice() : BigDecimal.ZERO;
    }
}
