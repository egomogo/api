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
import org.springframework.util.CollectionUtils
import java.util.concurrent.atomic.AtomicInteger

@Service
class ScarperServiceImpl(
        private val restaurantRepository: RestaurantRepository,
        private val kakaoPlaceMenuScraper: KakaoPlaceMenuScraper
) : ScarperService {

    private val log = LoggerFactory.getLogger(ScarperServiceImpl::class.java)

    @Transactional
    override fun saveScarpedMenuResult() : Int {
        // Fetch Restaurants that doesn't have any menu
        val proxyRestaurants : List<ProxyRestaurant> = fetchRestaurantsNullMenus()
        if (CollectionUtils.isEmpty(proxyRestaurants)) return 0

        // Scraping
        val scrapedResult = kakaoPlaceMenuScraper.scrap(proxyRestaurants)

        // Re-fetch Restaurant and Save Menus each restaurant
        return saveMenus(scrapedResult)
    }

    fun fetchRestaurantsNullMenus() : List<ProxyRestaurant> {
        val restaurants : List<Restaurant> = restaurantRepository.findByMenusIsNull()
        log.info("fetch restaurant size: ${restaurants.size}.")
        return restaurants.map { convertEntityToProxy(it) }
    }

    fun saveMenus(scrapedResult: Map<String, ProxyRestaurant>): Int {
        val restaurants = restaurantRepository.findAllById(scrapedResult.keys)
        log.info("start save menus ${restaurants.size} restaurants.")

        val count = AtomicInteger(0)
        restaurants.forEach {
            val proxyRestaurant = scrapedResult[getRestaurantIdFromJavaClass(it)]
            if (getRestaurantKakaoPlaceIdFromJavaClass(it) != proxyRestaurant!!.proxyKakaoPlaceId) {
                // 실제 매장과 스크래핑 매장이 상이한 경우
                val id = getRestaurantIdFromJavaClass(it)
                val kakaoPlaceId = getRestaurantKakaoPlaceIdFromJavaClass(it)
                log.error("Mismatch between DB restaurants kakao id and proxy kakao id. PK id: ${id}. " +
                        "DB kakao ID: ${kakaoPlaceId}, Proxy kakao ID: ${proxyRestaurant.proxyKakaoPlaceId}")
                return@forEach
            }
            proxyRestaurant.proxyMenus.forEach { pMenu -> it.addMenu(convertProxyToEntity(pMenu)) }
            count.getAndIncrement()
        }

        log.info("end save menus ${count.get()} restaurants.")
        return count.get()
    }

    private fun convertEntityToProxy(restaurant: Restaurant) : ProxyRestaurant {
        val id = getRestaurantIdFromJavaClass(restaurant)
        val name = restaurant.javaClass.getDeclaredField("name").let {
            it.isAccessible = true
            return@let it.get(restaurant) as String
        }
        val kakaoPlaceId = getRestaurantKakaoPlaceIdFromJavaClass(restaurant)
        return ProxyRestaurant(proxyId = id, proxyName = name, proxyKakaoPlaceId = kakaoPlaceId)
    }

    private fun convertProxyToEntity(proxyMenu: ProxyMenu) : Menu {
        return Menu.create(proxyMenu.name, proxyMenu.price)
    }

    private fun getRestaurantIdFromJavaClass(restaurant: Restaurant): String {
        return restaurant.javaClass.getDeclaredField("id").let {
            it.isAccessible = true
            return@let it.get(restaurant) as String
        }
    }

    private fun getRestaurantKakaoPlaceIdFromJavaClass(restaurant: Restaurant) : String {
        return restaurant.javaClass.getDeclaredField("kakaoPlaceId").let {
            it.isAccessible = true
            return@let it.get(restaurant) as String
        }
    }

}