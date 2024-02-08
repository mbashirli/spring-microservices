package dp.ms.orderservice.mapper;

import dp.ms.orderservice.dto.OrderDTO;
import dp.ms.orderservice.event.OrderPlacedEvent;
import dp.ms.orderservice.model.Order;
import org.mapstruct.Mapper;

@Mapper
public interface OrderMapper {
    OrderPlacedEvent orderToOrderPlacedEvent(Order order);
    OrderDTO orderToOrderDTO(Order order);
}
