package com.egomogo.scraper.scrap.kakao.menu

import com.egomogo.scraper.scrap.Scraper
import com.egomogo.scraper.scrap.kakao.proxy.ProxyMenu
import com.egomogo.scraper.scrap.kakao.proxy.ProxyRestaurant
import com.egomogo.scraper.scrap.util.Validator
import org.openqa.selenium.By
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Component

@Component
class KakaoPlaceMenuScraper : Scraper<String, ProxyRestaurant> {

    private val log = LoggerFactory.getLogger(KakaoPlaceMenuScraper::class.java)

    companion object {
        val ROOT_PATH: String = System.getProperty("user.dir")
    }

    override fun scrap(data: List<ProxyRestaurant>) : Map<String, ProxyRestaurant> {
        log.info("Start scraping Restaurant. size: ${data.size}")

        System.setProperty("webdriver.chrome.driver", "$ROOT_PATH/scraper/chromedriver/chromedriver.exe")

        val options = ChromeOptions()
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL)
        options.addArguments("--remote-allow-origins=*")
        options.addArguments("--disable-popup-blocking")                //팝업안띄움
        options.addArguments("headless")                                //브라우저 안띄움
        options.addArguments("--blink-settings=imagesEnabled=false")    //이미지 다운 안받음
        options.addArguments("disable-default-apps")

        val driver = ChromeDriver(options)
        driver.switchTo().defaultContent()

        val result = HashMap<String, ProxyRestaurant>()

        for (restaurant in data) {
            driver.get("https://place.map.kakao.com/${restaurant.proxyKakaoPlaceId}")
            sleep(2000)

            val titleOfWeb : String = driver.findElement(By.id("kakaoContent"))
                    .findElement(By.className("tit_location")).text

            if (!isMatchRestaurant(restaurant.proxyName, titleOfWeb)) {
                log.error("Mismatch between Real restaurant name and Scraped restaurant name. " +
                        "Real name: ${restaurant.proxyName}, Scraped name: $titleOfWeb")
                continue
            }

            try {
                val menusOfRestaurant : List<WebElement> = driver.findElements(By.className("info_menu"))
                menusOfRestaurant.forEach{
                    val menuName = it.findElement(By.className("loss_word")).text
                    if (menuName == null || menuName.isBlank()) return@forEach

                    val price: String = try {
                        it.findElement(By.className("price_menu")).text
                    } catch (e : org.openqa.selenium.NoSuchElementException) {
                        "가격정보 없음"
                    }
                    restaurant.addProxyMenu(ProxyMenu(name=menuName, price=price))
                    sleep(100)
                }
                log.info("Success scraped Restaurant. Restaurant Name: ${restaurant.proxyName}, menu size: ${restaurant.proxyMenus.size}.")
            } catch (e : org.openqa.selenium.NoSuchElementException) {
                log.error("Occurred NoSuchElementException during scraped restaurant. name -> ${restaurant.proxyName}")
            }
            result[restaurant.proxyId] = restaurant

            sleep(500)
        }

        sleep(1000)

        try {
            driver.quit()
        } catch (e: Exception) {
            log.error("Error during driver quit...")
        }

        return result
    }

    private fun isMatchRestaurant(expected: String, actual: String) : Boolean {
        if (expected == actual || expected.contains(actual) || actual.contains(expected)) {
            return true
        }
        return Validator.findSimilarity(expected, actual) > 0.5
    }

}