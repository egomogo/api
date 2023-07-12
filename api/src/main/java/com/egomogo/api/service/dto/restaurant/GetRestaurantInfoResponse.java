package com.egomogo.api.service.dto.restaurant;

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

    public static GetRestaurantInfoResponse of(Restaurant restaurant) {


        return GetRestaurantInfoResponse.builder()
                .id(restaurant.getId())
                .name(restaurant.getName())
                .address(restaurant.getAddress())
                .coords(CoordinateResponse.of(restaurant.getCoordinate()))
                .kakaoShopId(restaurant.getKakaoPlaceId())
                .menus(CollectionUtils.isEmpty(restaurant.getMenus()) ?
                        null : restaurant.getMenus().stream().map(MenuResponse::of).toList())
                .categories(restaurant.getCategories().stream().map(CategoryResponse::of).toList())
                .build();
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class MenuResponse {
        private String name;
        private String price;

        public static MenuResponse of(Menu menu) {
            return MenuResponse.builder().name(menu.getName()).price(menu.getPrice()).build();
        }
    }

    @Getter
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    private static class CoordinateResponse {
        private double x;
        private double y;

        public static CoordinateResponse of(Coordinate coordinate) {
            return CoordinateResponse.builder().x(coordinate.getX()).y(coordinate.getY()).build();
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
