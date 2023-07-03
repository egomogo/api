package com.egomogo.api.service.entity;

import com.egomogo.api.global.util.Generator;
import com.egomogo.api.service.type.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Getter
@Entity(name = "restaurant")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Restaurant {

    @Id
    @Column(name = "id", nullable = false)
    private String id = Generator.generateUUID();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "address", nullable = false)
    private String address;

    @Embedded
    private Coordinate coordinate;

    @Column(name = "naver_shop_id", nullable = false)
    private String naverShopId;

    @OneToMany(mappedBy = "restaurant", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Menu> menus = new ArrayList<>();

    @ElementCollection(targetClass = Category.class)
    @CollectionTable
    @Enumerated(EnumType.STRING)
    private List<Category> categories = new ArrayList<>();

}
