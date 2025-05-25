package com.fastcampus.search_service.controller;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

// import com.fastcampus.search_service.dto.ProductTagsDto;
import com.fastcampus.search_service.service.SearchService;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class SearchController {
    private final SearchService searchService;

    // @PostMapping("/search/addTagCache")
    // public void addTagCache(@RequestBody ProductTagsDto dto) {
    //     searchService.addTagCache(dto.productId(), dto.tags());
    // }

    // @PostMapping("/search/removeTagCache")
    // public void removeTagCache(@RequestBody ProductTagsDto dto) {
    //     searchService.removeTagCache(dto.productId(), dto.tags());
    // }

    @GetMapping("/search/tags/{tag}/productIds")
    public List<Long> getTagProductIds(@PathVariable String tag) {
        return searchService.getProductIdsByTag(tag);
    }
}
