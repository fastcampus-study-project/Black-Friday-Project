package com.fastcampus.order_service.service;

import java.util.List;

import org.springframework.stereotype.Service;

import com.fastcampus.order_service.dto.StartOrderResponseDto;
import com.fastcampus.order_service.entity.ProductOrder;
import com.fastcampus.order_service.enums.OrderStatus;
import com.fastcampus.order_service.feign.CatalogClient;
import com.fastcampus.order_service.feign.DeliveryClient;
import com.fastcampus.order_service.feign.PaymentClient;
import com.fastcampus.order_service.repository.OrderRepository;
import com.fastcampus.order_service.dto.ProcessPaymentDto;
import com.fastcampus.order_service.dto.ProductOrderDetailDto;
import com.fastcampus.order_service.dto.ProcessDeliveryDto;
import com.fastcampus.order_service.dto.DecreaseStockCountDto;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    private final PaymentClient paymentClient;

    private final DeliveryClient deliveryClient;

    private final CatalogClient catalogClient;

    public StartOrderResponseDto startOrder(Long userId, Long productId, Long count) {
        // 1. 상품 정보 조회
        var product = catalogClient.getProduct(productId);

        // 2. 결제 수단 정보 조회
        var paymentMethod = paymentClient.getPaymentMethod(userId);

        // 3. 배송지 정보 조회
        var address = deliveryClient.getUserAddress(userId);

        // 4. 주문 정보 생성
        var order = new ProductOrder(
                userId,
                productId,
                count,
                OrderStatus.INITIATED,
                null,
                null);
        orderRepository.save(order);

        var startOrderDto = new StartOrderResponseDto(order.id, paymentMethod, address);

        return startOrderDto;
    }

    public ProductOrder finishOrder(Long orderId, Long paymentMethodId, Long addressId) {
        var order = orderRepository.findById(orderId).orElseThrow();

        // 1. 상품 정보 조회
        var product = catalogClient.getProduct(order.productId);

        // 2. 결제
        var processPaymentDto = new ProcessPaymentDto(order.id, order.userId,
                Long.parseLong(product.get("price").toString()) * order.count, paymentMethodId);

        var payment = paymentClient.processPayment(processPaymentDto);

        // 3. 배송 요청
        var address = deliveryClient.getAddress(addressId);

        var processDeliveryDto = new ProcessDeliveryDto(order.id, product.get("name").toString(), order.count,
                address.get("address").toString());

        var delivery = deliveryClient.processDelivery(processDeliveryDto);

        // 4. 상품 재고 감소
        var decreaseStockCountDto = new DecreaseStockCountDto(order.count);

        catalogClient.decreaseStockCount(order.productId, decreaseStockCountDto);

        // 5. 주문 정보 업데이트
        order.paymentId = Long.parseLong(payment.get("id").toString());
        order.deliveryId = Long.parseLong(delivery.get("id").toString());
        order.orderStatus = OrderStatus.DELIVERY_REQUESTED;

        return orderRepository.save(order);
    }

    public List<ProductOrder> getUserOrders(Long userId) {
        return orderRepository.findByUserId(userId);
    }

    public ProductOrderDetailDto getOrderDetail(Long orderId) {
        var order = orderRepository.findById(orderId).orElseThrow();

        var paymentRes = paymentClient.getPayment(order.paymentId);
        var deliveryRes = deliveryClient.getDelivery(order.deliveryId);

        var dto = new ProductOrderDetailDto(
                order.id,
                order.userId,
                order.productId,
                order.paymentId,
                order.deliveryId,
                order.orderStatus,
                paymentRes.get("paymentStatus").toString(),
                deliveryRes.get("status").toString());

        return dto;
    }
}
