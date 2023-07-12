package com.egomogo.api.service.controller;

import com.egomogo.api.service.appservice.RestaurantService;
import com.egomogo.api.service.dto.restaurant.GetRandomRestaurants;
import com.egomogo.api.service.dto.restaurant.SaveRestaurantJson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurants/json")
    @ResponseStatus(HttpStatus.CREATED)
    public SaveRestaurantJson.Response saveRestaurantsFromJson(@RequestBody List<SaveRestaurantJson.Request> request) {
        return SaveRestaurantJson.Response.of(
                restaurantService.saveRestaurantsFromJson(request)
        );
    }

    @GetMapping("/restaurants/random")
    @ResponseStatus(HttpStatus.OK)
    public GetRandomRestaurants.Response getRandomRestaurants(@RequestParam("seed") Long seed,
                                                              @RequestParam(value = "category", required = false) List<String> categories,
                                                              @RequestParam("x") Double userX,
                                                              @RequestParam("y") Double userY,
                                                              @RequestParam(value = "distance_limit", defaultValue = "10000") Integer distanceLimit,
                                                              @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return GetRandomRestaurants.Response.fromDto(
                restaurantService.getRandomRestaurants(seed, categories, userX, userY, distanceLimit, pageable)
        );
    }
}
