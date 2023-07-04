package com.egomogo.api.service.appservice;

import com.egomogo.api.global.adapter.webclient.KakaoWebClientComponent;
import com.egomogo.api.global.adapter.webclient.dto.CoordinateDto;
import com.egomogo.api.global.exception.impl.BadRequest;
import com.egomogo.api.global.exception.model.ErrorCode;
import com.egomogo.api.global.util.ValidUtils;
import com.egomogo.api.service.dto.restaurant.*;
import com.egomogo.api.service.entity.Menu;
import com.egomogo.api.service.entity.Restaurant;
import com.egomogo.api.service.repository.MenuRepository;
import com.egomogo.api.service.repository.RestaurantRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Slf4j
@Service
@RequiredArgsConstructor
public class RestaurantServiceImpl implements RestaurantService {

    private final RestaurantRepository restaurantRepository;
    private final MenuRepository menuRepository;

    private final KakaoWebClientComponent kakaoWebClientComponent;

    @Override
    @Transactional
    public Integer saveRestaurantsFromJson(List<SaveRestaurantJson.Request> request) {
        validateSaveRestaurantsFromJson(request);

        Set<Restaurant> saved = new HashSet<>();
        request.forEach(it -> {
            CoordinateDto coords = kakaoWebClientComponent.getCoordinateByAddress(it.getAddress());
            Restaurant restaurant = Restaurant.create(
                    it.getName(), it.getAddress(), coords.getX(), coords.getY(), it.getNaverShopId()
            );
            if (!CollectionUtils.isEmpty(it.getMenus())) {
                it.getMenus().forEach(mit ->
                        restaurant.addMenu(Menu.create(mit.getName(), mit.getPrice())));
            }
            saved.add(restaurant);
        });

        return restaurantRepository.saveAll(saved).size();
    }

    @Override
    public Slice<IRestaurantDistanceDto> getRandomRestaurants(Long seed, String category, String userX, String userY, String distanceLimit, Pageable pageable) {
        Slice<IRestaurantDistanceDto> restaurantResults = restaurantRepository.findRestaurantDistanceByRandomAndDistanceLimit(Double.parseDouble(userX), Double.parseDouble(userY), Integer.parseInt(distanceLimit), seed, pageable);
        restaurantResults = restaurantResults.map(it -> {
            IRestaurantDistanceDtoImpl dto = new IRestaurantDistanceDtoImpl(it);
            List<Menu> menus = menuRepository.findTop3ByRestaurantId(dto.getRestaurant_id());
            dto.setMenus(menus.stream().map(MenuDto::fromEntity).toList());
            return dto;
        });
        return restaurantResults;
    }

    private void validateSaveRestaurantsFromJson(List<SaveRestaurantJson.Request> request) {
        request.forEach(it -> {
            if (ValidUtils.hasNotTexts(it.getName(), it.getAddress(), it.getNaverShopId())) {
                throw new BadRequest(ErrorCode.REQUIRED_PARAMETERS);
            }
            if (!CollectionUtils.isEmpty(it.getMenus())) {
                it.getMenus().forEach(m -> {
                    if (ValidUtils.hasNotTexts(m.getName(), m.getPrice())) {
                        throw new BadRequest(ErrorCode.REQUIRED_PARAMETERS);
                    }
                });
            }
        });
    }

}
