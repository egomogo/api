package com.egomogo.scraper.shell.appservice

import com.egomogo.domain.entity.Restaurant
import com.egomogo.domain.repository.RestaurantRepository
import com.egomogo.domain.util.SeoulDateTime
import com.egomogo.scraper.scrap.kakao.menu.KakaoPlaceMenuScraper
import com.egomogo.scraper.scrap.kakao.proxy.ProxyMenu
import com.egomogo.scraper.scrap.kakao.proxy.ProxyRestaurant
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.BDDMockito.*
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.jupiter.MockitoExtension

@ExtendWith(MockitoExtension::class)
class ScarperServiceImplUnitTest {
    @InjectMocks
    private lateinit var scraperService: ScarperServiceImpl
    @Mock
    private lateinit var restaurantRepository: RestaurantRepository
    @Mock
    private lateinit var kakaoPlaceMenuScraper: KakaoPlaceMenuScraper

    @Test
    @DisplayName("스크래핑 및 스크래핑 결과 저장 테스트")
    fun test_saveScarpedMenuResult() {
        // given
        val restaurants = listOf(
                Restaurant.builder()
                        .id("rid-1")
                        .name("nm-1")
                        .kakaoPlaceId("kid-1")
                        .menus(arrayListOf()).build(),
                Restaurant.builder()
                        .id("rid-2")
                        .name("nm-2")
                        .kakaoPlaceId("kid-2")
                        .menus(arrayListOf()).build(),
                Restaurant.builder()
                        .id("rid-3")
                        .name("nm-3")
                        .kakaoPlaceId("kid-3")
                        .menus(arrayListOf()).build())
        given(restaurantRepository.findByMenusIsNull())
                .willReturn(restaurants)
        val map = HashMap<String, ProxyRestaurant>()
        map["rid-1"] = ProxyRestaurant(
                proxyId = "rid-1",
                proxyName = "nm-1",
                proxyKakaoPlaceId = "kid-1",
                proxyScrapedAt = SeoulDateTime.now(),
                proxyMenus = arrayListOf(
                        ProxyMenu(name = "mnm-1-1", price = "mpr-1-1"),
                        ProxyMenu(name = "mnm-1-2", price = "mpr-1-2"),
                        ProxyMenu(name = "mnm-1-3", price = "mpr-1-3")))
        map["rid-2"] = ProxyRestaurant(
                proxyId = "rid-2",
                proxyName = "nm-2",
                proxyKakaoPlaceId = "kid-2",
                proxyScrapedAt = SeoulDateTime.now(),
                proxyMenus = arrayListOf(
                        ProxyMenu(name = "mnm-2-1", price = "mpr-2-1"),
                        ProxyMenu(name = "mnm-2-2", price = "mpr-2-2"),
                        ProxyMenu(name = "mnm-2-3", price = "mpr-2-3")))
        map["rid-3"] = ProxyRestaurant(
                proxyId = "rid-3",
                proxyName = "nm-3",
                proxyKakaoPlaceId = "kid-3",
                proxyScrapedAt = SeoulDateTime.now(),
                proxyMenus = arrayListOf(
                        ProxyMenu(name = "mnm-3-1", price = "mpr-3-1"),
                        ProxyMenu(name = "mnm-3-2", price = "mpr-3-2"),
                        ProxyMenu(name = "mnm-3-3", price = "mpr-3-3")))
        given(kakaoPlaceMenuScraper.scrap(anyList()))
                .willReturn(map)
        given(restaurantRepository.findAllById(anySet()))
                .willReturn(restaurants)
        // when
        val result: Int = scraperService.saveScarpedMenuResult()
        // then
        assertEquals(3, result)
    }
}