package com.egomogo.api.service.appservice;

import com.egomogo.api.global.adapter.webclient.KakaoWebClientComponent;
import com.egomogo.api.global.adapter.webclient.dto.CoordinateDto;
import com.egomogo.api.global.exception.impl.BadRequest;
import com.egomogo.api.global.exception.impl.NotFound;
import com.egomogo.api.global.exception.model.ErrorCode;
import com.egomogo.api.global.util.ValidUtils;
import com.egomogo.api.service.dto.restaurant.GetRestaurantInfoResponse;
import com.egomogo.api.service.dto.restaurant.SaveRestaurantJson;
import com.egomogo.domain.dto.IRestaurantDistanceDto;
import com.egomogo.domain.dto.IRestaurantDistanceDtoImpl;
import com.egomogo.domain.dto.MenuDto;
import com.egomogo.domain.dto.RestaurantDto;
import com.egomogo.domain.entity.Menu;
import com.egomogo.domain.entity.Restaurant;
import com.egomogo.domain.repository.MenuRepository;
import com.egomogo.domain.repository.RestaurantRepository;
import com.egomogo.domain.type.CategoryType;
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
            // 저장할 매장 주소를 이용하여 KAKAO API 호출하여 좌표 설정
            CoordinateDto coords = kakaoWebClientComponent.getCoordinateByAddress(it.getAddress());
            // 매장 인스턴스 생성
            Restaurant restaurant = Restaurant.create(
                    it.getName(), it.getAddress(), coords.getX(), coords.getY(), it.getKakaoPlaceId());
            // 메뉴가 존재할 경우, 메뉴 인스턴스를 생성하여 해당 매장에 추가
            if (!CollectionUtils.isEmpty(it.getMenus())) {
                it.getMenus().forEach(mit ->
                        restaurant.addMenu(Menu.create(mit.getName(), mit.getPrice())));
            }
            // 생성된 매장을 HashSet 객체에 추가
            saved.add(restaurant);
        });

        // HashSet 에 담긴 매장 정보를 DB에 저장
        return restaurantRepository.saveAll(saved).size();
    }

    @Override
    @Transactional(readOnly = true)
    public Slice<IRestaurantDistanceDto> getRandomRestaurants(Long seed, List<String> paramCategories, Double userX, Double userY, Integer distanceLimit, Pageable pageable) {
        // 요청자와의 거리를 연산한 매장 정보를 조회
        Slice<IRestaurantDistanceDto> restaurantDBResults;
        if (paramCategories == null || paramCategories.isEmpty()) {
            // 모든 카테고리 조회
            restaurantDBResults = restaurantRepository.findByRandomAndDistance(seed, userX, userY, distanceLimit, pageable);
        } else {
            // 특정 카테고리 리스트 조회
            Set<String> categories = CategoryType.nameSetOf(paramCategories);
            restaurantDBResults = restaurantRepository.findByRandomAndDistanceAndCategories(seed, userX, userY, distanceLimit, categories, pageable);
        }

        // Slice 객체의 Content 에 해당하는 데이터를 인터페이스에서 구현체로 변환하여 반환
        return restaurantDBResults.map(it -> {
            // 매장 DTO 인터페이스를 매장 DTO 구현체 클래스로 변환
            IRestaurantDistanceDtoImpl dtoResult = new IRestaurantDistanceDtoImpl(it);
            // 해당 매장에 대한 메뉴 리스트 조회 및 매장 DTO에 메뉴 리스트 초기화
            List<Menu> menus = menuRepository.findTop3ByRestaurantId(dtoResult.getId());
            return dtoResult.setMenus(menus.stream().map(MenuDto::fromEntity).toList());
        });
    }

    @Override
    public RestaurantDto getRestaurantInfoById(String restaurantId) {

        return RestaurantDto.fromEntity(restaurantRepository.findById(restaurantId).orElseThrow(() -> new NotFound(ErrorCode.NOT_FOUND)));
    }

    private void validateSaveRestaurantsFromJson(List<SaveRestaurantJson.Request> request) {
        request.forEach(it -> {
            if (ValidUtils.hasNotTexts(it.getName(), it.getAddress(), it.getKakaoPlaceId())) {
                // 매장 정보 중 필수 파라미터를 제공하지 않은 경우A
                throw new BadRequest(ErrorCode.NEED_REQUIRED_PARAMETERS);
            }
            if (!CollectionUtils.isEmpty(it.getMenus())) {
                it.getMenus().forEach(m -> {
                    if (ValidUtils.hasNotTexts(m.getName(), m.getPrice())) {
                        // 메뉴 정보 중 필수 파라미터를 제공하지 않은 경우
                        throw new BadRequest(ErrorCode.NEED_REQUIRED_PARAMETERS);
                    }
                });
            }
        });
    }

}
