package com.egomogo.api.service.controller;

import com.egomogo.api.service.appservice.RestaurantService;
import com.egomogo.domain.dto.RestaurantDto;
import com.egomogo.domain.entity.Coordinate;
import com.egomogo.domain.entity.Menu;
import com.egomogo.domain.entity.Restaurant;
import com.egomogo.domain.repository.RestaurantRepository;
import com.egomogo.domain.type.CategoryType;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(controllers = RestaurantController.class)
public class RestaurantControllerImpl {

    @Autowired
    MockMvc mockMvc;

    @InjectMocks
    RestaurantController restaurantController;

    @MockBean
    RestaurantService restaurantService;

    @Mock
    private RestaurantRepository restaurantRepository;

    @Test
    void getRestaurantInfoByIdTest() throws Exception {
        //given
        Restaurant testRestaurant =
        Restaurant.builder().id("testId").name("name").address("address").coordinate(new Coordinate(10.1, 12.2)).kakaoPlaceId("123")
                .menus(List.of(Menu.create("testMenu", "10000"))).categories(List.of(CategoryType.KOREAN)).build();
        given(restaurantRepository.findById(anyString())).willReturn( Optional.of(testRestaurant));

        //when
        //RestaurantDto result = restaurantService.getRestaurantInfoById("test");

        mockMvc.perform(get("/restaurants/testId")
                        .header("testId")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(testRestaurant)))
                //then
                .andExpect(status().isOk());


        //then
        

    }
}
