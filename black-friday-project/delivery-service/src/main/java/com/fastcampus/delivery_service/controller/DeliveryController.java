package com.fastcampus.delivery_service.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import com.fastcampus.delivery_service.dto.ProcessDeliveryDto;
import com.fastcampus.delivery_service.dto.RegisterAddressDto;
import com.fastcampus.delivery_service.entity.Delivery;
import com.fastcampus.delivery_service.entity.UserAddress;
import com.fastcampus.delivery_service.service.DeliveryService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class DeliveryController {

    private final DeliveryService deliveryService;

    @PostMapping("/delivery/addresses")
    public UserAddress registerAddress(@RequestBody RegisterAddressDto dto) {
        return deliveryService.addUserAddress(dto.userId(), dto.address(), dto.alias());
    }

    // @PostMapping("/delivery/process-delivery")
    // public Delivery processDelivery(@RequestBody ProcessDeliveryDto dto) {
    //     return deliveryService.processDelivery(dto.orderId(), dto.productName(), dto.productCount(), dto.address());
    // }

    @GetMapping("/delivery/deliveries/{deliveryId}")
    public Delivery getDelivery(@PathVariable Long deliveryId) {
        return deliveryService.getDelivery(deliveryId);
    }

    @GetMapping("/delivery/address/{addressId}")
    public UserAddress getAddress(@PathVariable Long addressId) throws Exception {
        return deliveryService.getAddress(addressId);
    }

    @GetMapping("/delivery/users/{userId}/first-address")
    public UserAddress getUserAddress(@PathVariable Long userId) throws Exception {
        return deliveryService.getUserAddress(userId);
    }
}
