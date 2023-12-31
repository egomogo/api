package com.egomogo.api.service.dto.restaurant;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class SaveRestaurantJson {
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        @NotBlank
        private String name;
        @NotBlank
        private String address;
        @NotBlank
        private String kakaoPlaceId;
        private List<SaveMenuRequest> menus;

        @Data @NoArgsConstructor @AllArgsConstructor @Builder
        public static class SaveMenuRequest {
            @NotBlank
            private String name;
            @NotBlank
            private String price;
        }
    }

    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Response {
        private int totalSize;
        public static Response of(int size) {
            return new Response(size);
        }
    }
}
