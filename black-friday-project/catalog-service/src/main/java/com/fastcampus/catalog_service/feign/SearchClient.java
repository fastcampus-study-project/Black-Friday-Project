package com.fastcampus.catalog_service.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.fastcampus.catalog_service.dto.ProductTagsDto;

@FeignClient(name = "searchClient", url = "http://search-service:8080")
public interface SearchClient {
    @PostMapping(value = "/search/addTagCache")
    void addTagCache(@RequestBody ProductTagsDto dto);

    @PostMapping(value = "/search/removeTagCache")
    void removeTagCache(@RequestBody ProductTagsDto dto);
}
