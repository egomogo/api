package com.egomogo.api.service.controller;

import com.egomogo.api.service.dto.restaurant.GetRandomRestaurants;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1")
@RequiredArgsConstructor
public class RestaurantController {

    @GetMapping("/restaurant/random")
    @ResponseStatus(HttpStatus.OK)
    public GetRandomRestaurants.Response getRandomRestaurants(@RequestParam("seed") Long seed,
                                                              @RequestParam("category") String category,
                                                              @RequestParam("x") String userX,
                                                              @RequestParam("y") String userY,
                                                              @RequestParam("distance_limit") String distanceLimit,
                                                              @PageableDefault(page = 0, size = 10) Pageable pageable) {
        return GetRandomRestaurants.Response.fromDto();
    }
}
