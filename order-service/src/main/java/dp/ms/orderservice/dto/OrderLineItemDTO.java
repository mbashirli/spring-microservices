package dp.ms.orderservice.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderLineItemDTO {
    @NotNull
    private String productId; // Assuming the product ID is sufficient to identify the product

    @NotNull
    @Min(1)
    private Integer quantity; // Ensure at least one item is ordered
}
