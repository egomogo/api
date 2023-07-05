package com.egomogo.api.service.appservice;

import com.egomogo.api.global.adapter.webclient.KakaoWebClientComponent;
import com.egomogo.api.service.dto.restaurant.CoordinateDto;
import com.egomogo.api.service.dto.restaurant.SaveRestaurantJson;
import com.egomogo.api.service.entity.Restaurant;
import com.egomogo.api.service.repository.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
class RestaurantServiceImplUnitTest {

    @InjectMocks
    private RestaurantServiceImpl restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private KakaoWebClientComponent kakaoWebClientComponent;

    @Test
    @DisplayName("매장 저장 - JSON 파일")
    void test_saveRestaurantsFromJson_success() {
        // given
        given(kakaoWebClientComponent.getCoordinateByAddress(anyString()))
                .willReturn(new CoordinateDto(1.1, 2.2));
        given(restaurantRepository.saveAll(anyCollection()))
                .willReturn(List.of(
                        Restaurant.create("name", "addr", 1.1, 2.2, "123"),
                        Restaurant.create("name", "addr", 1.1, 2.2, "123"),
                        Restaurant.create("name", "addr", 1.1, 2.2, "123")
                ));
        // when
        Integer result = restaurantService.saveRestaurantsFromJson(List.of(
                SaveRestaurantJson.Request.builder()
                        .name("n").address("a").naverShopId("na")
                        .menus(List.of(
                                SaveRestaurantJson.Request.SaveMenuRequest.builder()
                                        .name("mn").price("mp").build(),
                                SaveRestaurantJson.Request.SaveMenuRequest.builder()
                                        .name("mn").price("mp").build(),
                                SaveRestaurantJson.Request.SaveMenuRequest.builder()
                                        .name("mn").price("mp").build()
                        ))
                        .build()));
        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result);
    }

}