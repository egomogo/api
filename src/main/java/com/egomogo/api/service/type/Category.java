package com.egomogo.api.service.type;

import com.egomogo.api.global.exception.impl.BadRequest;
import com.egomogo.api.global.exception.model.ErrorCode;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum Category {

    KOREAN_FOOD("한식"), WESTERN_FOOD("양식"), ASIAN_FOOD("아시안"),
    FLOUR_BASED_FOOD("분식"), MEAT("고기"), CHINESE_FOOD("중식"),
    CAFE("카페"), DESSERT("디저트"), CONVENIENCE_FOOD("간편식"), JAPANESE_FOOD("일식");

    private final String korean;

    public static Category of(String category) {
        if (category != null && !category.isBlank()) {
            for (Category value : Category.values()) {
                if (value.name().equals(category)) {
                    return value;
                }
            }

        }
        // 유효하지 않은 카테고리로 요청했을 경우
        throw new BadRequest(ErrorCode.INVALID_PARAMETER_FORMAT);
    }


}
