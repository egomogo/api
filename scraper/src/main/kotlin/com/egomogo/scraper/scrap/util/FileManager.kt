package com.egomogo.scraper.scrap.util

interface FileManager<T> {

    fun saveStringFile(data: List<String>, fileName: String)

    fun saveJsonFile(data: List<T>, fileName: String)

    fun parseFromJson(filePath: String) : List<T>

}