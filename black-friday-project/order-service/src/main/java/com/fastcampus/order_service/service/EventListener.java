package com.fastcampus.order_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.fastcampus.order_service.dto.DecreaseStockCountDto;
import com.fastcampus.order_service.enums.OrderStatus;
import com.fastcampus.order_service.feign.CatalogClient;
import com.fastcampus.order_service.repository.OrderRepository;

import blackfriday.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final OrderRepository orderRepository;

    private final CatalogClient catalogClient;

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @KafkaListener(topics = "payment_result")
    public void consumePaymentResult(byte[] message) throws Exception {
        var object = EdaMessage.PaymentResultV1.parseFrom(message);
        logger.info("[payment_result] consumed: {}", object);

        // 결제 정보 업데이트
        var order = orderRepository.findById(object.getOrderId()).orElseThrow();
        order.paymentId = object.getPaymentId();
        order.orderStatus = OrderStatus.DELIVERY_REQUESTED;
        orderRepository.save(order);

        var product = catalogClient.getProduct(order.productId);
        var deliveryRequest = EdaMessage.DeliveryRequestV1.newBuilder()
                .setOrderId(order.id)
                .setProductName(product.get("name").toString())
                .setProductCount(order.count)
                .setAddress(order.deliveryAddress)
                .build();

        kafkaTemplate.send("delivery_request", deliveryRequest.toByteArray());
    }

    @KafkaListener(topics = "delivery_status_update")
    public void consumeDeliveryStatusUpdate(byte[] message) throws Exception {
        var object = EdaMessage.DeliveryStatusUpdateV1.parseFrom(message);
        logger.info("[delivery_status_update] consumed: {}", object);

        if (object.getDeliveryStatus().equals("REQUESTED")) {
            var order = orderRepository.findById(object.getOrderId()).orElseThrow();

            // deliveryId 저장
            order.deliveryId = object.getDeliveryId();
            orderRepository.save(order);

            // 상품 재고 감소
            var decreaseStockCountDto = new DecreaseStockCountDto(order.count);
            catalogClient.decreaseStockCount(order.productId, decreaseStockCountDto);
        }
    }
}
