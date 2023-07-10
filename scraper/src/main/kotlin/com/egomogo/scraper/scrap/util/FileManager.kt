package com.example.scrapertest.scraper.util

import com.example.scrapertest.scraper.kakao.proxy.ProxyRestaurant

interface FileManager<T> {

    fun saveStringFile(data: List<String>, fileName: String)

    fun saveJsonFile(data: List<T>, fileName: String)

    fun parseFromJson(filePath: String) : List<ProxyRestaurant>

}