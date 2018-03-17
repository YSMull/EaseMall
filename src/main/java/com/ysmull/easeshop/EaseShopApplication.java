package com.ysmull.easeshop;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @author maoyusu
 */
@ServletComponentScan
@SpringBootApplication
@EnableTransactionManagement
public class EaseShopApplication {


    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/");
        factory.setAcceptors(Runtime.getRuntime().availableProcessors());
//        factory.setSelectors(100);
        factory.addErrorPages(error404Page);
        return factory;
    }

    public static void main(String[] args) {
        SpringApplication.run(EaseShopApplication.class, args);
    }
}
