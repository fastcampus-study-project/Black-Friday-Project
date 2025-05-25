package com.fastcampus.delivery_service.dg;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.fastcampus.delivery_service.entity.Delivery;
import com.fastcampus.delivery_service.enums.DeliveryStatus;
import com.fastcampus.delivery_service.repository.DeliveryRepository;

import blackfriday.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class DeliveryStatusUpdater {
    private final DeliveryRepository deliveryRepository;

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @Scheduled(fixedDelay = 10000)
    public void deliveryStatusUpdate() {
        System.out.println("----------- delivery status update ------------");

        var inDeliveryList = deliveryRepository.findAllByStatus(DeliveryStatus.IN_DELIVERY);
        inDeliveryList.forEach(delivery -> {
            delivery.status = DeliveryStatus.COMPLETED;
            deliveryRepository.save(delivery);
            publishStatusChange(delivery);
        });

        var requestedList = deliveryRepository.findAllByStatus(DeliveryStatus.REQUESTED);
        requestedList.forEach(delivery -> {
            delivery.status = DeliveryStatus.IN_DELIVERY;
            deliveryRepository.save(delivery);
            publishStatusChange(delivery);
        });
    }

    private void publishStatusChange(Delivery delivery) {
        // 배송 상태 publish
        var deliveryStatusMessage = EdaMessage.DeliveryStatusUpdateV1.newBuilder()
                .setOrderId(delivery.orderId)
                .setDeliveryId(delivery.id)
                .setDeliveryStatus(delivery.status.toString())
                .build();

        kafkaTemplate.send("delivery_status_update", deliveryStatusMessage.toByteArray());
    }
}
