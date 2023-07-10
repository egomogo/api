package com.egomogo.domain.entity;

import com.egomogo.domain.entity.base.BaseAuditEntity;
import com.egomogo.domain.type.CategoryType;
import com.egomogo.domain.util.Generator;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "restaurant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@ToString
public class Restaurant extends BaseAuditEntity {

    @Id
    @Column(name = "id", nullable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Embedded
    private Coordinate coordinate;

    @Column(name = "kakao_place_id", nullable = false)
    private String kakaoPlaceId;

    @OneToMany(mappedBy = "restaurant", fetch = FetchType.LAZY, cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @ElementCollection(targetClass = CategoryType.class)
    @CollectionTable(name = "restaurant_categories")
    @Enumerated(EnumType.STRING)
    private List<CategoryType> categories = new ArrayList<>();

    public static Restaurant create(String name, String address, Double x, Double y, String kakaoPlaceId) {
        return Restaurant.builder()
                .id(Generator.generateUUID())
                .name(name)
                .address(address)
                .coordinate(new Coordinate(x, y))
                .kakaoPlaceId(kakaoPlaceId)
                .menus(new ArrayList<>())
                .categories(new ArrayList<>())
                .build();
    }

    public void addMenu(Menu menu) {
        this.menus.add(menu);
        menu.associate(this);
    }
}
