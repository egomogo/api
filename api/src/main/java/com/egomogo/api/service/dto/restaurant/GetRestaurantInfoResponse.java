package com.egomogo.api.service.dto.restaurant;

import com.egomogo.domain.dto.MenuDto;
import com.egomogo.domain.dto.RestaurantDto;
import com.egomogo.domain.entity.Coordinate;
import com.egomogo.domain.entity.Menu;
import com.egomogo.domain.entity.Restaurant;
import com.egomogo.domain.type.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.util.CollectionUtils;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetRestaurantInfoResponse {

    private String id;
    private String name;
    private String address;
    private CoordinateResponse coords;
    private String kakaoShopId;
    private List<MenuResponse> menus;
    private List<CategoryResponse> categories;

    public static GetRestaurantInfoResponse of(RestaurantDto restaurantDto) {


        return GetRestaurantInfoResponse.builder()
                .id(restaurantDto.getId())
                .name(restaurantDto.getName())
                .address(restaurantDto.getAddress())
                .coords(CoordinateResponse.of(restaurantDto.getX(), restaurantDto.getY()))
                .kakaoShopId(restaurantDto.getKakaoPlaceId())
                .menus(CollectionUtils.isEmpty(restaurantDto.getMenus()) ?
                        null : restaurantDto.getMenus().stream().map(MenuResponse::of).toList())
                .categories(restaurantDto.getCategories().stream().map(CategoryResponse::of).toList())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class MenuResponse {
        private String name;
        private String price;

        public static MenuResponse of(MenuDto menuDto) {
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

        public static CoordinateResponse of(double x, double y) {
            return CoordinateResponse.builder().x(x).y(y).build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class CategoryResponse {

        String categoryName;

        public static CategoryResponse of(CategoryType categoryType) {
            return CategoryResponse.builder().categoryName(categoryType.name()).build();
        }
    }
}
