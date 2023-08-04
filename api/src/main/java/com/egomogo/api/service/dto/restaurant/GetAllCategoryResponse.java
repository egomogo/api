package com.egomogo.api.service.dto.restaurant;

import com.egomogo.domain.type.CategoryTree;
import com.egomogo.domain.type.CategoryType;
import lombok.*;

import java.util.Arrays;
import java.util.List;

public class GetAllCategoryResponse {
    @Data
    @NoArgsConstructor
    @AllArgsConstructor
    @Builder
    public static class Response {
        private List<Category>  nodes;
        private List<int[]> edges;

        public static Response from(CategoryTree categoryTree) {
            return Response.builder()
                .nodes(Arrays.stream(categoryTree.getNodes()).map(Category::from).toList())
                .edges(categoryTree.getEdges())
                .build();
        }
        @Getter
        @NoArgsConstructor
        @AllArgsConstructor
        @Builder
        private static class Category {
            String code;
            String name;

            public static Category from(CategoryType categoryType) {
                return Category.builder().code(categoryType.name()).name(categoryType.getKoreanName()).build();
            }
        }
    }
}
