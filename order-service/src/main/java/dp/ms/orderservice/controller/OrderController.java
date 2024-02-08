package dp.ms.orderservice.controller;

import dp.ms.orderservice.dto.OrderDTO;
import dp.ms.orderservice.event.OrderPlacedEvent;
import dp.ms.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
@Slf4j
public class OrderController {

    private final OrderService orderService;


    @GetMapping("/{orderId}")
    public ResponseEntity<OrderDTO> getOrderInformation(@PathVariable("orderId") UUID orderId) {
        OrderDTO orderDTO = orderService.getOrderInformation(orderId);
        return ResponseEntity.status(HttpStatus.OK).body(orderDTO);
    }

    @PostMapping
    public ResponseEntity<OrderPlacedEvent> placeOrder(@RequestBody OrderDTO orderDTO) {
        OrderPlacedEvent orderPlacedEvent = orderService.placeOrder(orderDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(orderPlacedEvent);
    }

}
