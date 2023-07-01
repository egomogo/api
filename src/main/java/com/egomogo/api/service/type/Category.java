package com.egomogo.api.service.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    KOREANFOOD("한식"), WESTERNFOOD("양식"), ASIANFOOD("아시안"),
    FLOURBASEDFOOD("분식"), MEAT("고기"), CHINESE("중식"),
    CAFE("카페"), DESSERT("디저트"), CONVENIENCEFOOD("간편식"), JAPANESEFOOD("일식");

    private final String korean;


}
