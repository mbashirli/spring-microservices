package dp.ms.orderservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderDTO {

    @NotNull
    private UUID customerId;

    @NotNull
    private List<OrderLineItemDTO> orderLineItemsDTO;

    @NotBlank
    private String shippingAddress;

    private String orderNote;
}
