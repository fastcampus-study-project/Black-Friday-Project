package com.fastcampus.delivery_service.dg;

public interface DeliveryAdapter {
    Long processDelivery(String productName, Long productCount, String address);
}
