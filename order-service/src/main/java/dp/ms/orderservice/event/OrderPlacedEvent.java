package dp.ms.orderservice.event;

import dp.ms.orderservice.model.enums.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class OrderPlacedEvent {
    private UUID id;
    private LocalDateTime orderDate;
    private OrderStatus orderStatus;
    private BigDecimal totalPrice;
}
