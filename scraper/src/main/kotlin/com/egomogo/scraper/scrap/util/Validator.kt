package com.egomogo.scraper.scrap.util

import org.apache.commons.text.similarity.LevenshteinDistance

class Validator {
    companion object {
        fun findSimilarity(x: String, y: String) : Double {
            val maxLength = x.length.toDouble().coerceAtLeast(y.length.toDouble())
            return if (maxLength > 0) {
                (maxLength - LevenshteinDistance().apply(x, y)) / maxLength
            } else 1.0
        }
    }
}