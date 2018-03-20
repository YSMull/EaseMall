package com.ysmull.easemall;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.jetty.JettyServletWebServerFactory;
import org.springframework.boot.web.server.ErrorPage;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

/**
 * @author maoyusu
 */

@ServletComponentScan
@SpringBootApplication
public class EaseMallApplication {


    public static void main(String[] args) {
        SpringApplication.run(EaseMallApplication.class, args);
    }

    @Bean
    public ConfigurableServletWebServerFactory webServerFactory() {
        JettyServletWebServerFactory factory = new JettyServletWebServerFactory();
        ErrorPage error404Page = new ErrorPage(HttpStatus.NOT_FOUND, "/");
        factory.setAcceptors(Runtime.getRuntime().availableProcessors());
        factory.addErrorPages(error404Page);
        return factory;
    }
}
