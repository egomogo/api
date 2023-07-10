package com.egomogo.scraper.shell.appservice

import com.egomogo.domain.entity.Menu
import com.egomogo.domain.entity.Restaurant
import com.egomogo.domain.repository.RestaurantRepository
import com.egomogo.scraper.scrap.kakao.menu.KakaoPlaceMenuScraper
import com.egomogo.scraper.scrap.kakao.proxy.ProxyMenu
import com.egomogo.scraper.scrap.kakao.proxy.ProxyRestaurant
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ScarperServiceImpl(
        private val restaurantRepository: RestaurantRepository,
        private val kakaoPlaceMenuScraper: KakaoPlaceMenuScraper
) : ScarperService {

    private val log = LoggerFactory.getLogger(ScarperServiceImpl::class.java)

    override fun saveScarpedMenuResult() {
        // Fetch Restaurants that doesn't have any menu
        val proxyRestaurants : List<ProxyRestaurant> = fetchRestaurantsNullMenus()

        // Scraping
        val scrapedResult = kakaoPlaceMenuScraper.scrap(proxyRestaurants)

        // Re-fetch Restaurant and Save Menus each restaurant
        saveMenus(scrapedResult)
    }

    @Transactional
    fun saveMenus(scrapedResult: Map<String, ProxyRestaurant>) {
        val restaurants = restaurantRepository.findAllById(scrapedResult.keys)

        restaurants.forEach {
            val proxyRestaurant = scrapedResult[getRestaurantIdFromJavaClass(it)]
            if (getRestaurantKakaoPlaceIdFromJavaClass(it) != proxyRestaurant!!.kakaoPlaceId) {
                // 실제 매장과 스크래핑 매장이 상이한 경우
                val id = getRestaurantIdFromJavaClass(it)
                val kakaoPlaceId = getRestaurantKakaoPlaceIdFromJavaClass(it)
                log.error("Mismatch between DB restaurants kakao id and proxy kakao id. PK id: ${id}. " +
                        "DB kakao ID: ${kakaoPlaceId}, Proxy kakao ID: ${proxyRestaurant.kakaoPlaceId}")
                return@forEach
            }
            proxyRestaurant.menus.forEach {pMenu -> it.addMenu(convertProxyToEntity(pMenu)) }
        }
    }

    @Transactional(readOnly = true)
    fun fetchRestaurantsNullMenus() : List<ProxyRestaurant> {
        val restaurants : List<Restaurant> = restaurantRepository.findByMenusIsNull()
        return restaurants.map { convertEntityToProxy(it) }
    }

    private fun convertEntityToProxy(restaurant: Restaurant) : ProxyRestaurant {
        val id = getRestaurantIdFromJavaClass(restaurant)
        val name = restaurant.javaClass.getDeclaredField("name").let {
            it.isAccessible = true
            return@let it.get(this) as String
        }
        val kakaoPlaceId = getRestaurantKakaoPlaceIdFromJavaClass(restaurant)
        return ProxyRestaurant(proxyId = id, proxyName = name, kakaoPlaceId = kakaoPlaceId)
    }

    private fun convertProxyToEntity(proxyMenu: ProxyMenu) : Menu {
        return Menu.create(proxyMenu.name, proxyMenu.price)
    }

    private fun getRestaurantIdFromJavaClass(restaurant: Restaurant): String {
        return restaurant.javaClass.getDeclaredField("id").let {
            it.isAccessible = true
            return@let it.get(this) as String
        }
    }

    private fun getRestaurantKakaoPlaceIdFromJavaClass(restaurant: Restaurant) : String {
        return restaurant.javaClass.getDeclaredField("kakaoPlaceId").let {
            it.isAccessible = true
            return@let it.get(this) as String
        }
    }

}