package com.egomogo.api.service.entity;

import com.egomogo.api.global.util.Generator;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity(name = "menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Menu {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id = Generator.generateUUID();

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private String price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

}
