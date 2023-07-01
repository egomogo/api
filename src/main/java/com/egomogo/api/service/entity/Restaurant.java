package com.egomogo.api.service.entity;

import com.egomogo.api.service.type.Category;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "restaurant")
@Getter
@Entity
public class Restaurant {

    @Id
    private String id = UUID.randomUUID().toString();

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private String address;

    @Embedded
    private Coordinate coordinate;

    @Column(nullable = false)
    private String naverShopId;

    @OneToMany(mappedBy = "restaurant")
    private List<Menu> menus = new ArrayList<>();

    @Enumerated(EnumType.STRING)
    private List<Category> categories = new ArrayList<>();

}
