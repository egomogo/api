package com.egomogo.api.service.dto.restaurant;

import com.egomogo.domain.dto.MenuDto;
import com.egomogo.domain.dto.RestaurantDto;
import com.egomogo.domain.entity.Restaurant;
import lombok.*;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class GetRestaurantWishesInfoResponse {

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private List<RestaurantResponse> restaurants;

        public static Response fromDto(List<RestaurantDto> restaurantDtoList) {
            return Response.builder().restaurants(restaurantDtoList.stream().map(RestaurantResponse::fromDto).toList()).build();

        }

        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        private static class RestaurantResponse {
            private String id;
            private String name;
            private String address;
            private CoordinateResponse coords;
            private String kakaoShopId;
            private List<MenuResponse> menus;
            private List<String> categories;

            public static RestaurantResponse fromDto(RestaurantDto restaurantDto) {
                return RestaurantResponse.builder()
                        .id(restaurantDto.getId())
                        .name(restaurantDto.getName())
                        .address(restaurantDto.getAddress())
                        .coords(CoordinateResponse.fromDto(restaurantDto.getX(), restaurantDto.getY()))
                        .kakaoShopId(restaurantDto.getKakaoPlaceId())
                        .menus(CollectionUtils.isEmpty(restaurantDto.getMenus()) ?
                                null : restaurantDto.getMenus().stream().map(MenuResponse::fromDto).toList())
                        .categories(restaurantDto.getCategories().stream().map(Enum::name).toList())
                        .build();
            }
            @Getter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            private static class MenuResponse {
                private String name;
                private String price;

                public static MenuResponse fromDto(MenuDto menuDto) {
                    return MenuResponse.builder().name(menuDto.getName()).price(menuDto.getPrice()).build();
                }
            }

            @Getter
            @NoArgsConstructor
            @AllArgsConstructor
            @Builder
            private static class CoordinateResponse {
                private double x;
                private double y;

                public static CoordinateResponse fromDto(double x, double y) {
                    return CoordinateResponse.builder().x(x).y(y).build();
                }
            }
        }


    }
}
