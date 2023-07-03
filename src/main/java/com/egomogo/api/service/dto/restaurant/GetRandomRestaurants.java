package com.egomogo.api.service.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Slice;

import java.util.List;

public class GetRandomRestaurants {
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private List<RestaurantResponse> documents;
        private SliceMetaResponse meta;

        public static Response fromDto(Slice<IRestaurantDto> slice) {
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

            private static RestaurantResponse fromDto(IRestaurantDto dto) {
                return RestaurantResponse.builder()
                        .id(dto.getId())
                        .name(dto.getName())
                        .address(dto.getAddress())
                        .menus(dto.getMenus().stream().map(RestaurantMenuResponse::fromDto).toList())
                        .coords(RestaurantCoordinateResponse.fromDto(dto))
                        .distance(dto.getDistance())
                        .build();
            }

            @Data @NoArgsConstructor @AllArgsConstructor @Builder
            private static class RestaurantMenuResponse{
                private String name;
                private String price;

                private static RestaurantMenuResponse fromDto(IMenuDto dto) {
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

                private static RestaurantCoordinateResponse fromDto(IRestaurantDto dto) {
                    return RestaurantCoordinateResponse.builder()
                            .x(dto.getX())
                            .y(dto.getY())
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

            private static SliceMetaResponse fromDto(Slice<IRestaurantDto> slice) {
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
