package com.egomogo.scraper.scrap.kakao.proxy

data class ProxyRestaurant(
        val proxyId: String,
        val proxyName: String,
        val kakaoPlaceId: String,
        var menus: ArrayList<ProxyMenu> = ArrayList()
) {
    fun addMenu(menu: ProxyMenu) {
        this.menus.add(menu)
    }
}