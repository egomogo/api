package com.egomogo.api.service.controller;

import com.egomogo.api.service.appservice.RestaurantService;
import com.egomogo.api.service.dto.restaurant.GetRandomRestaurants;
import com.egomogo.api.service.dto.restaurant.SaveRestaurantJson;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RestaurantController {

    private final RestaurantService restaurantService;

    @PostMapping("/restaurants/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Long saveRestaurantsFromJson(@RequestBody SaveRestaurantJson.Request request) {
        return restaurantService.saveRestaurantsFromJson(request);
    }


    @GetMapping("/restaurant/random")
    @ResponseStatus(HttpStatus.OK)
    public GetRandomRestaurants.Response getRandomRestaurants(@RequestParam("seed") Long seed,
                                                              @RequestParam(value = "category", required = false) String category,
                                                              @RequestParam("x") String userX,
                                                              @RequestParam("y") String userY,
                                                              @RequestParam(value = "distance_limit", defaultValue = "10000") String distanceLimit,
                                                              @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return GetRandomRestaurants.Response.fromDto(
                restaurantService.getRandomRestaurants(seed, category, userX, userY, distanceLimit, pageable)
        );
    }
}
