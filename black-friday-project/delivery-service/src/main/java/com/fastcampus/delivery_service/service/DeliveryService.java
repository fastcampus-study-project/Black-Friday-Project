package com.fastcampus.delivery_service.service;

import org.springframework.stereotype.Service;

import com.fastcampus.delivery_service.dg.DeliveryAdapter;
import com.fastcampus.delivery_service.entity.Delivery;
import com.fastcampus.delivery_service.entity.UserAddress;
import com.fastcampus.delivery_service.enums.DeliveryStatus;
import com.fastcampus.delivery_service.repository.DeliveryRepository;
import com.fastcampus.delivery_service.repository.UserAddressRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class DeliveryService {
    
    private final UserAddressRepository userAddressRepository;

    private final DeliveryRepository deliveryRepository;

    private final DeliveryAdapter deliveryAdapter;

    public UserAddress addUserAddress(Long userId, String address, String alias) {
        var userAddress = UserAddress.builder()
            .userId(userId)
            .address(address)
            .alias(alias)
            .build();

        return userAddressRepository.save(userAddress);
    }

    public Delivery processDelivery(
        Long orderId,
        String productName,
        Long productCount,
        String address
    ) {
        var refCode = deliveryAdapter.processDelivery(productName, productCount, address);
        var delivery = Delivery.builder()
            .orderId(orderId)
            .productName(productName)
            .productCount(productCount)
            .address(address)
            .referenceCode(refCode)
            .status(DeliveryStatus.REQUESTED)
            .build();

        return deliveryRepository.save(delivery);
    }

    public Delivery getDelivery(Long deliveryId) {
        return deliveryRepository.findById(deliveryId).orElseThrow();
    }

    public UserAddress getAddress(Long addressId) {
        return userAddressRepository.findById(addressId).orElseThrow();
    }

    public UserAddress getUserAddress(Long userId) {
        return userAddressRepository.findByUserId(userId).stream().findFirst().orElseThrow();
    }
}
