package com.egomogo.scraper.scrap.kakao.menu

import com.egomogo.scraper.scrap.kakao.proxy.ProxyRestaurant
import com.egomogo.scraper.scrap.util.FileManager
import com.google.common.reflect.TypeToken
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonParser
import java.io.FileNotFoundException
import java.io.FileReader
import java.io.FileWriter
import java.io.IOException

class KakaoPlaceMenuScraperFileManager : FileManager<ProxyRestaurant> {

    companion object {
        private val ROOT_PATH: String = System.getProperty("user.dir")
    }

    override fun saveStringFile(data: List<String>, fileName: String) {
        try {
            val fw = FileWriter("$ROOT_PATH/$fileName")
            for (str in data) {
                fw.write(str + System.lineSeparator())
            }
            fw.flush()
            fw.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun saveJsonFile(data: List<ProxyRestaurant>, fileName: String) {
        try {
            val fw = FileWriter("$ROOT_PATH/$fileName")
            val gson = GsonBuilder().setPrettyPrinting().create()

            gson.toJson(data, fw)

            fw.flush()
            fw.close()
        } catch (e: IOException) {
            throw RuntimeException(e)
        }
    }

    override fun parseFromJson(filePath: String): List<ProxyRestaurant> {
        return try {
            val reader = FileReader("$ROOT_PATH/$filePath")
            val jsonArray = JsonParser.parseReader(reader).asJsonArray

            val gson = Gson()
            val type = object : TypeToken<List<ProxyRestaurant?>?>() {}.type

            gson.fromJson(jsonArray.toString(), type)

        } catch (e: FileNotFoundException) {
            throw RuntimeException(e)
        }
    }

}