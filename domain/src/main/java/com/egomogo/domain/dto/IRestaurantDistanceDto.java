package com.egomogo.domain.dto;

import java.util.List;

public interface IRestaurantDistanceDto {
    String getId();
    String getName();
    String getAddress();
    Double getX();
    Double getY();
    Integer getDistance();
    List<MenuDto> getMenus();
}
