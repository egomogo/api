package com.egomogo.api.service.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    KOREAN_FOOD("한식"), WESTERN_FOOD("양식"), ASIAN_FOOD("아시안"),
    FLOUR_BASED_FOOD("분식"), MEAT("고기"), CHINESE_FOOD("중식"),
    CAFE("카페"), DESSERT("디저트"), CONVENIENCE_FOOD("간편식"), JAPANESE_FOOD("일식");

    private final String korean;


}
