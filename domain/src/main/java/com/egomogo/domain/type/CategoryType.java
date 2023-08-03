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
    ROOT("루트"),
    KOREAN("한식"),
        MEAT("고기"), SEA_FOOD("해산물"), NODDLE("국수"), PORRIDGE("죽"), KOREAN_STEW("국,찌개"), DRIVERS("드라이버"), LUNCH_BOX("도시락"), RAW_FISH("회"),
    JAPANESE("일식"),
        TUNA_SASHIMI("참치회"), SUSHI("초밥"), PORK_CUTLET_UDON("돈가스,우동"), RAMEN("라멘"), SHABU_SHABU("샤브샤브"),
    ALCOHOL("술집"),
        INDOOR_STALLS("실내포차"), HOF_PUB("호프"), WINE_BAR("와인바"), IZAKAYA("이자카야"), COCKTAIL_BAR("칵테일바"),
    ASIAN("아시안식"),
        SOUTH_EAST_ASIAN("동남아시안식"), INDIAN("인도식"),
    CHINESE("중식"),
        LAMB_SKEWERS("양꼬치"),
    FAST_FOOD("패스트푸드"),
        SANDWICH("샌드위치"),
    CAFE_DESSERT("카페,디저트"),
        BAKERY("빵집"), RICE_CAKE("케이크"), ICE_CREAM("아이스크림"), DONUT("도넛"), TOAST("토스트"),
    CHICKEN("치킨"),
    SCHOOL_FOOD("학식"),
    WESTERN_FOOD("양식"),
        ITALY("이탈릭"), PIZZA("피자"), BURGER("햄버거"), STEAK_RIB("스테이크,립"), MEXICAN("맥시칸"),
    SALAD("샐러드"),
    OTHER("기타");

    private final String name;

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
