package com.egomogo.scraper.scrap.kakao.proxy

data class ProxyRestaurant(
        val id: String,
        val name: String,
        val kakaoPlaceId: String,
        var menus: ArrayList<ProxyMenu> = ArrayList()
) {
    fun addMenu(menu: ProxyMenu) {
        this.menus.add(menu)
    }
}