package com.egomogo.scraper.scrap.kakao.proxy

import java.time.LocalDateTime

data class ProxyRestaurant(
        val proxyId: String,
        val proxyName: String,
        val kakaoPlaceId: String,
        var scrapedAt: LocalDateTime,
        var menus: ArrayList<ProxyMenu> = ArrayList()
) {
    fun addMenu(menu: ProxyMenu) {
        this.menus.add(menu)
    }
}