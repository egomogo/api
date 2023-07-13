package com.egomogo.scraper.scrap.kakao.proxy

import java.time.LocalDateTime

data class ProxyRestaurant(
        val proxyId: String,
        val proxyName: String,
        val proxyKakaoPlaceId: String,
        var proxyScrapedAt: LocalDateTime,
        var proxyMenus: ArrayList<ProxyMenu> = ArrayList()
) {
    fun addProxyMenu(proxyMenu: ProxyMenu) {
        this.proxyMenus.add(proxyMenu)
    }
}