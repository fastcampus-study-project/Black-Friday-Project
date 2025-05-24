package com.fastcampus.catalog_service.dto;

import java.util.List;

public record ProductTagsDto(
    Long productId,
    List<String> tags
) {
    
}
