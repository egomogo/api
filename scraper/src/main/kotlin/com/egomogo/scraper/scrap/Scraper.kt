package com.egomogo.scraper.scrap

interface Scraper<T> {

    fun scrap(data: List<T>) : List<T>

    fun sleep(millis: Long) {
        try {
            Thread.sleep(millis)
        } catch (e : InterruptedException) {
            throw RuntimeException(e)
        }
    }

}