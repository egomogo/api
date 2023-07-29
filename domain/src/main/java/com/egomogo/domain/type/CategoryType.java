package com.egomogo.domain.type;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@RequiredArgsConstructor
public enum CategoryType {
    ROOT,
    KOREAN,
        MEAT, SEA_FOOD, NODDLE, PORRIDGE, KOREAN_STEW, DRIVERS, LUNCH_BOX, RAW_FISH,
    JAPANESE,
        TUNA_SASHIMI, SUSHI, PORK_CUTLET_UDON, RAMEN, SHABU_SHABU,
    ALCOHOL,
        INDOOR_STALLS, HOF_PUB, WINE_BAR, IZAKAYA, COCKTAIL_BAR,
    ASIAN,
        SOUTH_EAST_ASIAN, INDIAN,
    CHINESE,
        LAMB_SKEWERS,
    FAST_FOOD,
        SANDWICH,
    CAFE_DESSERT,
        BAKERY, RICE_CAKE, ICE_CREAM, DONUT, TOAST,
    CHICKEN,
    SCHOOL_FOOD,
    WESTERN_FOOD,
        ITALY, PIZZA, BURGER, STEAK_RIB, MEXICAN,
    SALAD,
    OTHER;

    public static Set<CategoryType> setOf(List<String> categories) {
        return categories.stream()
                .map(CategoryType::of)
                .collect(Collectors.toUnmodifiableSet());
    }

    public static Set<String> nameSetOf(List<String> categories) {
        return categories.stream()
                .map(it -> CategoryType.of(it).name())
                .collect(Collectors.toUnmodifiableSet());
    }

    public static CategoryType of(String category) {
        for (CategoryType categoryType : CategoryType.values()) {
            if (Objects.equals(categoryType.name(), category)) {
                return categoryType;
            }
        }
        throw new IllegalArgumentException("Request wrong category name. Request category is " + category + ".");
    }

}
