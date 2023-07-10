package com.egomogo.scraper.shell.command

import com.egomogo.scraper.shell.appservice.ScarperService
import org.springframework.shell.standard.ShellComponent
import org.springframework.shell.standard.ShellMethod

@ShellComponent
class ScraperCommand(
        private val scraperService: ScarperService
) {

    @ShellMethod(key = ["scrap"], value = "start scarping menu of restaurants doesn't have menus")
    fun startScrap() : String {
        val scrapedCount = scraperService.saveScarpedMenuResult()
        return "Success Scraped Result Count: $scrapedCount"
    }

}