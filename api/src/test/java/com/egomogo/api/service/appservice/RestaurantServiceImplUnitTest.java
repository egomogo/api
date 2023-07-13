package com.egomogo.api.service.appservice;

import com.egomogo.api.global.adapter.webclient.KakaoWebClientComponent;
import com.egomogo.api.global.adapter.webclient.dto.CoordinateDto;
import com.egomogo.api.service.dto.restaurant.SaveRestaurantJson;
import com.egomogo.domain.dto.IRestaurantDistanceDto;
import com.egomogo.domain.dto.IRestaurantDistanceDtoImpl;
import com.egomogo.domain.entity.Menu;
import com.egomogo.domain.entity.Restaurant;
import com.egomogo.domain.repository.MenuRepository;
import com.egomogo.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.SliceImpl;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceImplUnitTest {

    @InjectMocks
    private RestaurantServiceImpl restaurantService;
    @Mock
    private RestaurantRepository restaurantRepository;
    @Mock
    private MenuRepository menuRepository;
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
                        .name("n").address("a").kakaoPlaceId("na")
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

    @Test
    @DisplayName("랜덤 매장 조회 - 전체 카테고리 조회")
    void test_getRandomRestaurants_allCategories() {
        // given
        given(restaurantRepository.findByRandomAndDistance(anyLong(), anyDouble(), anyDouble(), anyInt(), any()))
                .willReturn(new SliceImpl<>(List.of(
                                IRestaurantDistanceDtoImpl.builder()
                                        .id("rid-1")
                                        .name("restaurant-1")
                                        .address("address-1")
                                        .x(127.123132)
                                        .y(37.123123)
                                        .distance(500).build(),
                                IRestaurantDistanceDtoImpl.builder()
                                        .id("rid-2")
                                        .name("restaurant-2")
                                        .address("address-2")
                                        .x(127.123132)
                                        .y(37.123123)
                                        .distance(500).build(),
                                IRestaurantDistanceDtoImpl.builder()
                                        .id("rid-3")
                                        .name("restaurant-3")
                                        .address("address-3")
                                        .x(127.123132)
                                        .y(37.123123)
                                        .distance(500).build())));
        given(menuRepository.findTop3ByRestaurantId(anyString()))
                .willReturn(List.of(
                        Menu.builder()
                                .id("mid-1")
                                .name("menu-1")
                                .price("price-1").build(),
                        Menu.builder()
                                .id("mid-2")
                                .name("menu-2")
                                .price("price-2").build(),
                        Menu.builder()
                                .id("mid-3")
                                .name("menu-3")
                                .price("price-3").build()));
        // when
        Slice<IRestaurantDistanceDto> result = restaurantService.getRandomRestaurants(
                12345L, List.of(), 123.1231, 37.1232231, 1000, PageRequest.of(0, 10));
        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getContent().size());
        Assertions.assertEquals(3, result.getContent().get(0).getMenus().size());
        Assertions.assertEquals(3, result.getContent().get(1).getMenus().size());
        Assertions.assertEquals(3, result.getContent().get(2).getMenus().size());
    }

    @Test
    @DisplayName("랜덤 매장 조회 - 특정 카테고리 조회")
    void test_getRandomRestaurants_with_categories() {
        // given
        given(restaurantRepository.findByRandomAndDistanceAndCategories(anyLong(), anyDouble(), anyDouble(), anyInt(), anyCollection(), any()))
                .willReturn(new SliceImpl<>(List.of(
                        IRestaurantDistanceDtoImpl.builder()
                                .id("rid-1")
                                .name("restaurant-1")
                                .address("address-1")
                                .x(127.123132)
                                .y(37.123123)
                                .distance(500).build(),
                        IRestaurantDistanceDtoImpl.builder()
                                .id("rid-2")
                                .name("restaurant-2")
                                .address("address-2")
                                .x(127.123132)
                                .y(37.123123)
                                .distance(500).build(),
                        IRestaurantDistanceDtoImpl.builder()
                                .id("rid-3")
                                .name("restaurant-3")
                                .address("address-3")
                                .x(127.123132)
                                .y(37.123123)
                                .distance(500).build())));
        given(menuRepository.findTop3ByRestaurantId(anyString()))
                .willReturn(List.of(
                        Menu.builder()
                                .id("mid-1")
                                .name("menu-1")
                                .price("price-1").build(),
                        Menu.builder()
                                .id("mid-2")
                                .name("menu-2")
                                .price("price-2").build(),
                        Menu.builder()
                                .id("mid-3")
                                .name("menu-3")
                                .price("price-3").build()));
        // when
        Slice<IRestaurantDistanceDto> result = restaurantService.getRandomRestaurants(
                123L, List.of("KOREAN", "MEAT"), 1.1, 1.1, 100, PageRequest.of(0, 10));
        // then
        Assertions.assertNotNull(result);
        Assertions.assertEquals(3, result.getContent().size());
        Assertions.assertEquals(3, result.getContent().get(0).getMenus().size());
        Assertions.assertEquals(3, result.getContent().get(1).getMenus().size());
        Assertions.assertEquals(3, result.getContent().get(2).getMenus().size());
    }

    @Test
    @DisplayName("랜덤 매장 조회 - 실패 - 잘못된 카테고리 요청")
    void test_getRandomRestaurants_with_categories_when_wrong_categories() {
        // given
        // when
        // then
        IllegalArgumentException ex = Assertions.assertThrows(
                IllegalArgumentException.class,
                () -> restaurantService.getRandomRestaurants(
                        123L, List.of("KOREAN", "MEAT", "WRONG"), 1.1, 1.1, 100, null));
        Assertions.assertEquals(
                "Request wrong category name. Request category is WRONG.",
                ex.getMessage()
        );
    }
}
