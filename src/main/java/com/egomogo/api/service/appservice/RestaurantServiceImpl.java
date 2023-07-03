package com.egomogo.api.service.appservice;

import com.egomogo.api.service.dto.restaurant.IRestaurantDto;
import com.egomogo.api.service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;

    @Override
    public Slice<IRestaurantDto> getRandomRestaurants(Long seed, String category, String userX, String userY, String distanceLimit, Pageable pageable) {
        Slice<IRestaurantDto> restaurants = restaurantRepository.findRestaurantDistanceByRandomAndDistanceLimit(Double.parseDouble(userX), Double.parseDouble(userY), Integer.parseInt(distanceLimit), seed);
        System.out.println(restaurants);
        return null;
    }
}
