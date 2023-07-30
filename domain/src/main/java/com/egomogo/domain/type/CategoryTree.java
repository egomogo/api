package com.egomogo.domain.type;

import lombok.Getter;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
@Getter
public class CategoryTree {
    private final CategoryNode root;
    private final CategoryType[] nodes;
    private final List<int[]> edges;

    public CategoryTree() {
        root = new CategoryNode(CategoryType.ROOT);
        nodes = CategoryType.values().clone();
        edges = new ArrayList<>();
        initTree();
        bfs();
    }

    private void bfs() {
        Queue<CategoryNode> q = new LinkedList<>();
        q.offer(root);
        while (!q.isEmpty()) {
            CategoryNode parent = q.poll();
            for (CategoryNode child : parent.getChildren()) {
                q.offer(child);
                edges.add(new int[]{parent.getValue().ordinal(), child.getValue().ordinal()});
            }
        }
    }

    private void initTree() {
        root
            .setChildren(
                new CategoryNode(CategoryType.KOREAN)
                    .setChildren(
                        new CategoryNode(CategoryType.MEAT),
                        new CategoryNode(CategoryType.SEA_FOOD),
                        new CategoryNode(CategoryType.NODDLE),
                        new CategoryNode(CategoryType.PORRIDGE),
                        new CategoryNode(CategoryType.KOREAN_STEW),
                        new CategoryNode(CategoryType.DRIVERS),
                        new CategoryNode(CategoryType.LUNCH_BOX)
                    ),
                new CategoryNode(CategoryType.JAPANESE)
                    .setChildren(
                        new CategoryNode(CategoryType.TUNA_SASHIMI),
                        new CategoryNode(CategoryType.SUSHI),
                        new CategoryNode(CategoryType.PORK_CUTLET_UDON),
                        new CategoryNode(CategoryType.RAMEN),
                        new CategoryNode(CategoryType.SHABU_SHABU)
                    ),
                new CategoryNode(CategoryType.ALCOHOL)
                    .setChildren(
                        new CategoryNode(CategoryType.INDOOR_STALLS),
                        new CategoryNode(CategoryType.HOF_PUB),
                        new CategoryNode(CategoryType.WINE_BAR),
                        new CategoryNode(CategoryType.IZAKAYA),
                        new CategoryNode(CategoryType.COCKTAIL_BAR)
                    ),
                new CategoryNode(CategoryType.ASIAN)
                    .setChildren(
                        new CategoryNode(CategoryType.SOUTH_EAST_ASIAN),
                        new CategoryNode(CategoryType.INDIAN)
                    ),
                new CategoryNode(CategoryType.CHINESE)
                    .setChildren(
                        new CategoryNode(CategoryType.LAMB_SKEWERS)
                    ),
                new CategoryNode(CategoryType.FAST_FOOD)
                    .setChildren(
                        new CategoryNode(CategoryType.SANDWICH)
                    ),
                new CategoryNode(CategoryType.CAFE_DESSERT)
                    .setChildren(
                        new CategoryNode(CategoryType.BAKERY),
                        new CategoryNode(CategoryType.RICE_CAKE),
                        new CategoryNode(CategoryType.ICE_CREAM),
                        new CategoryNode(CategoryType.DONUT),
                        new CategoryNode(CategoryType.TOAST)
                    ),
                new CategoryNode(CategoryType.CHICKEN),
                new CategoryNode(CategoryType.SCHOOL_FOOD),
                new CategoryNode(CategoryType.WESTERN_FOOD)
                    .setChildren(
                        new CategoryNode(CategoryType.ITALY),
                        new CategoryNode(CategoryType.PIZZA),
                        new CategoryNode(CategoryType.BURGER),
                        new CategoryNode(CategoryType.STEAK_RIB),
                        new CategoryNode(CategoryType.MEXICAN)
                    ),
                new CategoryNode(CategoryType.SALAD),
                new CategoryNode(CategoryType.OTHER)
            )
        ;
    }
}
