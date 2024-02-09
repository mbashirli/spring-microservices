package dp.ms.orderservice.service;

import dp.ms.orderservice.client.InventoryClient;
import dp.ms.orderservice.client.ProductClient;
import dp.ms.orderservice.dto.OrderDTO;
import dp.ms.orderservice.dto.OrderLineItemDTO;
import dp.ms.orderservice.event.OrderPlacedEvent;
import dp.ms.orderservice.exception.OrderCreationException;
import dp.ms.orderservice.exception.OrderNotFoundException;
import dp.ms.orderservice.mapper.OrderMapper;
import dp.ms.orderservice.model.Order;
import dp.ms.orderservice.model.OrderLineItem;
import dp.ms.orderservice.model.enums.OrderStatus;
import dp.ms.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {

    private final OrderMapper orderMapper;
    private final ProductClient productClient;
    private final InventoryClient inventoryClient;
    private final OrderRepository orderRepository;
    private final KafkaTemplate<String, Object> kafkaTemplate;

    public OrderDTO getOrderInformation(UUID orderId) {
        Optional<Order> order = orderRepository.findById(orderId);
        if (order.isPresent()){
            return orderMapper.orderToOrderDTO(order.get());
        }
        throw new OrderNotFoundException("Order not found. Order id: " + orderId);
    }

    @Transactional
    public OrderPlacedEvent placeOrder(OrderDTO orderDTO) {
        Boolean allProductsInStock = checkProductsInStock(orderDTO.getOrderLineItemsDTO());
        if (!allProductsInStock) {
            // If not all products are in stock, throw an exception
            throw new OrderCreationException("Order creation failed: One or more products are not in stock.");
        }

        log.info("All products in stock");

        try {
            Order order = new Order();
            List<OrderLineItem> orderLineItems = orderDTO.getOrderLineItemsDTO()
                    .stream()
                    .map(orderLineItemDTO -> OrderLineItem.builder()
                            .order(order)
                            .productId(orderLineItemDTO.getProductId())
                            .quantity(orderLineItemDTO.getQuantity())
                            .build())
                    .toList();

            order.setTotalPrice(getTotalPrice(orderLineItems));
            order.setOrderLineItems(orderLineItems);
            order.setOrderStatus(OrderStatus.PENDING);
            order.setOrderNote(orderDTO.getOrderNote());
            order.setCustomerId(orderDTO.getCustomerId());
            order.setTransactionId(UUID.randomUUID().toString());
            order.setShippingAddress(orderDTO.getShippingAddress());

            Order placedOrder = orderRepository.save(order);
            orderRepository.flush();

            decrementProductsQuantity(orderDTO.getOrderLineItemsDTO());

            OrderPlacedEvent orderPlacedEvent = orderMapper.orderToOrderPlacedEvent(
                    orderRepository.findById(placedOrder.getId()).get()
            );

            kafkaTemplate.send("notification.topic", orderPlacedEvent);
            log.info("Message sent successfully");

            return orderPlacedEvent;
        } catch (Exception ex) {
            throw new OrderCreationException("Order creation failed: " + ex);
        }
    }

    private BigDecimal getTotalPrice(List<OrderLineItem> orderLineItems){
        AtomicReference<BigDecimal> totalPrice = new AtomicReference<>(BigDecimal.ZERO);

        orderLineItems.forEach(orderLineItem -> {
            // todo: sends a request to the productClient for each orderLineItem, modify so only one request is sent
            BigDecimal price = productClient.getProductPrice(orderLineItem.getProductId()).multiply(BigDecimal.valueOf(orderLineItem.getQuantity()));
            orderLineItem.setPrice(price);
            totalPrice.updateAndGet(v -> v.add(price));
        });

        return totalPrice.get();
    }

    private Map<String, Integer> collectToMap(List<OrderLineItemDTO> orderLineItemsDTO){
        return orderLineItemsDTO.stream()
                .collect(Collectors.toMap(
                        OrderLineItemDTO::getProductId, // Key Mapper
                        OrderLineItemDTO::getQuantity // Value Mapper
                ));
    }

    private Boolean checkProductsInStock(List<OrderLineItemDTO> orderLineItemsDTO) {
        return inventoryClient.isInStock(collectToMap(orderLineItemsDTO));
    }

    private void decrementProductsQuantity(List<OrderLineItemDTO> orderLineItemsDTO){
        inventoryClient.decrementProductsQuantity(collectToMap(orderLineItemsDTO));
    }

}