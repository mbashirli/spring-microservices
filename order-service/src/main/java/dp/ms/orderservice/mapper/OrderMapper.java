package dp.ms.orderservice.mapper;

import dp.ms.orderservice.dto.OrderDTO;
import dp.ms.orderservice.event.OrderPlacedEvent;
import dp.ms.orderservice.model.Order;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper
public interface OrderMapper {
    OrderPlacedEvent orderToOrderPlacedEvent(Order order);

    @Mapping(source = "orderLineItems", target = "orderLineItemsDTO")
    OrderDTO orderToOrderDTO(Order order);
}
