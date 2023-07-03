package com.egomogo.api.service.dto.restaurant;

import java.util.List;

public interface IRestaurantDto {
    String getId();
    String getName();
    String getAddress();
    Double getX();
    Double getY();
    Integer getDistance();
    List<IMenuDto> getMenus();
}
