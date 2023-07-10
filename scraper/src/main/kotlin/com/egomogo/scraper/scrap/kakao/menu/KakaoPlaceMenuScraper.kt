package com.egomogo.scraper.scrap.kakao.menu

import com.egomogo.scraper.scrap.Scraper
import com.egomogo.scraper.scrap.kakao.proxy.ProxyMenu
import com.egomogo.scraper.scrap.kakao.proxy.ProxyRestaurant
import com.egomogo.scraper.scrap.util.Validator
import lombok.RequiredArgsConstructor
import org.openqa.selenium.By
import org.openqa.selenium.PageLoadStrategy
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.ChromeDriver
import org.openqa.selenium.chrome.ChromeOptions
import org.slf4j.LoggerFactory

@RequiredArgsConstructor
class KakaoPlaceMenuScraper : Scraper<ProxyRestaurant> {

    private val log = LoggerFactory.getLogger(KakaoPlaceMenuScraper::class.java)

    companion object {
        val ROOT_PATH: String = System.getProperty("user.dir")
    }

    override fun scrap(data: List<ProxyRestaurant>) : List<ProxyRestaurant> {
        log.info("Start scraping Restaurant. size: ${data.size}")

        System.setProperty("webdriver.chrome.driver", "$ROOT_PATH/chromedriver/chromedriver.exe")

        val options = ChromeOptions()
        options.setPageLoadStrategy(PageLoadStrategy.NORMAL)
        options.addArguments("--remote-allow-origins=*")
        options.addArguments("--disable-popup-blocking");       //팝업안띄움
//        options.addArguments("headless");                       //브라우저 안띄움
//        options.addArguments("--blink-settings=imagesEnabled=false") //이미지 다운 안받음
        options.addArguments("disable-default-apps")

        val driver = ChromeDriver(options)
        driver.switchTo().defaultContent()

        val result: ArrayList<ProxyRestaurant> = ArrayList()

        for (restaurant in data) {
            driver.get("https://place.map.kakao.com/${restaurant.kakaoPlaceId}")
            sleep(2000)

            val titleOfWeb : String = driver.findElement(By.id("kakaoContent"))
                    .findElement(By.className("tit_location")).text

            if (!isMatchRestaurant(restaurant.name, titleOfWeb)) {
                log.error("Mismatch between Real restaurant name and Scraped restaurant name. " +
                        "Real name: ${restaurant.name}, Scraped name: $titleOfWeb")
                continue
            }

            try {
                val menusOfRestaurant : List<WebElement> = driver.findElements(By.className("info_menu"))
                menusOfRestaurant.forEach{
                    val menuName = it.findElement(By.className("loss_word")).text
                    val price = it.findElement(By.className("price_menu")).text
                    restaurant.addMenu(ProxyMenu(name=menuName, price=price))
                    sleep(100)
                }
                result.add(restaurant)
                log.info("Success scraped Restaurant. Restaurant Name: ${restaurant.name}")
            } catch (e : NoSuchElementException) {
                result.add(restaurant)
            }

            sleep(1000)
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