package com.egomogo.api.service.controller;

import com.egomogo.api.service.dto.restaurant.GetAllCategoryResponse;
import com.egomogo.api.service.dto.restaurant.GetRestaurantInfoResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class CategoryController {

    @GetMapping("/restaurants/categories")
    @ResponseStatus(HttpStatus.OK)
    public GetAllCategoryResponse.Response getAllCategory(){
        return GetAllCategoryResponse.Response.fromDto();
    }
}
