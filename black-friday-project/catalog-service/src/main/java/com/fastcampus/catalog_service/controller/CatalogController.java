package com.fastcampus.catalog_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.fastcampus.catalog_service.cassandra.entity.Product;
import com.fastcampus.catalog_service.dto.DecreaseStockCountDto;
import com.fastcampus.catalog_service.dto.RegisterProductDto;
import com.fastcampus.catalog_service.service.CatalogService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class CatalogController {

    private final CatalogService catalogService;

    @PostMapping("/catalog/products")
    public Product registerProduct(@RequestBody RegisterProductDto dto) {
        return catalogService.registerProduct(
            dto.sellerId(),
            dto.name(),
            dto.description(),
            dto.price(),
            dto.stockCount(),
            dto.tags()
        );
    }

    @DeleteMapping("/catalog/products/{productId}")
    public void deleteProduct(@PathVariable Long productId) {
        catalogService.deleteProduct(productId);
    }

    @GetMapping("/catalog/products/{productId}")
    public Product getProductById(@PathVariable Long productId) throws Exception {
        return catalogService.getProductById(productId);
    }

    @GetMapping("/catalog/sellers/{sellerId}/products")
    public List<Product> getProductsBySellerId(@PathVariable Long sellerId) throws Exception {
        return catalogService.getProductsBySellerId(sellerId);
    }

    @PostMapping("/catalog/products/{productId}/decreaseStockCount")
    public Product decreaseStockCount(@PathVariable Long productId, @RequestBody DecreaseStockCountDto dto) {
        return catalogService.decreaseStockCount(productId, dto.decreaseCount());
    }
}
