package com.egomogo.scraper.scrap

interface Scraper<K, T> {

    fun scrap(data: List<T>) : Map<K, T>

    fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e : InterruptedException) {
            throw RuntimeException(e)
        }
    }

}