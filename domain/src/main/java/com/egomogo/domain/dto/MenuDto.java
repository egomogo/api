package com.egomogo.domain.dto;

import com.egomogo.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class MenuDto {
    private String id;
    private String name;
    private String price;

    public static MenuDto fromEntity(Menu entity) {
        return MenuDto.builder()
                .id(entity.getId())
                .name(entity.getName())
                .price(entity.getPrice())
                .build();
    }
}
