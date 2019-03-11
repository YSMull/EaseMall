package com.ysmull.easemall

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory
import org.springframework.boot.web.server.ErrorPage
import org.springframework.boot.web.servlet.ServletComponentScan
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory
import org.springframework.context.annotation.Bean
import org.springframework.http.HttpStatus

/**
 * @author maoyusu
 */

@ServletComponentScan
@SpringBootApplication
class EaseMallApplication {

    @Bean
    fun webServerFactory(): ConfigurableServletWebServerFactory {
        val factory = JettyServletWebServerFactory()
        val error404Page = ErrorPage(HttpStatus.NOT_FOUND, "/")
        factory.setAcceptors(Runtime.getRuntime().availableProcessors())
        factory.addErrorPages(error404Page)
        return factory
    }

    companion object {


        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(EaseMallApplication::class.java, *args)
        }
    }
}
