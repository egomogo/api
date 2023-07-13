package com.egomogo.scraper.scrap.kakao.menu

import com.egomogo.domain.util.SeoulDateTime
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

        // 스크래핑 결과를 반환해주기 위한 Map 객체
        val result = HashMap<String, ProxyRestaurant>()

        // 스크래핑 시작
        for (proxyRestaurant in data) {
            // 매장의 카카오 플레이스 사이트로 이동
            driver.get("https://place.map.kakao.com/${proxyRestaurant.proxyKakaoPlaceId}")
            sleep(2000)

            // 해당 사이트의 매장 이름과 데이터의 매장 이름을 비교
            val titleOfWeb : String = driver.findElement(By.id("kakaoContent"))
                    .findElement(By.className("tit_location")).text
            if (!isMatchRestaurant(proxyRestaurant.proxyName, titleOfWeb)) {
                log.error("Mismatch between Real restaurant name and Scraped restaurant name. " +
                        "Real name: ${proxyRestaurant.proxyName}, Scraped name: $titleOfWeb")
                continue
            }

            try {
                // 사이트 내 매장의 메뉴 리스트를 가져옴.
                val menusOfRestaurant : List<WebElement> = driver.findElements(By.className("info_menu"))
                menusOfRestaurant.forEach{
                    // 메뉴 리스트에서 메뉴 이름을 가져옴. 메뉴 이름이 비어있을 경우 무시하고 다음 메뉴 진행
                    val menuName = it.findElement(By.className("loss_word")).text
                    if (menuName == null || menuName.isBlank()) return@forEach

                    // 메뉴 리스트에서 가격 정보 가져옴. 없을 경우 `가격정보 없음`이라고 저장
                    val price: String = try {
                        it.findElement(By.className("price_menu")).text
                    } catch (e : org.openqa.selenium.NoSuchElementException) {
                        "가격정보 없음"
                    }

                    // 해당 매장에 메뉴 정보를 추가함
                    proxyRestaurant.addProxyMenu(ProxyMenu(name=menuName, price=price))
                    sleep(500)
                }
                log.info("Success scraped Restaurant. Restaurant Name: ${proxyRestaurant.proxyName}, menu size: ${proxyRestaurant.proxyMenus.size}.")
            } catch (e : org.openqa.selenium.NoSuchElementException) {
                log.error("Occurred NoSuchElementException during scraped restaurant. name -> ${proxyRestaurant.proxyName}")
            }

            // 매장의 마지막 스크래핑 일시를 현재 시간으로 저장
            proxyRestaurant.proxyScrapedAt = SeoulDateTime.now()

            // 결과 리스트에 스크래핑한 매장 추가
            result[proxyRestaurant.proxyId] = proxyRestaurant

            sleep(500)
        }

        sleep(1000)

        try {
            // 스크래핑 종료
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