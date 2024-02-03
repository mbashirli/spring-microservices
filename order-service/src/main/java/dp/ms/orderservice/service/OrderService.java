package dp.ms.orderservice.service;

import dp.ms.orderservice.client.InventoryClient;
import dp.ms.orderservice.dto.InventoryResponse;
import dp.ms.orderservice.dto.OrderLineItemsDto;
import dp.ms.orderservice.dto.OrderRequest;
import dp.ms.orderservice.event.OrderPlacedEvent;
import dp.ms.orderservice.model.Order;
import dp.ms.orderservice.model.OrderLineItems;
import dp.ms.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public String placeOrder(OrderRequest orderRequest){

        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());

        List<OrderLineItems> orderLineItems = orderRequest.getOrderLineItemsDtoList()
                .stream()
                .map(this::mapToDto)
                .toList();

        order.setOrderLineItemsList(orderLineItems);

        List<String> skuCodes = order.getOrderLineItemsList().stream().map(OrderLineItems::getSkuCode).collect(Collectors.toList());

        // Call Inventory Service, Place order if Item is in Stock
        InventoryResponse[] inventoryResponses = inventoryClient.isInStock(skuCodes);

        boolean allProductsInStock = Arrays.stream(inventoryResponses).allMatch(InventoryResponse::getIsInStock);

        if (Boolean.TRUE.equals(allProductsInStock)){
            orderRepository.save(order);
            kafkaTemplate.send("notification.topic", new OrderPlacedEvent(order.getOrderNumber()));
            return "Order placed successfully";
        }
        else {
            throw new IllegalArgumentException("Product not in stock. Please try again later");
        }
    }

    private OrderLineItems mapToDto(OrderLineItemsDto orderLineItemsDto) {
        return OrderLineItems.builder()
                .price(orderLineItemsDto.getPrice())
                .skuCode(orderLineItemsDto.getSkuCode())
                .quantity(orderLineItemsDto.getQuantity())
                .build();
    }
}
