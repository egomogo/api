package com.egomogo.domain.type;

import lombok.Getter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class CategoryNode {
    private final CategoryType value;
    private CategoryNode parent;
    private List<CategoryNode> children = new ArrayList<>();

    public CategoryNode(CategoryType category) {
        this.value = category;
    }

    public CategoryNode setChildren(CategoryNode... nodes) {
        this.children = Arrays
            .stream(nodes)
            .peek(node -> node.parent = this)
            .collect(Collectors.toList());
        return this;
    }
}
