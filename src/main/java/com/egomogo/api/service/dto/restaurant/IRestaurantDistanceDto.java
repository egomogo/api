package com.egomogo.api.service.dto.restaurant;

import java.util.List;

public interface IRestaurantDistanceDto {
    String getRestaurant_id();
    String getRestaurant_name();
    String getAddress();
    Double getX();
    Double getY();
    Integer getDistance();
    List<MenuDto> getMenus();
}
