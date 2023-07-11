package com.egomogo.scraper

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories

@SpringBootApplication
@EntityScan(basePackages = ["com.egomogo.domain"])
@ComponentScan(basePackages = ["com.egomogo"])
class ScraperApplication

fun main(args: Array<String>) {
    runApplication<ScraperApplication>(*args)
}
