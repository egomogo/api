package com.egomogo.api.service.controller;

import com.egomogo.api.service.appservice.RestaurantService;
import com.egomogo.api.service.dto.restaurant.GetRestaurantInfoResponse;
import com.egomogo.domain.dto.RestaurantDto;
import com.egomogo.domain.entity.Coordinate;
import com.egomogo.domain.entity.Menu;
import com.egomogo.domain.entity.Restaurant;
import com.egomogo.domain.repository.RestaurantRepository;
import com.egomogo.domain.type.CategoryType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.AutoConfigureDataJpa;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.List;
import java.util.Optional;

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
                        .content(new ObjectMapper().writeValueAsString(testRestaurant)))
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
}
