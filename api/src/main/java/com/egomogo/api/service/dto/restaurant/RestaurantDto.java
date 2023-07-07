package com.egomogo.api.service.dto.restaurant;

import com.egomogo.api.service.entity.Restaurant;
import com.egomogo.api.service.type.CategoryType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class RestaurantDto {
    private String id;
    private String name;
    private String address;
    private double x;
    private double y;
    private String naverShopId;
    private List<MenuDto> menus;
    private List<CategoryType> categories;

    public static RestaurantDto fromEntity(Restaurant entity) {
        return RestaurantDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .address(entity.getAddress())
                .x(entity.getCoordinate().getX())
                .y(entity.getCoordinate().getY())
                .naverShopId(entity.getNaverShopId())
                .menus(entity.getMenus().stream().map(MenuDto::fromEntity).toList())
                .categories(entity.getCategories())
                .build();
    }
}
