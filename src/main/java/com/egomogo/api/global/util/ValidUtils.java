package com.egomogo.api.global.util;

public class ValidUtils {

    public static boolean isSimilarBetweenText(String x, String y, Double criteriaSimilarity) {
        return similarityBetweenTexts(x, y) > criteriaSimilarity;
    }

    private static double similarityBetweenTexts(String x, String y) {
        double maxLength = Double.max(x.length(), y.length());
        if (maxLength > 0) {
            return (maxLength - org.apache.commons.lang3.StringUtils.getLevenshteinDistance(x, y)) / maxLength;
        }
        return 1.0;
    }

}
