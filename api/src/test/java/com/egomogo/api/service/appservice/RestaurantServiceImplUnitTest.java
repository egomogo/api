package com.egomogo.api.service.appservice;

import com.egomogo.api.global.adapter.webclient.KakaoWebClientComponent;
import com.egomogo.api.global.adapter.webclient.dto.CoordinateDto;
import com.egomogo.api.service.dto.restaurant.SaveRestaurantJson;
import com.egomogo.domain.dto.RestaurantDto;
import com.egomogo.domain.dto.IRestaurantDistanceDto;
import com.egomogo.domain.dto.IRestaurantDistanceDtoImpl;
import com.egomogo.domain.entity.Coordinate;
import com.egomogo.domain.entity.Menu;
import com.egomogo.domain.entity.Restaurant;
import com.egomogo.domain.repository.MenuRepository;
import com.egomogo.domain.repository.RestaurantRepository;
import com.egomogo.domain.type.CategoryType;
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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
    void getRestaurantInfoByIdTest() {
        //given
        Restaurant testRestaurant =
                Restaurant.builder().id("testId").name("name").address("address").coordinate(new Coordinate(10.1, 12.2)).kakaoPlaceId("123")
                        .menus(List.of(Menu.create("testMenu", "10000"))).categories(List.of(CategoryType.KOREAN)).build();
        given(restaurantRepository.findById(anyString())).willReturn( Optional.of(testRestaurant));

        //when
        RestaurantDto result = restaurantService.getRestaurantInfoById("testId");

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("testId", result.getId());
        Assertions.assertEquals("name", result.getName());
        Assertions.assertEquals("address",result.getAddress());
        Assertions.assertEquals(10.1,result.getX());
        Assertions.assertEquals(12.2,result.getY());
        Assertions.assertEquals("123",result.getKakaoPlaceId());

        Assertions.assertEquals("testMenu",result.getMenus().get(0).getName());
        Assertions.assertEquals("10000",result.getMenus().get(0).getPrice());

        Assertions.assertEquals(CategoryType.KOREAN,result.getCategories().get(0));

    }

    @Test
    void getRestaurantWishesInfoByIdTest() {
        //given
        Restaurant testRestaurant1 =
                Restaurant.builder().id("testId1").name("name1").address("address1").coordinate(new Coordinate(10.1, 12.2)).kakaoPlaceId("1")
                        .menus(List.of(Menu.create("testMenu1", "10000"))).categories(List.of(CategoryType.KOREAN)).build();

        Restaurant testRestaurant2 =
                Restaurant.builder().id("testId2").name("name2").address("address2").coordinate(new Coordinate(10.1, 12.2)).kakaoPlaceId("2")
                        .menus(List.of(Menu.create("testMenu2", "10000"))).categories(List.of(CategoryType.KOREAN)).build();

        List<Restaurant> mockRestaurants = new ArrayList<>();
        mockRestaurants.add(testRestaurant1);
        mockRestaurants.add(testRestaurant2);

        given(restaurantRepository.findByIdIn(anyList())).willReturn( mockRestaurants);

        //when
        List<String> testIds = new ArrayList<>();
        testIds.add("testId");
        List<RestaurantDto> result = restaurantService.getRestaurantWishesInfoById(testIds);

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("testId1", result.get(0).getId());
        Assertions.assertEquals("name1", result.get(0).getName());
        Assertions.assertEquals("address1",result.get(0).getAddress());
        Assertions.assertEquals(10.1,result.get(0).getX());
        Assertions.assertEquals(12.2,result.get(0).getY());
        Assertions.assertEquals("1",result.get(0).getKakaoPlaceId());

        Assertions.assertEquals("testMenu1",result.get(0).getMenus().get(0).getName());
        Assertions.assertEquals("10000",result.get(0).getMenus().get(0).getPrice());

        Assertions.assertEquals(CategoryType.KOREAN,result.get(0).getCategories().get(0));

        Assertions.assertEquals("testId2", result.get(1).getId());
        Assertions.assertEquals("name2", result.get(1).getName());
        Assertions.assertEquals("address2",result.get(1).getAddress());
        Assertions.assertEquals(10.1,result.get(1).getX());
        Assertions.assertEquals(12.2,result.get(1).getY());
        Assertions.assertEquals("2",result.get(1).getKakaoPlaceId());

        Assertions.assertEquals("testMenu2",result.get(1).getMenus().get(0).getName());
        Assertions.assertEquals("10000",result.get(1).getMenus().get(0).getPrice());

        Assertions.assertEquals(CategoryType.KOREAN,result.get(1).getCategories().get(0));

    }

    @DisplayName("랜덤 매장 조회 - 전체 카테고리 조회")
    void test_getRandomRestaurants_allCategories() {
        // given
        given(restaurantRepository.findByRandomAndDistance(anyLong(), anyDouble(), anyDouble(), anyInt(), any()))
                .willReturn(new SliceImpl<>(List.of(
                                IRestaurantDistanceDtoImpl.builder()
                                        .id("rid-1")
                                        .name("restaurant-1")
                                        .address("address-1")
                                        .x(1.1)
                                        .y(1.1)
                                        .distance(100).build(),
                                IRestaurantDistanceDtoImpl.builder()
                                        .id("rid-2")
                                        .name("restaurant-2")
                                        .address("address-2")
                                        .x(2.2)
                                        .y(2.2)
                                        .distance(200).build(),
                                IRestaurantDistanceDtoImpl.builder()
                                        .id("rid-3")
                                        .name("restaurant-3")
                                        .address("address-3")
                                        .x(3.3)
                                        .y(3.3)
                                        .distance(300).build())));
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

        Assertions.assertEquals("rid-1", result.getContent().get(0).getId());
        Assertions.assertEquals("restaurant-1", result.getContent().get(0).getName());
        Assertions.assertEquals("address-1", result.getContent().get(0).getAddress());
        Assertions.assertEquals(1.1, result.getContent().get(0).getX());
        Assertions.assertEquals(1.1, result.getContent().get(0).getY());
        Assertions.assertEquals(100, result.getContent().get(0).getDistance());
        Assertions.assertEquals(3, result.getContent().get(0).getMenus().size());

        Assertions.assertEquals("rid-2", result.getContent().get(1).getId());
        Assertions.assertEquals("restaurant-2", result.getContent().get(1).getName());
        Assertions.assertEquals("address-2", result.getContent().get(1).getAddress());
        Assertions.assertEquals(2.2, result.getContent().get(1).getX());
        Assertions.assertEquals(2.2, result.getContent().get(1).getY());
        Assertions.assertEquals(200, result.getContent().get(1).getDistance());
        Assertions.assertEquals(3, result.getContent().get(1).getMenus().size());

        Assertions.assertEquals("rid-3", result.getContent().get(2).getId());
        Assertions.assertEquals("restaurant-3", result.getContent().get(2).getName());
        Assertions.assertEquals("address-3", result.getContent().get(2).getAddress());
        Assertions.assertEquals(3.3, result.getContent().get(2).getX());
        Assertions.assertEquals(3.3, result.getContent().get(2).getY());
        Assertions.assertEquals(300, result.getContent().get(2).getDistance());
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
                                .x(1.1)
                                .y(1.1)
                                .distance(100).build(),
                        IRestaurantDistanceDtoImpl.builder()
                                .id("rid-2")
                                .name("restaurant-2")
                                .address("address-2")
                                .x(2.2)
                                .y(2.2)
                                .distance(200).build(),
                        IRestaurantDistanceDtoImpl.builder()
                                .id("rid-3")
                                .name("restaurant-3")
                                .address("address-3")
                                .x(3.3)
                                .y(3.3)
                                .distance(300).build())));
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

        Assertions.assertEquals("rid-1", result.getContent().get(0).getId());
        Assertions.assertEquals("restaurant-1", result.getContent().get(0).getName());
        Assertions.assertEquals("address-1", result.getContent().get(0).getAddress());
        Assertions.assertEquals(1.1, result.getContent().get(0).getX());
        Assertions.assertEquals(1.1, result.getContent().get(0).getY());
        Assertions.assertEquals(100, result.getContent().get(0).getDistance());
        Assertions.assertEquals(3, result.getContent().get(0).getMenus().size());

        Assertions.assertEquals("rid-2", result.getContent().get(1).getId());
        Assertions.assertEquals("restaurant-2", result.getContent().get(1).getName());
        Assertions.assertEquals("address-2", result.getContent().get(1).getAddress());
        Assertions.assertEquals(2.2, result.getContent().get(1).getX());
        Assertions.assertEquals(2.2, result.getContent().get(1).getY());
        Assertions.assertEquals(200, result.getContent().get(1).getDistance());
        Assertions.assertEquals(3, result.getContent().get(1).getMenus().size());

        Assertions.assertEquals("rid-3", result.getContent().get(2).getId());
        Assertions.assertEquals("restaurant-3", result.getContent().get(2).getName());
        Assertions.assertEquals("address-3", result.getContent().get(2).getAddress());
        Assertions.assertEquals(3.3, result.getContent().get(2).getX());
        Assertions.assertEquals(3.3, result.getContent().get(2).getY());
        Assertions.assertEquals(300, result.getContent().get(2).getDistance());
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
