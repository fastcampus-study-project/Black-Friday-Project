package com.fastcampus.payment_service.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import blackfriday.protobuf.EdaMessage;
import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class EventListener {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final PaymentService paymentService;

    private final KafkaTemplate<String, byte[]> kafkaTemplate;

    @KafkaListener(topics = "payment_request")
    public void consumePaymentRequest(byte[] message) throws Exception {
        var object = EdaMessage.PaymentRequestV1.parseFrom(message);
        logger.info("[payment_request] consumed: {}", object);

        var payment = paymentService.processPayment(
                object.getUserId(),
                object.getOrderId(),
                object.getAmountKRW(),
                object.getPaymentMethodId());

        // ExternalPaymentAdapter를 사용한 외부 연동도 EDA로 수행될 수 있음.
        // 하지만 여기서는 즉각 처리가 되었다고 가정하고 처리.

        var paymentResultMessage = EdaMessage.PaymentResultV1.newBuilder()
                .setOrderId(payment.orderId)
                .setPaymentId(payment.id)
                .setPaymentStatus(payment.paymentStatus.toString())
                .build();

        kafkaTemplate.send("payment_result", paymentResultMessage.toByteArray());
    }
}
