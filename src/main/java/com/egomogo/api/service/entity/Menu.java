package com.egomogo.api.service.entity;

import com.egomogo.api.global.util.Generator;
import com.egomogo.api.service.entity.base.BaseAuditEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Getter
@Entity(name = "menu")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@ToString(exclude = "restaurant")
public class Menu extends BaseAuditEntity {

    @Id
    @Column(name = "id", nullable = false, updatable = false)
    private String id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "price")
    private String price;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "restaurant_id", nullable = false)
    private Restaurant restaurant;

    public static Menu create(String name, String price) {
        return Menu.builder()
                .id(Generator.generateUUID())
                .name(name)
                .price(price)
                .build();
    }

    public void associate(Restaurant restaurant) {
        this.restaurant = restaurant;
    }

}
