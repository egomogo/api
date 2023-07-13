package com.egomogo.api.service.appservice;

import com.egomogo.api.service.dto.restaurant.GetRestaurantInfoResponse;
import com.egomogo.domain.dto.IRestaurantDistanceDto;
import com.egomogo.api.service.dto.restaurant.SaveRestaurantJson;
import com.egomogo.domain.dto.RestaurantDto;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;

import java.util.List;

public interface RestaurantService {

    /**
     * Json 형식의 요청을 통해 매장 정보를 저장하는 메소드.
     * @param request 매장 정보를 저장할 요청 DTO 클래스
     * @return 저장된 매장 정보 수
     */
    Integer saveRestaurantsFromJson(List<SaveRestaurantJson.Request> request);

    /**
     * 랜덤 매장을 반환하는 메소드. <br>
     * <br>
     * - 카테고리가 주어지면 해당 카테고리에 대한 매장에 대해서만 랜덤을 반환한다. <br>
     * - 사용자와 매장 간의 거리가 거리 제한 이하에 위치한 매장만을 반환한다. <br>
     * <br>
     * @param seed 랜덤 씨드 번호
     * @param category 카테고리
     * @param userX 사용자 x 좌표
     * @param userY 사용자 y 좌표
     * @param distanceLimit 필터링할 거리 제한
     * @param pageable 페이징 요청 객체
     * @return 매장 DTO 인터페이스가 담긴 Slice 객체
     */
    Slice<IRestaurantDistanceDto> getRandomRestaurants(Long seed,
                                                       List<String> paramCategories,
                                                       Double userX,
                                                       Double userY,
                                                       Integer distanceLimit,
                                                       Pageable pageable);

    RestaurantDto getRestaurantInfo(String restaurantId);
}
