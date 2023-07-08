package com.egomogo.domain.dto;

import java.util.List;

public class IRestaurantDistanceDtoImpl implements IRestaurantDistanceDto {

    private final String id;
    private final String name;
    private final String address;
    private final Double x;
    private final Double y;
    private final Integer distance;
    private List<MenuDto> menus;

    public IRestaurantDistanceDtoImpl(IRestaurantDistanceDto inf) {
        this.id = inf.getId();
        this.name = inf.getName();
        this.address = inf.getAddress();
        this.x = inf.getX();
        this.y = inf.getY();
        this.distance = inf.getDistance();
    }

    public IRestaurantDistanceDtoImpl setMenus(List<MenuDto> menus) {
        this.menus = menus;
        return this;
    }

    @Override
    public String getId() {
        return id;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getAddress() {
        return address;
    }

    @Override
    public Double getX() {
        return x;
    }

    @Override
    public Double getY() {
        return y;
    }

    @Override
    public Integer getDistance() {
        return distance;
    }

    @Override
    public List<MenuDto> getMenus() {
        return menus;
    }

}
