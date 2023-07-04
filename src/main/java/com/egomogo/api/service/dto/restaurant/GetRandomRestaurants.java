package com.egomogo.api.service.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;
import org.springframework.util.CollectionUtils;

import java.util.List;

public class GetRandomRestaurants {
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private List<RestaurantResponse> documents;
        private SliceMetaResponse meta;

        public static Response fromDto(Slice<IRestaurantDistanceDto> slice) {
            return Response.builder()
                    .documents(slice.getContent().stream().map(RestaurantResponse::fromDto).toList())
                    .meta(SliceMetaResponse.fromDto(slice))
                    .build();
        }

        @Data @NoArgsConstructor @AllArgsConstructor @Builder
        private static class RestaurantResponse {
            private String id;
            private String name;
            private String address;
            private List<RestaurantMenuResponse> menus;
            private RestaurantCoordinateResponse coords;
            private int distance;

            private static RestaurantResponse fromDto(IRestaurantDistanceDto dto) {
                return RestaurantResponse.builder()
                        .id(dto.getRestaurant_id())
                        .name(dto.getRestaurant_name())
                        .address(dto.getAddress())
                        .menus(CollectionUtils.isEmpty(dto.getMenus()) ?
                                null : dto.getMenus().stream().map(RestaurantMenuResponse::fromDto).toList())
                        .coords(RestaurantCoordinateResponse.of(dto.getX(), dto.getY()))
                        .distance(dto.getDistance())
                        .build();
            }

            @Data @NoArgsConstructor @AllArgsConstructor @Builder
            private static class RestaurantMenuResponse{
                private String name;
                private String price;

                private static RestaurantMenuResponse fromDto(MenuDto dto) {
                    return RestaurantMenuResponse.builder()
                            .name(dto.getName())
                            .price(dto.getPrice())
                            .build();
                }
            }

            @Data @NoArgsConstructor @AllArgsConstructor @Builder
            private static class RestaurantCoordinateResponse {
                private double x;
                private double y;

                private static RestaurantCoordinateResponse of(Double x, Double y) {
                    return RestaurantCoordinateResponse.builder()
                            .x(x)
                            .y(y)
                            .build();
                }
            }
        }

        @Data @NoArgsConstructor @AllArgsConstructor @Builder
        private static class SliceMetaResponse {
            private int number;
            private int size;
            private int numberOfElements;
            private Boolean isFirst;
            private Boolean isLast;
            private Boolean hasNext;
            private Boolean hasPrevious;

            private static SliceMetaResponse fromDto(Slice<?> slice) {
                return SliceMetaResponse.builder()
                        .number(slice.getNumber())
                        .size(slice.getSize())
                        .numberOfElements(slice.getNumberOfElements())
                        .isFirst(slice.isFirst())
                        .isLast(slice.isLast())
                        .hasNext(slice.hasNext())
                        .hasPrevious(slice.hasPrevious())
                        .build();
            }
        }
    }
}
