package com.egomogo.api.service.dto.restaurant;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

public class SaveRestaurantJson {
    @Data @NoArgsConstructor @AllArgsConstructor @Builder
    public static class Request {
        private String name;
        private String address;
        private String naverShopId;
        private List<SaveMenuRequest> menus;

        @Data @NoArgsConstructor @AllArgsConstructor @Builder
        public static class SaveMenuRequest {
            private String name;
            private String price;
        }
    }
}
