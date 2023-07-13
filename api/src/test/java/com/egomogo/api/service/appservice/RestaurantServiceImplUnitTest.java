package com.egomogo.api.service.appservice;

import com.egomogo.api.global.adapter.webclient.KakaoWebClientComponent;
import com.egomogo.api.global.adapter.webclient.dto.CoordinateDto;
import com.egomogo.api.service.dto.restaurant.SaveRestaurantJson;
import com.egomogo.domain.dto.RestaurantDto;
import com.egomogo.domain.entity.Menu;
import com.egomogo.domain.entity.Restaurant;
import com.egomogo.domain.repository.RestaurantRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyCollection;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class RestaurantServiceImplUnitTest {

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
    void getRestaurantInfoTest() {
        //given
        Restaurant testRestaurant = Restaurant.create("name", "address", 10.1, 12.2, "123");
        testRestaurant.addMenu(Menu.create("testMenu", "10000"));
        given(restaurantRepository.findById(anyString())).willReturn( Optional.of(testRestaurant));

        //when
        RestaurantDto result = restaurantService.getRestaurantInfo("test");

        //then
        Assertions.assertNotNull(result);
        Assertions.assertEquals("name", result.getName());
        Assertions.assertEquals("address",result.getAddress());
        Assertions.assertEquals(10.1,result.getX());
        Assertions.assertEquals(12.2,result.getY());
        Assertions.assertEquals("123",result.getKakaoPlaceId());

        Assertions.assertEquals("testMenu",result.getMenus().get(0).getName());
        Assertions.assertEquals("10000",result.getMenus().get(0).getPrice());

    }


}
