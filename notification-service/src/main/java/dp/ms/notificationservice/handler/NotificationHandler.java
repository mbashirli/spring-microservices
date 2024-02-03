package dp.ms.notificationservice.handler;

import dp.ms.notificationservice.event.OrderPlacedEvent;
import dp.ms.notificationservice.service.NotificationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
@KafkaListener(
        id = "notificationConsumerClient",
        topics = "notification.topic",
        groupId = "notification.topic.consumer",
        containerFactory = "kafkaListenerContainerFactory"
)
public class NotificationHandler {

    private final NotificationService notificationService;

    @KafkaHandler
    public void listen(OrderPlacedEvent orderPlacedEvent){
       log.info("Received order number: {}", orderPlacedEvent.getOrderNumber());

       // todo: send email through NotificationService
    }

}
