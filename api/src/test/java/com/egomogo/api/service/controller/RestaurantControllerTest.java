package com.egomogo.api.service.controller;

import com.egomogo.api.service.appservice.RestaurantService;
import com.egomogo.domain.dto.RestaurantDto;
import com.egomogo.domain.entity.Coordinate;
import com.egomogo.domain.entity.Menu;
import com.egomogo.domain.entity.Restaurant;
import com.egomogo.domain.type.CategoryType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(controllers = RestaurantController.class)
@AutoConfigureDataJpa
public class RestaurantControllerTest {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    RestaurantController restaurantController;

    @MockBean
    RestaurantService restaurantService;

    @Test
    void getRestaurantInfoByIdTest() throws Exception {
        //given
        Restaurant testRestaurant =
        Restaurant.builder().id("testId").name("name").address("address").coordinate(new Coordinate(10.1, 12.2)).kakaoPlaceId("123")
                .menus(List.of(Menu.create("testMenu", "10000"))).categories(List.of(CategoryType.KOREAN)).build();
        RestaurantDto restaurantDto = RestaurantDto.fromEntity(testRestaurant);
        given(restaurantService.getRestaurantInfoById(anyString())).willReturn(restaurantDto);
        //when

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurants/testId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restaurantDto)))
        //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value("testId"))
                .andExpect(jsonPath("$.name").value("name"))
                .andExpect(jsonPath("$.address").value("address"))
                .andExpect(jsonPath("$.coords.x").value("10.1"))
                .andExpect(jsonPath("$.coords.y").value("12.2"))
                .andExpect(jsonPath("$.kakaoShopId").value("123"))
                .andExpect(jsonPath("$.menus[0].name").value("testMenu"))
                .andExpect(jsonPath("$.menus[0].price").value("10000"))
                .andExpect(jsonPath("$.categories[0]").value("KOREAN"))
                .andDo(print());

    }

    @Test
    void getRestaurantWishesInfoByIdTest() throws Exception {
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


        List<RestaurantDto> restaurantDtoList = mockRestaurants.stream().map(RestaurantDto::fromEntity).toList();
        given(restaurantService.getRestaurantWishesInfoById(anyList())).willReturn(restaurantDtoList);
        //when

        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/restaurants/wishes?ids=test")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(restaurantDtoList)))
                //then
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.restaurants[0].id").value("testId1"))
                .andExpect(jsonPath("$.restaurants[0].name").value("name1"))
                .andExpect(jsonPath("$.restaurants[0].address").value("address1"))
                .andExpect(jsonPath("$.restaurants[0].coords.x").value("10.1"))
                .andExpect(jsonPath("$.restaurants[0].coords.y").value("12.2"))
                .andExpect(jsonPath("$.restaurants[0].kakaoShopId").value("1"))
                .andExpect(jsonPath("$.restaurants[0].menus[0].name").value("testMenu1"))
                .andExpect(jsonPath("$.restaurants[0].menus[0].price").value("10000"))
                .andExpect(jsonPath("$.restaurants[0].categories[0]").value("KOREAN"))
                .andExpect(jsonPath("$.restaurants[1].id").value("testId2"))
                .andExpect(jsonPath("$.restaurants[1].name").value("name2"))
                .andExpect(jsonPath("$.restaurants[1].address").value("address2"))
                .andExpect(jsonPath("$.restaurants[1].coords.x").value("10.1"))
                .andExpect(jsonPath("$.restaurants[1].coords.y").value("12.2"))
                .andExpect(jsonPath("$.restaurants[1].kakaoShopId").value("2"))
                .andExpect(jsonPath("$.restaurants[1].menus[0].name").value("testMenu2"))
                .andExpect(jsonPath("$.restaurants[1].menus[0].price").value("10000"))
                .andExpect(jsonPath("$.restaurants[1].categories[0]").value("KOREAN"))
                .andDo(print());

    }
}
