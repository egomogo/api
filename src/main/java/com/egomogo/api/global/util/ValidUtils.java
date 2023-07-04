package com.egomogo.api.global.util;

import org.springframework.util.StringUtils;

public class ValidUtils {

    public static boolean isSimilarBetweenText(String x, String y, Double criteriaSimilarity) {
        return similarityBetweenTexts(x, y) >= criteriaSimilarity;
    }

    public static double similarityBetweenTexts(String x, String y) {
        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0) {
            return (maxLength - org.apache.commons.lang3.StringUtils.getLevenshteinDistance(x, y)) / maxLength;
        }
        return 1.0;
    }

    public static boolean hasNotTexts(String... args) {
        for (String arg : args) {
            if (!StringUtils.hasText(arg)) {
                return true;
            }
        }
        return false;
    }

}
